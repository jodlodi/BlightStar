package io.github.jodlodi.blight_star.block.util;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.level.block.Block;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Set;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface BlightSource<T extends Block> extends Blighted {
	class SourceBlightProperty extends BlightProperty {
		public SourceBlightProperty(String name, int max) {
			super(name, createProperty(max));
		}

		static protected ImmutableSet<Integer> createProperty(int max) {
			Set<Integer> set = Sets.newHashSet();
			set.add(-max); // Negative version of the max value, signifying the dormant form.
			for (int i = 0; i <= max; i++) set.add(i);
			return ImmutableSet.copyOf(set);
		}
	}
}
