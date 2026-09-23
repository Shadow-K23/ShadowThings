package com.shadow.shadowthings.screen.custom;

import com.shadow.shadowthings.block.ModBlocks;
import com.shadow.shadowthings.block.entity.SoulFurnaceEntity;
import com.shadow.shadowthings.screen.ModMenuTypes; // Make sure to create this registry object!
import com.shadow.shadowthings.screen.custom.base.AbstractSoulMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class SoulFurnaceMenu extends AbstractSoulMenu {
    public final SoulFurnaceEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    // Client constructor
    public SoulFurnaceMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(6));
    }

    // Server constructor
    public SoulFurnaceMenu(int containerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.SOUL_FURNACE_MENU.get(), containerId);
        this.blockEntity = (SoulFurnaceEntity) entity;
        this.level = inv.player.level();
        this.data = data;

        // 1. Add Upgrade Slots (Slots 0 to 3)[cite: 1]
        this.addUpgradeSlots(this.blockEntity, 155, 6);

        // 2. Add Main Inventory Slots (Slot 4 is Input, Slot 5 is Output)
        this.addSlot(new SlotItemHandler(this.blockEntity.mainInventory, 0, 44, 30)); // Input
        this.addSlot(new SlotItemHandler(this.blockEntity.mainInventory, 1, 116, 30)); // Output

        // 3. Add Player Inventory (Slots 6 to 41)
        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.addDataSlots(data);
    }

    // --- SCREEN HELPERS ---
    public int getSouls() { return this.data.get(0); }
    public int getMaxSouls() { return this.data.get(1); }
    public int getProgress() { return this.data.get(2); }
    public int getMaxProgress() { return this.data.get(3); }
    public int getMomentum() { return this.data.get(4); }
    public int getMaxMomentum() { return this.data.get(5); }

    public int getScaledProgress() {
        int progress = this.data.get(2);
        int maxProgress = this.data.get(3);
        int arrowPixelWidth = 96; // Change to your arrow texture width
        return maxProgress != 0 && progress != 0 ? progress * arrowPixelWidth / maxProgress : 0;
    }

    public int getScaledMomentum() {
        int momentum = this.data.get(4);
        int maxMomentum = this.data.get(5);
        int barPixelHeight = 71; // Change to your momentum bar texture height
        return maxMomentum != 0 && momentum != 0 ? momentum * barPixelHeight / maxMomentum : 0;
    }

    // --- SHIFT-CLICK MATH ---
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_SLOT_COUNT = 6; // 4 upgrades + 2 main slots

    private static final int VANILLA_FIRST_SLOT_INDEX = TE_INVENTORY_SLOT_COUNT;
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT; // 36[cite: 2]

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // If clicked in TE -> Move to Player Inventory
        if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        }
        // If clicked in Player Inventory -> Move to TE (Prefers Input Slot 4, then Upgrades)
        else if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // Try input slot first (Slot 4)
            if (!moveItemStackTo(sourceStack, 4, 5, false)) {
                // Then try upgrade slots (Slots 0 to 3)
                if (!moveItemStackTo(sourceStack, 0, 4, false)) {
                    return ItemStack.EMPTY;
                }
            }
        } else {
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, ModBlocks.SOUL_FURNACE_CONTROLLER.get());
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

    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new SoulFurnaceMenu(i, inventory, this.blockEntity, this.data);
    }
}