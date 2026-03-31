package io.github.jodlodi.blight_star.block.util;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Set;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface BlightVessel<T extends Block> extends Blighted {
	default int calculateNewBlightLevel(BlockPos pos, Level level, int blight) {
		return Math.min(blight, this.maxBlight());
	}

	class VesselBlightProperty extends BlightProperty {
		public VesselBlightProperty(String name, int max) {
			super(name, createProperty(max));
		}

		static protected ImmutableSet<Integer> createProperty(int max) {
			Set<Integer> set = Sets.newHashSet();

			for (int i = -max; i <= max; i++) {
				if (i == 1) continue; // 1 and -1 are the exact same thing logic wise, so we can skip it existing.
				set.add(i);
			}

			return ImmutableSet.copyOf(set);
		}
	}
}
