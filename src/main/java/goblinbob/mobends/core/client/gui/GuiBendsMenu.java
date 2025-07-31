/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: GuiBendsMenu.java
 */

package goblinbob.mobends.core.client.gui;

import goblinbob.mobends.core.WebAPI;
import goblinbob.mobends.core.client.gui.elements.GuiSectionButton;
import goblinbob.mobends.core.client.gui.packswindow.GuiPacksWindow;
import goblinbob.mobends.core.client.gui.popup.GuiEditorNotFound;
import goblinbob.mobends.core.client.gui.popup.GuiPopUp;
import goblinbob.mobends.core.client.gui.settingswindow.GuiSettingsWindow;
import goblinbob.mobends.core.network.NetworkConfiguration;
import goblinbob.mobends.core.util.Draw;
import goblinbob.mobends.core.util.GuiHelper;
import goblinbob.mobends.standard.main.ModStatics;
import net.minecraft.client.gui.GuiScreen;
// REMOVED DEPRECATED: import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
// REMOVED DEPRECATED: import org.lwjgl.opengl.GL11;

import java.io.IOException;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.joml.Matrix4f;
import org.joml.Matrix3f;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;

public class GuiBendsMenu extends GuiScreen
{
	
	private static final ResourceLocation MENU_TITLE_TEXTURE = ResourceLocation.fromNamespaceAndPath(ModStatics.MODID, "textures/gui/title.png");
	public static final ResourceLocation ICONS_TEXTURE = ResourceLocation.fromNamespaceAndPath(ModStatics.MODID, "textures/gui/icons.png");

	private GuiSectionButton settingsButton;
	private GuiSectionButton packsButton;
	private GuiSectionButton customizeButton;
	//private GuiSectionButton addonsButton;
	private GuiPopUp popUp;

	public GuiBendsMenu()
	{
		Keyboard.enableRepeatEvents(true);

		this.settingsButton = new GuiSectionButton(Component.format("mobends.gui.section.settings"), 0xFFDA3A00)
				.setLeftIcon(0, 43, 19, 19).setRightIcon(19, 43, 19, 19);
		this.packsButton = new GuiSectionButton(Component.format("mobends.gui.section.packs"), 0xFF4577DE)
				.setLeftIcon(38, 43, 23, 20).setRightIcon(38, 43, 23, 20);
		this.customizeButton = new GuiSectionButton(Component.format("mobends.gui.section.customize"), 0xFF26DAA3)
				.setLeftIcon(80, 43, 19, 14).setRightIcon(80, 43, 19, 14);
//		this.addonsButton = new GuiSectionButton(Component.format("mobends.gui.section.addons"), 0xFFFFE565)
//				.setLeftIcon(61, 43, 19, 18).setRightIcon(61, 43, 19, 18);

		this.popUp = null;
	}

	public void initGui()
	{
		super.initGui();
		this.buttonList.clear();

		if (this.popUp != null)
			this.popUp.initGui(this.width / 2, this.height / 2);

		int startY = height / 2 - 32;
		int distance = 49;

		if (NetworkConfiguration.instance.areBendsPacksAllowed())
		{
			this.settingsButton.initGui((this.width - 318) / 2, startY);
			this.packsButton.initGui((this.width - 318) / 2, startY + distance);
			this.customizeButton.initGui((this.width - 318) / 2, startY + distance * 2);
		}
		else
		{
			this.settingsButton.initGui((this.width - 318) / 2, startY);
			this.customizeButton.initGui((this.width - 318) / 2, startY + distance);
		}
	}

	protected void keyTyped(char typedChar, int keyCode)
	{
		if (popUp != null)
		{
			return;
		}

		switch (keyCode)
		{
			case 1:
				GuiHelper.closeGui();
				break;
		}
	}

	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void updateScreen()
	{
		int mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
		int mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;

		if (this.popUp != null)
		{
			this.popUp.update(mouseX, mouseY);
			return;
		}

		this.settingsButton.update(mouseX, mouseY);
		this.packsButton.update(mouseX, mouseY);
		this.customizeButton.update(mouseX, mouseY);
	}

	@Override
	protected void mouseClicked(int x, int y, int state)
	{
		if (popUp != null)
		{
			popUp.mouseClicked(x, y, state);
			return;
		}

		if (settingsButton.mouseClicked(x, y, state))
		{
			mc.displayGuiScreen(new GuiSettingsWindow());
		}
		else if (packsButton.mouseClicked(x, y, state))
		{
			mc.displayGuiScreen(new GuiPacksWindow());
		}
		else if (customizeButton.mouseClicked(x, y, state))
		{
			IAnimationEditor editor = AnimationEditorRegistry.INSTANCE.getPrimaryEditor();

			if (editor == null)
			{
				openPopUp(new GuiEditorNotFound(this::closePopUp, () -> {
					GuiEditorNotFound editorNotFoundPopup = (GuiEditorNotFound) popUp;
					String downloadUrl = WebAPI.INSTANCE.getOfficialAnimationEditorUrl();

					if (downloadUrl == null || !GuiHelper.openUrlInBrowser(downloadUrl))
					{
						editorNotFoundPopup.setErrorOccurred(true);
					}
					else
					{
						closePopUp();
					}
				}));
			}
			else
			{
				// Opens the animation editor.
				editor.openEditorGui();
			}
		}

		try
		{
			super.mouseClicked(x, y, state);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	protected void mouseReleased(int mouseX, int mouseY, int state)
	{
		super.mouseReleased(mouseX, mouseY, state);
		this.settingsButton.mouseReleased(mouseX, mouseY, state);
		this.packsButton.mouseReleased(mouseX, mouseY, state);
		this.customizeButton.mouseReleased(mouseX, mouseY, state);
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);

		RenderSystem.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(MENU_TITLE_TEXTURE);
		int titleWidth = 167 * 2;
		int titleHeight = 37 * 2;

		Draw.texturedRectangle((width - titleWidth) / 2, (height - titleHeight) / 2 - 70, titleWidth, titleHeight, 0, 0, 1, 1);

		this.settingsButton.display();
		if (NetworkConfiguration.instance.areBendsPacksAllowed())
		{
			this.packsButton.display();
		}
		this.customizeButton.display();

		super.drawScreen(mouseX, mouseY, partialTicks);

		if (this.popUp != null)
		{
			RenderSystem.disableDepth();
			this.drawDefaultBackground();
			this.popUp.display(mouseX, mouseY, partialTicks);
			RenderSystem.enableDepth();
		}
	}

	public boolean doesGuiPauseGame()
	{
		return false;
	}

	private void closePopUp()
	{
		this.popUp = null;
		this.initGui();
	}

	private void openPopUp(GuiPopUp popUp)
	{
		this.popUp = popUp;
		this.initGui();
	}

}