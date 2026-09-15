package com.shadow.shadowthings.item.custom;

import com.shadow.shadowthings.util.UpgradeType;
import net.minecraft.world.item.Item;

public class SoulUpgradeItem extends Item {
    private final UpgradeType type;
    private final int tier; // Or you could call this 'value' or 'multiplier'

    public SoulUpgradeItem(Properties properties, UpgradeType type, int tier) {
        super(properties);
        this.type = type;
        this.tier = tier;
    }

    public UpgradeType getUpgradeType() {
        return this.type;
    }

    public int getTier() {
        return this.tier;
    }
}
