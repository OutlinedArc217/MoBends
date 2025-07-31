/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: IPartWrapper.java
 */

package goblinbob.mobends.standard.client.model.armor;

import goblinbob.mobends.core.client.model.IModelPart;
import goblinbob.mobends.core.client.model.ModelPartTransform;
import goblinbob.mobends.standard.data.BipedEntityData;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.geom.ModelPart;
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
        void replacePart(ModelBiped model, ModelPart part);
    }
}
