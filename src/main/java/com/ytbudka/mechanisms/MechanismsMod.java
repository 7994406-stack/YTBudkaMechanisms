package com.ytbudka.mechanisms;

import com.ytbudka.mechanisms.block.CoalGeneratorBlock;
import com.ytbudka.mechanisms.blockentity.CoalGeneratorBlockEntity;
import com.ytbudka.mechanisms.screen.CoalGeneratorMenu;
import com.ytbudka.mechanisms.screen.CoalGeneratorScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(MechanismsMod.MOD_ID)
public class MechanismsMod {
    public static final String MOD_ID = "mechanisms";

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final RegistryObject<Block> COAL_GENERATOR = BLOCKS.register("coal_generator",
            () -> new CoalGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Item> COAL_GENERATOR_ITEM = ITEMS.register("coal_generator",
            () -> new BlockItem(COAL_GENERATOR.get(), new Item.Properties()));

    public static final RegistryObject<BlockEntityType<CoalGeneratorBlockEntity>> COAL_GENERATOR_BE = BLOCK_ENTITIES.register("coal_generator",
            () -> BlockEntityType.Builder.of(CoalGeneratorBlockEntity::new, COAL_GENERATOR.get()).build(null));

    public static final RegistryObject<MenuType<CoalGeneratorMenu>> COAL_GENERATOR_MENU = MENUS.register("coal_generator",
            () -> IForgeMenuType.create(CoalGeneratorMenu::new));

    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.mechanisms")).icon(() -> new ItemStack(COAL_GENERATOR.get()))
            .displayItems((p, out) -> out.accept(COAL_GENERATOR_ITEM.get())).build());

    public MechanismsMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        MENUS.register(bus);
        TABS.register(bus);
        bus.register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(COAL_GENERATOR_MENU.get(), CoalGeneratorScreen::new);
        }
    }
}
