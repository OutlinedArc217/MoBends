/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: Ray.java
 */

package goblinbob.mobends.core.math.physics;

import goblinbob.mobends.core.math.vector.IVec3fRead;
import goblinbob.mobends.core.math.vector.Vec3fReadonly;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

public class Ray
{
	
	public final Vec3fReadonly position;
	public final Vec3fReadonly direction;
	
	public Ray(IVec3fRead position, IVec3fRead direction)
	{
		this.position = new Vec3fReadonly(position);
		this.direction = new Vec3fReadonly(direction);
	}
	
	public Ray(float posX, float posY, float posZ, float dirX, float dirY, float dirZ)
	{
		this.position = new Vec3fReadonly(posX, posY, posZ);
		this.direction = new Vec3fReadonly(dirX, dirY, dirZ);
	}
	
	public IVec3fRead getPosition()
	{
		return this.position;
	}
	
	public IVec3fRead getDirection()
	{
		return this.direction;
	}
	
}
