package com.shadow.shadowthings.screen.custom.base;

import com.shadow.shadowthings.block.entity.base.AbstractSoulEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public abstract class AbstractSoulMenu extends AbstractContainerMenu {

    protected AbstractSoulMenu(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    // The universal method for 1x4 upgrade slots!
    protected void addUpgradeSlots(AbstractSoulEntity entity, int startX, int startY) {
        for (int i = 0; i < 4; i++) {
            // Adds a vertical column of 4 slots, spaced 18 pixels apart
            this.addSlot(new SlotItemHandler(entity.upgradeInventory, i, startX, startY + (i * 18)));
        }
    }
}