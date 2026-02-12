package com.ytbudka.mechanisms.screen;
import com.ytbudka.mechanisms.MechanismsMod;
import com.ytbudka.mechanisms.blockentity.CoalGeneratorBlockEntity;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class CoalGeneratorMenu extends AbstractContainerMenu {
    public final CoalGeneratorBlockEntity blockEntity;
    private final ContainerData data;
    public CoalGeneratorMenu(int i, Inventory inv, CoalGeneratorBlockEntity be, ContainerData d) {
        super(MechanismsMod.COAL_GENERATOR_MENU.get(), i);
        this.blockEntity = be; this.data = d;
        addSlot(new SlotItemHandler(be.inventory, 0, 80, 35));
        for(int y=0; y<3; y++) for(int x=0; x<9; x++) addSlot(new Slot(inv, x+y*9+9, 8+x*18, 84+y*18));
        for(int x=0; x<9; x++) addSlot(new Slot(inv, x, 8+x*18, 142));
        addDataSlots(d);
    }
    public int getEnergy() { return data.get(0); }
    @Override public ItemStack quickMoveStack(Player p, int i) { return ItemStack.EMPTY; }
    @Override public boolean stillValid(Player p) { return true; }
}
