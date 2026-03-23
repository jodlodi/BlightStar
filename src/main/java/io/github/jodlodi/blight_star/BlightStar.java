package io.github.jodlodi.blight_star;

import com.mojang.logging.LogUtils;
import io.github.jodlodi.blight_star.init.*;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import javax.annotation.ParametersAreNonnullByDefault;

@Mod(BlightStar.ID)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlightStar {
    public static final String ID = "blight_star";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BlightStar(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ModInterestPoints.POINTS_OF_INTEREST.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModTabs.TABS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    public static ResourceLocation prefix(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }
}
