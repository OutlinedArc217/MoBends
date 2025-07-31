package goblinbob.mobends.core.util;

import goblinbob.mobends.core.math.Quaternion;
import goblinbob.mobends.core.math.QuaternionUtils;
import goblinbob.mobends.core.math.matrix.IMat4x4d;
import goblinbob.mobends.core.math.matrix.MatrixUtils;
import goblinbob.mobends.core.math.vector.IVec3dRead;
import goblinbob.mobends.core.math.vector.IVec3fRead;
// REMOVED DEPRECATED: import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.BufferUtils;
// REMOVED DEPRECATED: import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;
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

public class GlHelper
{
	
	private static final FloatBuffer BUF_FLOAT_16 = BufferUtils.createFloatBuffer(16);
	
	public static void vertex(IVec3fRead vector)
	{
		RenderSystem.glVertex3f(vector.getX(), vector.getY(), vector.getZ());
	}
	
	public static void vertex(IVec3dRead vector)
	{
		GL11.glVertex3d(vector.getX(), vector.getY(), vector.getZ());
	}
	
	public static void rotate(Quaternion quaternionIn)
    {
        RenderSystem.multMatrix(QuaternionUtils.quatToGlMatrix(BUF_FLOAT_16, quaternionIn));
    }
	
	public static void transform(IMat4x4d matrixIn)
	{
		RenderSystem.multMatrix(MatrixUtils.matToGlMatrix(matrixIn, BUF_FLOAT_16));
	}
	
}
