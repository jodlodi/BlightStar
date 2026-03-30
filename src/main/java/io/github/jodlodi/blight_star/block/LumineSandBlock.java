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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class LumineSandBlock extends Block implements BlightVessel<LumineSandBlock>, AutoBlockColor {
	public static final MapCodec<LumineSandBlock> CODEC = simpleCodec(LumineSandBlock::new);
	public static final IntegerProperty BLIGHT = Blighted.createProperty(5);

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
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!level.isAreaLoaded(pos, 3)) return;

		int blight = state.getValue(this.getBlightProperty());
		int highestNeighbour = 0;

		for (Direction direction : Direction.values()) {
			BlockPos relative = pos.relative(direction);
			BlockState relativeState = level.getBlockState(relative);

			if (relativeState.getBlock() instanceof Blighted blighted) {
				int relativeBlight = relativeState.getValue(blighted.getBlightProperty());
				if (relativeBlight > highestNeighbour) highestNeighbour = relativeBlight;
				if (relativeBlight > blight) break;
			}
		}

		if (highestNeighbour <= blight) {
			level.setBlock(pos, state.setValue(this.getBlightProperty(), --blight), GlitterBudBlock.UPDATE_ALL);
		}

		int spread = blight - 1;

		if (spread <= 0) return;

		Direction face = Direction.getRandom(random);
		BlockPos relative = pos.relative(face);
		BlockState relativeState = level.getBlockState(relative);

		if (relativeState.getBlock() instanceof Blighted blighted) {
			int nowBlight = relativeState.getValue(blighted.getBlightProperty());
			if (nowBlight < spread) {
				level.setBlock(relative, relativeState.setValue(blighted.getBlightProperty(), spread), Block.UPDATE_ALL);
			}
		} else if (relativeState.isSolidRender(level, relative)) {
			level.setBlock(relative, ModBlocks.LUMINESAND.get().defaultBlockState().setValue(ModBlocks.LUMINESAND.get().getBlightProperty(), spread), Block.UPDATE_ALL);
		}
	}

	@Override
	protected boolean isRandomlyTicking(BlockState state) {
		return super.isRandomlyTicking(state) && state.getValue(BLIGHT) > 0;
	}

	@Override
	protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
		return 0.2F;
	}

	@Override
	public IntegerProperty getBlightProperty() {
		return BLIGHT;
	}
}
