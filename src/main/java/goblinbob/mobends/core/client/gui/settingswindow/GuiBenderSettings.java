/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: GuiBenderSettings.java
 */

package goblinbob.mobends.core.client.gui.settingswindow;

import goblinbob.mobends.core.bender.EntityBender;
import goblinbob.mobends.core.client.gui.elements.GuiSmallToggleButton;
import goblinbob.mobends.core.client.gui.elements.IGuiListElement;
import net.minecraft.client.Minecraft;
// REMOVED DEPRECATED: import com.mojang.blaze3d.systems.RenderSystem;
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
import net.minecraft.world.entity.Entity;

public class GuiBenderSettings implements IGuiListElement
{

    private final EntityBender<?> bender;
    private final Minecraft mc;
    private final GuiSmallToggleButton toggleButton;

    private int x, y;
    private int listOrder;

    public GuiBenderSettings(EntityBender<?> bender)
    {
        this.bender = bender;
        this.mc = Minecraft.getMinecraft();
        this.toggleButton = new GuiSmallToggleButton();
        this.toggleButton.setToggleState(bender.isAnimated());
    }

    public void initGui(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.toggleButton.initGui(x + 4, y + 4);
    }

    @Override
    public boolean handleMouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (toggleButton.mouseClicked(mouseX, mouseY, mouseButton))
        {
            bender.setAnimate(toggleButton.getToggleState());
            return true;
        }

        return false;
    }

    public void update(int mouseX, int mouseY)
    {
        toggleButton.update(mouseX, mouseY);
    }

    public void draw(float partialTicks)
    {
        RenderSystem.color(1F, 1F, 1F);

//        Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSettingsWindow.BACKGROUND_TEXTURE);
//
//        // Container
//        Draw.borderBox(x + 4, y + 4, 100, getHeight(), 4, 36, 126);

        mc.fontRenderer.drawStringWithShadow(bender.getLocalizedName(), this.x + 38, this.y + 10, 0xffffff);

        toggleButton.draw();
    }

    @Override
    public int getX()
    {
        return x;
    }

    @Override
    public int getY()
    {
        return y;
    }

    @Override
    public int getHeight()
    {
        return 20;
    }

    @Override
    public int getOrder()
    {
        return listOrder;
    }

    @Override
    public void setOrder(int order)
    {
        listOrder = order;
    }

}
