package com.shadow.shadowthings.block.entity;

import com.shadow.shadowthings.block.entity.base.AbstractSoulEntity;
import com.shadow.shadowthings.item.ModItems;
import com.shadow.shadowthings.screen.custom.SoulCoreMenu;
import com.shadow.shadowthings.server.CoreMeltdownManager;
import com.shadow.shadowthings.server.ModDataAttachments;
import com.shadow.shadowthings.network.ModManaSyncPayload;
import com.shadow.shadowthings.util.ModTags;
import com.shadow.shadowthings.util.UpgradeType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class SoulCoreEntity extends AbstractSoulEntity implements MenuProvider {
    public SoulCoreEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SOUL_CORE_BE.get(), pos, blockState,0, 5000, 80, 100);
    }

    public boolean isFormed = false;

    public UUID ownerUUID;

    public final int BASE_MAX_CAPACITY = 5000;
    public final int BASE_TRANSFER_RATE = 80;
    public final int BASE_TRANSFER_AMOUNT = 100;
    public final int BASE_SIPHON_AMOUNT = 150;
    public final int BASE_SIPHON_RATE = 40;

    public boolean soulSiphonEnabled = false;
    public int siphonRate = 40; // How many ticks between siphons
    public int siphonAmount = 250; // How many souls to pull per siphon
    private int tickCounter = 0; // Internal timer

    private int coreRadius = 32;

    public int coreHealth = 10000;
    public final int MAX_CORE_HEALTH = 10000;
    public int safeCapacity = 200000;

    private final int[] soulHistory = new int[100];
    private int historyIndex = 0;
    public int rollingSoulChange = 0;
    private int inputThisTick = 0;
    private int outputThisTick = 0;

    private int lastRedstoneSignal = 0;
    public boolean isMeltingDown = false;
    public int meltdownTimer = 0; // Tracks the terrifying collapse animation

    public final CoreMeltdownManager meltdownManager = new CoreMeltdownManager(this);

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
        setChanged();
    }

    public int getCoreRadius() {
        return coreRadius;
    }

    public void setCoreRadius(int coreRadius) {
        this.coreRadius = coreRadius;
        setChanged();
    }

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
                case 2 -> SoulCoreEntity.this.coreHealth;
                case 3 -> SoulCoreEntity.this.rollingSoulChange;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> SoulCoreEntity.this.souls = Math.clamp(value, 0, maxSouls);
                case 1 -> SoulCoreEntity.this.maxSouls = value;
                case 2 -> SoulCoreEntity.this.coreHealth = value;
                case 3 -> SoulCoreEntity.this.rollingSoulChange = value;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    public void setSouls(int amount) {
        this.syncAndCheckRedstone();
        this.souls = Math.clamp(amount, 0, this.maxSouls);
        this.setChanged();
        if (this.level != null && !this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }
    public void addSouls(int amount) {
        this.syncAndCheckRedstone();
        this.inputThisTick += amount;
        this.souls = Math.clamp(this.souls + amount, 0, this.maxSouls);
        this.setChanged();
        if (this.level != null && !this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }
    public void removeSouls(int amount) {
        this.syncAndCheckRedstone();
        this.outputThisTick += amount;
        this.souls = Math.clamp(this.souls - amount, 0, this.maxSouls);
        this.setChanged();
        if (this.level != null && !this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }
    public void setMaxSouls(int amount) {
        this.syncAndCheckRedstone();
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
        this.maxSouls = Math.clamp(this.maxSouls - amount, 0 ,amount);
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
        // Only return if it's the client or the multiblock isn't built yet
        if (!this.isFormed) return;

        // --- 1. MELTDOWN CHECK (Absolute Priority) ---
        if (this.isMeltingDown) {
            this.meltdownManager.tick(level, pos);
            return; // Only stop normal operations if we are actively exploding!
        }
        if (level.isClientSide()) return;

        // --- 2. CORE DAMAGE & REGENERATION ---
        if (this.getSouls() > this.safeCapacity) {
            int overflow = this.getSouls() - this.safeCapacity;
            int damageRate = 1 + (overflow / 1000);

            this.coreHealth = Math.max(0, this.coreHealth - damageRate);
            this.setChanged();
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);

            if (this.coreHealth <= 0) {
                this.isMeltingDown = true;
                this.setChanged();
                this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
            }
        } else if (this.coreHealth < MAX_CORE_HEALTH) {
            this.coreHealth++;
            this.setChanged();
            // We only send updates every 20 ticks (1 sec) while healing so we don't spam the network needlessly
            if (level.getGameTime() % 20 == 0) {
                this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
            }
        }


        // --- 3. SIPHONING LOGIC ---
        // Only siphon if it is enabled AND there is still room in the tank!
        if (this.soulSiphonEnabled && this.souls < this.maxSouls) {
            this.tickCounter++;
            if (this.tickCounter >= this.siphonRate) {
                this.tickCounter = 0;
                performSiphon(level, pos);
            }
        }

        int delta = this.inputThisTick - this.outputThisTick;

        this.inputThisTick = 0;
        this.outputThisTick = 0;

        this.rollingSoulChange -= this.soulHistory[this.historyIndex];
        this.soulHistory[this.historyIndex] = delta;
        this.rollingSoulChange += delta;

        this.historyIndex = (this.historyIndex + 1) % 100;
    }
    private void performSiphon(Level level, BlockPos pos) {
        if (this.ownerUUID == null) return;

        AABB searchArea = new AABB(pos).inflate(5.0);

        List<Player> nearbyOwners = level.getEntitiesOfClass(Player.class, searchArea,
                player -> player.getUUID().equals(this.ownerUUID)
        );

        if (!nearbyOwners.isEmpty()) {
            Player owner = nearbyOwners.get(0);
            ServerPlayer player = owner.getServer().getPlayerList().getPlayer(ownerUUID);
            var manaData = owner.getData(ModDataAttachments.PLAYER_SOUL_MANA);

            if (manaData.getMana() > siphonAmount && player instanceof ServerPlayer serverPlayer){
                this.addSouls(this.siphonAmount);
                manaData.removeMana(siphonAmount);
                PacketDistributor.sendToPlayer(player, new ModManaSyncPayload(manaData.getMana(), manaData.getMaxMana()));
                this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
            }
        }
    }

    public void syncAndCheckRedstone() {
        if (this.level == null || this.level.isClientSide()) return;
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);

        int currentSignal = 0;
        if(this.getUpgradeLevel(UpgradeType.REDSTONE) < 1){return;}
        if (this.safeCapacity > 0) {
            float ratio = this.getSouls() / (float) this.safeCapacity;
            if (ratio >= 1.0f) currentSignal = 15;
            else if (ratio > 0) currentSignal = 1 + (int) (ratio * 14.0f);
        }

        if (currentSignal != this.lastRedstoneSignal) {
            this.lastRedstoneSignal = currentSignal;

            if (this.isFormed) {
                for (int x = -1; x <= 1; x++) {
                    for (int y = 0; y <= 2; y++) {
                        for (int z = -1; z <= 1; z++) {
                            BlockPos partPos = this.getBlockPos().offset(x, y, z);
                            this.level.updateNeighborsAt(partPos, this.level.getBlockState(partPos).getBlock());
                        }
                    }
                }
            }
        }
    }


    @Override
    protected void onUpgradesChanged() {
        super.onUpgradesChanged();
        if (this.level != null && !this.level.isClientSide()) {

            int capacityTier = this.getUpgradeLevel(UpgradeType.SOUL_CAPACITY);
            int transferRateTier = this.getUpgradeLevel(UpgradeType.SOUL_TRANSFER_RATE);
            int transferAmountTier = this.getUpgradeLevel(UpgradeType.SOUL_TRANSFER_AMOUNT);

            int bonusCapacity = (int) (Math.floor(Math.pow(capacityTier, 2.5))) * BASE_MAX_CAPACITY;
            int bonusTransferAmount = (int) (Math.floor(Math.pow(transferAmountTier, 1.75))) * BASE_TRANSFER_AMOUNT;
            int bonusSiphonAmount = (int) (Math.floor(Math.pow(transferAmountTier, 1.75))/4) * BASE_SIPHON_AMOUNT;

            int newMaxCapacity = BASE_MAX_CAPACITY + bonusCapacity;
            int newTransferAmount = BASE_TRANSFER_AMOUNT + bonusTransferAmount;
            int newTransferRate = BASE_TRANSFER_RATE;
            int newSiphonRate = BASE_SIPHON_RATE;
            int newSiphonAmount = BASE_SIPHON_AMOUNT + bonusSiphonAmount ;

            if (transferRateTier > 0) {
                newTransferRate = BASE_TRANSFER_RATE / (transferRateTier * 2);
                newSiphonRate = BASE_SIPHON_RATE / (transferRateTier * 2);
            }

            this.setMaxSouls(newMaxCapacity);
            this.transferRate = newTransferRate;
            this.transferAmount = newTransferAmount;
            this.siphonRate = newSiphonRate;
            this.siphonAmount = newSiphonAmount;
            if (this.hasUpgrade(ModItems.SOUL_UPGRADE_OVERLOAD.get())) {
                // OVERLOADED TIER!
                this.setMaxSouls(205000);
                this.safeCapacity = 200000;
            }
            }
        sync();
    }

    @Override
    public TagKey<Item> getAllowedUpgradeTag() {
        return ModTags.Items.CORE_UPGRADES;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("IsFormed", this.isFormed);

        tag.putBoolean("SoulSiphon", this.soulSiphonEnabled);
        tag.putInt("SiphonAmount", this.siphonAmount);
        tag.putInt("SiphonRate", this.siphonRate);
        tag.putInt("SoulTransferRate", this.transferRate);
        tag.putInt("SoulTransferAmount", this.transferAmount);

        tag.putInt("CoreHealth", this.coreHealth);
        tag.putInt("MeltdownTimer", this.meltdownTimer);
        tag.putBoolean("IsMeltingDown", this.isMeltingDown);

        if (this.ownerUUID != null) {
            tag.putUUID("OwnerUUID", this.ownerUUID);
        }
        tag.putInt("CoreRadius", this.coreRadius);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.isFormed = tag.getBoolean("IsFormed");

        // ADD THESE TWO LINES: Load the soul data when the client receives the packet!
        this.soulSiphonEnabled = tag.getBoolean("SoulSiphon");
        this.siphonAmount = tag.getInt("SiphonAmount");
        this.siphonRate = tag.getInt("SiphonRate");
        this.transferRate = tag.getInt("SoulTransferRate");
        this.transferAmount = tag.getInt("SoulTransferAmount");

        this.coreHealth = tag.getInt("CoreHealth");
        this.meltdownTimer = tag.getInt("MeltdownTimer");
        this.isMeltingDown = tag.getBoolean("IsMeltingDown");

        if (tag.hasUUID("OwnerUUID")) {
            this.ownerUUID = tag.getUUID("OwnerUUID");
        } else {
            this.ownerUUID = null;
        }
        if (tag.contains("CoreRadius")) {
            this.coreRadius = tag.getInt("CoreRadius");
        }
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("block.shadowthings.soul_core");
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
        // 1. Get the base tag
        CompoundTag tag = super.getUpdateTag(registries);

        // 2. Explicitly force the network packet to carry the meltdown data!
        tag.putBoolean("IsMeltingDown", this.isMeltingDown);
        tag.putInt("MeltdownTimer", this.meltdownTimer);
        tag.putInt("CoreHealth", this.coreHealth);
        tag.putInt("Souls", this.souls);
        tag.putInt("MaxSouls", this.maxSouls);
        tag.putBoolean("SoulSiphon", this.soulSiphonEnabled);
        tag.putInt("SoulTransferRate", this.transferRate);
        tag.putInt("SoulTransferAmount", this.transferAmount);

        return tag;
    }

}
