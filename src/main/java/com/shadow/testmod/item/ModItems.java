package com.shadow.testmod.item;

import com.shadow.testmod.TestMod;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TestMod.MODID);

    public static final DeferredItem<Item> SHADOWINGOT = ITEMS.register("shadow_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAWSHADOWINGOT = ITEMS.register("raw_shadow_steel",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SHADOWSWORD = ITEMS.register("shadow_sword",
            ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SHADOWPICKAXE = ITEMS.register("shadow_pickaxe",
                ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SHADOWAXE = ITEMS.register("shadow_axe",
                ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SHADOWSHOVEL = ITEMS.register("shadow_shovel",
                ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SHADOWHOE = ITEMS.register("shadow_hoe",
                ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SHADOWSPEAR = ITEMS.register("shadow_spear",
                ()-> new Item(new Item.Properties()));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
