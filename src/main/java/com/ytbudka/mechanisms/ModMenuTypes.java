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
        
# Обновляем MechanismsMod.java
cat > src/main/java/com/ytbudka/mechanisms/MechanismsMod.java << 'EOF'
package com.ytbudka.mechanisms;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
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
            .icon(() -> new ItemStack(ModBlocks.COAL_GENERATOR.get()))
            .displayItems((params, output) -> {
                output.accept(ModItems.COAL_GENERATOR_ITEM.get());
            })
            .build());
    
    public MechanismsMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        CREATIVE_TABS.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        
        MinecraftForge.EVENT_BUS.register(this);
    }
}
