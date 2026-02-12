package com.ytbudka.mechanisms.blockentity;

import com.ytbudka.mechanisms.energy.EnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class MachineBlockEntity extends BlockEntity implements MenuProvider {
    
    protected final EnergyStorage energyStorage;
    protected int maxProgress = 100;
    protected int currentProgress = 0;
    protected boolean isWorking = false;
    
    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int energyCapacity) {
        super(type, pos, state);
        this.energyStorage = new EnergyStorage(energyCapacity, 1000);
    }
    
    // Обновление каждый тик
    public void tick() {
        if (level == null || level.isClientSide) return;
        
        if (canWork() && hasEnoughEnergy()) {
            isWorking = true;
            consumeEnergy();
            currentProgress++;
            
            if (currentProgress >= maxProgress) {
                processComplete();
                currentProgress = 0;
            }
        } else {
            isWorking = false;
        }
        
        setChanged();
    }
    
    // Проверка условий для работы
    protected abstract boolean canWork();
    
    // Завершение процесса
    protected abstract void processComplete();
    
    // Потребление энергии за тик
    protected void consumeEnergy() {
        energyStorage.extractEnergy(getEnergyPerTick(), false);
    }
    
    // Проверка энергии
    protected boolean hasEnoughEnergy() {
        return energyStorage.getEnergyStored() >= getEnergyPerTick();
    }
    
    // Энергия за тик (переопределить в наследниках)
    protected int getEnergyPerTick() {
        return 20; // 20 FE/tick по умолчанию
    }
    
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Energy", energyStorage.serializeNBT());
        tag.putInt("Progress", currentProgress);
        tag.putBoolean("Working", isWorking);
    }
    
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Energy")) {
            energyStorage.deserializeNBT(tag.getCompound("Energy"));
        }
        currentProgress = tag.getInt("Progress");
        isWorking = tag.getBoolean("Working");
    }
    
    public EnergyStorage getEnergyStorage() {
        return energyStorage;
    }
    
    public int getProgress() {
        return currentProgress;
    }
    
    public int getMaxProgress() {
        return maxProgress;
    }
    
    public boolean isWorking() {
        return isWorking;
    }
}
