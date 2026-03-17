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

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = TABS.register("example_tab", () -> CreativeModeTab.builder()
			.title(Component.translatable("itemGroup.blight_star")) //The language key for the title of your CreativeModeTab
			.withTabsBefore(CreativeModeTabs.COMBAT)
			.icon(() -> ModItems.ROT_BERRY.get().getDefaultInstance())
			.displayItems((parameters, output) -> {
				output.accept(ModItems.ROT_BERRY);
				output.accept(ModItems.ROT_BLOCK);
			}).build());
}
