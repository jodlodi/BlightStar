package io.github.jodlodi.blight_star.init;

import io.github.jodlodi.blight_star.BlightStar;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ModItems {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BlightStar.ID);

	public static final DeferredItem<Item> ROT_BERRY = ITEMS.registerSimpleItem("rot_berry", new Item.Properties().food(new FoodProperties.Builder()
			.alwaysEdible().nutrition(1).saturationModifier(2f).build()));

	public static final DeferredItem<BlockItem> ROT_BLOCK = ITEMS.registerSimpleBlockItem("rot_block", ModBlocks.ROT_BLOCK);
	public static final DeferredItem<BlockItem> SPECTRAL_SAND = ITEMS.registerSimpleBlockItem("spectral_sand", ModBlocks.SPECTRAL_SAND);
	public static final DeferredItem<BlockItem> SPROUT = ITEMS.registerSimpleBlockItem("sprout", ModBlocks.SPROUT);
}
