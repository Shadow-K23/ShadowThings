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
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new net.minecraft.world.inventory.SimpleContainerData(6));
    }

    // Server-side constructor (Accepts the real data from the entity)
    public SoulCrucibleMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.SOUL_CRUCIBLE_MENU.get(), id);
        checkContainerSize(inv, 1);

        this.blockEntity = (SoulCrucibleEntity) entity;
        this.levelAccess = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());
        this.data = data; // Assign the data

        this.addUpgradeSlots(blockEntity, 155, 6);
        this.addSlot(new SlotItemHandler(this.blockEntity.mainInventory, 0, 80, 33));

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        // Tell the menu to actively sync the variables to the client!
        addDataSlots(data);
    }

    public SoulCrucibleEntity getBlockEntity() {
        return this.blockEntity;
    }

    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_SLOT_COUNT = 5; // 4 upgrades + 2 main slots

    private static final int VANILLA_FIRST_SLOT_INDEX = TE_INVENTORY_SLOT_COUNT;
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

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

    public ContainerData getData() {
        return this.data;
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