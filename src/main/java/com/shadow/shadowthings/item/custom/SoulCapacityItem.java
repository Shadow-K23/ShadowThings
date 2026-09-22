package com.shadow.shadowthings.item.custom;

import com.shadow.shadowthings.server.ModDataAttachments;
import com.shadow.shadowthings.soul.ModPlayerSoulMana;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.SoundType;

public class SoulCapacityItem extends Item {

    private final int increaseAmount;
    private final int hardCap;

    // We now require these values when creating the item!
    public SoulCapacityItem(Properties properties, int increaseAmount, int hardCap) {
        super(properties);
        this.increaseAmount = increaseAmount;
        this.hardCap = hardCap;
    }


    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.AMETHYST_BLOCK_STEP;
    }
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        if (!level.isClientSide() && entityLiving instanceof Player player) {

            ModPlayerSoulMana playerData = player.getData(ModDataAttachments.PLAYER_SOUL_MANA);
            int currentMax = playerData.getMaxMana();

            if (currentMax < this.hardCap) {
                // Uses the instance variables specific to the item eaten
                int manaBonus = Math.clamp(this.increaseAmount, 1, this.hardCap);

                playerData.addMaxMana(manaBonus);

                player.displayClientMessage(Component.literal("Max Souls increased by: " + manaBonus), true);
                level.playSound(null,player.blockPosition(),SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.PLAYERS,0.5f, 1.5f);
            }
        }
        return super.finishUsingItem(stack, level, entityLiving);
    }
}
