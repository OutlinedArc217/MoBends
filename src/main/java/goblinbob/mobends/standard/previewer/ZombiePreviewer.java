/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: ZombiePreviewer.java
 */

package goblinbob.mobends.standard.previewer;

import goblinbob.mobends.standard.data.ZombieData;
import net.minecraft.world.entity.Entity;

public class ZombiePreviewer extends BipedPreviewer<ZombieData>
{

	/**
	 * The Entity is generated specifically just for preview, so
	 * it can be manipulated in any way.
	 */
	@Override
	public void prePreview(ZombieData data, String animationToPreview)
	{
		super.prePreview(data, animationToPreview);
	}

	@Override
	public void postPreview(ZombieData data, String animationToPreview)
	{
		// No behaviour
	}

}
