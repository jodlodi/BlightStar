package io.github.jodlodi.blight_star.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SproutBlock extends Block implements SimpleWaterloggedBlock {
	public static final MapCodec<SproutBlock> CODEC = simpleCodec(SproutBlock::new);
	protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 12.0, 12.0);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public SproutBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
	}

	@Override
	public MapCodec<SproutBlock> codec() {
		return CODEC;
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		return this.defaultBlockState().setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		if (state.getValue(WATERLOGGED)) level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}

	@Override
	protected FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!level.isAreaLoaded(pos, 3)) return;
		BlockState below = level.getBlockState(pos.below());
		if (below.getBlock() instanceof BlightBlock blightBlock) {
			int blight = below.getValue(blightBlock.getProperty());
			if (blight < blightBlock.getProperty().max) level.setBlock(pos.below(), below.setValue(blightBlock.getProperty(), blightBlock.getProperty().max), SproutBlock.UPDATE_ALL);
		}
	}
}
