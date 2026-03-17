package io.github.jodlodi.blight_star.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RotBlock extends Block {
	public static final MapCodec<RotBlock> CODEC = simpleCodec(RotBlock::new);
	protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);

	public RotBlock(Properties properties) {
		super(properties);
	}

	@Override
	public MapCodec<RotBlock> codec() {
		return CODEC;
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter reader, BlockPos pos) {
		return Shapes.block();
	}

	@Override
	protected VoxelShape getVisualShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
		return Shapes.block();
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!level.isAreaLoaded(pos, 3)) return;
		BlockState blockstate = this.defaultBlockState();
		ResourceKey<Biome> target = Biomes.SWAMP;
		Holder<Biome> biome = level.registryAccess().registryOrThrow(Registries.BIOME).getHolderOrThrow(target);

		if (level.getPoiManager().getCountInRange(holder -> holder.is(PoiTypes.LIGHTNING_ROD), pos, 16, PoiManager.Occupancy.ANY) > 0L) {
			for (int i = 0; i < 8; i++) {
				BlockPos offset = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
				if (level.getBlockState(offset).isSolidRender(level, offset)) level.setBlockAndUpdate(offset, blockstate);
				if (level.getBiome(offset).is(target)) continue;

				int x = QuartPos.fromBlock(offset.getX());
				int y = QuartPos.fromBlock(offset.getY());
				int z = QuartPos.fromBlock(offset.getZ());

				LevelChunk chunkAt = level.getChunk(offset.getX() >> 4, offset.getZ() >> 4);
				for (LevelChunkSection section : chunkAt.getSections()) {
					if (section.getBiomes().get(x & 3, y & 3, z & 3).is(target)) continue;
					if (section.getBiomes() instanceof PalettedContainer<Holder<Biome>> container) container.set(x & 3, y & 3, z & 3, biome);
				}

				if (!chunkAt.isUnsaved()) chunkAt.setUnsaved(true);
				level.getChunkSource().chunkMap.resendBiomesForChunks(List.of(chunkAt));
			}
		}
	}

	@Override
	protected boolean isRandomlyTicking(BlockState state) {
		return super.isRandomlyTicking(state);
	}

	@Override
	protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
		return 0.2F;
	}
}
