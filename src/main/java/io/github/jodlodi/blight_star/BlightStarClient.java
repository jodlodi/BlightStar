package io.github.jodlodi.blight_star;

import io.github.jodlodi.blight_star.client.init.ModModelLayers;
import io.github.jodlodi.blight_star.client.model.BlockModel;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mod(value = BlightStar.ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = BlightStar.ID, value = Dist.CLIENT)
public class BlightStarClient {
    public BlightStarClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

		IEventBus bus = container.getEventBus();

        //bus.addListener(ColorHandler::registerBlockColors);
        //bus.addListener(ColorHandler::registerItemColors);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        //event.registerBlockEntityRenderer(ModBlockEntities.REGISTERED.get(), SandRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.BLOCK, BlockModel::createBlockLayer);
    }
}
