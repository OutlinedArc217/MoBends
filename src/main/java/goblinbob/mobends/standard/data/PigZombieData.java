/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: PigZombieData.java
 */

package goblinbob.mobends.standard.data;

import goblinbob.mobends.standard.animation.controller.PigZombieController;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.world.entity.Entity;

public class PigZombieData extends BipedEntityData<EntityPigZombie>
{
	
	private final PigZombieController controller = new PigZombieController();
	
	public PigZombieData(EntityPigZombie entity)
	{
		super(entity);
	}

	@Override
	public PigZombieController getController()
	{
		return controller;
	}

	@Override
	public void onTicksRestart()
	{
		// No behaviour
	}

}
