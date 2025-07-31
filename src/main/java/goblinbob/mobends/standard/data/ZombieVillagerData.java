/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: ZombieVillagerData.java
 */

package goblinbob.mobends.standard.data;

import goblinbob.mobends.standard.animation.controller.ZombieVillagerController;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.world.entity.Entity;

public class ZombieVillagerData extends ZombieDataBase<EntityZombieVillager>
{

	private final ZombieVillagerController controller = new ZombieVillagerController();
	
	public ZombieVillagerData(EntityZombieVillager entity)
	{
		super(entity);
	}
	
	@Override
	public ZombieVillagerController getController()
	{
		return controller;
	}

	@Override
	public void onTicksRestart()
	{
		// No behaviour
	}

}
