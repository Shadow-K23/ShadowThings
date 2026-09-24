package com.shadow.shadowthings.screen.custom;


import com.shadow.shadowthings.block.ModBlocks;
import com.shadow.shadowthings.block.entity.SoulCrafterEntity;
import com.shadow.shadowthings.screen.ModMenuTypes;
import com.shadow.shadowthings.screen.custom.base.AbstractSoulMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class SoulCrafterMenu extends AbstractSoulMenu {
    public final SoulCrafterEntity blockEntity;
    private final ContainerLevelAccess levelAccess;

    // Client-side constructor
    public SoulCrafterMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    // Server-side constructor
    public SoulCrafterMenu(int id, Inventory inv, BlockEntity entity) {
        super(ModMenuTypes.SOUL_CRAFTER_MENU.get(), id); // You'll need to register this type next!
        checkContainerSize(inv, 1);

        this.blockEntity = (SoulCrafterEntity) entity;
        this.levelAccess = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());
        this.addUpgradeSlots(blockEntity, 155, 6);
        // Add the 1 slot for the Crafter's Catalyst (x: 80, y: 35)
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 0, 80, 33));

        // Add the Player's Inventory (Standard math for alignment)
        addPlayerInventory(inv);
        addPlayerHotbar(inv);
    }

    public SoulCrafterEntity getBlockEntity() {
        return this.blockEntity;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        // Required method for shift-clicking items between inventories
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stackInSlot = slot.getItem();
        ItemStack originalStack = stackInSlot.copy();

        if (index == 0) { // If clicking the Crafter slot
            if (!this.moveItemStackTo(stackInSlot, 1, 37, true)) return ItemStack.EMPTY;
        } else if (!this.moveItemStackTo(stackInSlot, 0, 1, false)) { // If clicking Player inventory
            return ItemStack.EMPTY;
        }

        if (stackInSlot.isEmpty()) slot.setByPlayer(ItemStack.EMPTY);
        else slot.setChanged();

        return originalStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.levelAccess, player, ModBlocks.SOUL_CRAFTER.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}