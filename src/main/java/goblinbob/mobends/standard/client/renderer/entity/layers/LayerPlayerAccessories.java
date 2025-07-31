/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: LayerPlayerAccessories.java
 */

package goblinbob.mobends.standard.client.renderer.entity.layers;

import goblinbob.mobends.core.asset.AssetLocation;
import goblinbob.mobends.core.asset.AssetModels;
import goblinbob.mobends.core.client.model.IModelPart;
import goblinbob.mobends.core.supporters.*;
import goblinbob.mobends.core.util.BenderHelper;
import goblinbob.mobends.core.util.Color;
import goblinbob.mobends.standard.data.PlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import com.mojang.blaze3d.vertex.VertexConsumer;
// REMOVED DEPRECATED: import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.RenderItem;
// REMOVED DEPRECATED: import com.mojang.blaze3d.vertex.Tessellator;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureManager;
// REMOVED DEPRECATED: import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;

import java.util.Map;
import java.util.Set;
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
import net.minecraft.world.phys.Vec3;

public class LayerPlayerAccessories implements RenderLayer<AbstractClientPlayer>
{
    private final LivingEntityRenderer<? extends AbstractClientPlayer> renderPlayer;
    private final RenderItem itemRenderer;
    private final ModelManager modelManager;
    private final TextureManager textureManager;
    private final ItemStack emptyItemStack = new ItemStack(Items.AIR);

    public LayerPlayerAccessories(LivingEntityRenderer<? extends AbstractClientPlayer> renderPlayer)
    {
        Minecraft mc = Minecraft.getMinecraft();
        this.renderPlayer = renderPlayer;
        this.itemRenderer = mc.getRenderItem();
        this.modelManager = mc.getRenderItem().getItemModelMesher().getModelManager();
        this.textureManager = mc.getTextureManager();
    }

    @Override
    public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        Set<Map.Entry<String, AccessoryDetails>> accessories = SupporterContent.getAccessories();

        final PlayerData data = BenderHelper.getData(player, renderPlayer);
        assert data != null;

        Map<String, AccessorySettings> settingsMap = SupporterContent.getAccessorySettingsMapFor(player);

        for (Map.Entry<String, AccessoryDetails> entry : accessories)
        {
            AccessorySettings accessorySettings = settingsMap.getOrDefault(entry.getKey(), AccessorySettings.DEFAULT);
            renderAccessory(player, data, entry.getValue(), accessorySettings, scale);
        }
    }

    private void renderAccessory(AbstractClientPlayer player, PlayerData data, AccessoryDetails accessory, AccessorySettings settings, float scale)
    {
        if (!settings.isUnlocked() || settings.isHidden())
        {
            return;
        }

        for (AccessoryPart part : accessory.getParts())
        {
            renderPart(player, data, part, settings, scale);
        }
    }

    private void renderPart(AbstractClientPlayer player, PlayerData data, AccessoryPart part, AccessorySettings settings, float scale)
    {
        SimpleBakedModel simpleBakedModel = AssetModels.INSTANCE.getModel(part.getModelPath());

        if (simpleBakedModel == null)
        {
            return;
        }

        RenderSystem.pushMatrix();

        // Reverting the sneak transform
        if (player.isSneaking())
        {
            if (player.capabilities.isFlying)
            {
                RenderSystem.translate(0F, 4F * scale, 0F);
            }
            else
            {
                RenderSystem.translate(0F, 3F * scale, 0F);
            }
        }

        applyBindPointTransform(data, part.getBindPoint(), scale);

        ItemTransformVec3f transformVec3f = simpleBakedModel.getItemCameraTransforms().head;
        RenderSystem.translate(transformVec3f.translation.x, transformVec3f.translation.y, transformVec3f.translation.z);
        RenderSystem.rotate(transformVec3f.rotation.x, 1, 0, 0);
        RenderSystem.rotate(transformVec3f.rotation.y, 0, 1, 0);
        RenderSystem.rotate(transformVec3f.rotation.z, 0, 0, 1);
        RenderSystem.scale(transformVec3f.scale.x, transformVec3f.scale.y, transformVec3f.scale.z);

        // Diffuse pass
        textureManager.bindTexture(part.getDiffuseTexturePath());
        drawModel(simpleBakedModel, 0xffffffff);

        // Inked pass
        AssetLocation inkedLocation = part.getInkedTexturePath();
        if (inkedLocation != null)
        {
            textureManager.bindTexture(inkedLocation);
            RenderSystem.color(1, 0, 0);
            drawModel(simpleBakedModel, Color.asHex(settings.getColor()));
            RenderSystem.color(1, 1, 0);
        }

        RenderSystem.popMatrix();
    }

    private void drawModel(IBakedModel model, int color)
    {
        Tessellator tessellator = Tessellator.getInstance();
        VertexConsumer bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.ITEM);

        for (Direction enumfacing : Direction.values())
        {
            itemRenderer.renderQuads(bufferbuilder, model.getQuads(null, enumfacing, 0L), color, emptyItemStack);
        }

        itemRenderer.renderQuads(bufferbuilder, model.getQuads(null, null, 0L), color, emptyItemStack);
        tessellator.draw();
    }

    private void applyBindPointTransform(PlayerData data, BindPoint bindPoint, float scale)
    {
        IModelPart modelPart = bindPoint.getPartSelector().apply(data);

        if (modelPart != null)
        {
            modelPart.applyCharacterTransform(scale);
        }
    }

    @Override
    public boolean shouldCombineTextures()
    {
        return false;
    }
}
