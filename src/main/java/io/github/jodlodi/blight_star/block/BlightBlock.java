package io.github.jodlodi.blight_star.block;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface BlightBlock {
	static IntegerProperty createProperty(int max) {
		return IntegerProperty.create("blight", 0, max);
	}

	IntegerProperty getProperty();
}
