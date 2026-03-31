package io.github.jodlodi.blight_star.data.client;

import io.github.jodlodi.blight_star.BlightStar;
import io.github.jodlodi.blight_star.block.RotBlock;
import io.github.jodlodi.blight_star.block.util.Blighted;
import io.github.jodlodi.blight_star.init.ModBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.client.model.generators.loaders.CompositeModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

import static io.github.jodlodi.blight_star.BlightStar.prefix;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockModelGen extends BlockStateProvider {
	protected static final ResourceLocation SOLID = ResourceLocation.withDefaultNamespace("solid");
	protected static final ResourceLocation CUTOUT = ResourceLocation.withDefaultNamespace("cutout");
	protected static final ResourceLocation TRANSLUCENT = ResourceLocation.withDefaultNamespace("translucent");

	public BlockModelGen(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, BlightStar.ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		this.makeBlightBlock(ModBlocks.LUMINESAND);
		this.makeBlightBlock(ModBlocks.GLITTER_BUD);

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
	}

	public <T extends Block & Blighted> void makeBlightBlock(DeferredBlock<T> block) {
		List<ConfiguredModel[]> models = new ArrayList<>();

		for (int blight = 0; blight <= block.get().maxBlight(); blight++) {
			if (blight == 0) {
				models.add(ConfiguredModel.builder().modelFile(this.models().withExistingParent(ModelProvider.BLOCK_FOLDER + "/" + block.getId().getPath() + "/" + blight, "block/cube_all").texture("all", prefix(ModelProvider.BLOCK_FOLDER + "/" + block.getId().getPath() + "/" + 0))).build());
			} else {
				models.add(ConfiguredModel.builder().modelFile(this.models().withExistingParent(ModelProvider.BLOCK_FOLDER + "/" + block.getId().getPath() + "/" + blight, "block/block")
						.texture("particle", prefix(ModelProvider.BLOCK_FOLDER + "/" + block.getId().getPath() + "/" + 0)).customLoader(CompositeModelBuilder::begin)
						.child("zero", this.nestedFromParent("block/cube_all").texture("all", prefix(ModelProvider.BLOCK_FOLDER + "/" + block.getId().getPath() + "/" + 0)))
						.child("dust", this.nestedEmissiveBlockAll(TRANSLUCENT, 15).texture("all", prefix(ModelProvider.BLOCK_FOLDER + "/" + block.getId().getPath() + "/" + blight)))
						.end()
				).build());
			}
		}

		this.getVariantBuilder(block.get()).forAllStates(state -> models.get(block.get().getBlight(state)));
	}

	protected BlockModelBuilder makeTintedBlockAll(String name, ResourceLocation renderType) {
		return this.makeTintedBlock(name, renderType)
				.texture("north", "#all").texture("south", "#all").texture("east", "#all")
				.texture("west", "#all").texture("up", "#all").texture("down", "#all");
	}

	protected BlockModelBuilder makeTintedBlock(String name, ResourceLocation renderType) {
		return models().withExistingParent(name, "minecraft:block/block").renderType(renderType).texture("particle", "#north")
				.element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
				.face(Direction.NORTH).texture("#north").cullface(Direction.NORTH).tintindex(0).end()
				.face(Direction.EAST).texture("#east").cullface(Direction.EAST).tintindex(0).end()
				.face(Direction.SOUTH).texture("#south").cullface(Direction.SOUTH).tintindex(0).end()
				.face(Direction.WEST).texture("#west").cullface(Direction.WEST).tintindex(0).end()
				.face(Direction.UP).texture("#up").cullface(Direction.UP).tintindex(0).end()
				.face(Direction.DOWN).texture("#down").cullface(Direction.DOWN).tintindex(0).end().end();
	}

	protected BlockModelBuilder makeEmissiveBlockAll(String name, ResourceLocation renderType, int emissivity) {
		return this.makeEmissiveBlock(name, renderType, emissivity)
				.texture("north", "#all").texture("south", "#all").texture("east", "#all")
				.texture("west", "#all").texture("up", "#all").texture("down", "#all");
	}

	protected BlockModelBuilder makeEmissiveBlock(String name, ResourceLocation renderType, int emissivity) {
		return models().withExistingParent(name, "minecraft:block/block").renderType(renderType).texture("particle", "#north")
				.element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
				.face(Direction.NORTH).texture("#north").cullface(Direction.NORTH).emissivity(emissivity, emissivity).tintindex(0).end()
				.face(Direction.EAST).texture("#east").cullface(Direction.EAST).emissivity(emissivity, emissivity).tintindex(0).end()
				.face(Direction.SOUTH).texture("#south").cullface(Direction.SOUTH).emissivity(emissivity, emissivity).tintindex(0).end()
				.face(Direction.WEST).texture("#west").cullface(Direction.WEST).emissivity(emissivity, emissivity).tintindex(0).end()
				.face(Direction.UP).texture("#up").cullface(Direction.UP).emissivity(emissivity, emissivity).tintindex(0).end()
				.face(Direction.DOWN).texture("#down").cullface(Direction.DOWN).emissivity(emissivity, emissivity).tintindex(0).end().end();
	}

	protected BlockModelBuilder nestedEmissiveBlockAll(ResourceLocation renderType, int emissivity) {
		return this.nestedEmissiveBlock(renderType, emissivity)
				.texture("north", "#all").texture("south", "#all").texture("east", "#all")
				.texture("west", "#all").texture("up", "#all").texture("down", "#all");
	}

	protected BlockModelBuilder nestedEmissiveBlock(ResourceLocation renderType, int emissivity) {
		return this.nestedFromParent("block/block").renderType(renderType).texture("particle", "#north")
				.element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
				.face(Direction.NORTH).texture("#north").cullface(Direction.NORTH).emissivity(emissivity, emissivity).tintindex(0).end()
				.face(Direction.EAST).texture("#east").cullface(Direction.EAST).emissivity(emissivity, emissivity).tintindex(0).end()
				.face(Direction.SOUTH).texture("#south").cullface(Direction.SOUTH).emissivity(emissivity, emissivity).tintindex(0).end()
				.face(Direction.WEST).texture("#west").cullface(Direction.WEST).emissivity(emissivity, emissivity).tintindex(0).end()
				.face(Direction.UP).texture("#up").cullface(Direction.UP).emissivity(emissivity, emissivity).tintindex(0).end()
				.face(Direction.DOWN).texture("#down").cullface(Direction.DOWN).emissivity(emissivity, emissivity).tintindex(0).end().end();
	}

	protected void simpleBlockExisting(Block b) {
		this.simpleBlock(b, new ConfiguredModel(models().getExistingFile(prefix(name(b)))));
	}

	protected BlockModelBuilder nestedFromParent(String parent) {
		return this.nestedFromParent(ResourceLocation.fromNamespaceAndPath("minecraft", parent));
	}

	protected BlockModelBuilder nestedFromParent(ResourceLocation parent) {
		return this.models().nested().parent(this.models().getExistingFile(parent));
	}

	protected ResourceLocation key(Block block) {
		return BuiltInRegistries.BLOCK.getKey(block);
	}

	protected String name(Block block) {
		return key(block).getPath();
	}
}
