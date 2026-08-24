package com.shadow.testmod.item;

import com.shadow.testmod.TestMod;
import com.shadow.testmod.item.custom.ChiselItem;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.shadow.testmod.item.ModToolTiers.SHADOW;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TestMod.MODID);


    //BASIC MOD ITEMS
    public static final DeferredItem<Item> SHADOWINGOT = ITEMS.register("shadow_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAWSHADOWINGOT = ITEMS.register("raw_shadow_steel",
            () -> new Item(new Item.Properties()));


    //SHADOW TOOL SET
    public static final DeferredItem<Item> SHADOWSWORD = ITEMS.register("shadow_sword",
            ()-> new SwordItem(
                    SHADOW,
                    new Item.Properties()
                            .attributes(SwordItem.createAttributes(SHADOW,9f,-2.2f))));

    public static final DeferredItem<Item> SHADOWPICKAXE = ITEMS.register("shadow_pickaxe",
            ()-> new PickaxeItem(
                    SHADOW,
                    new Item.Properties()
                            .attributes(PickaxeItem.createAttributes(SHADOW,0f,-2.8f))));

    public static final DeferredItem<Item> SHADOWAXE = ITEMS.register("shadow_axe",
            ()-> new AxeItem(
                    SHADOW,
                    new Item.Properties()
                        .attributes(AxeItem.createAttributes(SHADOW,15f,-3.2f))));

    public static final DeferredItem<Item> SHADOWSHOVEL = ITEMS.register("shadow_shovel",
            ()-> new ShovelItem(
                   SHADOW,
                    new Item.Properties()
                            .attributes(SwordItem.createAttributes(SHADOW,0f,-2.8f))));

    public static final DeferredItem<Item> SHADOWHOE = ITEMS.register("shadow_hoe",
            ()-> new HoeItem(
                    SHADOW,
                    new Item.Properties()
                            .attributes(SwordItem.createAttributes(SHADOW,0f,-2.8f))));

    public static final DeferredItem<Item> SHADOWSPEAR = ITEMS.register("shadow_spear",
            ()-> new SwordItem(
                    SHADOW,
                    new Item.Properties()
                            .attributes(SwordItem.createAttributes(SHADOW,13f,-2.8f))));


    //ADVANCED TOOLS
    public static final DeferredItem<Item> CHISEL = ITEMS.register("chisel",
            ()-> new ChiselItem(new Item.Properties().durability(100)));





    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
