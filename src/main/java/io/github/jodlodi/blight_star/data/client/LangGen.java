package io.github.jodlodi.blight_star.data.client;

import io.github.jodlodi.blight_star.BlightStar;
import io.github.jodlodi.blight_star.Config;
import io.github.jodlodi.blight_star.init.ModBlocks;
import io.github.jodlodi.blight_star.init.ModItems;
import io.github.jodlodi.blight_star.init.ModTabs;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class LangGen extends LanguageProvider {
	public LangGen(PackOutput output) {
		super(output, BlightStar.ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		this.addBlock(ModBlocks.ROT_BLOCK, "Rot Block");
		this.addBlock(ModBlocks.SPROUT, "Sprout");

		this.addItem(ModItems.ROT_BERRY, "Rot Berry");

		this.addTab(ModTabs.BLIGHT_STAR, "Blight Star");

		this.addConfigValue(Config.LOG_DIRT_BLOCK, "Pie or Die");
		this.addConfigValue(Config.MAGIC_NUMBER, "Pie or Maybe");
		this.addConfigValue(Config.MAGIC_NUMBER_INTRODUCTION, "Pie or Suck");
		this.addConfigValue(Config.ITEM_STRINGS, "Pie or Fuck");
	}

	protected void addTab(DeferredHolder<CreativeModeTab, CreativeModeTab> tab, String translation) {
		this.add(tab.get().getDisplayName().getString(), translation);
	}

	protected void addConfigValue(ModConfigSpec.ConfigValue<?> value, String translation) {
		StringBuilder path = new StringBuilder(BlightStar.ID + ".configuration");
		for (String s : value.getPath()) path.append(".").append(s);
		this.add(path.toString(), translation);
	}
}
