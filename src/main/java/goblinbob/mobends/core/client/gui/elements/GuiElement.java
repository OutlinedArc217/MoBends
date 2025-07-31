package goblinbob.mobends.core.client.gui.elements;

// REMOVED DEPRECATED: import net.minecraft.client.renderer.GlStateManager;

import java.util.LinkedList;
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

public abstract class GuiElement implements IGuiElement, IGuiElementsContainer
{

    protected int x;
    protected int y;

    protected IGuiElement parent;
    protected LinkedList<IGuiElement> children;

    public GuiElement(IGuiElement parent, int x, int y)
    {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.children = new LinkedList<>();
    }

    @Override
    public void initGui()
    {
        this.children.clear();
    }

    @Override
    public IGuiElement getParent() {
        return this.parent;
    }

    @Override
    public LinkedList<IGuiElement> getElements() {
        return this.children;
    }

    @Override
    public void update(int mouseX, int mouseY)
    {
        this.updateChildren(mouseX, mouseY);
    }

    @Override
    public boolean handleMouseClicked(int mouseX, int mouseY, int button)
    {
        return this.handleMouseClickedChildren(mouseX, mouseY, button);
    }

    @Override
    public boolean handleMouseReleased(int mouseX, int mouseY, int button)
    {
        return this.handleMouseReleasedChildren(mouseX, mouseY, button);
    }

    @Override
    public void draw(float partialTicks)
    {
        RenderSystem.pushMatrix();
        RenderSystem.translate(this.getViewX(), this.getViewY(), 0);

        this.drawBackground(partialTicks);
        this.drawChildren(partialTicks);
        this.drawForeground(partialTicks);

        RenderSystem.popMatrix();
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

    protected abstract void drawBackground(float partialTicks);

    protected abstract void drawForeground(float partialTicks);

}
