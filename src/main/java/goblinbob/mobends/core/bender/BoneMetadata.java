/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: BoneMetadata.java
 */

package goblinbob.mobends.core.bender;

import goblinbob.mobends.core.math.physics.AABBox;
import goblinbob.mobends.core.math.physics.IAABBox;
import net.minecraft.world.phys.AABB;

public class BoneMetadata
{

	protected AABBox boundingBox;
	
	public BoneMetadata(IAABBox boundingBox)
	{
		this.boundingBox = new AABBox(boundingBox);
	}
	
	public BoneMetadata(float x0, float y0, float z0, float x1, float y1, float z1)
	{
		this.boundingBox = new AABBox(x0, y0, z0, x1, y1, z1);
	}
	
	public IAABBox getBounds()
	{
		return this.boundingBox;
	}
	
}
