/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: GuiAddonsWindow.java
 */

package goblinbob.mobends.core.client.gui.addonswindow;

import goblinbob.mobends.core.addon.Addons;
import goblinbob.mobends.core.addon.IAddon;
import goblinbob.mobends.standard.main.ModStatics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class GuiAddonsWindow extends Gui
{
	public static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(ModStatics.MODID, "textures/gui/addons_window.png");
	
	private static final int WIDTH = 210;
	private static final int HEIGHT = 122;
	static final int SCROLLBAR_WIDTH = 5;
	
	private int x, y;
	private FontRenderer fontRenderer;
	
	public GuiAddonsWindow()
	{
		this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
	}
	
	public void initGui(int x, int y)
	{
		this.x = x - WIDTH/2;
		this.y = y - HEIGHT/2;
	}
	
	public void onOpened()
	{
	}
	
	public void display(int mouseX, int mouseY, float partialTicks)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		this.drawTexturedModalRect(this.x, this.y, 0, 0, WIDTH, HEIGHT);

		this.drawCenteredString(this.fontRenderer, Component.format("mobends.gui.addons"),
				(int) (this.x + WIDTH/2), this.y + 4, 0xFFFFFF);
		
		int y = this.y + 50;
		for (IAddon addon : Addons.getRegistered()) {
			this.drawCenteredString(this.fontRenderer, addon.getDisplayName(),
					(int) (this.x + WIDTH/2), y, 0xFFFFFF);
			y += 50;
		}
	}
	
	public void update(int mouseX, int mouseY)
	{
	}
	
	public boolean mouseClicked(int mouseX, int mouseY, int state)
	{
		return false;
	}
	
	public void mouseReleased(int mouseX, int mouseY, int event)
	{
		
	}
}
