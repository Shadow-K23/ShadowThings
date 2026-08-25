package com.shadow.shadowthings.item;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.item.custom.ChiselItem;
import com.shadow.shadowthings.item.custom.FuelItem;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.shadow.shadowthings.util.ModToolTiers.SHADOW;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ShadowThings.MODID);


    //BASIC MOD ITEMS
    public static final DeferredItem<Item> SHADOWINGOT = ITEMS.register("shadow_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAWSHADOWINGOT = ITEMS.register("raw_shadow_steel",
            () -> new Item(new Item.Properties()));


    //SHADOW TOOL SET
    public static final DeferredItem<SwordItem> SHADOWSWORD = ITEMS.register("shadow_sword",
            ()-> new SwordItem(
                    SHADOW,
                    new Item.Properties()
                            .attributes(SwordItem.createAttributes(SHADOW,9f,-2.2f))));

    public static final DeferredItem<PickaxeItem> SHADOWPICKAXE = ITEMS.register("shadow_pickaxe",
            ()-> new PickaxeItem(
                    SHADOW,
                    new Item.Properties()
                            .attributes(PickaxeItem.createAttributes(SHADOW,-2f,-2.8f))));

    public static final DeferredItem<AxeItem> SHADOWAXE = ITEMS.register("shadow_axe",
            ()-> new AxeItem(
                    SHADOW,
                    new Item.Properties()
                        .attributes(AxeItem.createAttributes(SHADOW,15f,-3.2f))));

    public static final DeferredItem<ShovelItem> SHADOWSHOVEL = ITEMS.register("shadow_shovel",
            ()-> new ShovelItem(
                   SHADOW,
                    new Item.Properties()
                            .attributes(SwordItem.createAttributes(SHADOW,-2f,-3.0f))));

    public static final DeferredItem<HoeItem> SHADOWHOE = ITEMS.register("shadow_hoe",
            ()-> new HoeItem(
                    SHADOW,
                    new Item.Properties()
                            .attributes(SwordItem.createAttributes(SHADOW,-2f,-3.0f))));

    public static final DeferredItem<SwordItem> SHADOWSPEAR = ITEMS.register("shadow_spear",
            ()-> new SwordItem(
                    SHADOW,
                    new Item.Properties()
                            .attributes(SwordItem.createAttributes(SHADOW,13f,-2.8f))));

    //FOODS

    public static final DeferredItem<Item> DRAGON_FRUIT = ITEMS.register("dragon_fruit",
            () -> new Item(new Item.Properties().food(ModFoodProperties.DRAGON_FRUIT)));

    //FUELS
    public static final DeferredItem<Item> SUPER_FUEL = ITEMS.register("super_fuel",
            () -> new FuelItem(new Item.Properties(), 800));
    //ADVANCED TOOLS
    public static final DeferredItem<Item> CHISEL = ITEMS.register("chisel",
            ()-> new ChiselItem(new Item.Properties().durability(100)));





    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
