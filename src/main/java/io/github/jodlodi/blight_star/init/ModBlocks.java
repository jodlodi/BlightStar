package io.github.jodlodi.blight_star.init;

import io.github.jodlodi.blight_star.BlightStar;
import io.github.jodlodi.blight_star.block.RotBlock;
import io.github.jodlodi.blight_star.block.SproutBlock;
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

	public static final DeferredBlock<SproutBlock> SPROUT = BLOCKS.register("sprout", () -> new SproutBlock(BlockBehaviour.Properties.of()
			.mapColor(MapColor.TERRACOTTA_GREEN)
			.sound(SoundType.MUD)
			.randomTicks()
			.forceSolidOn()
			.requiresCorrectToolForDrops()
			.strength(3.0F, 6.0F)
			.speedFactor(0.8F)
			.noOcclusion()
	));

	private static boolean always(BlockState state, BlockGetter blockGetter, BlockPos pos) {
		return true;
	}

	private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
		return false;
	}
}
