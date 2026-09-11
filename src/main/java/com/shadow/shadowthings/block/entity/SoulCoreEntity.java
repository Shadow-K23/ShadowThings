package com.shadow.shadowthings.block.entity;

import com.shadow.shadowthings.block.custom.SoulCoreBlock;
import com.shadow.shadowthings.screen.custom.SoulCoreMenu;
import com.shadow.shadowthings.server.ModDataAttachments;
import com.shadow.shadowthings.soul.ModManaSyncPayload;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import org.apache.logging.log4j.core.jmx.Server;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class SoulCoreEntity extends BlockEntity implements MenuProvider {
    public SoulCoreEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SOUL_CORE_BE.get(), pos, blockState);
    }

    public boolean isFormed = false;

    private int souls = 0;
    private int maxSouls = 5000;

    public UUID ownerUUID;

    public boolean soulTransferEnabled = false;
    public int transferRate = 0;
    public int transferAmount = 0;

    public boolean soulSiphonEnabled = false;
    public int siphonRate = 10; // How many ticks between siphons (20 ticks = 1 second)
    public int siphonAmount = 10; // How many souls to pull per siphon
    private int tickCounter = 0; // Internal timer

    public int getSouls() {
        return souls;
    }

    public int getMaxSouls() {
        return maxSouls;
    }
    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> SoulCoreEntity.this.souls;
                case 1 -> SoulCoreEntity.this.maxSouls;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> SoulCoreEntity.this.souls = Math.clamp(value,0,maxSouls);
                case 1 -> SoulCoreEntity.this.maxSouls = value;
            }
        }

        @Override
        public int getCount() {
            return 2; // We are syncing 2 variables (souls and maxSouls)
        }
    };

    public void setSouls(int amount) {
        this.souls = Math.clamp(amount, 0, this.maxSouls);
        this.setChanged();
        if (this.level != null && !this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }
    public void addSouls(int amount) {
        this.souls = Math.clamp(this.souls + amount, 0, this.maxSouls);
        this.setChanged();
        if (this.level != null && !this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }
    public void removeSouls(int amount) {
        this.souls = Math.clamp(this.souls - amount, 0, this.maxSouls);
        this.setChanged();
        if (this.level != null && !this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }
    public void setMaxSouls(int amount) {
        this.maxSouls = amount;
        this.setChanged();
        if (this.level != null && !this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }
    public void addMaxSouls(int amount) {
        this.maxSouls = this.maxSouls + amount;
        this.setChanged();
        if (this.level != null && !this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }
    public void removeMaxSouls(int amount) {
        this.maxSouls = this.maxSouls - amount;
        this.setChanged();
        if (this.level != null && !this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }

    //SOUL CORE TOGGLEABLES

    public void toggleSoulSiphon() {
        this.soulSiphonEnabled = !this.soulSiphonEnabled;
        this.setChanged();

        if (this.level != null && !this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide() || !this.isFormed || !this.soulSiphonEnabled) return;
        // Stop siphoning if the core is already full!
        if (this.souls >= this.maxSouls) return;

        this.tickCounter++;
        if (this.tickCounter >= this.siphonRate) {
            this.tickCounter = 0; // Reset the timer

            performSiphon(level, pos);
        }
    }
    private void performSiphon(Level level, BlockPos pos) {
        if (this.ownerUUID == null) return;

        // 1. Create a 5-block radius around the Core
        AABB searchArea = new AABB(pos).inflate(5.0);

        // 2. Find players in that area whose UUID matches the owner
        List<Player> nearbyOwners = level.getEntitiesOfClass(Player.class, searchArea,
                player -> player.getUUID().equals(this.ownerUUID)
        );

        // 3. If the owner is nearby, drain them!
        if (!nearbyOwners.isEmpty()) {
            Player owner = nearbyOwners.get(0);
            ServerPlayer player = owner.getServer().getPlayerList().getPlayer(ownerUUID);
            var manaData = owner.getData(ModDataAttachments.PLAYER_SOUL_MANA);

            if (manaData.getMana() > siphonAmount && player instanceof ServerPlayer serverPlayer){
                this.addSouls(this.siphonAmount);
                manaData.removeMana(siphonAmount);
                PacketDistributor.sendToPlayer(serverPlayer, new ModManaSyncPayload(manaData.getMana()));
                this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
            }


            // TEMPORARY PLACEHOLDER: Just adds souls directly for testing

        }
    }


    public final ItemStackHandler inventory = new ItemStackHandler(1){
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(),3);
            }
        }
    };

    private float rotation;

    public void clearContents(){
        inventory.setStackInSlot(0, ItemStack.EMPTY);
    }

    public void drops(){
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for(int i = 0; i < inventory.getSlots(); i++){
            inv.setItem(i, inventory.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inv);
    }


    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putBoolean("IsFormed", this.isFormed);

        // ADD THESE TWO LINES: Save the soul data so it syncs to the client!
        tag.putInt("Souls", this.souls);
        tag.putInt("MaxSouls", this.maxSouls);
        tag.putBoolean("SoulSiphon", this.soulSiphonEnabled);

        if (this.ownerUUID != null) {
            tag.putUUID("OwnerUUID", this.ownerUUID);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        this.isFormed = tag.getBoolean("IsFormed");

        // ADD THESE TWO LINES: Load the soul data when the client receives the packet!
        this.souls = tag.getInt("Souls");
        this.maxSouls = tag.getInt("MaxSouls");
        this.soulSiphonEnabled = tag.getBoolean("SoulSiphon");


        if (tag.hasUUID("OwnerUUID")) {
            this.ownerUUID = tag.getUUID("OwnerUUID");
        } else {
            this.ownerUUID = null;
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Soul Core");
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new SoulCoreMenu(i, inventory, this, this.data);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }

}
