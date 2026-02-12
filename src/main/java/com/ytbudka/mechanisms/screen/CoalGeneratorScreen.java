package com.ytbudka.mechanisms.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ytbudka.mechanisms.MechanismsMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CoalGeneratorScreen extends AbstractContainerScreen<CoalGeneratorMenu> {
    private static final ResourceLocation TEXTURE =
        new ResourceLocation(MechanismsMod.MOD_ID, "textures/gui/coal_generator_gui.png");
    
    public CoalGeneratorScreen(CoalGeneratorMenu menu, Inventory inv, Component component) {
        super(menu, inv, component);
    }
    
    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = this.imageHeight - 94;
    }
    
    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        
        // Отрисовка прогресса горения
        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 80, y + 34, 176, 0, 14, menu.getScaledProgress());
        }
        
        // Отрисовка энергии
        int energy = menu.getEnergyStored();
        int maxEnergy = menu.getMaxEnergy();
        int energyHeight = maxEnergy != 0 ? (int)(60 * ((float)energy / maxEnergy)) : 0;
        guiGraphics.blit(TEXTURE, x + 152, y + 13 + (60 - energyHeight), 176, 14 + (60 - energyHeight), 12, energyHeight);
    }
    
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
        
        // Тултип для энергии
        if (mouseX >= leftPos + 152 && mouseX <= leftPos + 164 && mouseY >= topPos + 13 && mouseY <= topPos + 73) {
            guiGraphics.renderTooltip(this.font, Component.literal(menu.getEnergyStored() + " / " + menu.getMaxEnergy() + " FE"), mouseX, mouseY);
        }
    }
}
