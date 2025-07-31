/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: LayerCustomHeldItem.java
 */

package goblinbob.mobends.standard.client.renderer.entity.layers;

import goblinbob.mobends.core.data.EntityData;
import goblinbob.mobends.core.data.EntityDatabase;
import goblinbob.mobends.core.math.SmoothOrientation;
import goblinbob.mobends.core.util.GlHelper;
import goblinbob.mobends.standard.data.BipedEntityData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
// REMOVED DEPRECATED: import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
import net.minecraft.client.model.EntityModel;

@SideOnly(Side.CLIENT)
public class LayerCustomHeldItem implements RenderLayer<LivingEntity>
{

    protected final LivingEntityRenderer<?, EntityModel<?>> livingEntityRenderer;

    public LayerCustomHeldItem(LivingEntityRenderer<?, EntityModel<?>> livingEntityRendererIn)
    {
        this.livingEntityRenderer = livingEntityRendererIn;
    }

    public void doRenderLayer(LivingEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        boolean flag = entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT;
        ItemStack itemstack = flag ? entitylivingbaseIn.getHeldItemOffhand() : entitylivingbaseIn.getHeldItemMainhand();
        ItemStack itemstack1 = flag ? entitylivingbaseIn.getHeldItemMainhand() : entitylivingbaseIn.getHeldItemOffhand();

        if (!itemstack.isEmpty() || !itemstack1.isEmpty())
        {
            RenderSystem.pushMatrix();

            if (this.livingEntityRenderer.getMainModel().isChild)
            {
                RenderSystem.translate(0.0F, 0.75F, 0.0F);
                RenderSystem.scale(0.5F, 0.5F, 0.5F);
            }

            this.renderHeldItem(entitylivingbaseIn, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
            this.renderHeldItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
            RenderSystem.popMatrix();
        }
    }

    private void renderHeldItem(LivingEntity entity, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide handSide)
    {
        if (!p_188358_2_.isEmpty())
        {
            RenderSystem.pushMatrix();

            if (entity.isSneaking())
            {
                RenderSystem.translate(0.0F, 0.2F, 0.0F);
            }
            // Forge: moved this call down, fixes incorrect offset while sneaking.
            this.translateToHand(handSide, entity);
            RenderSystem.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            RenderSystem.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            boolean flag = handSide == EnumHandSide.LEFT;
            RenderSystem.translate((float)(flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);
            Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, p_188358_2_, p_188358_3_, flag);
            RenderSystem.popMatrix();
        }
    }

    /**
     * MO BENDS
     * This is the only function that had it's code changed
     */
    protected void translateToHand(EnumHandSide handSide, LivingEntity entity)
    {
    	((ModelBiped)this.livingEntityRenderer.getMainModel()).postRenderArm(0.0625F, handSide);
    	
    	EntityData<?> entityData = EntityDatabase.instance.get(entity);
    	if (entityData instanceof BipedEntityData)
    	{
    		BipedEntityData<?> bipedData = (BipedEntityData<?>) entityData;
    		SmoothOrientation itemRotation = handSide == EnumHandSide.RIGHT ? bipedData.renderRightItemRotation : bipedData.renderLeftItemRotation;
    		
    		RenderSystem.translate(0, 8F * 0.0625F, 0);
    		GlHelper.rotate(itemRotation.getSmooth());
            RenderSystem.translate(0, -8F * 0.0625F, 0);
    	}
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }

}