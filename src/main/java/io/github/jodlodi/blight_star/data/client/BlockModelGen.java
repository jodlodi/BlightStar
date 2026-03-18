package io.github.jodlodi.blight_star.data.client;

import io.github.jodlodi.blight_star.BlightStar;
import io.github.jodlodi.blight_star.init.ModBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.ParametersAreNonnullByDefault;

import static io.github.jodlodi.blight_star.BlightStar.prefix;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockModelGen extends BlockStateProvider {
	public BlockModelGen(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, BlightStar.ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		this.simpleBlock(ModBlocks.ROT_BLOCK.get());
		this.simpleBlockExisting(ModBlocks.SPROUT.get());
	}

	protected void simpleBlockExisting(Block b) {
		this.simpleBlock(b, new ConfiguredModel(models().getExistingFile(prefix(name(b)))));
	}

	protected ResourceLocation key(Block block) {
		return BuiltInRegistries.BLOCK.getKey(block);
	}

	protected String name(Block block) {
		return key(block).getPath();
	}
}
