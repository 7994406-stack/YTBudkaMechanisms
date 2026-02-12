package com.ytbudka.mechanisms;

import com.ytbudka.mechanisms.screen.CoalGeneratorMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
        DeferredRegister.create(ForgeRegistries.MENU_TYPES, MechanismsMod.MOD_ID);
    
    public static final RegistryObject<MenuType<CoalGeneratorMenu>> COAL_GENERATOR_MENU =
        MENUS.register("coal_generator_menu", () ->
            IForgeMenuType.create(CoalGeneratorMenu::new));
    
    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
