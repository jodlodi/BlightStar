package io.github.jodlodi.blight_star.data.tag;

import io.github.jodlodi.blight_star.BlightStar;
import io.github.jodlodi.blight_star.init.ModBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockTagGen extends IntrinsicHolderTagsProvider<Block> {
	@SuppressWarnings("deprecation")
	public BlockTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, Registries.BLOCK, lookupProvider, block -> block.builtInRegistryHolder().key(), BlightStar.ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
				.add(ModBlocks.LUMINESAND.get());

		this.tag(BlockTags.SAND)
				.add(ModBlocks.LUMINESAND.get());

		this.tag(BlockTags.SMELTS_TO_GLASS)
				.add(ModBlocks.LUMINESAND.get());

		this.tag(Tags.Blocks.SANDS)
				.add(ModBlocks.LUMINESAND.get());

		this.tag(BlockTags.SMELTS_TO_GLASS)
				.add(ModBlocks.LUMINESAND.get());
	}
}
