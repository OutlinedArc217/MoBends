/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: RenderBendsArrow.java
 */

package goblinbob.mobends.standard.client.renderer.entity;

import goblinbob.mobends.standard.main.ModConfig;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.world.entity.Entity;

@SideOnly(Side.CLIENT)
public abstract class RenderBendsArrow<T extends EntityArrow> extends RenderArrow<T>
{
	
    public RenderBendsArrow(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}
    
    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
    	if(ModConfig.showArrowTrails)
    		ArrowTrailManager.renderTrail(entity, x, y, z, partialTicks);

    	super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
}