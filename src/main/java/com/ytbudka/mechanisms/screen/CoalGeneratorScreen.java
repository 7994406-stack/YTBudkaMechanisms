package com.ytbudka.mechanisms.screen;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CoalGeneratorScreen extends AbstractContainerScreen<CoalGeneratorMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/gui/container/furnace.png");
    public CoalGeneratorScreen(CoalGeneratorMenu m, Inventory i, Component t) { super(m, i, t); }
    @Override protected void renderBg(GuiGraphics g, float t, int x, int y) {
        RenderSystem.setShaderTexture(0, TEXTURE);
        g.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        g.drawString(this.font, "Energy: " + menu.getEnergy() + " FE", leftPos + 10, topPos + 10, 0x404040, false);
    }
    @Override public void render(GuiGraphics g, int x, int y, float t) { renderBackground(g); super.render(g, x, y, t); renderTooltip(g, x, y); }
}
