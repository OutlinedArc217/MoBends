/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: Plane.java
 */

package goblinbob.mobends.core.math.physics;

import goblinbob.mobends.core.math.vector.IVec3fRead;
import goblinbob.mobends.core.math.vector.Vec3fReadonly;
import net.minecraft.world.phys.Vec3;

public class Plane implements ICollider
{
	
	public final Vec3fReadonly position;
	public final Vec3fReadonly normal;
	
	public Plane(IVec3fRead position, IVec3fRead normal)
	{
		this.position = new Vec3fReadonly(position);
		this.normal = new Vec3fReadonly(normal);
	}
	
	public Plane(float posX, float posY, float posZ, float dirX, float dirY, float dirZ)
	{
		this.position = new Vec3fReadonly(posX, posY, posZ);
		this.normal = new Vec3fReadonly(dirX, dirY, dirZ);
	}
	
	public IVec3fRead getPosition()
	{
		return this.position;
	}
	
	public IVec3fRead getNormal()
	{
		return this.normal;
	}

	@Override
	public RayHitInfo intersect(Ray ray)
	{
		return Physics.intersect(ray, this);
	}
	
}
