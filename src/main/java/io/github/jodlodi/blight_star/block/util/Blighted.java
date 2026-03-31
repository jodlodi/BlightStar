package io.github.jodlodi.blight_star.block.util;

import com.google.common.collect.ImmutableSet;
import io.github.jodlodi.blight_star.BlightStar;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface Blighted {

	Blighted.BlightProperty getBlightProperty();

	default int maxBlight() {
		return this.getBlightProperty().max;
	}

	default int spreadThin(BlockPos pos, int blight) {
		return blight - 1;
	}

	default int getBlight(BlockState state) {
		return Math.abs(state.getValue(this.getBlightProperty()));
	}

	default boolean canSpread(BlockState state) {
		return this.getBlight(state) > 1;
	}

	default boolean isDormant(BlockState state) {
		return state.getValue(this.getBlightProperty()) <= 0;
	}

	default boolean isDormantAndCanSpread(BlockState state) {
		return state.getValue(this.getBlightProperty()) < 1;
	}

	default boolean isDead(BlockState state) {
		return state.getValue(this.getBlightProperty()) == 0;
	}

	default BlockState lieDormant(BlockState state) {
		int blight = state.getValue(this.getBlightProperty());
		if (blight <= 0) return state;
		try {
			return state.setValue(this.getBlightProperty(), blight * -1);
		} catch (Exception e) {
			return state;
		}
	}

	default BlockState awaken(BlockState state) {
		int blight = state.getValue(this.getBlightProperty());
		if (blight >= 0) return state;
		try {
			return state.setValue(this.getBlightProperty(), blight * -1);
		} catch (Exception e) {
			return state;
		}
	}

	default BlockState setTo(BlockState state, BlockPos pos, int blight) {
		if (blight > this.maxBlight()) blight = this.maxBlight();
		if (!this.getBlightProperty().getPossibleValues().contains(blight)) blight *= -1;
		if (!this.getBlightProperty().getPossibleValues().contains(blight)) {
			BlightStar.LOGGER.error("Impossible blight value {} of property {} attempted to set for block {}!", blight, this.getBlightProperty(), state.getBlock());
			return state;
		}

		return state.setValue(this.getBlightProperty(), blight);
	}

	class BlightProperty extends Property<Integer> {
		protected final ImmutableSet<Integer> values;
		protected final int max;

		protected BlightProperty(String name, Set<Integer> values) {
			super(name, Integer.class);
			this.values = ImmutableSet.copyOf(values);

			int big = Integer.MIN_VALUE;
			for (int i : this.values) if (i > big) big = i;
			this.max = big;
		}

		@Override
		public Collection<Integer> getPossibleValues() {
			return this.values;
		}

		@Override
		public boolean equals(Object other) {
			if (this == other) {
				return true;
			} else {
				if (other instanceof BlightProperty property && super.equals(other)) {
					return this.values.equals(property.values);
				}

				return false;
			}
		}

		@Override
		public int generateHashCode() {
			return 31 * super.generateHashCode() + this.values.hashCode();
		}

		@Override
		public Optional<Integer> getValue(String value) {
			try {
				if (value.equals("0")) { // 0 if 0
					return this.values.contains(0) ? Optional.of(0) : Optional.empty();
				} else if (value.startsWith("n")) { // negative number if `n`
					int integer = Integer.parseInt(value.substring(1));
					return this.values.contains(integer * -1) ? Optional.of(integer * -1) : Optional.empty();
				} else if (value.startsWith("p")) { // positive value if `p`
					Integer integer = Integer.valueOf(value.substring(1));
					return this.values.contains(integer) ? Optional.of(integer) : Optional.empty();
				} else return Optional.empty(); // idfk otherwise
			} catch (NumberFormatException numberformatexception) {
				return Optional.empty();
			}
		}

		@Override
		public String getName(Integer value) {
			if (value == 0) return "0";
			else if (value < 0) return "n" + (value * -1);
			return "p" + value;
		}
	}
}
