/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: SneakAnimationBit.java
 */

package goblinbob.mobends.standard.animation.bit.biped;

import goblinbob.mobends.core.animation.bit.AnimationBit;
import goblinbob.mobends.standard.data.BipedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class SneakAnimationBit extends AnimationBit<BipedEntityData<?>>
{
	private static final String[] ACTIONS = new String[] { "sneak" };
	
	@Override
	public String[] getActions(BipedEntityData<?> entityData)
	{
		return ACTIONS;
	}

	@Override
	public void perform(BipedEntityData<?> data)
	{
		data.localOffset.slideToZero(0.3F);
		data.globalOffset.slideY(-1.3F);
	
		final float PI = (float) Math.PI;
		float limbSwing = data.limbSwing.get() * 0.6662F;
		float limbSwingAmount = data.limbSwingAmount.get() * 1.4F * 1.1F / PI * 180F;
		float var = (limbSwing / PI) % 2;
		data.rightLeg.rotation.setSmoothness(1.0F).orientX(Mth.cos(limbSwing) * limbSwingAmount - 5F)
				.rotateZ(10);
		data.leftLeg.rotation.setSmoothness(1.0F).orientX(Mth.cos(limbSwing + PI) * limbSwingAmount - 5F)
				.rotateZ(-10);

		data.rightArm.rotation.setSmoothness(0.8F).orientX(20F * Mth.cos(limbSwing + PI) - 20F)
				.rotateZ(10.0F);
		data.leftArm.rotation.setSmoothness(0.8F).orientX(20F * Mth.cos(limbSwing) - 20F)
				.rotateZ(-10.0F);
		
		data.leftForeLeg.rotation.setSmoothness(0.3F).orientX(var > 1 ? 45F : 10F);
		data.rightForeLeg.rotation.setSmoothness(0.3F).orientX(var > 1 ? 10F : 45F);

		float var2 = 25F + Mth.cos(limbSwing * 2F) * 5F;
		data.body.rotation.localRotateX(var2);
		data.head.rotation.rotateX(-var2);
	}
}
