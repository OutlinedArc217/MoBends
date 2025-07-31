/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: RayHitInfo.java
 */

package goblinbob.mobends.core.math.physics;

import goblinbob.mobends.core.math.vector.IVec3fRead;
import goblinbob.mobends.core.math.vector.Vec3fReadonly;
import net.minecraft.world.phys.Vec3;

public class RayHitInfo
{
	
	public final Vec3fReadonly hitPoint;
	
	public RayHitInfo(IVec3fRead hitPoint)
	{
		this.hitPoint = new Vec3fReadonly(hitPoint);
	}
	
	public RayHitInfo(float x, float y, float z)
	{
		this.hitPoint = new Vec3fReadonly(x, y, z);
	}
	
}
