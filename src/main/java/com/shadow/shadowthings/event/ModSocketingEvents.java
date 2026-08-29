package com.shadow.shadowthings.event;

import com.shadow.shadowthings.component.ModDataComponents;
import com.shadow.shadowthings.component.ModSocketedGems;
import com.shadow.shadowthings.item.ModItems;
import com.shadow.shadowthings.item.custom.ModGemItem;
import com.shadow.shadowthings.util.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.ArmorItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = "shadowthings")
public class ModSocketingEvents {

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        // 1. Check if left item is a weapon/tool or armor
        if (left.getItem() instanceof TieredItem || left.getItem() instanceof ArmorItem) {

            // 2. Check if right item is your gem (replace with your custom gem tag/item)
            if (right.getItem() instanceof ModGemItem) {

                ItemStack output = left.copy();

                // Fetch existing gems or use EMPTY if none exist
                ModSocketedGems existing = output.getOrDefault(ModDataComponents.SOCKETED_GEMS.get(), ModSocketedGems.EMPTY);

                // Enforce a maximum amount of sockets (e.g., Max 3 gems)
                if (existing.gems().size() < 3) {
                    List<ItemStack> newGems = new ArrayList<>(existing.gems());

                    ItemStack socketedGem = right.copy();
                    socketedGem.setCount(1); // Only socket one gem at a time
                    newGems.add(socketedGem);

                    // Apply the new immutable record to the item
                    output.set(ModDataComponents.SOCKETED_GEMS.get(), new ModSocketedGems(List.copyOf(newGems)));

                    event.setOutput(output);
                    event.setCost(5); // Cost in XP levels
                    event.setMaterialCost(1); // Consumes 1 gem from the right slot
                }
            }
        }
    }

    @SubscribeEvent
    public static void onItemAttributeModifier(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();

        // Check if the item has sockets
        if (stack.has(ModDataComponents.SOCKETED_GEMS.get())) {
            ModSocketedGems socketData = stack.get(ModDataComponents.SOCKETED_GEMS.get());

            // 1. Create variables to hold the total bonuses
            float totalBonusDamage = 0.0f;
            float totalBonusAttackSpeed = 0.0f;
            // double totalBonusSpeed = 0.0; // You can add more for other gem types!

            // 2. Tally up the stats from all socketed gems
            for (ItemStack gem : socketData.gems()) {
                if (gem.is(ModItems.RUBY_GEM.get())) {
                    totalBonusDamage += 2.0f;
                }
                else if (gem.is(ModItems.TOPAZ_GEM.get())){
                    totalBonusAttackSpeed += 0.25f;
                }
                // Add else if for other gems here, adding to their respective totals
            }

            EquipmentSlotGroup slot = EquipmentSlotGroup.MAINHAND;
            if (stack.getItem() instanceof ArmorItem armor) {
                slot = EquipmentSlotGroup.bySlot(armor.getEquipmentSlot());
            }
            // 3. Apply the modifiers if the totals are greater than 0
            if (totalBonusDamage > 0f) {
                event.addModifier(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                ResourceLocation.fromNamespaceAndPath("shadowthings", "gem_damage_boost"),
                                totalBonusDamage, // Apply the combined total
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        slot
                );
            }
            else if (totalBonusAttackSpeed > 0f) {
                event.addModifier(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(
                                ResourceLocation.fromNamespaceAndPath("shadowthings", "gem_attack_speed_boost"),
                                totalBonusAttackSpeed, // Apply the combined total
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        slot
                );
            }
        }
    }
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        if (stack.has(ModDataComponents.SOCKETED_GEMS.get())) {
            ModSocketedGems socketData = stack.get(ModDataComponents.SOCKETED_GEMS.get());

            if (!socketData.gems().isEmpty()) {
                event.getToolTip().add(Component.empty()); // Blank space for padding
                event.getToolTip().add(Component.literal("Socketed Gems:").withStyle(ChatFormatting.GOLD));

                for (ItemStack gem : socketData.gems()) {
                    event.getToolTip().add(Component.literal(" ✦ ").withStyle(ChatFormatting.DARK_GRAY)
                            .append(gem.getHoverName().copy().withStyle(ChatFormatting.GRAY)));
                }
            }
        }
    }
}
