/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: BipedRenderer.java
 */

package goblinbob.mobends.standard.client.renderer.entity.mutated;

import goblinbob.mobends.core.client.MutatedRenderer;
import goblinbob.mobends.core.data.EntityData;
import goblinbob.mobends.standard.data.BipedEntityData;
import goblinbob.mobends.standard.main.ModConfig;
// REMOVED DEPRECATED: import com.mojang.blaze3d.systems.RenderSystem;
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

public class BipedRenderer<T extends LivingEntity> extends MutatedRenderer<T>
{

    @Override
    public void renderLocalAccessories(T entity, EntityData<?> data, float partialTicks)
    {
        float scale = 0.0625F;

        if (data instanceof BipedEntityData)
        {
            BipedEntityData<?> bipedData = (BipedEntityData<?>) data;
            if (ModConfig.showSwordTrail)
            {
                RenderSystem.pushMatrix();
                RenderSystem.scale(scale, scale, scale);
                bipedData.swordTrail.render();
                RenderSystem.color(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.popMatrix();
            }
        }
    }

    @Override
    protected void transformLocally(T entity, EntityData<?> data, float partialTicks)
    {
        if (entity.isSneaking())
        {
            RenderSystem.translate(0F, 5F * scale, 0F);
        }
    }

}
