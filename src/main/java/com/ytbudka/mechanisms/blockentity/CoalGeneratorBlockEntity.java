package com.ytbudka.mechanisms.blockentity;

import com.ytbudka.mechanisms.ModBlockEntities;
import com.ytbudka.mechanisms.screen.CoalGeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CoalGeneratorBlockEntity extends MachineBlockEntity {
    
    private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
        
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0;
        }
    };
    
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    
    private int burnTime = 0;
    private int maxBurnTime = 0;
    
    // Данные для синхронизации с GUI
    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> CoalGeneratorBlockEntity.this.energyStorage.getEnergyStored();
                case 1 -> CoalGeneratorBlockEntity.this.energyStorage.getMaxEnergyStored();
                case 2 -> CoalGeneratorBlockEntity.this.burnTime;
                case 3 -> CoalGeneratorBlockEntity.this.maxBurnTime;
                default -> 0;
            };
        }
        
        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> CoalGeneratorBlockEntity.this.energyStorage.setEnergy(value);
                case 2 -> CoalGeneratorBlockEntity.this.burnTime = value;
                case 3 -> CoalGeneratorBlockEntity.this.maxBurnTime = value;
            }
        }
        
        @Override
        public int getCount() {
            return 4;
        }
    };
    
    public CoalGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COAL_GENERATOR.get(), pos, state, 50000); // 50k FE вместимость
    }
    
    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }
    
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }
    
    @Override
    public void tick() {
        if (level == null || level.isClientSide) return;
        
        // Если есть топливо и место для энергии
        if (burnTime <= 0 && canGenerate()) {
            ItemStack fuel = itemHandler.getStackInSlot(0);
            if (!fuel.isEmpty()) {
                burnTime = ForgeHooks.getBurnTime(fuel, RecipeType.SMELTING);
                maxBurnTime = burnTime;
                itemHandler.extractItem(0, 1, false);
            }
        }
        
        // Генерация энергии
        if (burnTime > 0) {
            burnTime--;
            energyStorage.receiveEnergy(40, false); // 40 FE/tick
            isWorking = true;
        } else {
            isWorking = false;
        }
        
        setChanged();
    }
    
    private boolean canGenerate() {
        return energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored();
    }
    
    @Override
    protected boolean canWork() {
        return burnTime > 0;
    }
    
    @Override
    protected void processComplete() {
        // Не используется для генератора
    }
    
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
    
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.mechanisms.coal_generator");
    }
    
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new CoalGeneratorMenu(id, inventory, this, this.data);
    }
    
    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("Inventory", itemHandler.serializeNBT());
        tag.putInt("BurnTime", burnTime);
        tag.putInt("MaxBurnTime", maxBurnTime);
        super.saveAdditional(tag);
    }
    
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("Inventory"));
        burnTime = tag.getInt("BurnTime");
        maxBurnTime = tag.getInt("MaxBurnTime");
    }
    
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        if (cap == ForgeCapabilities.ENERGY) {
            return LazyOptional.of(() -> energyStorage).cast();
        }
        return super.getCapability(cap, side);
    }
    
    public int getBurnTime() {
        return burnTime;
    }
    
    public int getMaxBurnTime() {
        return maxBurnTime;
    }
}
