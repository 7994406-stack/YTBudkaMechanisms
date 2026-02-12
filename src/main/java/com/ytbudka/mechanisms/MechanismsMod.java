package com.ytbudka.mechanisms;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod(MechanismsMod.MOD_ID)
public class MechanismsMod {
    public static final String MOD_ID = "mechanisms";
    
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = 
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    
    public static final RegistryObject<CreativeModeTab> MECHANISMS_TAB = CREATIVE_TABS.register("mechanisms_tab",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.mechanisms"))
            .icon(() -> new ItemStack(Items.REDSTONE))
            .displayItems((params, output) -> {
                // Здесь будем добавлять наши блоки
            })
            .build());
    
    public MechanismsMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        CREATIVE_TABS.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        
        MinecraftForge.EVENT_BUS.register(this);
    }
}
