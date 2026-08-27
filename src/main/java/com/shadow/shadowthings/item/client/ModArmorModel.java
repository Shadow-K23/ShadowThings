package com.shadow.shadowthings.item.client;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.item.custom.ModArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ModArmorModel extends GeoModel<ModArmorItem> {

    @Override
    public ResourceLocation getModelResource(ModArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID,
                "geo/item/armor/" + animatable.getArmorName() + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ModArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID,
                "textures/armor/" + animatable.getArmorName() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(ModArmorItem animatable) {
        // You can also make this dynamic later if you add animations!
        return null;
    }
}