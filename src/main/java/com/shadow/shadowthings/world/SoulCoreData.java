package com.shadow.shadowthings.world;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SoulCoreData extends SavedData {
    // Stores the Player UUID and the exact coordinate of their Core
    private final Map<UUID, BlockPos> playerCores = new HashMap<>();

    public boolean hasCore(UUID playerUUID) {
        return playerCores.containsKey(playerUUID);
    }

    public void setCore(UUID playerUUID, BlockPos pos) {
        playerCores.put(playerUUID, pos);
        this.setDirty(); // Crucial: Tells Minecraft to save this file to disk!
    }

    public void removeCore(UUID playerUUID) {
        playerCores.remove(playerUUID);
        this.setDirty();
    }

    public BlockPos getCorePosition(UUID playerUUID) {
        return playerCores.get(playerUUID);
    }

    // --- SAVING AND LOADING TO DISK ---
    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        CompoundTag coresTag = new CompoundTag();
        for (Map.Entry<UUID, BlockPos> entry : playerCores.entrySet()) {
            coresTag.putLong(entry.getKey().toString(), entry.getValue().asLong());
        }
        tag.put("PlayerCores", coresTag);
        return tag;
    }

    public static SoulCoreData load(CompoundTag tag, HolderLookup.Provider registries) {
        SoulCoreData data = new SoulCoreData();
        CompoundTag coresTag = tag.getCompound("PlayerCores");
        for (String key : coresTag.getAllKeys()) {
            data.playerCores.put(UUID.fromString(key), BlockPos.of(coresTag.getLong(key)));
        }
        return data;
    }

    // A helper method to easily grab this data from anywhere
    public static SoulCoreData get(ServerLevel level) {
        // We attach it to the Overworld so the list is shared across all dimensions
        return level.getServer().overworld().getDataStorage().computeIfAbsent(
                new SavedData.Factory<>(SoulCoreData::new, SoulCoreData::load),
                "soul_cores_tracker"
        );
    }
}
