/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: PartContainer.java
 */

// WARNING: ModelPart is final in 1.20.1 - this class needs major refactoring
package goblinbob.mobends.standard.client.model.armor;

import goblinbob.mobends.core.client.model.IModelPart;
import goblinbob.mobends.core.math.SmoothOrientation;
import goblinbob.mobends.core.math.TransformUtils;
import goblinbob.mobends.core.math.matrix.IMat4x4d;
import goblinbob.mobends.core.math.physics.ICollider;
import goblinbob.mobends.core.math.vector.IVec3f;
import goblinbob.mobends.core.math.vector.Vec3f;
import goblinbob.mobends.core.util.GlHelper;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
// REMOVED DEPRECATED: import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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

public class PartContainer /* extends ModelPart - TODO: Reimplement as composition */ implements IModelPart
{
	public Vec3f position;
	/**
	 * A secondary position variable is used to offset
	 * the model relative to the first position, which may
	 * be overridden by animation.
	 * */
	public Vec3f offset;
	/**
	 * Used to offset the container item relative to
	 * the rotation point
	 * */
	public Vec3f innerOffset;
	public Vec3f scale;
	public SmoothOrientation rotation;
	public float offsetScale = 1.0F;
	/**
	 * Offset applied before the parent transformation.
	 */
	public Vec3f globalOffset = new Vec3f();
	
	private ModelPart innerModel;
	
	protected IModelPart parent;
	protected ICollider collider;
	
	public PartContainer(Model modelBase, ModelPart model)
	{
		super(modelBase, 0, 0);
		this.innerModel = model;
		this.position = new Vec3f();
		this.offset = new Vec3f();
		this.innerOffset = new Vec3f();
		this.scale = new Vec3f(1, 1, 1);
		this.rotation = new SmoothOrientation();
		
		this.mirror = model.mirror;
	}

	public ModelPart getModel() { return this.innerModel; }
	public IModelPart getParent() { return this.parent; }
	
	public PartContainer setParent(IModelPart parent)
	{
		this.parent = parent;
		return this;
	}
	
	public PartContainer setPosition(float x, float y, float z)
	{
		this.position.set(x, y, z);
		return this;
	}
	
	public PartContainer setInnerOffset(float x, float y, float z)
	{
		this.innerOffset.set(x, y, z);
		return this;
	}

	private void renderContainedModel(float scale)
	{
		float x = this.innerModel.rotationPointX;
		float y = this.innerModel.rotationPointY;
		float z = this.innerModel.rotationPointZ;
		float ox = this.innerModel.offsetX;
		float oy = this.innerModel.offsetY;
		float oz = this.innerModel.offsetZ;
		
		// We assume that the rotation angle is updated each frame (since they're the original parts.)
		this.innerModel.rotateAngleX = this.innerModel.rotateAngleY = this.innerModel.rotateAngleZ = 0;
		this.innerModel.rotationPointX = this.innerModel.rotationPointY = this.innerModel.rotationPointZ = 0;
		this.innerModel.offsetX = this.innerModel.offsetY = this.innerModel.offsetZ = 0;
		this.innerModel.showModel = true;
		this.innerModel.isHidden = false;

		// Rendering the model, with no offset and rotation.
        this.innerModel.render(scale);
		
		// Restoring the previous values.
		this.innerModel.rotationPointX = x;
		this.innerModel.rotationPointY = y;
		this.innerModel.rotationPointZ = z;
		this.innerModel.offsetX = ox;
		this.innerModel.offsetY = oy;
		this.innerModel.offsetZ = oz;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void render(float scale)
    {
        this.renderPart(scale);
    }
	
	@Override
	public void renderPart(float scale)
	{
		if (!(this.isShowing())) return;
		
        RenderSystem.pushMatrix();
        this.applyCharacterTransform(scale);
        
        // This is applied outside the standalone transform method, so that children aren't affected.
        if (this.innerOffset.x != 0.0F || this.innerOffset.y != 0.0F || this.innerOffset.z != 0.0F)
		RenderSystem.translate(this.innerOffset.x * scale, this.innerOffset.y * scale, this.innerOffset.z * scale);
        
		this.renderContainedModel(scale);

        if (this.childModels != null)
        {
            for (int k = 0; k < this.childModels.size(); ++k)
            {
                ((ModelPart)this.childModels.get(k)).render(scale);
            }
        }
        RenderSystem.popMatrix();
	}
	
	@Override
	public void renderJustPart(float scale)
	{
		if (!(this.isShowing())) return;
		
        RenderSystem.pushMatrix();
        this.applyLocalTransform(scale);
        
        // This is applied outside the standalone transform method, so that children aren't affected.
        if (this.innerOffset.x != 0.0F || this.innerOffset.y != 0.0F || this.innerOffset.z != 0.0F)
        	RenderSystem.translate(this.innerOffset.x * scale, this.innerOffset.y * scale, this.innerOffset.z * scale);
        
        this.renderContainedModel(scale);
        
        if (this.childModels != null)
        {
            for (int k = 0; k < this.childModels.size(); ++k)
            {
                ((ModelPart)this.childModels.get(k)).render(scale);
            }
        }
        RenderSystem.popMatrix();
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void postRender(float scale)
    {
        this.applyCharacterTransform(scale);
        this.applyPostTransform(scale);
    }
	
	@Override
	public void applyCharacterTransform(float scale)
	{
		if (this.parent != null)
			this.parent.applyCharacterTransform(scale);
		this.applyLocalTransform(scale);
	}

	@Override
	public void applyLocalTransform(float scale)
	{
		if (this.position.x != 0.0F || this.position.y != 0.0F || this.position.z != 0.0F)
        	RenderSystem.translate(this.position.x * scale * offsetScale, this.position.y * scale * offsetScale, this.position.z * scale * offsetScale);

		if (this.offset.x != 0.0F || this.offset.y != 0.0F || this.offset.z != 0.0F)
        	RenderSystem.translate(this.offset.x * scale * offsetScale, this.offset.y * scale * offsetScale, this.offset.z * scale * offsetScale);
		
		GlHelper.rotate(this.rotation.getSmooth());
        
        if (this.scale.x != 0.0F || this.scale.y != 0.0F || this.scale.z != 0.0F)
        	RenderSystem.scale(this.scale.x, this.scale.y, this.scale.z);
	}

	@Override
	public void applyPostTransform(float scale)
	{
	}

	@Override
	public void update(float ticksPerFrame)
	{
		this.rotation.update(ticksPerFrame);
	}

	@Override
	public Vec3f getPosition() { return this.position; }
	@Override
	public Vec3f getScale() { return this.scale; }
	@Override
	public Vec3f getOffset() { return this.offset; }
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
		return this.showModel && !this.isHidden;
	}

	@Override
	public void setVisible(boolean showModel)
	{
		this.showModel = showModel;
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
	public void applyLocalTransform(float scale, IMat4x4d matrix)
	{
		if (this.position.x != 0.0F || this.position.y != 0.0F || this.position.z != 0.0F)
			TransformUtils.translate(matrix, this.position.x * scale * offsetScale, this.position.y * scale * offsetScale, this.position.z * scale * offsetScale, matrix);

		if (this.offset.x != 0.0F || this.offset.y != 0.0F || this.offset.z != 0.0F)
			TransformUtils.translate(matrix, this.offset.x * scale * offsetScale, this.offset.y * scale * offsetScale, this.offset.z * scale * offsetScale);

		TransformUtils.rotate(matrix, rotation.getSmooth());

		if(this.scale.x != 0.0F || this.scale.y != 0.0F || this.scale.z != 0.0F)
    		TransformUtils.scale(matrix, this.scale.x, this.scale.y, this.scale.z);
	}
}
