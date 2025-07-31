/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: MutatedRenderer.java
 */

package goblinbob.mobends.core.client;

import goblinbob.mobends.core.data.EntityData;
import goblinbob.mobends.core.util.GlHelper;
import net.minecraft.client.Minecraft;
// REMOVED DEPRECATED: import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.joml.Matrix4f;
import org.joml.Matrix3f;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;

public abstract class MutatedRenderer<T extends LivingEntity>
{

    protected final float scale = 0.0625F;
    protected final TextureManager textureManager;

    public MutatedRenderer()
    {
        textureManager = Minecraft.getMinecraft().getTextureManager();
    }

    /**
     * Called right before the entity is rendered
     */
    public void beforeRender(EntityData<T> data, T entity, float partialTicks)
    {
        double entityX = entity.prevPosX + (entity.getX() - entity.prevPosX) * partialTicks;
        double entityY = entity.prevPosY + (entity.getY() - entity.prevPosY) * partialTicks;
        double entityZ = entity.prevPosZ + (entity.getZ() - entity.prevPosZ) * partialTicks;

        Entity viewEntity = Minecraft.getMinecraft().getRenderViewEntity();
        double viewX = entityX, viewY = entityY, viewZ = entityZ;
        if (viewEntity != null)
        {
            // Checking in case of Main Menu or GUI rendering.
            viewX = viewEntity.prevPosX + (viewEntity.getX() - viewEntity.prevPosX) * partialTicks;
            viewY = viewEntity.prevPosY + (viewEntity.getY() - viewEntity.prevPosY) * partialTicks;
            viewZ = viewEntity.prevPosZ + (viewEntity.getZ() - viewEntity.prevPosZ) * partialTicks;
        }
        RenderSystem.translate(entityX - viewX, entityY - viewY, entityZ - viewZ);
        RenderSystem.rotate(-interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks), 0F, 1F, 0F);

        this.renderLocalAccessories(entity, data, partialTicks);

        float globalScale = entity.isChild() ? getChildScale() : 1;

        RenderSystem.translate(data.globalOffset.getX() * scale * globalScale,
                data.globalOffset.getY() * scale * globalScale,
                data.globalOffset.getZ() * scale * globalScale);
        RenderSystem.translate(0, entity.height / 2, 0);
        GlHelper.rotate(data.centerRotation.getSmooth());
        RenderSystem.translate(0, -entity.height / 2, 0);
        GlHelper.rotate(data.renderRotation.getSmooth());

        RenderSystem.translate(data.localOffset.getX() * scale * globalScale,
                data.localOffset.getY() * scale * globalScale,
                data.localOffset.getZ() * scale * globalScale);

        this.transformLocally(entity, data, partialTicks);

        RenderSystem.rotate(interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks), 0F, 1F, 0F);
        RenderSystem.translate(viewX - entityX, viewY - entityY, viewZ - entityZ);
    }

    /**
     * Called right after the entity is rendered.
     */
    public void afterRender(T entity, float partialTicks)
    {
        // No default behaviour
    }

    /**
     * Used to render accessories for that entity, e.g. Sword trails. Also used to transform the entity, like offset or
     * rotate it.
     */
    protected void renderLocalAccessories(T entity, EntityData<?> data, float partialTicks)
    {
        // No default behaviour
    }

    protected void transformLocally(T entity, EntityData<?> data, float partialTicks)
    {
        // No default behaviour
    }

    protected static float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks)
    {
        float f;
        for (f = yawOffset - prevYawOffset; f < -180.0F; f += 360.0F) ;

        while (f >= 180.0F)
            f -= 360.0F;

        return prevYawOffset + partialTicks * f;
    }

    protected float getChildScale()
    {
        return 0.5F;
    }

}
