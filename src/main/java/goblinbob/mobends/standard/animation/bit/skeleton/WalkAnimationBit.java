/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: WalkAnimationBit.java
 */

package goblinbob.mobends.standard.animation.bit.skeleton;

import goblinbob.mobends.standard.data.SkeletonData;
import net.minecraft.util.Mth;

public class WalkAnimationBit extends goblinbob.mobends.standard.animation.bit.biped.WalkAnimationBit<SkeletonData>
{
	@Override
	public void perform(SkeletonData data)
	{
		super.perform(data);

		if (data.isStrafing())
		{
			final float PI = (float) Math.PI;
			float limbSwing = data.limbSwing.get() * 0.6662F;

			float legSwingAmount = 0.7F * data.limbSwingAmount.get() / PI * 180F;
			data.rightLeg.rotation.setSmoothness(1.0F).orientZ(-5F + Mth.cos(limbSwing) * legSwingAmount);
			data.leftLeg.rotation.setSmoothness(1.0F).orientZ(-5F + Mth.cos(limbSwing + PI) * legSwingAmount);
		}
	}
}
