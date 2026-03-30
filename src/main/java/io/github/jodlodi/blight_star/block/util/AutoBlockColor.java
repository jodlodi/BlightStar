package io.github.jodlodi.blight_star.block.util;

import io.github.jodlodi.blight_star.BlightStar;
import io.github.jodlodi.blight_star.init.ModBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientBlockExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface AutoBlockColor {
	BlockColor getColor();

	default boolean shouldParticlesTint(BlockState state, ClientLevel level, BlockPos pos) {
		return false;
	}

	default IClientBlockExtensions createClientExtension() {
		return new IClientBlockExtensions() {
			@Override
			public boolean areBreakingParticlesTinted(BlockState state, ClientLevel level, BlockPos pos) {
				return AutoBlockColor.this.shouldParticlesTint(state, level, pos);
			}
		};
	}

	@EventBusSubscriber(modid = BlightStar.ID, value = Dist.CLIENT)
	class EventListener {
		@SubscribeEvent
		public static void registerBlockClientExtensions(RegisterClientExtensionsEvent event) {
			ModBlocks.BLOCKS.getEntries().forEach(holder -> {
				if (holder.get() instanceof AutoBlockColor color && !event.isBlockRegistered(holder.get())) {
					event.registerBlock(color.createClientExtension(), holder);
				}
			});
		}
	}
}
