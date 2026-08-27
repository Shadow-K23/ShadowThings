package com.shadow.shadowthings.item.client;

import com.shadow.shadowthings.item.custom.ModArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class ModArmorRenderer extends GeoArmorRenderer<ModArmorItem> {

    public ModArmorRenderer() {

        super(new ModArmorModel());

        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}