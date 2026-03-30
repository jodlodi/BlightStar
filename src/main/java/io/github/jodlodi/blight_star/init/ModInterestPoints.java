package io.github.jodlodi.blight_star.init;

import com.google.common.collect.ImmutableSet;
import io.github.jodlodi.blight_star.BlightStar;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModInterestPoints {
	public static final DeferredRegister<PoiType> POINTS_OF_INTEREST = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, BlightStar.ID);

	public static final DeferredHolder<PoiType, PoiType> SPROUT = POINTS_OF_INTEREST.register("sprout", () -> new PoiType(ImmutableSet.copyOf(ModBlocks.GLITTER_BUD.get().getStateDefinition().getPossibleStates()), 0, 1));
}
