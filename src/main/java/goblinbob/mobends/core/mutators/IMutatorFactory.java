/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: IMutatorFactory.java
 */

package goblinbob.mobends.core.mutators;


import goblinbob.mobends.core.data.IEntityDataFactory;
import goblinbob.mobends.core.data.LivingEntityData;
import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface
public interface IMutatorFactory<E extends LivingEntity>
{
	
	Mutator<? extends LivingEntityData<E>, ? extends E, ?> createMutator(IEntityDataFactory<E> dataFactory);
	
}
