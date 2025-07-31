package goblinbob.mobends.core.client;

import goblinbob.mobends.core.util.IColorRead;
import net.minecraft.client.renderer.VertexConsumer;
// REMOVED DEPRECATED: import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;

import java.nio.ByteBuffer;
import java.util.List;
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

public class Mesh
{
	
	private VertexFormat vertexFormat;
	private VertexConsumer bufferBuilder;
	private VertexBuffer buffer;
	
	public Mesh(VertexFormat vertexFormat, int maxVertices)
	{
		this.vertexFormat = vertexFormat;
		this.bufferBuilder = new VertexConsumer(vertexFormat.getNextOffset() * maxVertices);
	}
	
	public void beginDrawing(int drawMode)
	{
		this.bufferBuilder.begin(drawMode, this.vertexFormat);
	}
	
	public void finishDrawing()
	{
		this.bufferBuilder.finishDrawing();
		this.buffer = new VertexBuffer(this.vertexFormat);
		this.buffer.bufferData(this.bufferBuilder.getByteBuffer());
	}
	
	public Mesh pos(double x, double y, double z)
	{
		this.bufferBuilder.pos(x, y, z);
		return this;
	}
	
	public Mesh normal(float x, float y, float z)
	{
		this.bufferBuilder.normal(x, y, z);
		return this;
	}
	
	public Mesh tex(double u, double v)
	{
		this.bufferBuilder.tex(u, v);
		return this;
	}
	
	public Mesh color(IColorRead color)
	{
		this.bufferBuilder.color(color.getR(), color.getG(), color.getB(), color.getA());
		return this;
	}
	
	public void endVertex()
	{
		this.bufferBuilder.endVertex();
	}
	
	public void display()
	{
		if (this.bufferBuilder.getVertexCount() > 0)
        {
            int i = this.vertexFormat.getNextOffset();
            ByteBuffer bytebuffer = this.bufferBuilder.getByteBuffer();
            List<VertexFormatElement> list = this.vertexFormat.getElements();

            for (int j = 0; j < list.size(); ++j)
            {
                VertexFormatElement vertexformatelement = list.get(j);
                VertexFormatElement.EnumUsage usage = vertexformatelement.getUsage();
                bytebuffer.position(this.vertexFormat.getOffset(j));

                // moved to VertexFormatElement.preDraw
                usage.preDraw(this.vertexFormat, j, i, bytebuffer);
            }

            RenderSystem.glDrawArrays(this.bufferBuilder.getDrawMode(), 0, this.bufferBuilder.getVertexCount());
            int i1 = 0;

            for (int j1 = list.size(); i1 < j1; ++i1)
            {
                VertexFormatElement vertexformatelement1 = list.get(i1);

                // moved to VertexFormatElement.postDraws
                vertexformatelement1.getUsage().postDraw(this.vertexFormat, i1, i, bytebuffer);
            }
        }
	}

	public VertexConsumer getBufferBuilder()
	{
		return this.bufferBuilder;
	}
	
}
