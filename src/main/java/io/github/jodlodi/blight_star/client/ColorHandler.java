package io.github.jodlodi.blight_star.client;

import io.github.jodlodi.blight_star.BlightStar;
import io.github.jodlodi.blight_star.init.ModBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@EventBusSubscriber(modid = BlightStar.ID, value = Dist.CLIENT)
public class ColorHandler {
	private static final PerlinNoise3D HUE_NOISE = new PerlinNoise3D(92445514L);
	private static final PerlinNoise3D SATURATION_NOISE = new PerlinNoise3D(56491515L);

	@SubscribeEvent
	public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
		event.register((state, level, pos, tintIndex) -> {
			if (pos == null) return 0xFFFFFFFF;
			return Color.HSBtoRGB(
					(float) HUE_NOISE.noise(pos, 0.025) * 2,
					(float) SATURATION_NOISE.noise(pos, 0.125) * 0.5F + 0.25F,
					1.0F
			);
		}, ModBlocks.SPECTRAL_SAND.get());
	}

	@SubscribeEvent
	public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
		BlockColors blockColors = event.getBlockColors();
		event.register((stack, tintIndex) -> stack.getItem() instanceof BlockItem item ? blockColors.getColor(item.getBlock().defaultBlockState(), null, null, tintIndex) : -1, ModBlocks.SPECTRAL_SAND.get());
	}

	public static class PerlinNoise3D {
		private final int[] permutation;
		private static final int[] grad3 = {1, 1, 0, 1, 0, 1, 0, -1, 1, -1, 0, -1, 0, -1, -1, 1};

		public PerlinNoise3D(long seed) {
			permutation = new int[512];
			RandomSource rand = RandomSource.create(seed);
			int[] p = new int[256];

			for (int i = 0; i < 256; i++) {
				p[i] = i;
			}

			for (int i = 255; i >= 0; i--) {
				int j = rand.nextInt(i + 1);
				// Swap
				int temp = p[i];
				p[i] = p[j];
				p[j] = temp;
				permutation[i] = p[i];
				permutation[i + 256] = p[i]; // Duplicate
			}
		}

		private static double fade(double t) {
			return t * t * t * (t * (t * 6 - 15) + 10);
		}

		private static double lerp(double a, double b, double t) {
			return a + t * (b - a);
		}

		private static double grad(int hash, double x, double y, double z) {
			int h = hash & 15; // for 4 bits
			double u = h < 8 ? x : y;
			double v = h < 4 ? y : (h == 12 || h == 14 ? x : z);
			return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
		}

		public double noise(BlockPos pos, double scale) {
			return this.noise(pos.getX() * scale, pos.getY() * scale, pos.getZ() * scale);
		}

		public double noise(double x, double y, double z) {
			int X = (int) Math.floor(x) & 255;
			int Y = (int) Math.floor(y) & 255;
			int Z = (int) Math.floor(z) & 255;

			x -= Math.floor(x);
			y -= Math.floor(y);
			z -= Math.floor(z);

			double u = fade(x);
			double v = fade(y);
			double w = fade(z);

			int A = permutation[X] + Y;
			int AA = permutation[A] + Z;
			int AB = permutation[A + 1] + Z;
			int B = permutation[X + 1] + Y;
			int BA = permutation[B] + Z;
			int BB = permutation[B + 1] + Z;

			double res = lerp(
					lerp(
							lerp(grad(permutation[AA], x, y, z),
									grad(permutation[BA], x - 1, y, z), u),
							lerp(grad(permutation[AB], x, y - 1, z),
									grad(permutation[BB], x - 1, y - 1, z), u),
							v
					),
					lerp(
							lerp(grad(permutation[AA + 1], x, y, z - 1),
									grad(permutation[BA + 1], x - 1, y, z - 1), u),
							lerp(grad(permutation[AB + 1], x, y - 1, z - 1),
									grad(permutation[BB + 1], x - 1, y - 1, z - 1), u),
							v
					),
					w
			);

			return (res + 1) / 2; // Normalize to [0, 1]
		}
	}
}
