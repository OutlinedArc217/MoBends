/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: WalkAnimationBit.java
 */

package goblinbob.mobends.standard.animation.bit.pigzombie;

import goblinbob.mobends.standard.data.PigZombieData;
import net.minecraft.util.Mth;

public class WalkAnimationBit extends goblinbob.mobends.standard.animation.bit.biped.WalkAnimationBit<PigZombieData>
{
	@Override
	public void perform(PigZombieData data)
	{
		super.perform(data);
		
		data.globalOffset.slideY(-3F);
		
		data.body.rotation.localRotateX(20F)
						  .rotateZ(-10F);
		data.head.rotation.rotateX(-20F);
		
		data.rightArm.rotation.rotateX(-20F)
						      .rotateZ(10F);
		data.leftArm.rotation.rotateX(-20F)
	    					 .rotateZ(10F);
		
		data.rightLeg.rotation.rotateZ(10F);
		data.leftLeg.rotation.rotateZ(-10F);
		
		data.rightLeg.rotation.rotateX(-30F);
		data.leftLeg.rotation.rotateX(-10F)
							 .rotateY(-10F);
		
		data.rightForeLeg.rotation.rotateX(25);
		data.leftForeLeg.rotation.rotateX(25);
		
		float limbSwing = data.limbSwing.get() * 0.6662F;
		data.globalOffset.slideY(Math.abs(Mth.sin(limbSwing)) * -1.4F - 3F);
	}
}
