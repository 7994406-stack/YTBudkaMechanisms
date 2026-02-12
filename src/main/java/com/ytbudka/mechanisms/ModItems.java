package com.ytbudka.mechanisms;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = 
        DeferredRegister.create(ForgeRegistries.ITEMS, MechanismsMod.MOD_ID);
    
    // Здесь будем регистрировать предметы
    
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
