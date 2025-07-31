package goblinbob.mobends.standard.client.model.armor;

import goblinbob.mobends.core.client.model.IModelPart;
import goblinbob.mobends.core.client.model.ModelPartTransform;
import goblinbob.mobends.standard.data.BipedEntityData;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
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

public interface IPartWrapper
{
    public void apply(ArmorWrapper armorWrapper);
    public void deapply(ArmorWrapper armorWrapper);
    public void syncUp(BipedEntityData<?> data);

    public IPartWrapper offsetInner(float x, float y, float z);
    public IPartWrapper setParent(IModelPart parent);

    @FunctionalInterface
    public interface DataPartSelector
    {
        ModelPartTransform selectPart(BipedEntityData<?> data);
    }

    @FunctionalInterface
    public interface ModelPartSetter
    {
        void replacePart(ModelBiped model, ModelRenderer part);
    }
}
