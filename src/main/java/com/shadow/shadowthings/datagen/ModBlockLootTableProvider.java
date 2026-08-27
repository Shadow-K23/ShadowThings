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
        dropSelf(ModBlocks.MAGIC_BLOCK.get());

        //NON-BLOCK BLOCKS
        dropSelf(ModBlocks.SHADOW_STAIRS.get());
        add(ModBlocks.SHADOW_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.SHADOW_SLAB.get()));

        dropSelf(ModBlocks.SHADOW_BUTTON.get());
        dropSelf(ModBlocks.SHADOW_PRESSURE_PLATE.get());

        dropSelf(ModBlocks.SHADOW_WALL.get());
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

        //CROP LOOT TABLES


        LootItemCondition.Builder lootItemConditionBuilder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.RADISH_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RadishCropBlock.AGE, 3));

        this.add(ModBlocks.RADISH_CROP.get(), this.createCropDrops(ModBlocks.RADISH_CROP.get(),
                ModItems.DRAGON_FRUIT.get(), ModItems.RADISH_SEEDS.get(), lootItemConditionBuilder));

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
