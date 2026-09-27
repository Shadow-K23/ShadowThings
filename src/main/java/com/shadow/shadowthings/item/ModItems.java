package com.shadow.shadowthings.item;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.ModBlocks;
import com.shadow.shadowthings.item.custom.*;
import com.shadow.shadowthings.sound.ModSounds;
import com.shadow.shadowthings.util.ModToolTiers;
import com.shadow.shadowthings.util.UpgradeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.shadow.shadowthings.util.ModToolTiers.SHADOW;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ShadowThings.MODID);


    //BASIC MOD ITEMS
    public static final DeferredItem<Item> SHADOW_INGOT = ITEMS.register("shadow_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_SHADOW_INGOT = ITEMS.register("raw_shadow_steel",
            () -> new Item(new Item.Properties()));

    //CUSTOM WEAPONS

    public static  final DeferredItem<Item> SHADOW_BOW = ITEMS.register("shadow_bow",
            ()-> new BowItem(new Item.Properties().durability(800)));

    public static final DeferredItem<Item> SHADOW_SCYTHE = ITEMS.register("shadow_scythe", () -> {

        // Build the custom modifiers
        ItemAttributeModifiers.Builder modifiers = ItemAttributeModifiers.builder();

        // Standard Damage (e.g., Diamond Tier + 4 extra damage)
        modifiers.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(
                        Item.BASE_ATTACK_DAMAGE_ID, 20.0, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.MAINHAND);

        // Slow Attack Speed (It's a heavy weapon, so we subtract from the player's base speed of 4.0)
        modifiers.add(Attributes.ATTACK_SPEED, new AttributeModifier(
                        Item.BASE_ATTACK_SPEED_ID, -3.0, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.MAINHAND);

        // CUSTOM REACH! Adds 1.5 extra blocks of attack range natively.
        modifiers.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath("shadowthings", "scythe_reach"), 1.5, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.MAINHAND);

        // Register the scythe with these attributes applied
        return new SoulScytheItem(ModToolTiers.SHADOW, new Item.Properties()
                .attributes(modifiers.build()));
    });

    //SHADOW TOOL SET
    public static final DeferredItem<SwordItem> SHADOW_SWORD = ITEMS.register("shadow_sword",
            ()-> new SwordItem(
                    SHADOW,
                    new Item.Properties()
                            .attributes(SwordItem.createAttributes(SHADOW,9f,-2.2f))));

    public static final DeferredItem<PickaxeItem> SHADOW_PICKAXE = ITEMS.register("shadow_pickaxe",
            ()-> new PickaxeItem(
                    SHADOW,
                    new Item.Properties()
                            .attributes(PickaxeItem.createAttributes(SHADOW,-2f,-2.8f))));

    public static final DeferredItem<AxeItem> SHADOW_AXE = ITEMS.register("shadow_axe",
            ()-> new AxeItem(
                    SHADOW,
                    new Item.Properties()
                        .attributes(AxeItem.createAttributes(SHADOW,11f,-3.2f))));

    public static final DeferredItem<ShovelItem> SHADOW_SHOVEL = ITEMS.register("shadow_shovel",
            ()-> new ShovelItem(
                   SHADOW,
                    new Item.Properties()
                            .attributes(SwordItem.createAttributes(SHADOW,-2f,-3.0f))));

    public static final DeferredItem<HoeItem> SHADOW_HOE = ITEMS.register("shadow_hoe",
            ()-> new HoeItem(
                    SHADOW,
                    new Item.Properties()
                            .attributes(SwordItem.createAttributes(SHADOW,-2f,-3.0f))));

    public static final DeferredItem<HammerItem> SHADOW_HAMMER = ITEMS.register("shadow_hammer",
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

    //public static final  DeferredItem<Item> SHADOW_HORSE_ARMOR = ITEMS.register("shadow_horse_armor",
    //            () -> new AnimalArmorItem(
    //                    ModArmorMaterials.SHADOW_ARMOR_MATERIAL, AnimalArmorItem.BodyType.EQUESTRIAN, false , new Item.Properties().stacksTo(1)));
    //MISC

    public static final  DeferredItem<Item> BAR_BRAWL_MUSIC_DISC = ITEMS.register("bar_brawl_music_disc",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.BAR_BRAWL_KEY).stacksTo(1)));

    //FOODS

    public static final DeferredItem<Item> SOUL_FRUIT_1 = ITEMS.register("soul_fruit_1",
            () -> new SoulCapacityItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(3)
                            .saturationModifier(0.25f)
                            .fast()
                            .alwaysEdible()
                            .build()),
                    150, 1750));
    public static final DeferredItem<Item> SOUL_FRUIT_2 = ITEMS.register("soul_fruit_2",
            () -> new SoulCapacityItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(1f)
                            .fast()
                            .alwaysEdible()
                            .build()),
                    250, 4250));
    public static final DeferredItem<Item> SOUL_FRUIT_3 = ITEMS.register("soul_fruit_3",
            () -> new SoulCapacityItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(3)
                            .saturationModifier(2.25f)
                            .fast()
                            .alwaysEdible()
                            .effect(()-> new MobEffectInstance(MobEffects.REGENERATION, 200), 1f)
                            .build()),
                    475, 7500));

    public static final DeferredItem<Item> SHADOW_BERRIES = ITEMS.register("shadow_berry",
            () -> new ItemNameBlockItem(ModBlocks.SHADOW_BERRY_BUSH.get(), new Item.Properties().food(ModFoodProperties.SHADOW_BERRY)));

    //SEEDS


    public static final DeferredItem<Item> RADISH_SEEDS = ITEMS.register("radish_seeds",
            ()-> new ItemNameBlockItem(ModBlocks.RADISH_CROP.get(), new Item.Properties()));

    //FUELS

    //ADVANCED TOOLS
    public static final DeferredItem<Item> SOUL_LINKER = ITEMS.register("soul_linker",
            ()-> new SoulLinkerItem(new Item.Properties().durability(100)));

    //COMPONENTS

    public static final DeferredItem<Item> SOUL_MATRIX = ITEMS.register("soul_matrix",
            () -> new Item(new Item.Properties().stacksTo(1)));

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


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
