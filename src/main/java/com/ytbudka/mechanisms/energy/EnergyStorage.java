package com.ytbudka.mechanisms.energy;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyStorage extends net.minecraftforge.energy.EnergyStorage {
    public EnergyStorage(int capacity, int maxTransfer) { super(capacity, maxTransfer); }
    public int setEnergy(int energy) { this.energy = energy; return energy; }
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("energy", this.energy);
        return tag;
    }
    public void deserializeNBT(CompoundTag nbt) { this.energy = nbt.getInt("energy"); }
}
