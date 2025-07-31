package goblinbob.mobends.standard.client.renderer.entity.mutated;

import goblinbob.mobends.core.data.EntityData;
import net.minecraft.client.entity.AbstractClientPlayer;
// REMOVED DEPRECATED: import net.minecraft.client.renderer.GlStateManager;
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
