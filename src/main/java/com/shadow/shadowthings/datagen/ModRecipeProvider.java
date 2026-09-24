package com.shadow.shadowthings.datagen;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.ModBlocks;
import com.shadow.shadowthings.item.ModItems;
import com.shadow.shadowthings.recipe.SoulInfusionRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        //LISTS OF ITEMS FOR SMELTING
        List<ItemLike> SHADOW_SMELTABLES =
                List.of(
                        ModItems.RAW_SHADOW_INGOT,
                        ModBlocks.SHADOW_ORE,
                        ModBlocks.SHADOW_DEEPSLATE_ORE,
                        ModBlocks.SHADOW_END_ORE,
                        ModBlocks.SHADOW_NETHER_ORE);

        //BLOCK CRAFTING
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SHADOW_BLOCK.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.SHADOW_INGOT.get())
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOW_INGOT)).save(recipeOutput, "shadowthings:shadow_block_craft");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SOUL_STRUCTURE_BLOCK.get(), 4)
                .pattern(" B ")
                .pattern("BCB")
                .pattern(" B ")
                .define('B', ModItems.SHADOW_INGOT.get())
                .define('C', Items.TERRACOTTA)
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOW_INGOT)).save(recipeOutput, "shadowthings:soul_structure_block_craft");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SHADOW_MACHINE_BLOCK.get())
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('B', Items.IRON_INGOT)
                .define('A', Items.REDSTONE)
                .define('C', ModBlocks.SHADOW_BLOCK)
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOW_INGOT)).save(recipeOutput, "shadowthings:soul_machine_block_craft");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SOUL_CORE.get())
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('B', ModItems.SHADOW_INGOT)
                .define('A', Items.DIAMOND)
                .define('C', ModItems.SOUL_MATRIX)
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOW_INGOT)).save(recipeOutput, "shadowthings:soul_core_craft");


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SOUL_CONDENSER.get())
                .pattern("ABA")
                .pattern("DCD")
                .pattern("ABA")
                .define('B', ModItems.SHADOW_INGOT)
                .define('C', ModBlocks.SHADOW_MACHINE_BLOCK)
                .define('A', Items.IRON_INGOT)
                .define('D', Items.GOLD_INGOT)
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOW_INGOT)).save(recipeOutput, "shadowthings:soul_condenser_craft");


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SOUL_CRUCIBLE.get())
                .pattern("ABA")
                .pattern("DCD")
                .pattern("ABA")
                .define('B', ModItems.SHADOW_INGOT)
                .define('C', ModBlocks.SHADOW_MACHINE_BLOCK)
                .define('A', Items.GOLD_INGOT)
                .define('D', Items.MAGMA_BLOCK)
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOW_INGOT)).save(recipeOutput, "shadowthings:soul_crucible_craft");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SOUL_PEDESTAL.get())
                .pattern("CDC")
                .pattern(" C ")
                .pattern("BBB")
                .define('B', Items.DEEPSLATE_BRICKS)
                .define('D', Items.BLUE_WOOL)
                .define('C', ModItems.SHADOW_INGOT)
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOW_INGOT)).save(recipeOutput, "shadowthings:soul_pedestal_craft");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SOUL_CRAFTER.get())
                .pattern("BAB")
                .pattern("DCD")
                .pattern("BAB")
                .define('B', ModItems.SHADOW_INGOT)
                .define('C', ModItems.SOUL_MATRIX)
                .define('A', Items.GOLD_INGOT)
                .define('D', Items.DIAMOND)
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOW_INGOT)).save(recipeOutput, "shadowthings:soul_crafter_craft");

        //COMPONENTS
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SOUL_MATRIX.get())
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('B', ModItems.SHADOW_INGOT)
                .define('A', Items.REDSTONE)
                .define('C', Items.ENDER_PEARL)
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOW_INGOT)).save(recipeOutput, "shadowthings:soul_matrix_craft");

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SOUL_LINKER.get())
                .pattern(" BC")
                .pattern(" AB")
                .pattern("A  ")
                .define('B', ModItems.SHADOW_INGOT)
                .define('A', Items.STICK)
                .define('C', Items.ENDER_PEARL)
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOW_INGOT)).save(recipeOutput, "shadowthings:soul_linker_craft");



        //UPGRADES
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SOUL_UPGRADE_BASE_1.get(), 4)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('B', ModItems.SHADOW_INGOT)
                .define('A', Items.IRON_NUGGET)
                .define('C', ItemTags.PLANKS)
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOW_INGOT)).save(recipeOutput, "shadowthings:upgrade_base_1_craft");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SOUL_UPGRADE_BASE_2.get(), 4)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('B', ModItems.SHADOW_INGOT)
                .define('A', Items.IRON_INGOT)
                .define('C', ItemTags.PLANKS)
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOW_INGOT)).save(recipeOutput, "shadowthings:upgrade_base_2_craft");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SOUL_UPGRADE_BASE_3.get(), 4)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('B', ModItems.SHADOW_INGOT)
                .define('A', Items.GOLD_NUGGET)
                .define('C', ItemTags.PLANKS)
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOW_INGOT)).save(recipeOutput, "shadowthings:upgrade_base_3_craft");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SOUL_UPGRADE_BASE_4.get(), 4)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('B', ModItems.SHADOW_INGOT)
                .define('A', Items.GOLD_INGOT)
                .define('C', ItemTags.PLANKS)
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOW_INGOT)).save(recipeOutput, "shadowthings:upgrade_base_4_craft");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SOUL_UPGRADE_BASE_5.get(), 4)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('B', ModItems.SHADOW_INGOT)
                .define('A', Items.DIAMOND)
                .define('C', ItemTags.PLANKS)
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOW_INGOT)).save(recipeOutput, "shadowthings:upgrade_base_5_craft");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SOUL_UPGRADE_CAPACITY_1.get())
                .pattern(" A ")
                .pattern("ACA")
                .pattern(" A ")
                .define('A', Items.AMETHYST_SHARD)
                .define('C', ModItems.SOUL_UPGRADE_BASE_1)
                .unlockedBy("has_upgrade_base_1",has(ModItems.SOUL_UPGRADE_BASE_1)).save(recipeOutput, "shadowthings:upgrade_capacity_1_craft");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SOUL_UPGRADE_TRANSFER_RATE_1.get())
                .pattern(" A ")
                .pattern("ACA")
                .pattern(" A ")
                .define('A', Items.SUGAR)
                .define('C', ModItems.SOUL_UPGRADE_BASE_1)
                .unlockedBy("has_upgrade_base_1",has(ModItems.SOUL_UPGRADE_BASE_1)).save(recipeOutput, "shadowthings:upgrade_transfer_rate_1_craft");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SOUL_UPGRADE_TRANSFER_AMOUNT_1.get())
                .pattern(" A ")
                .pattern("ACA")
                .pattern(" A ")
                .define('A', Items.REDSTONE)
                .define('C', ModItems.SOUL_UPGRADE_BASE_1)
                .unlockedBy("has_upgrade_base_1",has(ModItems.SOUL_UPGRADE_BASE_1)).save(recipeOutput, "shadowthings:upgrade_transfer_amount_1_craft");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_1.get())
                .pattern(" A ")
                .pattern("ACA")
                .pattern(" A ")
                .define('A', Items.QUARTZ)
                .define('C', ModItems.SOUL_UPGRADE_BASE_1)
                .unlockedBy("has_upgrade_base_1",has(ModItems.SOUL_UPGRADE_BASE_1)).save(recipeOutput, "shadowthings:upgrade_soul_efficiency_1_craft");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SOUL_UPGRADE_SMELT_SPEED_1.get())
                .pattern(" A ")
                .pattern("BCB")
                .pattern(" A ")
                .define('A', Items.LAPIS_LAZULI)
                .define('B', Items.SUGAR)
                .define('C', ModItems.SOUL_UPGRADE_BASE_1)
                .unlockedBy("has_upgrade_base_1",has(ModItems.SOUL_UPGRADE_BASE_1)).save(recipeOutput, "shadowthings:upgrade_smelt_speed_1_craft");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SOUL_UPGRADE_SMELT_AMOUNT_1.get())
                .pattern(" A ")
                .pattern("BCB")
                .pattern(" A ")
                .define('A', Items.LAPIS_LAZULI)
                .define('B', Items.REDSTONE)
                .define('C', ModItems.SOUL_UPGRADE_BASE_1)
                .unlockedBy("has_upgrade_base_1",has(ModItems.SOUL_UPGRADE_BASE_1)).save(recipeOutput, "shadowthings:upgrade_smelt_amount_1_craft");
        //FOOD

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.SOUL_FRUIT_2.get())
                .pattern(" A ")
                .pattern("BCB")
                .pattern(" A ")
                .define('A', Items.LAPIS_LAZULI)
                .define('B', Items.REDSTONE)
                .define('C', ModItems.SOUL_FRUIT_1)
                .unlockedBy("has_soul_fruit_1",has(ModItems.SOUL_FRUIT_1)).save(recipeOutput, "shadowthings:soul_fruit_2_craft");

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.SOUL_FRUIT_3.get())
                .pattern(" A ")
                .pattern("BCB")
                .pattern(" A ")
                .define('A', Items.DIAMOND)
                .define('B', Items.AMETHYST_SHARD)
                .define('C', ModItems.SOUL_FRUIT_2)
                .unlockedBy("has_soul_fruit_1",has(ModItems.SOUL_FRUIT_1)).save(recipeOutput, "shadowthings:soul_fruit_3_craft");


        //SOUL CRAFTER RECIPES

        //TOOL RECIPES
        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings", "shadow_sword_infusion"), // 1. The unique JSON file name
                new SoulInfusionRecipe(
                        Ingredient.of(Items.NETHERITE_SWORD),                                // 2. The Catalyst (Center item)
                        List.of(                                                        // 3. The Pedestal Ingredients
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(Items.OBSIDIAN),
                                Ingredient.of(Items.DIAMOND)
                        ),
                        1500,                                                           // 4. The Soul Cost
                        new ItemStack(ModItems.SHADOW_SWORD.get())                            // 5. The Result
                ),
                null
        );

        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings", "shadow_pickaxe_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(Items.NETHERITE_PICKAXE),
                        List.of(
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(Items.OBSIDIAN),
                                Ingredient.of(Items.DIAMOND)
                        ),
                        1500,
                        new ItemStack(ModItems.SHADOW_PICKAXE.get())
                ),
                null
        );

        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings", "shadow_axe_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(Items.NETHERITE_AXE),
                        List.of(
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(Items.OBSIDIAN),
                                Ingredient.of(Items.DIAMOND)
                        ),
                        1500,
                        new ItemStack(ModItems.SHADOW_AXE.get())
                ),
                null
        );

        //ADVANCED TOOLS

        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","shadow_hammer_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SHADOW_PICKAXE),
                        List.of(
                                Ingredient.of(ModBlocks.SHADOW_BLOCK),
                                Ingredient.of(ModBlocks.SHADOW_BLOCK),
                                Ingredient.of(Items.OBSIDIAN),
                                Ingredient.of(Items.DIAMOND),
                                Ingredient.of(Items.DIAMOND)
                        ),
                        1750,
                        new ItemStack(ModItems.SHADOW_HAMMER.get())
                ),
                null
        );

        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","shadow_shovel_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(Items.NETHERITE_SHOVEL),
                        List.of(
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(Items.OBSIDIAN),
                                Ingredient.of(Items.DIAMOND)
                        ),
                        1500,
                        new ItemStack(ModItems.SHADOW_SHOVEL.get())
                ),
                null
        );

        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","shadow_hoe_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(Items.NETHERITE_HOE),
                        List.of(
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(Items.OBSIDIAN),
                                Ingredient.of(Items.DIAMOND)
                        ),
                        1500,
                        new ItemStack(ModItems.SHADOW_HOE.get())
                ),
                null
        );

        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","shadow_scythe_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(Items.NETHERITE_SWORD),
                        List.of(
                                Ingredient.of(ModBlocks.SHADOW_BLOCK),
                                Ingredient.of(ModBlocks.SHADOW_BLOCK),
                                Ingredient.of(Items.OBSIDIAN),
                                Ingredient.of(Items.DIAMOND),
                                Ingredient.of(Items.DIAMOND)
                        ),
                        1750,
                        new ItemStack(ModItems.SHADOW_SCYTHE.get())
                ),
                null
        );

        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","shadow_bow_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(Items.BOW),
                        List.of(
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(Items.OBSIDIAN),
                                Ingredient.of(Items.DIAMOND)
                        ),
                        1500,
                        new ItemStack(ModItems.SHADOW_BOW.get())
                ),
                null
        );

        //ARMOR

        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","shadow_helmet_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(Items.NETHERITE_HELMET),
                        List.of(
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(Items.OBSIDIAN),
                                Ingredient.of(Items.DIAMOND),
                                Ingredient.of(Items.DIAMOND)
                        ),
                        2250,
                        new ItemStack(ModItems.SHADOW_HELMET.get())
                ),
                null
        );

        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","shadow_chestplate_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(Items.NETHERITE_CHESTPLATE),
                        List.of(
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(Items.OBSIDIAN),
                                Ingredient.of(Items.DIAMOND),
                                Ingredient.of(Items.DIAMOND)
                        ),
                        2250,
                        new ItemStack(ModItems.SHADOW_CHESTPLATE.get())
                ),
                null
        );

        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","shadow_leggings_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(Items.NETHERITE_LEGGINGS),
                        List.of(
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(Items.OBSIDIAN),
                                Ingredient.of(Items.DIAMOND),
                                Ingredient.of(Items.DIAMOND)
                        ),
                        2250,
                        new ItemStack(ModItems.SHADOW_LEGGINGS.get())
                ),
                null
        );

        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","shadow_boots_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(Items.NETHERITE_BOOTS),
                        List.of(
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(Items.OBSIDIAN),
                                Ingredient.of(Items.DIAMOND),
                                Ingredient.of(Items.DIAMOND)
                        ),
                        2250,
                        new ItemStack(ModItems.SHADOW_BOOTS.get())
                ),
                null
        );

        //COMPONENTS & MULTIBLOCK PARTS

        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_furnace_controller_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SOUL_MATRIX),
                        List.of(
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(Items.COAL_BLOCK),
                                Ingredient.of(Items.COAL_BLOCK),
                                Ingredient.of(Items.MAGMA_BLOCK),
                                Ingredient.of(Items.MAGMA_BLOCK)
                        ),
                        3500,
                        new ItemStack(ModBlocks.SOUL_FURNACE_CONTROLLER.get())
                ),
                null
        );

        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_collector_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModBlocks.SHADOW_MACHINE_BLOCK),
                        List.of(
                                Ingredient.of(ModItems.SOUL_MATRIX),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(Items.ENDER_PEARL),
                                Ingredient.of(Items.ENDER_PEARL),
                                Ingredient.of(Items.DIAMOND),
                                Ingredient.of(Items.DIAMOND)
                        ),
                        1500,
                        new ItemStack(ModBlocks.SOUL_COLLECTOR.get())
                ),
                null
        );
        //UPGRADES TIER 2-4
        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_upgrade_capacity_2_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SOUL_UPGRADE_BASE_2),
                        List.of(
                                Ingredient.of(ModItems.SOUL_UPGRADE_CAPACITY_1),
                                Ingredient.of(Items.AMETHYST_SHARD),
                                Ingredient.of(Items.AMETHYST_SHARD),
                                Ingredient.of(Items.DIAMOND)
                        ),
                        650,
                        new ItemStack(ModItems.SOUL_UPGRADE_CAPACITY_2.get())
                ),
                null
        );
        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_upgrade_capacity_3_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SOUL_UPGRADE_BASE_3),
                        List.of(
                                Ingredient.of(ModItems.SOUL_UPGRADE_CAPACITY_2),
                                Ingredient.of(Items.AMETHYST_SHARD),
                                Ingredient.of(Items.AMETHYST_SHARD),
                                Ingredient.of(Items.DIAMOND),
                                Ingredient.of(Items.ENDER_PEARL)
                        ),
                        1250,
                        new ItemStack(ModItems.SOUL_UPGRADE_CAPACITY_3.get())
                ),
                null
        );
        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_upgrade_capacity_4_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SOUL_UPGRADE_BASE_4),
                        List.of(
                                Ingredient.of(ModItems.SOUL_UPGRADE_CAPACITY_3),
                                Ingredient.of(Items.AMETHYST_SHARD),
                                Ingredient.of(Items.AMETHYST_SHARD),
                                Ingredient.of(Items.DIAMOND),
                                Ingredient.of(Items.ENDER_PEARL),
                                Ingredient.of(Items.NETHERITE_INGOT)
                        ),
                        2000,
                        new ItemStack(ModItems.SOUL_UPGRADE_CAPACITY_4.get())
                ),
                null
        );

        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_upgrade_transfer_rate_2_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SOUL_UPGRADE_BASE_2),
                        List.of(
                                Ingredient.of(ModItems.SOUL_UPGRADE_TRANSFER_RATE_1),
                                Ingredient.of(Items.SUGAR),
                                Ingredient.of(Items.SUGAR),
                                Ingredient.of(Items.DIAMOND)
                        ),
                        650,
                        new ItemStack(ModItems.SOUL_UPGRADE_TRANSFER_RATE_2.get())
                ),
                null
        );
        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_upgrade_transfer_rate_3_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SOUL_UPGRADE_BASE_3),
                        List.of(
                                Ingredient.of(ModItems.SOUL_UPGRADE_TRANSFER_RATE_2),
                                Ingredient.of(Items.SUGAR),
                                Ingredient.of(Items.SUGAR),
                                Ingredient.of(Items.DIAMOND),
                                Ingredient.of(Items.ENDER_PEARL)
                        ),
                        1250,
                        new ItemStack(ModItems.SOUL_UPGRADE_TRANSFER_RATE_3.get())
                ),
                null
        );
        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_upgrade_transfer_rate_4_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SOUL_UPGRADE_BASE_4),
                        List.of(
                                Ingredient.of(ModItems.SOUL_UPGRADE_TRANSFER_RATE_3),
                                Ingredient.of(Items.SUGAR),
                                Ingredient.of(Items.SUGAR),
                                Ingredient.of(Items.DIAMOND),
                                Ingredient.of(Items.ENDER_PEARL),
                                Ingredient.of(Items.NETHERITE_INGOT)
                        ),
                        2000,
                        new ItemStack(ModItems.SOUL_UPGRADE_TRANSFER_RATE_4.get())
                ),
                null
        );

        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_upgrade_transfer_amount_2_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SOUL_UPGRADE_BASE_2),
                        List.of(
                                Ingredient.of(ModItems.SOUL_UPGRADE_TRANSFER_AMOUNT_1),
                                Ingredient.of(Items.REDSTONE),
                                Ingredient.of(Items.REDSTONE),
                                Ingredient.of(Items.DIAMOND)
                        ),
                        650,
                        new ItemStack(ModItems.SOUL_UPGRADE_TRANSFER_AMOUNT_2.get())
                ),
                null
        );
        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_upgrade_transfer_amount_3_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SOUL_UPGRADE_BASE_3),
                        List.of(
                                Ingredient.of(ModItems.SOUL_UPGRADE_TRANSFER_AMOUNT_2),
                                Ingredient.of(Items.REDSTONE),
                                Ingredient.of(Items.REDSTONE),
                                Ingredient.of(Items.DIAMOND),
                                Ingredient.of(Items.ENDER_PEARL)
                        ),
                        1250,
                        new ItemStack(ModItems.SOUL_UPGRADE_TRANSFER_AMOUNT_3.get())
                ),
                null
        );
        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_upgrade_transfer_amount_4_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SOUL_UPGRADE_BASE_4),
                        List.of(
                                Ingredient.of(ModItems.SOUL_UPGRADE_TRANSFER_AMOUNT_3),
                                Ingredient.of(Items.REDSTONE),
                                Ingredient.of(Items.REDSTONE),
                                Ingredient.of(Items.DIAMOND),
                                Ingredient.of(Items.ENDER_PEARL),
                                Ingredient.of(Items.NETHERITE_INGOT)
                        ),
                        2000,
                        new ItemStack(ModItems.SOUL_UPGRADE_TRANSFER_AMOUNT_4.get())
                ),
                null
        );

        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_upgrade_soul_efficiency_2_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SOUL_UPGRADE_BASE_2),
                        List.of(
                                Ingredient.of(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_1),
                                Ingredient.of(Items.QUARTZ),
                                Ingredient.of(Items.QUARTZ),
                                Ingredient.of(Items.DIAMOND)
                        ),
                        650,
                        new ItemStack(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_2.get())
                ),
                null
        );
        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_upgrade_soul_efficiency_3_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SOUL_UPGRADE_BASE_3),
                        List.of(
                                Ingredient.of(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_2),
                                Ingredient.of(Items.QUARTZ),
                                Ingredient.of(Items.QUARTZ),
                                Ingredient.of(Items.DIAMOND),
                                Ingredient.of(Items.ENDER_PEARL)
                        ),
                        1250,
                        new ItemStack(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_3.get())
                ),
                null
        );
        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_upgrade_soul_efficiency_4_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SOUL_UPGRADE_BASE_4),
                        List.of(
                                Ingredient.of(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_3),
                                Ingredient.of(Items.QUARTZ),
                                Ingredient.of(Items.QUARTZ),
                                Ingredient.of(Items.DIAMOND),
                                Ingredient.of(Items.ENDER_PEARL),
                                Ingredient.of(Items.NETHERITE_INGOT)
                        ),
                        2000,
                        new ItemStack(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_4.get())
                ),
                null
        );

        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_upgrade_smelt_speed_2_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SOUL_UPGRADE_BASE_2),
                        List.of(
                                Ingredient.of(ModItems.SOUL_UPGRADE_SMELT_SPEED_1),
                                Ingredient.of(Items.LAPIS_LAZULI),
                                Ingredient.of(Items.LAPIS_LAZULI),
                                Ingredient.of(Items.DIAMOND)
                        ),
                        650,
                        new ItemStack(ModItems.SOUL_UPGRADE_SMELT_SPEED_2.get())
                ),
                null
        );
        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_upgrade_smelt_speed_3_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SOUL_UPGRADE_BASE_3),
                        List.of(
                                Ingredient.of(ModItems.SOUL_UPGRADE_SMELT_SPEED_2),
                                Ingredient.of(Items.LAPIS_LAZULI),
                                Ingredient.of(Items.LAPIS_LAZULI),
                                Ingredient.of(Items.DIAMOND),
                                Ingredient.of(Items.ENDER_PEARL)
                        ),
                        1250,
                        new ItemStack(ModItems.SOUL_UPGRADE_SMELT_SPEED_3.get())
                ),
                null
        );
        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_upgrade_smelt_speed_4_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SOUL_UPGRADE_BASE_4),
                        List.of(
                                Ingredient.of(ModItems.SOUL_UPGRADE_SMELT_SPEED_3),
                                Ingredient.of(Items.LAPIS_LAZULI),
                                Ingredient.of(Items.LAPIS_LAZULI),
                                Ingredient.of(Items.DIAMOND),
                                Ingredient.of(Items.ENDER_PEARL),
                                Ingredient.of(Items.NETHERITE_INGOT)
                        ),
                        2000,
                        new ItemStack(ModItems.SOUL_UPGRADE_SMELT_SPEED_4.get())
                ),
                null
        );

        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_upgrade_smelt_amount_2_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SOUL_UPGRADE_BASE_2),
                        List.of(
                                Ingredient.of(ModItems.SOUL_UPGRADE_SMELT_AMOUNT_1),
                                Ingredient.of(Items.LAPIS_LAZULI),
                                Ingredient.of(Items.REDSTONE),
                                Ingredient.of(Items.DIAMOND)
                        ),
                        650,
                        new ItemStack(ModItems.SOUL_UPGRADE_SMELT_AMOUNT_2.get())
                ),
                null
        );
        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_upgrade_smelt_amount_3_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SOUL_UPGRADE_BASE_3),
                        List.of(
                                Ingredient.of(ModItems.SOUL_UPGRADE_SMELT_AMOUNT_2),
                                Ingredient.of(Items.LAPIS_LAZULI),
                                Ingredient.of(Items.REDSTONE),
                                Ingredient.of(Items.DIAMOND),
                                Ingredient.of(Items.ENDER_PEARL)
                        ),
                        1250,
                        new ItemStack(ModItems.SOUL_UPGRADE_SMELT_AMOUNT_3.get())
                ),
                null
        );
        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_upgrade_smelt_amount_4_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SOUL_UPGRADE_BASE_4),
                        List.of(
                                Ingredient.of(ModItems.SOUL_UPGRADE_SMELT_AMOUNT_3),
                                Ingredient.of(Items.LAPIS_LAZULI),
                                Ingredient.of(Items.REDSTONE),
                                Ingredient.of(Items.DIAMOND),
                                Ingredient.of(Items.ENDER_PEARL),
                                Ingredient.of(Items.NETHERITE_INGOT)
                        ),
                        2000,
                        new ItemStack(ModItems.SOUL_UPGRADE_SMELT_AMOUNT_4.get())
                ),
                null
        );

        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_upgrade_redstone_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SOUL_UPGRADE_BASE_5),
                        List.of(
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(ModItems.SHADOW_INGOT),
                                Ingredient.of(Items.REDSTONE_BLOCK),
                                Ingredient.of(Items.LEVER)
                        ),
                        1750,
                        new ItemStack(ModItems.SOUL_UPGRADE_REDSTONE_CONTROL.get())
                ),
                null
        );
        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath("shadowthings","soul_upgrade_overload_infusion"),
                new SoulInfusionRecipe(
                        Ingredient.of(ModItems.SOUL_UPGRADE_BASE_5),
                        List.of(
                                Ingredient.of(ModItems.SOUL_MATRIX),
                                Ingredient.of(ModItems.SOUL_MATRIX),
                                Ingredient.of(ModBlocks.SHADOW_BLOCK),
                                Ingredient.of(Items.NETHER_STAR),
                                Ingredient.of(Items.NETHERITE_INGOT),
                                Ingredient.of(Items.NETHERITE_INGOT)
                        ),
                        7500,
                        new ItemStack(ModItems.SOUL_UPGRADE_OVERLOAD.get())
                ),
                null
        );


        //NON-BLOCK BLOCKS

        stairBuilder(ModBlocks.SHADOW_STAIRS.get(), Ingredient.of(ModBlocks.SHADOWWOOD_PLANKS)).group("shadow")
                .unlockedBy("has_shadow_planks",has(ModBlocks.SHADOWWOOD_PLANKS)).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SHADOW_SLAB.get(), ModBlocks.SHADOWWOOD_PLANKS.get());

        buttonBuilder(ModBlocks.SHADOW_BUTTON.get(), Ingredient.of(ModBlocks.SHADOWWOOD_PLANKS.get())).group("shadow")
                .unlockedBy("has_shadow_planks", has(ModBlocks.SHADOWWOOD_PLANKS.get())).save(recipeOutput);

        pressurePlate(recipeOutput, ModBlocks.SHADOW_PRESSURE_PLATE.get(), ModBlocks.SHADOWWOOD_PLANKS.get());

        fenceBuilder(ModBlocks.SHADOW_FENCE.get(), Ingredient.of(ModBlocks.SHADOWWOOD_PLANKS.get())).group("shadow")
                .unlockedBy("has_shadow_planks", has(ModBlocks.SHADOWWOOD_PLANKS.get())).save(recipeOutput);

        fenceGateBuilder(ModBlocks.SHADOW_FENCE_GATE.get(), Ingredient.of(ModBlocks.SHADOWWOOD_PLANKS.get())).group("shadow")
                .unlockedBy("has_shadow_planks", has(ModBlocks.SHADOWWOOD_PLANKS.get())).save(recipeOutput);

        doorBuilder(ModBlocks.SHADOW_DOOR.get(), Ingredient.of(ModBlocks.SHADOWWOOD_PLANKS.get())).group("shadow")
                .unlockedBy("has_shadow_planks", has(ModBlocks.SHADOWWOOD_PLANKS.get())).save(recipeOutput);

        trapdoorBuilder(ModBlocks.SHADOW_TRAPDOOR.get(), Ingredient.of(ModBlocks.SHADOWWOOD_PLANKS.get())).group("shadow")
                .unlockedBy("has_shadow_planks", has(ModBlocks.SHADOWWOOD_PLANKS.get())).save(recipeOutput);

        //SHAPELESS
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ModItems.SHADOW_INGOT,9)
                .requires(ModBlocks.SHADOW_BLOCK)
                .unlockedBy("has_shadow_block",has(ModBlocks.SHADOW_BLOCK)).save(recipeOutput,"shadowthings:shadow_ingot_craft");

        oreSmelting(recipeOutput, SHADOW_SMELTABLES,RecipeCategory.MISC,ModItems.SHADOW_INGOT.get(), 0.25f,200, "shadow_steel");
        oreBlasting(recipeOutput, SHADOW_SMELTABLES,RecipeCategory.MISC,ModItems.SHADOW_INGOT.get(), 0.25f,100, "shadow_steel");
    }
    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, ShadowThings.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
