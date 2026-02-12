package com.ytbudka.mechanisms.energy;

public interface IEnergyStorage {
    // Получить текущее количество энергии
    int getEnergyStored();
    
    // Получить максимальную вместимость
    int getMaxEnergyStored();
    
    // Добавить энергию (возвращает сколько приняла)
    int receiveEnergy(int maxReceive, boolean simulate);
    
    // Извлечь энергию (возвращает сколько отдала)
    int extractEnergy(int maxExtract, boolean simulate);
    
    // Можно ли извлекать энергию
    boolean canExtract();
    
    // Можно ли принимать энергию
    boolean canReceive();
}
