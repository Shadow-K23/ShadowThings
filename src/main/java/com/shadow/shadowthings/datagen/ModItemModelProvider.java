package com.shadow.shadowthings.datagen;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.ModBlocks;
import com.shadow.shadowthings.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import org.spongepowered.asm.mixin.Shadow;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ShadowThings.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //BASIC ITEMS
        basicItem(ModItems.RAWSHADOWINGOT.get());
        basicItem(ModItems.SHADOWINGOT.get());

        //NON-BLOCK BLOCKS

        buttonItem(ModBlocks.SHADOW_BUTTON, ModBlocks.SHADOW_BLOCK);
        fenceItem(ModBlocks.SHADOW_FENCE, ModBlocks.SHADOW_BLOCK);
        wallItem(ModBlocks.SHADOW_WALL, ModBlocks.SHADOW_BLOCK);

        basicItem(ModBlocks.SHADOW_DOOR.asItem());

        //TOOLS
        handheldItem(ModItems.SHADOWSWORD);
        handheldItem(ModItems.SHADOWPICKAXE);
        handheldItem(ModItems.SHADOWAXE);
        handheldItem(ModItems.SHADOWHOE);
        handheldItem(ModItems.SHADOWSHOVEL);
        handheldItem(ModItems.SHADOWSPEAR);
        //ADVANCED ITEMS
        basicItem(ModItems.CHISEL.get());
        //FOOD
        basicItem(ModItems.DRAGON_FRUIT.get());
        //FUEL
        basicItem(ModItems.SUPER_FUEL.get());
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

    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }
    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID,"item/" + item.getId().getPath()));
    }
}
