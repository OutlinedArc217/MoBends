/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: SprintAnimationBit.java
 */

package goblinbob.mobends.standard.animation.bit.biped;

import goblinbob.mobends.core.animation.bit.AnimationBit;
import goblinbob.mobends.standard.data.BipedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class SprintAnimationBit<T extends BipedEntityData<?>> extends AnimationBit<T>
{
	private static String[] ACTIONS = new String[] { "sprint" };
	
	@Override
	public String[] getActions(T entityData)
	{
		return ACTIONS;
	}

	@Override
	public void perform(T data)
	{
		data.localOffset.slideToZero(0.3F);
		data.globalOffset.slideToZero(0.1F);
		data.centerRotation.setSmoothness(.3F).orientZero();
		data.renderRotation.setSmoothness(.3F).orientZero();
		data.renderRightItemRotation.setSmoothness(.3F).orientZero();
		data.renderLeftItemRotation.setSmoothness(.3F).orientZero();

		final float headPitch = data.headPitch.get();
		final float headYaw = data.headYaw.get();

		final float PI = (float) Math.PI;
		float limbSwing = data.limbSwing.get() * 0.6662F * 0.8F;
		float armSwingAmount =  data.limbSwingAmount.get() / PI * 180F * 1.1F;
		data.rightArm.rotation.setSmoothness(0.8F).orientX(Mth.cos(limbSwing + PI) * armSwingAmount)
				.rotateZ(5);
		data.leftArm.rotation.setSmoothness(0.8F).orientX(Mth.cos(limbSwing) * armSwingAmount)
				.rotateZ(-5);
		
		float legSwingAmount = 1.26F * data.limbSwingAmount.get() / PI * 180F;
		data.rightLeg.rotation.setSmoothness(1.0F).orientX(-5F + Mth.cos(limbSwing) * legSwingAmount)
				.rotateZ(2);
		data.leftLeg.rotation.setSmoothness(1.0F).orientX(-5F + Mth.cos(limbSwing + PI) * legSwingAmount)
				.rotateZ(-2);

		float foreLegSwingAmount = 0.7F * data.limbSwingAmount.get() / PI * 180F;
		float var = (limbSwing / PI) % 2;
		data.leftForeLeg.rotation.setSmoothness(.7F).orientX(40F + Mth.cos(limbSwing + 1.8F) * foreLegSwingAmount);
		data.rightForeLeg.rotation.setSmoothness(.7F).orientX(40F + Mth.cos(limbSwing + PI + 1.8F) * foreLegSwingAmount);
		data.leftForeArm.rotation.setSmoothness(.3F).orientX((var > 1 ? -10F : -45F));
		data.rightForeArm.rotation.setSmoothness(.3F).orientX((var > 1 ? -45F : -10F));

		float bodyRotationY = Mth.cos(limbSwing) * -40;
		float bodyRotationX = Mth.cos(limbSwing * 2F) * 10F + 10F;
		float var10 = headYaw * .3F;
		var10 = Math.max(-10, Math.min(var10, 10));
		data.body.rotation.setSmoothness(.8F).orientY(bodyRotationY)
				.rotateX(bodyRotationX)
				.rotateZ(-var10);
		data.head.rotation.setSmoothness(.5F).orientX(headPitch - bodyRotationX)
											 .rotateY(headYaw - bodyRotationY);
		

		data.globalOffset.slideY(Mth.cos(limbSwing * 2F + 0.6F) * 1.5f, .9f);
	}

}
