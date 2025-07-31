/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: ModelPartRenderer.java
 */

package goblinbob.mobends.core.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import goblinbob.mobends.core.math.matrix.IMat4x4d;
import goblinbob.mobends.core.math.vector.Vec3;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.MultiBufferSource;
import org.joml.Matrix4f;
import org.joml.Matrix3f;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.world.phys.Vec3;

@OnlyIn(Dist.CLIENT)
public class ModelPartRenderer {
    private final ModelPart modelPart;
    private final Vec3 position;
    private final Vec3 rotation;
    private final Vec3 scale;

    public ModelPartRenderer(ModelPart modelPart) {
        this.modelPart = modelPart;
        this.position = new Vec3();
        this.rotation = new Vec3();
        this.scale = new Vec3(1.0, 1.0, 1.0);
    }

    public void render(PoseStack matrixStack, 
                      VertexConsumer buffer,
                      int packedLight,
                      int packedOverlay,
                      float red, float green, float blue, float alpha) {
        matrixStack.pushPose();
        
        matrixStack.translate(position.x, position.y, position.z);
        matrixStack.scale((float)scale.x, (float)scale.y, (float)scale.z);
        matrixStack.mulPose(com.mojang.math.Quaternion.fromXYZ(
            (float)rotation.x,
            (float)rotation.y,
            (float)rotation.z
        ));
      
        modelPart.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        
        matrixStack.popPose();
    }

    public void setTransform(IMat4x4d transform) {
        transform.decompose(position, rotation, scale);
    }

    public ModelPart getModelPart() {
        return modelPart;
    }

    public Vec3 getPosition() {
        return position;
    }

    public Vec3 getRotation() {
        return rotation;
    }

    public Vec3 getScale() {
        return scale;
    }
          }
