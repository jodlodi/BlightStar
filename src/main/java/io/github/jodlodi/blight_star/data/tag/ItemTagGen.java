package io.github.jodlodi.blight_star.data.tag;

import io.github.jodlodi.blight_star.BlightStar;
import io.github.jodlodi.blight_star.init.ModItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemTagGen extends ItemTagsProvider {
	public ItemTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> provider, ExistingFileHelper helper) {
		super(output, future, provider, BlightStar.ID, helper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(ItemTags.SAND)
				.add(ModItems.LUMINESAND.get());

		this.tag(ItemTags.SMELTS_TO_GLASS)
				.add(ModItems.LUMINESAND.get());

		this.tag(Tags.Items.SANDS)
				.add(ModItems.LUMINESAND.get());
	}
}
