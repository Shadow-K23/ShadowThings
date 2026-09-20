package com.shadow.shadowthings.screen.custom;


import com.shadow.shadowthings.block.ModBlocks;
import com.shadow.shadowthings.block.entity.SoulCrafterEntity;
import com.shadow.shadowthings.block.entity.SoulCrucibleEntity;
import com.shadow.shadowthings.screen.ModMenuTypes;
import com.shadow.shadowthings.screen.custom.base.AbstractSoulMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class SoulCrucibleMenu extends AbstractSoulMenu {
    public final SoulCrucibleEntity blockEntity;
    private final ContainerLevelAccess levelAccess;
    private final ContainerData data; // Add this

    // Client-side constructor (Adds dummy data for the client to read)
    public SoulCrucibleMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new net.minecraft.world.inventory.SimpleContainerData(4));
    }

    // Server-side constructor (Accepts the real data from the entity)
    public SoulCrucibleMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.SOUL_CRUCIBLE_MENU.get(), id);
        checkContainerSize(inv, 1);

        this.blockEntity = (SoulCrucibleEntity) entity;
        this.levelAccess = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());
        this.data = data; // Assign the data

        this.addUpgradeSlots(blockEntity, 180, 10);
        this.addSlot(new SlotItemHandler(this.blockEntity.mainInventory, 0, 80, 35));

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        // Tell the menu to actively sync the variables to the client!
        addDataSlots(data);
    }

    public SoulCrucibleEntity getBlockEntity() {
        return this.blockEntity;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        // Required method for shift-clicking items between inventories
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stackInSlot = slot.getItem();
        ItemStack originalStack = stackInSlot.copy();

        if (index == 0) {
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
        return stillValid(this.levelAccess, player, ModBlocks.SOUL_CRUCIBLE.get());
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