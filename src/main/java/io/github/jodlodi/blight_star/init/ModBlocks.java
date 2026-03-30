package io.github.jodlodi.blight_star.init;

import io.github.jodlodi.blight_star.BlightStar;
import io.github.jodlodi.blight_star.block.RotBlock;
import io.github.jodlodi.blight_star.block.LumineSandBlock;
import io.github.jodlodi.blight_star.block.GlitterBudBlock;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ModBlocks {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BlightStar.ID);

	public static final DeferredBlock<RotBlock> ROT_BLOCK = BLOCKS.register("rot_block", () -> new RotBlock(BlockBehaviour.Properties.of()
			.mapColor(MapColor.TERRACOTTA_GREEN)
			.sound(SoundType.MUD)
			.randomTicks()
			.strength(0.5F)
			.speedFactor(0.8F)
			.isValidSpawn(Blocks::always)
			.isRedstoneConductor(ModBlocks::always)
			.isViewBlocking(ModBlocks::always)
			.isSuffocating(ModBlocks::always)
	));

	public static final DeferredBlock<LumineSandBlock> LUMINESAND = BLOCKS.register("luminesand", () -> new LumineSandBlock(BlockBehaviour.Properties.of()
			.mapColor(MapColor.TERRACOTTA_WHITE)
			.sound(SoundType.SAND)
			.randomTicks()
			.strength(0.5F)
			.speedFactor(1.05F)
			.lightLevel(state -> 1)
			.isValidSpawn(Blocks::always)
			.isRedstoneConductor(ModBlocks::always)
			.isViewBlocking(ModBlocks::always)
			.isSuffocating(ModBlocks::always)
	));

	public static final DeferredBlock<GlitterBudBlock> GLITTER_BUD = BLOCKS.register("glitter_bud", () -> new GlitterBudBlock(BlockBehaviour.Properties.of()
			.mapColor(MapColor.SNOW)
			.sound(SoundType.CALCITE)
			.randomTicks()
			.forceSolidOn()
			.requiresCorrectToolForDrops()
			.strength(3.0F, 6.0F)
			.lightLevel(state -> 8)
			.noOcclusion()
	));

	private static boolean always(BlockState state, BlockGetter blockGetter, BlockPos pos) {
		return true;
	}

	private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
		return false;
	}
}
