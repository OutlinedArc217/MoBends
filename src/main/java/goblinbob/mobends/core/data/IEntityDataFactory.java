/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: IEntityDataFactory.java
 */

package goblinbob.mobends.core.data;

import net.minecraft.world.entity.Entity;

@FunctionalInterface
public interface IEntityDataFactory<E extends Entity>
{
	EntityData<E> createEntityData(E entity);
}
