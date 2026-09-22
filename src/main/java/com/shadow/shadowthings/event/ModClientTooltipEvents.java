package com.shadow.shadowthings.event;

import com.shadow.shadowthings.component.ModDataComponents;
import com.shadow.shadowthings.util.ModTags;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

// Registers automatically to the Forge Event Bus on the Client tier
@EventBusSubscriber(modid = "shadowthings", value = Dist.CLIENT)
public class ModClientTooltipEvents {

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(item);
        String keyString = key.toString();
        String pathOnly = key.getPath();

        if (stack.is(ModTags.Items.ADVANCED_ITEM_TOOLTIP)) {
            if (Screen.hasShiftDown()) {
                event.getToolTip().add(Component.translatable("tooltip.shadowthings." + pathOnly));
            } else {
                event.getToolTip().add(Component.translatable("tooltip.shadowthings.shift"));
            }
        }
        else if (stack.getItem() instanceof BlockItem blockItem) {
            if (blockItem.getBlock().defaultBlockState().is(ModTags.Blocks.ADVANCED_BLOCK_TOOLTIP)) {
                if (Screen.hasShiftDown()) {
                    event.getToolTip().add(Component.translatable("tooltip.shadowthings." + pathOnly));
                } else {
                    event.getToolTip().add(Component.translatable("tooltip.shadowthings.shift"));
                }
            }
        }
    }
}