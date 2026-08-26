package com.shadow.shadowthings.item;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.item.custom.ChiselItem;
import com.shadow.shadowthings.item.custom.FuelItem;
import com.shadow.shadowthings.item.custom.HammerItem;
import com.shadow.shadowthings.item.custom.ModArmorItem;
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
                        .attributes(AxeItem.createAttributes(SHADOW,11f,-3.2f))));

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

    public static final DeferredItem<HammerItem> SHADOWHAMMER = ITEMS.register("shadow_hammer",
            ()-> new HammerItem(
                    SHADOW,
                    new Item.Properties()
                            .attributes(PickaxeItem.createAttributes(SHADOW,15f,-3.5f))));
    //ARMOR ITEMS

    public static final  DeferredItem<ArmorItem> SHADOW_HELMET = ITEMS.register("shadow_helmet",
                () -> new ModArmorItem(
                    ModArmorMaterials.SHADOW_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(42))));
    public static final  DeferredItem<ArmorItem> SHADOW_CHESTPLATE = ITEMS.register("shadow_chestplate",
                () -> new ModArmorItem(
                        ModArmorMaterials.SHADOW_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                        new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(42))));
    public static final  DeferredItem<ArmorItem> SHADOW_LEGGINGS = ITEMS.register("shadow_leggings",
                () -> new ModArmorItem(
                        ModArmorMaterials.SHADOW_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                        new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(42))));
    public static final  DeferredItem<ArmorItem> SHADOW_BOOTS = ITEMS.register("shadow_boots",
                () -> new ModArmorItem(
                        ModArmorMaterials.SHADOW_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                        new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(42))));

    public static final  DeferredItem<Item> SHADOW_HORSE_ARMOR = ITEMS.register("shadow_horse_armor",
                () -> new AnimalArmorItem(
                        ModArmorMaterials.SHADOW_ARMOR_MATERIAL, AnimalArmorItem.BodyType.EQUESTRIAN, false , new Item.Properties().stacksTo(1)));


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
