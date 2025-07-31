/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: LayerCustomCape.java
 */

package goblinbob.mobends.standard.client.renderer.entity.layers;

import goblinbob.mobends.core.util.BenderHelper;
import goblinbob.mobends.standard.client.renderer.entity.BendsCapeRenderer;
import goblinbob.mobends.standard.data.PlayerData;
import net.minecraft.client.entity.AbstractClientPlayer;
// REMOVED DEPRECATED: import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.world.item.ItemStack;
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
public class LayerCustomCape implements RenderLayer<AbstractClientPlayer>
{

    private final RenderPlayer playerRenderer;
    private final BendsCapeRenderer capeRenderer;

    public LayerCustomCape(RenderPlayer playerRendererIn)
    {
        this.playerRenderer = playerRendererIn;
        this.capeRenderer = new BendsCapeRenderer();
    }

    public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        final PlayerData data = BenderHelper.getData(player, playerRenderer);
        assert data != null;

        if (player.hasPlayerInfo() && !player.isInvisible() && player.isWearing(EnumPlayerModelParts.CAPE) && player.getLocationCape() != null)
        {
            final ItemStack itemstack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

            if (itemstack.getItem() != Items.ELYTRA)
            {
                RenderSystem.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.playerRenderer.bindTexture(player.getLocationCape());
                RenderSystem.pushMatrix();

                if (player.isSneaking())
                {
                    if (player.capabilities.isFlying)
                    {
                        RenderSystem.translate(0F, 4F * scale, 0F);
                    }
                    else
                    {
                        RenderSystem.translate(0F, 4F * scale, 0F);
                    }
                }

                data.body.applyLocalTransform(0.0625F);
                RenderSystem.translate(0.0F, -12.0F * scale, 2.2F * scale);
                data.cape.applyLocalTransform(0.0625F);
                RenderSystem.rotate(180.0F, 0.0F, 1.0F, 0.0F);

                capeRenderer.applyAnimation(data);
                capeRenderer.render(0.0625F);

                RenderSystem.popMatrix();
            }
        }
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}
