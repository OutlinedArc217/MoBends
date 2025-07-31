/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: ZombieMutatorBase.java
 */

package goblinbob.mobends.standard.mutators;

import goblinbob.mobends.core.data.IEntityDataFactory;
import goblinbob.mobends.standard.data.ZombieDataBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.entity.LivingEntity;

/**
 * This base is used both by ZombieMutator and ZombieVillagerMutator, but since
 * the ModelZombieVillager doesn't extend ModelZombie, a seperate base class had
 * to be made.
 * 
 * @author Iwo Plaza
 *
 * @param <D>
 * @param <E>
 * @param <M>
 */
public abstract class ZombieMutatorBase<D extends ZombieDataBase<E>,
										E extends EntityZombie,
										M extends ModelBiped>
									   extends BipedMutator<D, E, M>
{

	// Should the height of the texture be 64 or 32(half)?
	protected boolean halfTexture = false;
	
	public ZombieMutatorBase(IEntityDataFactory<E> dataCreationFunction)
	{
		super(dataCreationFunction);
	}
	
	@Override
	public void fetchFields(LivingEntityRenderer<? extends E> renderer)
	{
		super.fetchFields(renderer);

		if (renderer.getMainModel() instanceof ModelZombie)
		{
			ModelZombie model = (ModelZombie) renderer.getMainModel();
			
			this.halfTexture = model.textureHeight == 32;
		}
	}
}
