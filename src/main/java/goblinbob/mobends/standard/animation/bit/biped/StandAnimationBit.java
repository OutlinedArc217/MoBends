/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: StandAnimationBit.java
 */

package goblinbob.mobends.standard.animation.bit.biped;

import goblinbob.mobends.core.animation.bit.AnimationBit;
import goblinbob.mobends.core.client.event.DataUpdateHandler;
import goblinbob.mobends.standard.data.BipedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class StandAnimationBit<T extends BipedEntityData<?>> extends AnimationBit<T>
{
	private static final String[] ACTIONS = new String[] { "stand" };
	
	protected final float kneelDuration = 0.15F;

	@Override
	public String[] getActions(T entityData)
	{
		return ACTIONS;
	}

	@Override
	public void onPlay(T data)
	{
		float touchdown = Math.min(data.getTicksAfterTouchdown() * kneelDuration, 1.0F);
		if (touchdown < 0.5F)
		{
			data.body.rotation.orientInstant(20F, 1F, 0F, 0F);
			data.rightLeg.rotation.orient(-20F, 1F, 0F, 0F);
			data.leftLeg.rotation.orient(-45, 1F, 0F, 0F);
			data.rightForeLeg.rotation.orient(60F, 1F, 0F, 0F);
			data.leftForeLeg.rotation.orient(60, 1F, 0F, 0F);
		}
	}

	@Override
	public void perform(T data)
	{
		data.localOffset.slideToZero(0.3F);
		data.globalOffset.slideToZero(0.3F);
		data.renderRotation.setSmoothness(.3F).orientZero();
		data.centerRotation.setSmoothness(.3F).orientZero();
		data.renderRightItemRotation.setSmoothness(.3F).orientZero();
		data.renderLeftItemRotation.setSmoothness(.3F).orientZero();
		
		data.rightLeg.rotation.orient(0F, 1F, 0F, 0F);
		data.rightLeg.rotation.rotate(2F, 0F, 0F, 1F);
		data.rightLeg.rotation.rotate(5, 0F, 1F, 0F);
		data.leftLeg.rotation.orient(0F, 1F, 0F, 0F);
		data.leftLeg.rotation.rotate(-2F, 0F, 0F, 1F);
		data.leftLeg.rotation.rotate(-5, 0F, 1F, 0F);
		data.rightForeLeg.rotation.orient(4F, 1F, 0F, 0F);
		data.leftForeLeg.rotation.orient(4F, 1F, 0F, 0F);
		data.rightForeArm.rotation.orient(-4.0F, 1F, 0F, 0F);
		data.leftForeArm.rotation.orient(-4.0F, 1F, 0F, 0F);

		data.head.rotation.orientX(data.headPitch.get())
						  .rotateY(data.headYaw.get());

		final float PI = (float) Math.PI;
		float phase = DataUpdateHandler.getTicks() / 10;
		data.body.rotation.setSmoothness(1.0F).orientX(((Mth.cos(phase) - 1) / 2) * -3);
		data.rightArm.rotation.setSmoothness(0.4F).orientX(0.0F)
				.rotateZ(Mth.cos(phase + PI/2) * -2.5F + 2.5F);
		data.leftArm.rotation.setSmoothness(0.4F).orientX(0.0F)
				.rotateZ(Mth.cos(phase + PI/2) * 2.5F - 2.5F);

		float touchdown = Math.min(data.getTicksAfterTouchdown() * kneelDuration, 1.0F);
		if (touchdown < 1.0F)
		{
			data.body.rotation.setSmoothness(1F);
			data.body.rotation.orient(20.0F * (1 - touchdown), 1F, 0F, 0F);
			data.globalOffset.setY((float) -Math.sin(touchdown * Math.PI) * 2.0F);
		}
	}
}
