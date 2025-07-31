/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: LayerCustomElytra.java
 */

package goblinbob.mobends.standard.client.renderer.entity.layers;

import goblinbob.mobends.core.util.BenderHelper;
import goblinbob.mobends.standard.data.PlayerData;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelElytra;
// REMOVED DEPRECATED: import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.entity.Entity;

@SideOnly(Side.CLIENT)
public class LayerCustomElytra implements RenderLayer<AbstractClientPlayer>
{
    /** The basic Elytra texture. */
    private static final ResourceLocation TEXTURE_ELYTRA = ResourceLocation.parse("textures/entity/elytra.png");
    /** Instance of the player renderer. */
    protected final RenderPlayer renderPlayer;
    /** The model used by the Elytra. */
    private final ModelElytra modelElytra = new ModelElytra();

    public LayerCustomElytra(RenderPlayer p_i47185_1_)
    {
        this.renderPlayer = p_i47185_1_;
    }

    public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        final PlayerData data = BenderHelper.getData(player, renderPlayer);
        assert data != null;

        ItemStack itemstack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

        if (itemstack.getItem() == Items.ELYTRA)
        {
            RenderSystem.color(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(RenderSystem.SourceFactor.ONE, RenderSystem.DestFactor.ZERO);

            if (player.isPlayerInfoSet() && player.getLocationElytra() != null)
            {
                this.renderPlayer.bindTexture(player.getLocationElytra());
            }
            else if (player.hasPlayerInfo() && player.getLocationCape() != null && player.isWearing(EnumPlayerModelParts.CAPE))
            {
                this.renderPlayer.bindTexture(player.getLocationCape());
            }
            else
            {
                this.renderPlayer.bindTexture(TEXTURE_ELYTRA);
            }

            RenderSystem.pushMatrix();
            //RenderSystem.translate(0.0F, 0.0F, 0.225F);
            data.body.applyCharacterTransform(0.0625F);
            //RenderSystem.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            //RenderSystem.translate(0.0F, 0.0F, 0.125F);
            RenderSystem.translate(0.0F, -12.0F * scale, 0.0F);
            this.modelElytra.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, player);
            this.modelElytra.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

            if (itemstack.isItemEnchanted())
            {
                LayerArmorBase.renderEnchantedGlint(this.renderPlayer, player, this.modelElytra, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            }

            RenderSystem.disableBlend();
            RenderSystem.popMatrix();
        }
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}