/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: ModelPartPostOffset.java
 */

// WARNING: ModelPart is final in 1.20.1 - this class needs major refactoring
package goblinbob.mobends.core.client.model;

import net.minecraft.client.model.Model;
// REMOVED DEPRECATED: import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.util.vector.Vector3f;
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

/**
 * This part is used, when accessories are rendered using it's postRender() method. It simply offsets those accessories
 * by a certain transformation.
 */
public class ModelPartPostOffset /* extends ModelPart - TODO: Reimplement as composition */
{

    /**
     * The amount to offset whatever is using the postRender method.
     */
    protected Vector3f postOffset = new Vector3f(0.0F, 0.0F, 0.0F);

    public ModelPartPostOffset(Model model, boolean register, int texOffsetX, int texOffsetY)
    {
        super(model, register, texOffsetY, texOffsetY);
    }

    public ModelPartPostOffset(Model model, boolean register)
    {
        super(model, register);
    }

    public ModelPartPostOffset(Model model, int texOffsetX, int texOffsetY)
    {
        super(model, texOffsetX, texOffsetY);
    }

    public ModelPartPostOffset setPostOffset(float x, float y, float z)
    {
        this.postOffset.set(x, y, z);
        return this;
    }

    @Override
    public void propagateTransform(float scale)
    {
        super.propagateTransform(scale);
    }

    @Override
    public void applyPostTransform(float scale)
    {
        RenderSystem.translate(this.postOffset.x * scale, this.postOffset.y * scale, this.postOffset.z * scale);
    }

}
