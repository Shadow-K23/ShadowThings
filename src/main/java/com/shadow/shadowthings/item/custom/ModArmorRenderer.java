package com.shadow.shadowthings.item.custom;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ModArmorRenderer extends GeoArmorRenderer<ModArmorItem> {

        // Change this line to have empty parentheses ()
        public ModArmorRenderer() {
            super(new DefaultedItemGeoModel<>(ResourceLocation.fromNamespaceAndPath("shadowthings", "armor/shadow_armor")));
        }
    }