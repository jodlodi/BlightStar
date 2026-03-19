package io.github.jodlodi.blight_star.data.client;

import io.github.jodlodi.blight_star.BlightStar;
import io.github.jodlodi.blight_star.block.RotBlock;
import io.github.jodlodi.blight_star.init.ModBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.Map;

import static io.github.jodlodi.blight_star.BlightStar.prefix;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockModelGen extends BlockStateProvider {
	public BlockModelGen(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, BlightStar.ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		this.getVariantBuilder(ModBlocks.ROT_BLOCK.get()).forAllStates(state -> {
			switch (state.getValue(RotBlock.AGE)) {
				case 0 -> {
					return ConfiguredModel.builder().modelFile(this.models().cubeAll(ModBlocks.ROT_BLOCK.getId().getPath() + "_0", BlightStar.prefix(ModelProvider.BLOCK_FOLDER + "/0"))).build();
				}
				case 1 -> {
					return ConfiguredModel.builder().modelFile(this.models().cubeAll(ModBlocks.ROT_BLOCK.getId().getPath() + "_1", BlightStar.prefix(ModelProvider.BLOCK_FOLDER + "/1"))).build();
				}
				case 2 -> {
					return ConfiguredModel.builder().modelFile(this.models().cubeAll(ModBlocks.ROT_BLOCK.getId().getPath() + "_2", BlightStar.prefix(ModelProvider.BLOCK_FOLDER + "/2"))).build();
				}
				case 3 -> {
					return ConfiguredModel.builder().modelFile(this.models().cubeAll(ModBlocks.ROT_BLOCK.getId().getPath() + "_3", BlightStar.prefix(ModelProvider.BLOCK_FOLDER + "/3"))).build();
				}
				default -> {
					return ConfiguredModel.builder().modelFile(this.models().cubeAll(ModBlocks.ROT_BLOCK.getId().getPath() + "_4", BlightStar.prefix(ModelProvider.BLOCK_FOLDER + "/4"))).build();
				}
			}
		});

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
