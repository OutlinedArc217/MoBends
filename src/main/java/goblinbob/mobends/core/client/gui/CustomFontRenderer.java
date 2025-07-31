/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: CustomFontRenderer.java
 */

package goblinbob.mobends.core.client.gui;

import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.vertex.VertexConsumer;
// REMOVED DEPRECATED: import com.mojang.blaze3d.vertex.Tessellator;
// REMOVED DEPRECATED: // MC 1.20.1: Removed - import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
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

public class CustomFontRenderer
{

    protected CustomFont font;
    protected int characterSpacing = 1;

    public void setFont(CustomFont font)
    {
        this.font = font;
    }

    protected void drawSymbol(CustomFont.Symbol symbol, VertexConsumer vertexBuffer, int x, int y)
    {
        x += symbol.offsetX;
        y += symbol.offsetY;
        int width = symbol.width;
        int height = symbol.height;
        float textureX = (float) symbol.u / this.font.atlasWidth;
        float textureY = (float) symbol.v / this.font.atlasHeight;
        float textureWidth = (float) width / this.font.atlasWidth;
        float textureHeight = (float) height / this.font.atlasHeight;

        vertexBuffer.pos((double) x, (double) (y), 0)
                .tex(textureX, textureY + textureHeight).endVertex();
        vertexBuffer.pos((double) (x + width), (double) (y), 0)
                .tex(textureX + textureWidth, textureY + textureHeight).endVertex();
        vertexBuffer.pos((double) (x + width), (double) (y - height), 0)
                .tex(textureX + textureWidth, textureY).endVertex();
        vertexBuffer.pos((double) x, (double) (y - height), 0)
                .tex(textureX, textureY).endVertex();
    }

    public int getTextWidth(String textToDraw)
    {
        int width = 0;
        for (int i = 0; i < textToDraw.length(); ++i)
        {
            CustomFont.Symbol symbol = this.font.getSymbol(textToDraw.charAt(i));
            width += symbol.width;
            if (i != textToDraw.length() - 1)
                width += characterSpacing;
        }
        return width;
    }

    public void drawText(String textToDraw, int x, int y)
    {
        if (this.font == null)
            return;

        Minecraft.getMinecraft().getTextureManager().bindTexture(this.font.resourceLocation);
        Tessellator tessellator = Tessellator.getInstance();
        VertexConsumer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        int nextCharX = x;
        for (int i = 0; i < textToDraw.length(); ++i)
        {
            CustomFont.Symbol symbol = this.font.getSymbol(textToDraw.charAt(i));
            this.drawSymbol(symbol, vertexbuffer, nextCharX, y);
            nextCharX += symbol.width + characterSpacing;
        }
        tessellator.draw();
    }

    public void drawCenteredText(String textToDraw, int x, int y)
    {
        int width = this.getTextWidth(textToDraw);
        this.drawText(textToDraw, x - width / 2, y);
    }

}
