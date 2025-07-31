/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: ModelPartTransform.java
 */

package goblinbob.mobends.core.client.model;

import goblinbob.mobends.core.math.SmoothOrientation;
import goblinbob.mobends.core.math.TransformUtils;
import goblinbob.mobends.core.math.matrix.IMat4x4d;
import goblinbob.mobends.core.math.vector.IVec3f;
import goblinbob.mobends.core.math.vector.Vec3f;
import goblinbob.mobends.core.util.GlHelper;
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
import net.minecraft.world.phys.Vec3;

/**
 * Used for manipulating the transform of things that are
 * going to postRender this part.
 */
public class ModelPartTransform implements IModelPart
{
	public Vec3f position;
	public Vec3f scale;
	public Vec3f offset;
	public SmoothOrientation rotation;
	/**
	 * The scale at which animation position offset is applied, used for child models.
	 */
	public float offsetScale = 1.0F;
	/**
	 * Offset applied before the parent transformation.
	 */
	public Vec3f globalOffset = new Vec3f();

	public final ModelPartTransform parent;
	
	public ModelPartTransform(ModelPartTransform parent)
	{
		this.position = new Vec3f();
		this.scale = new Vec3f(1, 1, 1);
		this.offset = new Vec3f();
		this.rotation = new SmoothOrientation();
		this.parent = parent;
	}
	
	public ModelPartTransform()
	{
		this(null);
	}

	@Override
	public void renderPart(float scale)
	{
		// Since this is just a transform, do nothing.
	}
	
	@Override
	public void renderJustPart(float scale)
	{
	}

	@Override
	public void update(float ticksPerFrame)
	{
		this.rotation.update(ticksPerFrame);
	}

	@Override
	public IVec3f getPosition() { return this.position; }
	@Override
	public IVec3f getScale() { return this.scale; }
	@Override
	public IVec3f getOffset() { return this.offset; }
	@Override
	public SmoothOrientation getRotation() { return this.rotation; }
	@Override
	public float getOffsetScale() { return this.offsetScale; }
	@Override
	public IVec3f getGlobalOffset()
	{
		return globalOffset;
	}

	@Override
	public void syncUp(IModelPart part)
	{
		if(part == null)
			return;
		this.position.set(part.getPosition());
		this.rotation.set(part.getRotation());
		this.offset.set(part.getOffset());
		this.scale.set(part.getScale());
		this.offsetScale = part.getOffsetScale();
	}

	@Override
	public boolean isShowing()
	{
		return true;
	}

	@Override
	public void applyPreTransform(float scale)
	{
		if (this.globalOffset.x != 0.0F || this.globalOffset.y != 0.0F || this.globalOffset.z != 0.0F)
			RenderSystem.translate(this.globalOffset.x * scale, this.globalOffset.y * scale, this.globalOffset.z * scale);
	}

	@Override
	public void applyPreTransform(float scale, IMat4x4d dest)
	{
		if (this.globalOffset.x != 0.0F || this.globalOffset.y != 0.0F || this.globalOffset.z != 0.0F)
			TransformUtils.translate(dest, this.globalOffset.x * scale, this.globalOffset.y * scale, this.globalOffset.z * scale);
	}

	@Override
	public void applyLocalTransform(float scale)
	{
		if (this.position.x != 0.0F || this.position.y != 0.0F || this.position.z != 0.0F)
        	RenderSystem.translate(this.position.x * scale * offsetScale, this.position.y * scale * offsetScale, this.position.z * scale * offsetScale);

		if (this.offset.x != 0.0F || this.offset.y != 0.0F || this.offset.z != 0.0F)
			RenderSystem.translate(this.offset.x * scale * offsetScale, this.offset.y * scale * offsetScale, this.offset.z * scale * offsetScale);

		GlHelper.rotate(this.rotation.getSmooth());

		if(this.scale.x != 0.0F || this.scale.y != 0.0F || this.scale.z != 0.0F)
        	RenderSystem.scale(this.scale.x, this.scale.y, this.scale.z);
	}

	@Override
	public void propagateTransform(float scale)
	{
		this.applyLocalTransform(scale);
	}

	@Override
	public void applyPostTransform(float scale)
	{
	}

	@Override
	public void setVisible(boolean showModel)
	{
		// Do nothing
	}

	@Override
	public void applyLocalTransform(float scale, IMat4x4d matrix)
	{
		if (this.position.x != 0.0F || this.position.y != 0.0F || this.position.z != 0.0F)
			TransformUtils.translate(matrix, this.position.x * scale, this.position.y * scale, this.position.z * scale);

		if (this.offset.x != 0.0F || this.offset.y != 0.0F || this.offset.z != 0.0F)
			TransformUtils.translate(matrix, this.offset.x * scale * offsetScale, this.offset.y * scale * offsetScale, this.offset.z * scale * offsetScale);

		TransformUtils.rotate(matrix, this.rotation.getSmooth());
		
    	/*if(this.scale.x != 0.0F || this.scale.y != 0.0F || this.scale.z != 0.0F)
    		TransformUtils.scale(dest, this.scale.x, this.scale.y, this.scale.z, dest);*/
	}
	
	@Override
	public IModelPart getParent()
	{
		return this.parent;
	}
}
