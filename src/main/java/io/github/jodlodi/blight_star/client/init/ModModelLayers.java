package io.github.jodlodi.blight_star.client.init;

import io.github.jodlodi.blight_star.BlightStar;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.geom.ModelLayerLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ModModelLayers {
	public static final ModelLayerLocation BLOCK = register("block");

	private static ModelLayerLocation register(String name) {
		return register(name, "main");
	}

	private static ModelLayerLocation register(String p_171301_, String layer) {
		return new ModelLayerLocation(BlightStar.prefix(p_171301_), layer);
	}
}
