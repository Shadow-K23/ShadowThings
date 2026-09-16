package com.shadow.shadowthings.item;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.ModBlocks;
import com.shadow.shadowthings.item.custom.*;
import com.shadow.shadowthings.sound.ModSounds;
import com.shadow.shadowthings.util.UpgradeType;
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

    //CUSTOM WEAPONS

    public static  final  DeferredItem<Item> SHADOW_BOW = ITEMS.register("shadow_bow",
            ()-> new BowItem(new Item.Properties().durability(800)));

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
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(42)), "shadow_armor"));
    public static final  DeferredItem<ArmorItem> SHADOW_CHESTPLATE = ITEMS.register("shadow_chestplate",
                () -> new ModArmorItem(
                        ModArmorMaterials.SHADOW_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                        new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(42)), "shadow_armor"));
    public static final  DeferredItem<ArmorItem> SHADOW_LEGGINGS = ITEMS.register("shadow_leggings",
                () -> new ModArmorItem(
                        ModArmorMaterials.SHADOW_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                        new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(42)), "shadow_armor"));
    public static final  DeferredItem<ArmorItem> SHADOW_BOOTS = ITEMS.register("shadow_boots",
                () -> new ModArmorItem(
                        ModArmorMaterials.SHADOW_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                        new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(42)), "shadow_armor"));

    public static final  DeferredItem<Item> SHADOW_HORSE_ARMOR = ITEMS.register("shadow_horse_armor",
                () -> new AnimalArmorItem(
                        ModArmorMaterials.SHADOW_ARMOR_MATERIAL, AnimalArmorItem.BodyType.EQUESTRIAN, false , new Item.Properties().stacksTo(1)));
    //MISC

    public static final  DeferredItem<Item> BAR_BRAWL_MUSIC_DISC = ITEMS.register("bar_brawl_music_disc",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.BAR_BRAWL_KEY).stacksTo(1)));

    //FOODS

    public static final DeferredItem<Item> DRAGON_FRUIT = ITEMS.register("dragon_fruit",
            () -> new Item(new Item.Properties().food(ModFoodProperties.DRAGON_FRUIT)));

    public static final DeferredItem<Item> SHADOW_BERRIES = ITEMS.register("shadow_berry",
            () -> new ItemNameBlockItem(ModBlocks.SHADOW_BERRY_BUSH.get(), new Item.Properties().food(ModFoodProperties.SHADOW_BERRY)));

    //SEEDS


    public static final DeferredItem<Item> RADISH_SEEDS = ITEMS.register("radish_seeds",
            ()-> new ItemNameBlockItem(ModBlocks.RADISH_CROP.get(), new Item.Properties()));

    //FUELS
    public static final DeferredItem<Item> SUPER_FUEL = ITEMS.register("super_fuel",
            () -> new FuelItem(new Item.Properties(), 800));
    //ADVANCED TOOLS
    public static final DeferredItem<Item> SOUL_LINKER = ITEMS.register("soul_linker",
            ()-> new SoulLinkerItem(new Item.Properties().durability(100)));


    //SOUL UPGRADES

        //UPGRADE BASES
            public static final DeferredItem<Item> SOUL_UPGRADE_BASE_1 = ITEMS.register("soul_upgrade_base_1",
                ()-> new Item(new Item.Properties().stacksTo(16)));
            public static final DeferredItem<Item> SOUL_UPGRADE_BASE_2 = ITEMS.register("soul_upgrade_base_2",
                ()-> new Item(new Item.Properties().stacksTo(16)));
            public static final DeferredItem<Item> SOUL_UPGRADE_BASE_3 = ITEMS.register("soul_upgrade_base_3",
                ()-> new Item(new Item.Properties().stacksTo(16)));
            public static final DeferredItem<Item> SOUL_UPGRADE_BASE_4 = ITEMS.register("soul_upgrade_base_4",
                ()-> new Item(new Item.Properties().stacksTo(16)));
            public static final DeferredItem<Item> SOUL_UPGRADE_BASE_5 = ITEMS.register("soul_upgrade_base_5",
                ()-> new Item(new Item.Properties().stacksTo(16)));


    public static final DeferredItem<Item> SOUL_UPGRADE_CAPACITY_1 = ITEMS.register("soul_upgrade_capacity_1",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_CAPACITY,1));
    public static final DeferredItem<Item> SOUL_UPGRADE_CAPACITY_2 = ITEMS.register("soul_upgrade_capacity_2",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_CAPACITY,2));
    public static final DeferredItem<Item> SOUL_UPGRADE_CAPACITY_3 = ITEMS.register("soul_upgrade_capacity_3",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_CAPACITY,3));
    public static final DeferredItem<Item> SOUL_UPGRADE_CAPACITY_4 = ITEMS.register("soul_upgrade_capacity_4",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_CAPACITY,4));

    public static final DeferredItem<Item> SOUL_UPGRADE_TRANSFER_RATE_1 = ITEMS.register("soul_upgrade_transfer_rate_1",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_TRANSFER_RATE,1));
    public static final DeferredItem<Item> SOUL_UPGRADE_TRANSFER_RATE_2 = ITEMS.register("soul_upgrade_transfer_rate_2",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_TRANSFER_RATE,2));
    public static final DeferredItem<Item> SOUL_UPGRADE_TRANSFER_RATE_3 = ITEMS.register("soul_upgrade_transfer_rate_3",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_TRANSFER_RATE,3));
    public static final DeferredItem<Item> SOUL_UPGRADE_TRANSFER_RATE_4 = ITEMS.register("soul_upgrade_transfer_rate_4",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_TRANSFER_RATE,4));

    public static final DeferredItem<Item> SOUL_UPGRADE_TRANSFER_AMOUNT_1 = ITEMS.register("soul_upgrade_transfer_amount_1",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_TRANSFER_AMOUNT,1));
    public static final DeferredItem<Item> SOUL_UPGRADE_TRANSFER_AMOUNT_2 = ITEMS.register("soul_upgrade_transfer_amount_2",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_TRANSFER_AMOUNT,2));
    public static final DeferredItem<Item> SOUL_UPGRADE_TRANSFER_AMOUNT_3 = ITEMS.register("soul_upgrade_transfer_amount_3",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_TRANSFER_AMOUNT,3));
    public static final DeferredItem<Item> SOUL_UPGRADE_TRANSFER_AMOUNT_4 = ITEMS.register("soul_upgrade_transfer_amount_4",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_TRANSFER_AMOUNT,4));

    public static final DeferredItem<Item> SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_1 = ITEMS.register("soul_upgrade_soul_usage_efficiency_1",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_USAGE_EFFICIENCY,1));
    public static final DeferredItem<Item> SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_2 = ITEMS.register("soul_upgrade_soul_usage_efficiency_2",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_USAGE_EFFICIENCY,2));
    public static final DeferredItem<Item> SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_3 = ITEMS.register("soul_upgrade_soul_usage_efficiency_3",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_USAGE_EFFICIENCY,3));
    public static final DeferredItem<Item> SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_4 = ITEMS.register("soul_upgrade_soul_usage_efficiency_4",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_USAGE_EFFICIENCY,4));

    public static final DeferredItem<Item> SOUL_UPGRADE_SMELT_SPEED_1 = ITEMS.register("soul_upgrade_smelt_speed_1",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_SMELT_SPEED,1));
    public static final DeferredItem<Item> SOUL_UPGRADE_SMELT_SPEED_2 = ITEMS.register("soul_upgrade_smelt_speed_2",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_SMELT_SPEED,2));
    public static final DeferredItem<Item> SOUL_UPGRADE_SMELT_SPEED_3 = ITEMS.register("soul_upgrade_smelt_speed_3",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_SMELT_SPEED,3));
    public static final DeferredItem<Item> SOUL_UPGRADE_SMELT_SPEED_4 = ITEMS.register("soul_upgrade_smelt_speed_4",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_SMELT_SPEED,4));

    public static final DeferredItem<Item> SOUL_UPGRADE_SMELT_AMOUNT_1 = ITEMS.register("soul_upgrade_smelt_amount_1",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_SMELT_AMOUNT,1));
    public static final DeferredItem<Item> SOUL_UPGRADE_SMELT_AMOUNT_2 = ITEMS.register("soul_upgrade_smelt_amount_2",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_SMELT_AMOUNT,2));
    public static final DeferredItem<Item> SOUL_UPGRADE_SMELT_AMOUNT_3 = ITEMS.register("soul_upgrade_smelt_amount_3",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_SMELT_AMOUNT,3));
    public static final DeferredItem<Item> SOUL_UPGRADE_SMELT_AMOUNT_4 = ITEMS.register("soul_upgrade_smelt_amount_4",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(4), UpgradeType.SOUL_SMELT_AMOUNT,4));


    //SPECIAL UPGRADES
    public static final DeferredItem<Item> SOUL_UPGRADE_OVERLOAD = ITEMS.register("soul_upgrade_overload",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(1), UpgradeType.OVERLOAD,5));

    public static final DeferredItem<Item> SOUL_UPGRADE_REDSTONE_CONTROL = ITEMS.register("soul_upgrade_redstone_control",
            ()-> new SoulUpgradeItem(new Item.Properties().stacksTo(1), UpgradeType.REDSTONE,5));


    //TODO: REMOVE THE USELESS GEMS
    //GEMS
    public static final DeferredItem<Item> RUBY_GEM = ITEMS.register("ruby_gem",
            () -> new ModGemItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> TOPAZ_GEM = ITEMS.register("topaz_gem",
            () -> new ModGemItem(new Item.Properties().stacksTo(1)));



    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
