/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: ClientProxy.java
 */

package goblinbob.mobends.standard.client;

import goblinbob.mobends.core.Core;
import goblinbob.mobends.standard.client.event.RenderingEventHandler;
import goblinbob.mobends.standard.client.renderer.entity.RenderBendsSpectralArrow;
import goblinbob.mobends.standard.client.renderer.entity.RenderBendsTippedArrow;
import goblinbob.mobends.standard.main.CommonProxy;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraft.world.entity.Entity;

public class ClientProxy extends CommonProxy
{
	
	@Override
	public void preInit()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntitySpectralArrow.class, RenderBendsSpectralArrow::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTippedArrow.class, RenderBendsTippedArrow::new);
	}

	@Override
	public void init()
	{
		MinecraftForge.EVENT_BUS.register(new RenderingEventHandler());
	}
	
	@Override
	public void postInit() {}
	
	@Override
	public void createCore()
	{
		Core.createAsClient();
	}
	
}
