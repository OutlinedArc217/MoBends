/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: LayerWolfMisc.java
 */

package goblinbob.mobends.standard.client.renderer.entity.layers;

import goblinbob.mobends.core.client.Mesh;
import goblinbob.mobends.core.data.EntityData;
import goblinbob.mobends.core.data.EntityDatabase;
import goblinbob.mobends.core.util.Color;
import goblinbob.mobends.core.util.MeshBuilder;
import goblinbob.mobends.standard.data.WolfData;
import goblinbob.mobends.standard.main.ModStatics;
import net.minecraft.client.Minecraft;
// REMOVED DEPRECATED: import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureManager;
// REMOVED DEPRECATED: import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.resources.ResourceLocation;
// REMOVED DEPRECATED: import org.lwjgl.opengl.GL11;
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

public class LayerWolfMisc implements RenderLayer<EntityWolf>
{

    private static final ResourceLocation WOLF_MISC_TEXTURE = ResourceLocation.fromNamespaceAndPath(ModStatics.MODID, "textures/entity/wolf_misc.png");
    private static final int textureWidth = 8;
    private static final int textureHeight = 8;

    protected final TextureManager textureManager;
    private Mesh mouthBottom;
    private Mesh mouthTop;
    private Mesh mouthInside;
    private Mesh tongue;

    public LayerWolfMisc()
    {
        textureManager = Minecraft.getMinecraft().getTextureManager();

        mouthBottom = new Mesh(DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL, 6);
        mouthBottom.beginDrawing(GL11.GL_TRIANGLES);
        MeshBuilder.texturedXZPlane(mouthBottom, -1.5F, 0F, -4F, 3, 4, true, Color.WHITE,
                new int[] { 0, 0, 3, 4 }, textureWidth, textureHeight);
        mouthBottom.finishDrawing();

        mouthTop = new Mesh(DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL, 6);
        mouthTop.beginDrawing(GL11.GL_TRIANGLES);
        MeshBuilder.texturedXZPlane(mouthTop, -1.5F, 1F, -4F, 3, 4, false, Color.WHITE,
                new int[] { 0, 0, 3, 4 }, textureWidth, textureHeight);
        mouthTop.finishDrawing();

        mouthInside = new Mesh(DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL, 6);
        mouthInside.beginDrawing(GL11.GL_TRIANGLES);
        MeshBuilder.texturedXZPlane(mouthInside, -1.5F, -4.1F, -3.0F, 3, 1, true, Color.WHITE,
                new int[] { 0, 0, 3, 4 }, textureWidth, textureHeight);
        mouthInside.finishDrawing();

        tongue = new Mesh(DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL, 6);
        tongue.beginDrawing(GL11.GL_TRIANGLES);
        MeshBuilder.texturedXZPlane(tongue, -1.5F, 0F, -4F, 3, 6, true, Color.WHITE,
                new int[] { 3, 0, 6, 6 }, textureWidth, textureHeight);
        tongue.finishDrawing();
    }

    public void doRenderLayer(EntityWolf wolf, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        final EntityData<?> entityData = EntityDatabase.instance.get(wolf);
        if (entityData instanceof WolfData)
        {
            final WolfData data = (WolfData) entityData;

            textureManager.bindTexture(WOLF_MISC_TEXTURE);

            boolean isChild = wolf.isChild();

            RenderSystem.pushMatrix();
            if (isChild)
            {
                RenderSystem.translate(0.0F, 10.0F * scale, 0.0F * scale);
                data.body.applyLocalTransform(scale * 0.5F);
            }
            else
            {
                data.body.applyLocalTransform(scale);
            }
            data.head.applyLocalTransform(scale);

            RenderSystem.enableCull();
            // Mouth inside
            RenderSystem.pushMatrix();
            RenderSystem.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            RenderSystem.scale(scale, scale, scale);
            mouthInside.display();
            RenderSystem.popMatrix();

            // Mouth bottom
            RenderSystem.pushMatrix();
            data.nose.applyLocalTransform(scale);
            RenderSystem.scale(scale, scale, scale);
            mouthTop.display();
            RenderSystem.popMatrix();

            // Mouth top
            RenderSystem.pushMatrix();
            data.mouth.applyLocalTransform(scale);
            RenderSystem.scale(scale, scale, scale);
            mouthBottom.display();
            RenderSystem.popMatrix();

            // Tongue
            RenderSystem.pushMatrix();
            data.tongue.applyLocalTransform(scale);
            RenderSystem.scale(scale, scale, scale);
            tongue.display();
            RenderSystem.popMatrix();

            RenderSystem.popMatrix();
        }
    }

    @Override
    public boolean shouldCombineTextures()
    {
        return false;
    }

}
