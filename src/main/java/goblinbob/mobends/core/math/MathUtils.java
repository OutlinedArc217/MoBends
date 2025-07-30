package goblinbob.mobends.core.math;

import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public final class MathUtils {
    private MathUtils() {}

    public static final float PI = (float) Math.PI;
    public static final float TWO_PI = 2.0f * PI;
    public static final float HALF_PI = PI / 2.0f;
    
    public static float lerp(float start, float end, float progress) {
        return start + (end - start) * progress;
    }

    public static float smoothStep(float edge0, float edge1, float x) {
        float t = Mth.clamp((x - edge0) / (edge1 - edge0), 0.0f, 1.0f);
        return t * t * (3.0f - 2.0f * t);
    }

    public static Quaternionf slerp(Quaternionf start, Quaternionf end, float alpha) {
        float dot = start.dot(end);
        
        if (dot < 0.0f) {
            end.mul(-1.0f);
            dot = -dot;
        }

        if (dot > 0.9995f) {
            return new Quaternionf()
                .set(start)
                .nlerp(end, alpha);
        }

        float theta = (float) Math.acos(dot);
        float sinTheta = (float) Math.sin(theta);
        float scale0 = (float) Math.sin((1.0f - alpha) * theta) / sinTheta;
        float scale1 = (float) Math.sin(alpha * theta) / sinTheta;

        return new Quaternionf()
            .set(start.x * scale0 + end.x * scale1,
                 start.y * scale0 + end.y * scale1,
                 start.z * scale0 + end.z * scale1,
                 start.w * scale0 + end.w * scale1);
    }

    public static void decomposeMatrix(Matrix4f matrix, Vector3f translation, 
                                     Vector3f rotation, Vector3f scale) {
        translation.set(matrix.m30(), matrix.m31(), matrix.m32());

        scale.set(
            new Vector3f(matrix.m00(), matrix.m01(), matrix.m02()).length(),
            new Vector3f(matrix.m10(), matrix.m11(), matrix.m12()).length(),
            new Vector3f(matrix.m20(), matrix.m21(), matrix.m22()).length()
        );

        Matrix4f rotMat = new Matrix4f(matrix);
        rotMat.scale(1.0f / scale.x, 1.0f / scale.y, 1.0f / scale.z);
        
        rotation.set(
            (float) Math.atan2(rotMat.m21(), rotMat.m22()),
            (float) Math.atan2(-rotMat.m20(), 
                Math.sqrt(rotMat.m21() * rotMat.m21() + rotMat.m22() * rotMat.m22())),
            (float) Math.atan2(rotMat.m10(), rotMat.m00())
        );
    }

    public static float wrapAngle(float angle) {
        angle %= TWO_PI;
        if (angle < -PI) {
            angle += TWO_PI;
        } else if (angle > PI) {
            angle -= TWO_PI;
        }
        return angle;
    }
                 }
