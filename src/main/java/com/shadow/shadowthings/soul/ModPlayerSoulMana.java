package com.shadow.shadowthings.soul;


public class ModPlayerSoulMana {

    private int mana = 0;
    private int maxMana = 200;
    private int manaRegen = 5;

    public int getMana(){return mana;}
    public int getMaxMana(){return maxMana;}
    public int getManaRegen(){return manaRegen;}

    public void setMana(int mana){
        this.mana = Math.clamp(mana, 0, maxMana);
    }

    public void removeMana(int mana){
        setMana(this.mana - mana);
    }

    public void addMana(int mana){
        setMana(this.mana + mana);
    }

    public void setMaxMana(int mana){
        this.maxMana = mana;
    }

    public void addMaxMana(int mana){
        setMaxMana(this.mana + mana);
    }

    public void removeMaxMana(int mana){
        setMaxMana(this.mana - mana);
    }

    public void setManaRegen(int mana){
        this.manaRegen = mana;
    }

    public void addManaRegen(int mana){
        setManaRegen(this.manaRegen + mana);
    }
    public void removeManaRegen(int mana){
        setManaRegen(this.manaRegen - mana);
    }


}
