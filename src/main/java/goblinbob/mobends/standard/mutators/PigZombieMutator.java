/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: PigZombieMutator.java
 */

package goblinbob.mobends.standard.mutators;

import goblinbob.mobends.core.data.IEntityDataFactory;
import goblinbob.mobends.standard.data.PigZombieData;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.world.entity.LivingEntity;

public class PigZombieMutator extends BipedMutator<PigZombieData, EntityPigZombie, ModelZombie>
{

	// Should the height of the texture be 64 or 32(half)?
	protected boolean halfTexture = false;
	
	public PigZombieMutator(IEntityDataFactory<EntityPigZombie> dataFactory)
	{
		super(dataFactory);
	}
	
	@Override
	public void fetchFields(LivingEntityRenderer<? extends EntityPigZombie> renderer)
	{
		super.fetchFields(renderer);

		if (renderer.getMainModel() instanceof ModelZombie)
		{
			ModelZombie model = (ModelZombie) renderer.getMainModel();
			
			this.halfTexture = model.textureHeight == 32;
		}
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
