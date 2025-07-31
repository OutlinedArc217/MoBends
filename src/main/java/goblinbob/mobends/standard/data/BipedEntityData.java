/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: BipedEntityData.java
 */

package goblinbob.mobends.standard.data;

import goblinbob.mobends.core.client.model.ModelPartTransform;
import goblinbob.mobends.core.data.LivingEntityData;
import goblinbob.mobends.core.math.SmoothOrientation;
import goblinbob.mobends.core.supporters.SupporterContent;
import goblinbob.mobends.standard.client.renderer.entity.SwordTrail;
import net.minecraft.world.entity.LivingEntity;
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

public abstract class BipedEntityData<E extends LivingEntity> extends LivingEntityData<E>
{
	/*
	 * These models need to be represented only
	 * as transforms, because that's the only thing
	 * that needs to persist between frames.
	 */
	
	public ModelPartTransform head;
    public ModelPartTransform body;
    public ModelPartTransform rightArm;
    public ModelPartTransform leftArm;
    public ModelPartTransform rightLeg;
    public ModelPartTransform leftLeg;
    public ModelPartTransform rightForeArm;
    public ModelPartTransform leftForeArm;
    public ModelPartTransform rightForeLeg;
    public ModelPartTransform leftForeLeg;
	
    public SmoothOrientation renderRightItemRotation;
    public SmoothOrientation renderLeftItemRotation;
	
    public SwordTrail swordTrail;
    
	public BipedEntityData(E entity)
	{
		super(entity);
	}

	@Override
	public void initModelPose()
	{
		super.initModelPose();
		
		this.body = new ModelPartTransform();
		this.head = new ModelPartTransform(this.body);
		this.rightArm = new ModelPartTransform(this.body);
		this.leftArm = new ModelPartTransform(this.body);
		this.rightLeg = new ModelPartTransform();
		this.leftLeg = new ModelPartTransform();
		this.rightForeArm = new ModelPartTransform(this.rightArm);
		this.leftForeArm = new ModelPartTransform(this.leftArm);
		this.rightForeLeg = new ModelPartTransform(this.rightLeg);
		this.leftForeLeg = new ModelPartTransform(this.leftLeg);
		this.renderRightItemRotation = new SmoothOrientation();
		this.renderLeftItemRotation = new SmoothOrientation();
		
		this.swordTrail = new SwordTrail(() -> SupporterContent.getTrailColorFor(this.entity));
		
		this.nameToPartMap.put("body", body);
		this.nameToPartMap.put("head", head);
		this.nameToPartMap.put("leftArm", leftArm);
		this.nameToPartMap.put("rightArm", rightArm);
		this.nameToPartMap.put("leftLeg", leftLeg);
		this.nameToPartMap.put("rightLeg", rightLeg);
		this.nameToPartMap.put("leftForeArm", leftForeArm);
        this.nameToPartMap.put("rightForeArm", rightForeArm);
        this.nameToPartMap.put("leftForeLeg", leftForeLeg);
        this.nameToPartMap.put("rightForeLeg", rightForeLeg);
        this.nameToPartMap.put("renderRightItemRotation", renderRightItemRotation);
        this.nameToPartMap.put("renderLeftItemRotation", renderLeftItemRotation);
		
		this.body.position.set(0F, 12F, 0F);
		this.head.position.set(0F, -12F, 0F);
		this.rightArm.position.set(-5F, -10F, 0F);
		this.leftArm.position.set(5F, -10f, 0f);
		this.rightLeg.position.set(0F, 12.0F, 0.0F);
		this.leftLeg.position.set(0F, 12.0F, 0.0F);
		this.rightForeArm.position.set(0F, 4F, 2F);
		this.leftForeArm.position.set(0F, 4F, 2F);
		this.leftForeLeg.position.set(0, 6.0F, -2.0F);
		this.rightForeLeg.position.set(0, 6.0F, -2.0F);
	}

	@Override
	public void updateParts(float ticksPerFrame)
	{
		super.updateParts(ticksPerFrame);
		
		this.head.update(ticksPerFrame);
		this.body.update(ticksPerFrame);
		this.rightArm.update(ticksPerFrame);
		this.leftArm.update(ticksPerFrame);
		this.rightLeg.update(ticksPerFrame);
		this.leftLeg.update(ticksPerFrame);
		this.rightForeArm.update(ticksPerFrame);
		this.leftForeArm.update(ticksPerFrame);
		this.rightForeLeg.update(ticksPerFrame);
		this.leftForeLeg.update(ticksPerFrame);
		
		this.globalOffset.update(ticksPerFrame);
		this.renderRotation.update(ticksPerFrame);
		this.renderRightItemRotation.update(ticksPerFrame);
		this.renderLeftItemRotation.update(ticksPerFrame);
		
		this.swordTrail.update(ticksPerFrame);
	}
	
	@Override
	public E getEntity()
	{
		return this.entity;
	}
}
