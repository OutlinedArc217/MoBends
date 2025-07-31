/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: LayerCustomBipedArmor.java
 */

package goblinbob.mobends.standard.client.renderer.entity.layers;

import goblinbob.mobends.core.data.EntityData;
import goblinbob.mobends.core.data.EntityDatabase;
import goblinbob.mobends.standard.client.model.armor.ArmorModelFactory;
import goblinbob.mobends.standard.client.model.armor.MalformedArmorModelException;
import goblinbob.mobends.standard.data.BipedEntityData;
import goblinbob.mobends.standard.main.ModConfig;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.inventory.EntityEquipmentSlot;
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
import net.minecraft.world.entity.LivingEntity;

@SideOnly(Side.CLIENT)
public class LayerCustomBipedArmor extends LayerArmorBase<ModelBiped>
{
	
    public LayerCustomBipedArmor(LivingEntityRenderer<?> rendererIn)
    {
        super(rendererIn);
    }
    
    @Override
    public void initArmor()
    {
    	this.modelLeggings = new ModelBiped(0.5F);
        this.modelArmor = new ModelBiped(1.0F);
    }

    @SuppressWarnings("incomplete-switch")
    @Override
    protected void setModelSlotVisible(ModelBiped model, EntityEquipmentSlot slotIn)
    {
        this.hideModelParts(model);

        switch (slotIn)
        {
            case HEAD:
                model.bipedHead.showModel = true;
                model.bipedHeadwear.showModel = true;
                break;
            case CHEST:
                model.bipedBody.showModel = true;
                model.bipedRightArm.showModel = true;
                model.bipedLeftArm.showModel = true;
                break;
            case LEGS:
                model.bipedBody.showModel = true;
                model.bipedRightLeg.showModel = true;
                model.bipedLeftLeg.showModel = true;
                break;
            case FEET:
                model.bipedRightLeg.showModel = true;
                model.bipedLeftLeg.showModel = true;
                break;
            default:
                break;
        }
    }

    protected void hideModelParts(ModelBiped model)
    {
        model.setVisible(false);
    }

    @Override
    protected ModelBiped getArmorModelHook(net.minecraft.entity.LivingEntity entity, net.minecraft.item.ItemStack itemStack, EntityEquipmentSlot slot, ModelBiped model)
    {
    	EntityData<?> entityData = EntityDatabase.instance.get(entity);
    	
    	final ModelBiped suggestedModel = net.minecraftforge.client.ForgeHooksClient.getArmorModel(entity, itemStack, slot, model);

        boolean shouldBeMutated = !ModConfig.shouldKeepArmorAsVanilla(itemStack.getItem()) && (entityData != null && entityData instanceof BipedEntityData);

        try
        {
            return ArmorModelFactory.getArmorModel(suggestedModel, shouldBeMutated);
        }
        catch(MalformedArmorModelException ex)
        {
            ex.printStackTrace();
            return suggestedModel;
        }
    }
}