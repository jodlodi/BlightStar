package io.github.jodlodi.blight_star.block.util;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface Blighted {
	static IntegerProperty createProperty(int max) {
		return IntegerProperty.create("blight", 0, max);
	}

	IntegerProperty getBlightProperty();

	default int minBlight() {
		return this.getBlightProperty().min;
	}

	default int maxBlight() {
		return this.getBlightProperty().max;
	}
}
