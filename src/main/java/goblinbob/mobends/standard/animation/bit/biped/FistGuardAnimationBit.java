/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: FistGuardAnimationBit.java
 */

package goblinbob.mobends.standard.animation.bit.biped;

import goblinbob.mobends.core.animation.bit.AnimationBit;
import goblinbob.mobends.standard.data.BipedEntityData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.util.EnumHandSide;
import net.minecraft.core.Direction;

public class FistGuardAnimationBit extends AnimationBit<BipedEntityData<?>>
{
	private static final String[] ACTIONS = new String[] { "fist_guard" };
	
	@Override
	public String[] getActions(BipedEntityData<?> entityData)
	{
		return ACTIONS;
	}

	@Override
	public void perform(BipedEntityData<?> data)
	{
		LivingEntity living = data.getEntity();
		EnumHandSide primaryHand = living.getPrimaryHand();

		boolean mainHandSwitch = primaryHand == EnumHandSide.RIGHT;
		// Main Hand Direction Multiplier - it helps switch animation sides depending on
		// what is your main hand.
		float handDirMtp = mainHandSwitch ? 1 : -1;
		
		data.globalOffset.slideY(-2.0F);
		data.renderRotation.setSmoothness(.3F).orientY(-20 * handDirMtp);
		
		data.rightArm.rotation.setSmoothness(.3F).orientX(-90F)
				.rotateZ(20F);
		data.rightForeArm.rotation.setSmoothness(.3F).orientX(-80F);

		data.leftArm.rotation.setSmoothness(.3F).orientX(-90F)
				.rotateZ(-20F);
		data.leftForeArm.rotation.setSmoothness(.3F).orientX(-80F);
		
		data.body.rotation.rotateX(10);

		data.rightLeg.rotation.setSmoothness(.3F).orientX(-30F)
				.rotateZ(10);
		data.leftLeg.rotation.setSmoothness(.3F).orientX(-30F)
				.rotateY(-25F)
				.rotateZ(-10);

		data.rightForeLeg.rotation.setSmoothness(.3F).orientX(30);
		data.leftForeLeg.rotation.setSmoothness(.3F).orientX(30);

		data.head.rotation.rotateX(-10);
		data.head.rotation.rotateY(-20 * handDirMtp);
	}
}
