/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: ZombieData.java
 */

package goblinbob.mobends.standard.data;

import goblinbob.mobends.standard.animation.controller.ZombieController;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.entity.Entity;

public class ZombieData extends ZombieDataBase<EntityZombie>
{
	
	private final ZombieController controller = new ZombieController();
	
	public ZombieData(EntityZombie entity)
	{
		super(entity);
	}
	
	@Override
	public ZombieController getController()
	{
		return this.controller;
	}

	@Override
	public void onTicksRestart()
	{
		// No behaviour
	}

}