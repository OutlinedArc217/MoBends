/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: PlayerRenderer.java
 */

package goblinbob.mobends.standard.client.renderer.entity.mutated;

import goblinbob.mobends.core.data.EntityData;
import net.minecraft.client.entity.AbstractClientPlayer;
// REMOVED DEPRECATED: import com.mojang.blaze3d.systems.RenderSystem;
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
import net.minecraft.world.entity.Entity;

public class PlayerRenderer extends BipedRenderer<AbstractClientPlayer>
{

    @Override
    protected void transformLocally(AbstractClientPlayer entity, EntityData<?> data, float partialTicks)
    {
        if (entity.isSneaking())
        {
            if (entity.capabilities.isFlying)
            {
                RenderSystem.translate(0F, 4F * scale, 0F);
            }
            else
            {
                RenderSystem.translate(0F, 5F * scale, 0F);
            }
        }
    }

}
