package goblinbob.mobends.core.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
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

public class ModelPartExtended extends ModelPart
{

    protected IModelPart extension;

    public ModelPartExtended(ModelBase model, boolean register, int texOffsetX, int texOffsetY)
    {
        super(model, register, texOffsetX, texOffsetY);
    }

    public ModelPartExtended(ModelBase model, boolean register)
    {
        super(model, register);
    }

    public ModelPartExtended(ModelBase model, int texOffsetX, int texOffsetY)
    {
        super(model, texOffsetX, texOffsetY);
    }

    public ModelPartExtended setExtension(IModelPart modelPart)
    {
        extension = modelPart;
        return this;
    }

    @Override
    public void renderPart(float scale)
    {
        if (!(this.isShowing())) return;
        if (!this.compiled)
            this.compileDisplayList(scale);

        RenderSystem.pushMatrix();

        this.applyCharacterTransform(scale);
        RenderSystem.callList(this.displayList);
        if (extension != null)
            extension.renderJustPart(scale);

        if (this.childModels != null)
        {
			for (ModelRenderer childModel : this.childModels)
			{
				childModel.render(scale);
			}
        }
        RenderSystem.popMatrix();
    }

    @Override
    public void renderJustPart(float scale)
    {
        if (!(this.isShowing())) return;
        if (!this.compiled)
            this.compileDisplayList(scale);

        RenderSystem.pushMatrix();

        this.applyLocalTransform(scale);
        RenderSystem.callList(this.displayList);
        if (extension != null)
            extension.renderJustPart(scale);

        if (this.childModels != null)
        {
			for (ModelRenderer childModel : this.childModels)
			{
				childModel.render(scale);
			}
        }
        RenderSystem.popMatrix();
    }

    @Override
    public void applyCharacterTransform(float scale)
    {
        super.applyCharacterTransform(scale);
    }

    @Override
    public void applyPostTransform(float scale)
    {
        if (extension != null)
            extension.propagateTransform(scale);
    }

    @Override
    public void propagateTransform(float scale)
    {
        super.propagateTransform(scale);
        this.applyPostTransform(scale);
    }

}
