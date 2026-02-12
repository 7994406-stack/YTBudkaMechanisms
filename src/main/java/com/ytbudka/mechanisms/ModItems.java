package com.ytbudka.mechanisms;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = 
        DeferredRegister.create(ForgeRegistries.ITEMS, MechanismsMod.MOD_ID);
    
    public static final RegistryObject<Item> COAL_GENERATOR_ITEM = ITEMS.register("coal_generator",
        () -> new BlockItem(ModBlocks.COAL_GENERATOR.get(), new Item.Properties()));
    
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
