package io.github.jodlodi.blight_star.block;

import com.mojang.serialization.MapCodec;
import io.github.jodlodi.blight_star.block.util.AutoBlockColor;
import io.github.jodlodi.blight_star.block.util.BlightVessel;
import io.github.jodlodi.blight_star.block.util.Blighted;
import io.github.jodlodi.blight_star.client.ColorHandler;
import io.github.jodlodi.blight_star.init.ModBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class LumineSandBlock extends Block implements BlightVessel<LumineSandBlock>, AutoBlockColor {
	public static final MapCodec<LumineSandBlock> CODEC = simpleCodec(LumineSandBlock::new);
	public static final VesselBlightProperty BLIGHT = new VesselBlightProperty("blight", 5);

	public LumineSandBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(BLIGHT, 0));
	}

	@Override
	public BlockColor getColor() {
		return ColorHandler.LUMINESAND;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BLIGHT);
	}

	@Override
	public MapCodec<LumineSandBlock> codec() {
		return CODEC;
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
		super.neighborChanged(state, level, pos, block, fromPos, isMoving);
		int blight = this.getBlight(state);
		if (blight == 0) return;

		int highestNeighbour = Integer.MIN_VALUE;
		int lowestNeighbour = blight;

		for (Direction direction : Direction.values()) {
			BlockPos relative = pos.relative(direction);
			BlockState relativeState = level.getBlockState(relative);

			if (relativeState.getBlock() instanceof Blighted blighted) {
				int relativeBlight = blighted.getBlight(relativeState);
				if (highestNeighbour < relativeBlight) highestNeighbour = relativeBlight;
				if (blighted.spreadThin(relative, lowestNeighbour) > relativeBlight) lowestNeighbour = relativeBlight;
			} else if (relativeState.isSolidRender(level, relative)) {
				if (highestNeighbour < 0) highestNeighbour = 0;
				if (this.spreadThin(relative, lowestNeighbour) > 0) lowestNeighbour = 0;
			}
		}

		if (highestNeighbour <= blight) {
			int newBlight = Math.max(highestNeighbour - 1, 0);

			if (this.isDormant(state) && newBlight > lowestNeighbour) {
				level.setBlock(pos, this.setTo(state, pos, newBlight), Block.UPDATE_ALL);
			} else {
				level.setBlock(pos, this.lieDormant(this.setTo(state, pos, newBlight)), Block.UPDATE_ALL);
			}
		} else if (this.isDormant(state) && blight > lowestNeighbour) {
			level.setBlock(pos, this.awaken(state), Block.UPDATE_ALL);
		}
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!level.isAreaLoaded(pos, 3)) return;
		int blight = state.getValue(this.getBlightProperty());

		for (Direction direction : Direction.allShuffled(random)) {
			BlockPos relative = pos.relative(direction);
			BlockState relativeState = level.getBlockState(relative);

			if (relativeState.getBlock() instanceof Blighted blighted) {
				int nowBlight = blighted.getBlight(relativeState);
				int spread = blighted.spreadThin(relative, blight);
				if (nowBlight < spread) {
					level.setBlock(relative, blighted.setTo(relativeState, relative, spread), Block.UPDATE_ALL);
					return;
				}
			} else if (relativeState.isSolidRender(level, relative)) {
				int spread = ModBlocks.LUMINESAND.get().spreadThin(relative, blight);
				if (spread <= 0) continue;
				level.setBlock(relative, ModBlocks.LUMINESAND.get().setTo(ModBlocks.LUMINESAND.get().defaultBlockState(), relative, spread), Block.UPDATE_ALL);
				return;
			}
		}

		level.setBlock(pos, this.lieDormant(state), Block.UPDATE_ALL);
	}

	@Override
	public int spreadThin(BlockPos pos, int blight) {
		if (RandomSource.create(pos.asLong()).nextInt(4) == 0) blight--;
		return blight - 1;
	}

	@Override
	protected boolean isRandomlyTicking(BlockState state) {
		return super.isRandomlyTicking(state) && this.canSpread(state);
	}

	@Override
	public Blighted.BlightProperty getBlightProperty() {
		return BLIGHT;
	}
}
