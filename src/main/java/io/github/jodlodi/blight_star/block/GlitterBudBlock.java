package io.github.jodlodi.blight_star.block;

import com.mojang.serialization.MapCodec;
import io.github.jodlodi.blight_star.block.util.AutoBlockColor;
import io.github.jodlodi.blight_star.block.util.BlightSource;
import io.github.jodlodi.blight_star.block.util.Blighted;
import io.github.jodlodi.blight_star.client.ColorHandler;
import io.github.jodlodi.blight_star.init.ModBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GlitterBudBlock extends Block implements BlightSource<GlitterBudBlock>, AutoBlockColor {
	public static final MapCodec<GlitterBudBlock> CODEC = simpleCodec(GlitterBudBlock::new);
	public static final SourceBlightProperty BLIGHT = new SourceBlightProperty("blight", 8);

	public GlitterBudBlock(Properties properties) {
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
	public MapCodec<GlitterBudBlock> codec() {
		return CODEC;
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!level.isAreaLoaded(pos, 3)) return;

		int blight = this.getBlight(state);

		if (blight < this.maxBlight() && random.nextInt(20) == 0) {
			level.setBlock(pos, state.setValue(this.getBlightProperty(), ++blight), GlitterBudBlock.UPDATE_ALL);
		}

		if (blight <= 1) return;

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

		if (blight == this.maxBlight()) {
			level.setBlock(pos, this.lieDormant(state), GlitterBudBlock.UPDATE_ALL);
		}
	}

	@Override
	protected boolean isRandomlyTicking(BlockState state) {
		return super.isRandomlyTicking(state) && !this.isDormant(state);
	}

	@Override
	public BlightProperty getBlightProperty() {
		return BLIGHT;
	}
}
