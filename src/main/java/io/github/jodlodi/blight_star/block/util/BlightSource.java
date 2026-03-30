package io.github.jodlodi.blight_star.block.util;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.level.block.Block;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface BlightSource<T extends Block> extends Blighted {
}
