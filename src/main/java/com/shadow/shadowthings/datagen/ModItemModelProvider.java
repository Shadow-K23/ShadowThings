package com.shadow.shadowthings.datagen;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.ModBlocks;
import com.shadow.shadowthings.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {

    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }



    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ShadowThings.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //BASIC ITEMS
        basicItem(ModItems.RAWSHADOWINGOT.get());
        basicItem(ModItems.SHADOWINGOT.get());

        //SOUL UPGRADES
        basicItem(ModItems.SOUL_UPGRADE_BASE_1.get());
        basicItem(ModItems.SOUL_UPGRADE_BASE_2.get());
        basicItem(ModItems.SOUL_UPGRADE_BASE_3.get());
        basicItem(ModItems.SOUL_UPGRADE_BASE_4.get());
        basicItem(ModItems.SOUL_UPGRADE_BASE_5.get());

        basicItem(ModItems.SOUL_UPGRADE_CAPACITY_1.get());
        basicItem(ModItems.SOUL_UPGRADE_CAPACITY_2.get());
        basicItem(ModItems.SOUL_UPGRADE_CAPACITY_3.get());
        basicItem(ModItems.SOUL_UPGRADE_CAPACITY_4.get());

        basicItem(ModItems.SOUL_UPGRADE_TRANSFER_AMOUNT_1.get());
        basicItem(ModItems.SOUL_UPGRADE_TRANSFER_AMOUNT_2.get());
        basicItem(ModItems.SOUL_UPGRADE_TRANSFER_AMOUNT_3.get());
        basicItem(ModItems.SOUL_UPGRADE_TRANSFER_AMOUNT_4.get());

        basicItem(ModItems.SOUL_UPGRADE_TRANSFER_RATE_1.get());
        basicItem(ModItems.SOUL_UPGRADE_TRANSFER_RATE_2.get());
        basicItem(ModItems.SOUL_UPGRADE_TRANSFER_RATE_3.get());
        basicItem(ModItems.SOUL_UPGRADE_TRANSFER_RATE_4.get());

        basicItem(ModItems.SOUL_UPGRADE_SMELT_AMOUNT_1.get());
        basicItem(ModItems.SOUL_UPGRADE_SMELT_AMOUNT_2.get());
        basicItem(ModItems.SOUL_UPGRADE_SMELT_AMOUNT_3.get());
        basicItem(ModItems.SOUL_UPGRADE_SMELT_AMOUNT_4.get());

        basicItem(ModItems.SOUL_UPGRADE_SMELT_SPEED_1.get());
        basicItem(ModItems.SOUL_UPGRADE_SMELT_SPEED_2.get());
        basicItem(ModItems.SOUL_UPGRADE_SMELT_SPEED_3.get());
        basicItem(ModItems.SOUL_UPGRADE_SMELT_SPEED_4.get());

        basicItem(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_1.get());
        basicItem(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_2.get());
        basicItem(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_3.get());
        basicItem(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_4.get());

        basicItem(ModItems.SOUL_UPGRADE_REDSTONE_CONTROL.get());
        basicItem(ModItems.SOUL_UPGRADE_OVERLOAD.get());


        //COMPONENTS

        basicItem(ModItems.SOUL_MATRIX.get());

        //NON-BLOCK BLOCKS
        buttonItem(ModBlocks.SHADOW_BUTTON, ModBlocks.SHADOWWOOD_PLANKS);
        fenceItem(ModBlocks.SHADOW_FENCE, ModBlocks.SHADOWWOOD_PLANKS);

        basicItem(ModBlocks.SHADOW_DOOR.asItem());

        //TOOLS
        handheldItem(ModItems.SHADOWSWORD);
        handheldItem(ModItems.SHADOWPICKAXE);
        handheldItem(ModItems.SHADOWAXE);
        handheldItem(ModItems.SHADOWHOE);
        handheldItem(ModItems.SHADOWSHOVEL);
        handheldItem(ModItems.SHADOWSPEAR);

        handheldItem(ModItems.SHADOW_SCYTHE);

        //ADVANCED ITEMS
        handheldItem(ModItems.SOUL_LINKER);
        handheldItem(ModItems.SHADOWHAMMER);

        withExistingParent(ModBlocks.SOUL_CRAFTER.getId().getPath(), modLoc("block/soul_crafter"));
        withExistingParent(ModBlocks.SOUL_PEDESTAL.getId().getPath(), modLoc("block/soul_pedestal"));

        //ARMOR
        trimmedArmorItem(ModItems.SHADOW_BOOTS);
        trimmedArmorItem(ModItems.SHADOW_LEGGINGS);
        trimmedArmorItem(ModItems.SHADOW_CHESTPLATE);
        trimmedArmorItem(ModItems.SHADOW_HELMET);

        basicItem(ModItems.SHADOW_HORSE_ARMOR.get());

        //FOOD
        basicItem(ModItems.DRAGON_FRUIT.get());
        basicItem(ModItems.SHADOW_BERRIES.get());
        //SEEDS
        basicItem(ModItems.RADISH_SEEDS.get());
        //FUEL
        basicItem(ModItems.SUPER_FUEL.get());
        //MISC
        basicItem(ModItems.BAR_BRAWL_MUSIC_DISC.get());

        saplingItem(ModBlocks.SHADOWWOOD_SAPLING);
    }


    private ItemModelBuilder saplingItem(DeferredBlock<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID,"block/" + item.getId().getPath()));
    }
    // Shoutout to El_Redstoniano for making this
    private void trimmedArmorItem(DeferredItem<ArmorItem> itemDeferredItem) {
        final String MOD_ID = ShadowThings.MODID; // Change this to your mod id

        if(itemDeferredItem.get() instanceof ArmorItem armorItem) {
            trimMaterials.forEach((trimMaterial, value) -> {
                float trimValue = value;

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = armorItem.toString();
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemDeferredItem.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc.getNamespace()  + ":item/" + trimNameResLoc.getPath()))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                ResourceLocation.fromNamespaceAndPath(MOD_ID,
                                        "item/" + itemDeferredItem.getId().getPath()));
            });
        }
    }

    public void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID,"item/" + item.getId().getPath()));
    }
}
