package com.shadow.shadowthings.datagen;

import com.shadow.shadowthings.block.ModBlocks;
import com.shadow.shadowthings.block.custom.RadishCropBlock;
import com.shadow.shadowthings.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.fml.common.Mod;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider( HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        //SELF DROPPING BLOCKS
        dropSelf(ModBlocks.SHADOW_BLOCK.get());
        dropSelf(ModBlocks.SOUL_STRUCTURE_BLOCK.get());
        dropSelf(ModBlocks.SOUL_CORE.get());
        dropSelf(ModBlocks.SOUL_CRAFTER.get());
        dropSelf(ModBlocks.SOUL_PEDESTAL.get());
        dropSelf(ModBlocks.SOUL_FURNACE_CONTROLLER.get());
        dropSelf(ModBlocks.SOUL_CONDENSER.get());
        dropSelf(ModBlocks.SOUL_CRUCIBLE.get());
        dropSelf(ModBlocks.SOUL_COLLECTOR.get());
        dropSelf(ModBlocks.SHADOW_MACHINE_BLOCK.get());

        //TREE
        this.dropSelf(ModBlocks.SHADOWWOOD_LOG.get());
        this.dropSelf(ModBlocks.SHADOWWOOD_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_SHADOWWOOD_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_SHADOWWOOD_WOOD.get());
        this.dropSelf(ModBlocks.SHADOWWOOD_PLANKS.get());
        this.dropSelf(ModBlocks.SHADOWWOOD_SAPLING.get());

        this.add(ModBlocks.SHADOWWOOD_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.SHADOWWOOD_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        //NON-BLOCK BLOCKS
        dropSelf(ModBlocks.SHADOW_STAIRS.get());
        add(ModBlocks.SHADOW_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.SHADOW_SLAB.get()));

        dropSelf(ModBlocks.SHADOW_BUTTON.get());
        dropSelf(ModBlocks.SHADOW_PRESSURE_PLATE.get());

        dropSelf(ModBlocks.SHADOW_FENCE.get());
        dropSelf(ModBlocks.SHADOW_FENCE_GATE.get());
        dropSelf(ModBlocks.SHADOW_TRAPDOOR.get());

        add(ModBlocks.SHADOW_DOOR.get(),
                block -> createDoorTable(ModBlocks.SHADOW_DOOR.get()));

        //ORE LOOT TABLES
        add(ModBlocks.SHADOW_ORE.get(),
                block -> createOreDrop(ModBlocks.SHADOW_ORE.get(), ModItems.RAWSHADOWINGOT.get()));
        add(ModBlocks.SHADOW_DEEPSLATE_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.SHADOW_DEEPSLATE_ORE.get(),ModItems.RAWSHADOWINGOT.get(),2f,5f));
        add(ModBlocks.SHADOW_NETHER_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.SHADOW_NETHER_ORE.get(),ModItems.RAWSHADOWINGOT.get(),3f,7f));
        add(ModBlocks.SHADOW_END_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.SHADOW_END_ORE.get(),ModItems.RAWSHADOWINGOT.get(),4f,9f));

        //CROP LOOT TABLES


        LootItemCondition.Builder lootItemConditionBuilder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.RADISH_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RadishCropBlock.AGE, 3));

        this.add(ModBlocks.RADISH_CROP.get(), this.createCropDrops(ModBlocks.RADISH_CROP.get(),
                ModItems.DRAGON_FRUIT.get(), ModItems.RADISH_SEEDS.get(), lootItemConditionBuilder));

        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        this.add(ModBlocks.SHADOW_BERRY_BUSH.get(), block -> this.applyExplosionDecay(
                block,LootTable.lootTable().withPool(LootPool.lootPool().when(
                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.SHADOW_BERRY_BUSH.get())
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 3))
                                ).add(LootItem.lootTableItem(ModItems.SHADOW_BERRIES.get()))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                ).withPool(LootPool.lootPool().when(
                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.SHADOW_BERRY_BUSH.get())
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 2))
                                ).add(LootItem.lootTableItem(ModItems.SHADOW_BERRIES.get()))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                )));

    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock,
                LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
