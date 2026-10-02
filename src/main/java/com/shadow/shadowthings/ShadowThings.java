package com.shadow.shadowthings;

import com.shadow.shadowthings.block.ModBlocks;
import com.shadow.shadowthings.block.entity.ModBlockEntities;
import com.shadow.shadowthings.block.entity.renderer.*;
import com.shadow.shadowthings.client.model.SoulCoreModel;
import com.shadow.shadowthings.client.model.SoulFurnaceModel;
import com.shadow.shadowthings.client.model.SoulOrbModel;
import com.shadow.shadowthings.component.ModDataComponents;
import com.shadow.shadowthings.effect.ModEffects;
import com.shadow.shadowthings.enchantment.ModEnchantmentEffects;
import com.shadow.shadowthings.item.ModItems;
import com.shadow.shadowthings.potion.ModPotions;
import com.shadow.shadowthings.recipe.ModRecipes;
import com.shadow.shadowthings.screen.ModMenuTypes;
import com.shadow.shadowthings.screen.custom.SoulCoreScreen;
import com.shadow.shadowthings.screen.custom.SoulCrafterScreen;
import com.shadow.shadowthings.screen.custom.SoulCrucibleScreen;
import com.shadow.shadowthings.screen.custom.SoulFurnaceScreen;
import com.shadow.shadowthings.server.ModDataAttachments;
import com.shadow.shadowthings.soul.ModManaHudOverlay;
import com.shadow.shadowthings.sound.ModSounds;
import com.shadow.shadowthings.util.ModItemProperties;
import com.shadow.shadowthings.worldgen.ModOverworldRegion;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import terrablender.api.Regions;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ShadowThings.MODID)
public class ShadowThings {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "shadowthings";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ShadowThings(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModSounds.register(modEventBus);

        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);

        ModEnchantmentEffects.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        ModDataComponents.register(modEventBus);
        ModDataAttachments.register(modEventBus);

        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Regions.register(new ModOverworldRegion(
                    ResourceLocation.fromNamespaceAndPath("shadowthings", "overworld_region"),
                    3
            ));
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.SHADOW_INGOT);
            event.accept(ModItems.RAW_SHADOW_INGOT);
        }
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS){
            event.accept(ModBlocks.SHADOW_BLOCK);
            event.accept(ModBlocks.SHADOW_ORE);
            event.accept(ModBlocks.SHADOW_DEEPSLATE_ORE);
        }
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents{
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event){
            ModItemProperties.addCustomProperties();
        }

        @SubscribeEvent
        public static void registerGuiLayers(RegisterGuiLayersEvent event) {
            event.registerAbove(
                    VanillaGuiLayers.CHAT, // Place it relative to vanilla UI elements
                    ResourceLocation.fromNamespaceAndPath("shadowthings", "mana_overlay"),
                    ModManaHudOverlay::render
            );
        }
        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
            event.registerBlockEntityRenderer(ModBlockEntities.SOUL_CORE_BE.get(), SoulCoreEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.SOUL_CRAFTER_BE.get(), SoulCrafterEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.SOUL_PEDESTAL_BE.get(), SoulPedestalEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.SOUL_FURNACE_BE.get(), SoulFurnaceEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.SOUL_CONDENSER_BE.get(), SoulCondenserEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.SOUL_CRUCIBLE_BE.get(), SoulCrucibleEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.SOUL_COLLECTOR_BE.get(), SoulCollectorEntityRenderer::new);
        }
        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            // This tells the game how to build your 3D model using the math from Blockbench
            event.registerLayerDefinition(SoulCoreModel.LAYER_LOCATION, SoulCoreModel::createBodyLayer);
            event.registerLayerDefinition(SoulFurnaceModel.LAYER_LOCATION, SoulFurnaceModel::createBodyLayer);
            event.registerLayerDefinition(SoulOrbModel.LAYER_LOCATION, SoulOrbModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            // This links the server menu to the client GUI rendering
            event.register(ModMenuTypes.SOUL_CORE_MENU.get(), SoulCoreScreen::new);
            event.register(ModMenuTypes.SOUL_CRAFTER_MENU.get(), SoulCrafterScreen::new);
            event.register(ModMenuTypes.SOUL_FURNACE_MENU.get(), SoulFurnaceScreen::new);
            event.register(ModMenuTypes.SOUL_CRUCIBLE_MENU.get(), SoulCrucibleScreen::new);
        }

        @SubscribeEvent
        public static void registerCapabilities(RegisterCapabilitiesEvent event) {
            event.registerBlockEntity(
                    Capabilities.ItemHandler.BLOCK,
                    ModBlockEntities.SOUL_CRUCIBLE_BE.get(),
                    (blockEntity, direction) -> blockEntity.mainInventory
            );
            event.registerBlockEntity(
                    Capabilities.ItemHandler.BLOCK,
                    ModBlockEntities.SOUL_PEDESTAL_BE.get(),
                    (blockEntity, direction) -> blockEntity.inventory
            );
            event.registerBlockEntity(
                    Capabilities.ItemHandler.BLOCK,
                    ModBlockEntities.SOUL_CRAFTER_BE.get(),
                    (blockEntity,direction) -> blockEntity.automationInventory
            );
            event.registerBlockEntity(
                    Capabilities.ItemHandler.BLOCK,
                    ModBlockEntities.SOUL_CRUCIBLE_BE.get(),
                    (blockEntity,direction) -> blockEntity.mainInventory
            );
        }
    }
}
