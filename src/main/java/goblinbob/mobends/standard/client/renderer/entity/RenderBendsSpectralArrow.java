/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: RenderBendsSpectralArrow.java
 */

package goblinbob.mobends.standard.client.renderer.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.world.entity.Entity;

@SideOnly(Side.CLIENT)
public class RenderBendsSpectralArrow extends RenderBendsArrow<EntitySpectralArrow>
{
	public static final ResourceLocation RES_SPECTRAL_ARROW = ResourceLocation.parse(
			"textures/entity/projectiles/spectral_arrow.png");

	public RenderBendsSpectralArrow(RenderManager manager)
	{
		super(manager);
	}

	protected ResourceLocation getEntityTexture(EntitySpectralArrow entity)
	{
		return RES_SPECTRAL_ARROW;
	}

	public static class Factory implements IRenderFactory
	{
		@Override
		public Render createRenderFor(RenderManager manager)
		{
			return new RenderBendsSpectralArrow(manager);
		}
	}
}
