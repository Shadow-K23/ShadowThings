package com.shadow.shadowthings.datagen;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ShadowThings.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //BASIC ITEMS
        basicItem(ModItems.RAWSHADOWINGOT.get());
        basicItem(ModItems.SHADOWINGOT.get());


        //TOOLS
        basicItem(ModItems.SHADOWSWORD.get());
        basicItem(ModItems.SHADOWPICKAXE.get());
        basicItem(ModItems.SHADOWAXE.get());
        basicItem(ModItems.SHADOWHOE.get());
        basicItem(ModItems.SHADOWSHOVEL.get());
        basicItem(ModItems.SHADOWSPEAR.get());
        //ADVANCED ITEMS
        basicItem(ModItems.CHISEL.get());
        //FOOD
        basicItem(ModItems.DRAGON_FRUIT.get());
        //FUEL
        basicItem(ModItems.SUPER_FUEL.get());


    }
}
