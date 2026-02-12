package com.ytbudka.mechanisms.blockentity;
import com.ytbudka.mechanisms.MechanismsMod;
import com.ytbudka.mechanisms.energy.EnergyStorage;
import com.ytbudka.mechanisms.screen.CoalGeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

public class CoalGeneratorBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler inventory = new ItemStackHandler(1);
    public final EnergyStorage energy = new EnergyStorage(10000, 100);
    protected final ContainerData data;
    private int progress = 0;

    public CoalGeneratorBlockEntity(BlockPos p, BlockState s) {
        super(MechanismsMod.COAL_GENERATOR_BE.get(), p, s);
        this.data = new ContainerData() {
            public int get(int i) { return i == 0 ? energy.getEnergyStored() : energy.getMaxEnergyStored(); }
            public void set(int i, int v) { }
            public int getCount() { return 2; }
        };
    }
    public void tick() {
        if (inventory.getStackInSlot(0).is(Items.COAL)) {
            if (energy.receiveEnergy(50, true) == 50) {
                energy.receiveEnergy(50, false);
                if (++progress > 20) { inventory.extractItem(0, 1, false); progress = 0; }
                setChanged();
            }
        }
    }
    @Override public Component getDisplayName() { return Component.literal("Coal Generator"); }
    @Override public AbstractContainerMenu createMenu(int i, Inventory inv, Player p) { return new CoalGeneratorMenu(i, inv, this, this.data); }
    @Override protected void saveAdditional(CompoundTag t) { t.put("inv", inventory.serializeNBT()); t.put("energy", energy.serializeNBT()); super.saveAdditional(t); }
    @Override public void load(CompoundTag t) { inventory.deserializeNBT(t.getCompound("inv")); energy.deserializeNBT(t.getCompound("energy")); super.load(t); }
}
