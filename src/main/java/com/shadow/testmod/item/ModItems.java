package com.shadow.testmod.item;

import com.shadow.testmod.TestMod;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TestMod.MODID);

    public static final DeferredItem<Item> SHADOWINGOT = ITEMS.register("shadowingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAWSHADOWINGOT = ITEMS.register("raw_shadowingot",
            () -> new Item(new Item.Properties()));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
