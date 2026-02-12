package com.ytbudka.mechanisms;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = 
        DeferredRegister.create(ForgeRegistries.BLOCKS, MechanismsMod.MOD_ID);
    
    // Простой генератор (пока без логики, чтобы проверить появление)
    public static final RegistryObject<Block> COAL_GENERATOR = BLOCKS.register("coal_generator",
        () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
