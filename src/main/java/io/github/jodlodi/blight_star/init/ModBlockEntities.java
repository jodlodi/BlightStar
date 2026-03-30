package io.github.jodlodi.blight_star.init;

import io.github.jodlodi.blight_star.BlightStar;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, BlightStar.ID);

	/*public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SpectralSandBlock.Entity>> REGISTERED = BLOCK_ENTITIES.register("antibuilder", () ->
			BlockEntityType.Builder.of(SpectralSandBlock.Entity::new, ModBlocks.SPECTRAL_SAND.get()).build(null));*/
}
