/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: RenderBendsTippedArrow.java
 */

package goblinbob.mobends.standard.client.renderer.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraft.world.entity.Entity;

public class RenderBendsTippedArrow extends RenderBendsArrow<EntityTippedArrow>
{
	public static final ResourceLocation RES_ARROW = ResourceLocation.parse("textures/entity/projectiles/arrow.png");
	public static final ResourceLocation RES_TIPPED_ARROW = ResourceLocation.parse(
			"textures/entity/projectiles/tipped_arrow.png");

	public RenderBendsTippedArrow(RenderManager manager)
	{
		super(manager);
	}

	protected ResourceLocation getEntityTexture(EntityTippedArrow entity)
	{
		return entity.getColor() > 0 ? RES_TIPPED_ARROW : RES_ARROW;
	}

	public static class Factory implements IRenderFactory
	{
		@Override
		public Render createRenderFor(RenderManager manager)
		{
			return new RenderBendsTippedArrow(manager);
		}
	}
}
