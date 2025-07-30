package goblinbob.mobends.core.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import goblinbob.mobends.core.math.matrix.IMat4x4d;
import goblinbob.mobends.core.math.vector.Vec3d;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelPartRenderer {
    private final ModelPart modelPart;
    private final Vec3d position;
    private final Vec3d rotation;
    private final Vec3d scale;

    public ModelPartRenderer(ModelPart modelPart) {
        this.modelPart = modelPart;
        this.position = new Vec3d();
        this.rotation = new Vec3d();
        this.scale = new Vec3d(1.0, 1.0, 1.0);
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

    public Vec3d getPosition() {
        return position;
    }

    public Vec3d getRotation() {
        return rotation;
    }

    public Vec3d getScale() {
        return scale;
    }
          }
