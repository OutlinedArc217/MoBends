/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: ZombieMutator.java
 */

package goblinbob.mobends.standard.mutators;

import goblinbob.mobends.core.data.IEntityDataFactory;
import goblinbob.mobends.standard.data.ZombieData;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.entity.Entity;

public class ZombieMutator extends ZombieMutatorBase<ZombieData, EntityZombie, ModelZombie>
{

	public ZombieMutator(IEntityDataFactory<EntityZombie> dataFactory)
	{
		super(dataFactory);
	}
	
	@Override
	public void storeVanillaModel(ModelZombie model)
	{
		ModelZombie vanillaModel = new ModelZombie(0.0F, this.halfTexture);
		this.vanillaModel = vanillaModel;
		
		// Calling the super method here, since it
		// requires the vanillaModel property to be
		// set.
		super.storeVanillaModel(model);
	}
	
	@Override
	public boolean shouldModelBeSkipped(Model model)
	{
		return !(model instanceof ModelZombie);
	}
}
