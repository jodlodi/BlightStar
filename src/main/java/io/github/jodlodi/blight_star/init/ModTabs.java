package io.github.jodlodi.blight_star.init;

import io.github.jodlodi.blight_star.BlightStar;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ModTabs {
	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BlightStar.ID);

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BLIGHT_STAR = TABS.register("blight_star", () -> CreativeModeTab.builder()
			.title(Component.translatable("itemGroup.blight_star"))
			.withTabsBefore(CreativeModeTabs.COMBAT)
			.icon(() -> ModItems.ROT_BERRY.get().getDefaultInstance())
			.displayItems((parameters, output) -> {
				output.accept(ModItems.ROT_BLOCK);
				output.accept(ModItems.LUMINESAND);
				output.accept(ModItems.GLITTER_BUD);
				output.accept(ModItems.ROT_BERRY);
			}).build());
}
