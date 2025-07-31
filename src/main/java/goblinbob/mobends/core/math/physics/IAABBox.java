/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: IAABBox.java
 */

package goblinbob.mobends.core.math.physics;

import goblinbob.mobends.core.math.vector.IVec3fRead;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;

public interface IAABBox
{
	
	IVec3fRead getMin();
	IVec3fRead getMax();
	
}
