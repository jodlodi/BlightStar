package io.github.jodlodi.blight_star.data;

import io.github.jodlodi.blight_star.BlightStar;
import io.github.jodlodi.blight_star.data.client.BlockModelGen;
import io.github.jodlodi.blight_star.data.client.ItemModelGen;
import io.github.jodlodi.blight_star.data.client.LangGen;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@EventBusSubscriber(modid = BlightStar.ID)
public class DataGen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = event.getGenerator().getPackOutput();
		ExistingFileHelper helper = event.getExistingFileHelper();

		generator.addProvider(event.includeClient(), new BlockModelGen(output, helper));
		generator.addProvider(event.includeClient(), new ItemModelGen(output, helper));

		generator.addProvider(event.includeClient(), new LangGen(output));
	}
}
