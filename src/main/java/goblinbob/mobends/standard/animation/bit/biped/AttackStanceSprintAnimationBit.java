/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: AttackStanceSprintAnimationBit.java
 */

package goblinbob.mobends.standard.animation.bit.biped;

import goblinbob.mobends.core.animation.bit.AnimationBit;
import goblinbob.mobends.core.client.model.IModelPart;
import goblinbob.mobends.standard.data.BipedEntityData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
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
import net.minecraft.core.Direction;

public class AttackStanceSprintAnimationBit extends AnimationBit<BipedEntityData<?>>
{

	private static final String[] ACTIONS = new String[] { "attack_stance_sprint" };
	
	@Override
	public String[] getActions(BipedEntityData<?> entityData)
	{
		return ACTIONS;
	}

	@Override
	public void perform(BipedEntityData<?> data)
	{
		data.localOffset.slideToZero(0.3F);

		final LivingEntity living = data.getEntity();
		final EnumHandSide primaryHand = living.getPrimaryHand();

		boolean mainHandSwitch = primaryHand == EnumHandSide.RIGHT;
		// Main Hand Direction Multiplier - it helps switch animation sides depending on
		// what is your main hand.
		float handDirMtp = mainHandSwitch ? 1 : -1;
		IModelPart mainArm = mainHandSwitch ? data.rightArm : data.leftArm;
		IModelPart offArm = mainHandSwitch ? data.leftArm : data.rightArm;
		IModelPart mainForeArm = mainHandSwitch ? data.rightForeArm : data.leftForeArm;
		IModelPart offForeArm = mainHandSwitch ? data.leftForeArm : data.rightForeArm;

		if (living.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSword)
		{
			data.swordTrail.add(data, 0.0F, 0.0F, -10.0F);
		}

		data.body.rotation.rotateY(20 * handDirMtp);
		data.head.rotation.rotateY(-20 * handDirMtp);
		mainArm.getRotation().orientZ(60.0F * handDirMtp);
		mainArm.getRotation().rotateY(60.0F);
		offArm.getRotation().rotateZ(-30.0F * handDirMtp);
		
		if (mainHandSwitch)
		{
			data.renderRightItemRotation.setSmoothness(.3F).orientX(45);
		}
		else
		{
			data.renderLeftItemRotation.setSmoothness(.3F).orientX(45);
		}
	}

}
