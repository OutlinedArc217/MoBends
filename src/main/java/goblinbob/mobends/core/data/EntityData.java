/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: EntityData.java
 */

package goblinbob.mobends.core.data;

// TODO: Create Object /* TODO: Implement IAnimationController */ interface - package missing
import goblinbob.mobends.core.client.event.DataUpdateHandler;
import goblinbob.mobends.core.client.model.IBendsModel;
import goblinbob.mobends.core.math.SmoothOrientation;
import goblinbob.mobends.core.math.vector.SmoothVector3f;
import goblinbob.mobends.core.pack.state.PackAnimationState;
import goblinbob.mobends.core.util.GUtil;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
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

public abstract class EntityData<E extends Entity> implements IBendsModel
{

    protected int entityID;
    protected final E entity;

    protected double positionX, positionY, positionZ;
    protected double prevMotionX, prevMotionY, prevMotionZ;
    protected double motionX, motionY, motionZ;
    protected final HashMap<String, Object> nameToPartMap = new HashMap<>();

    public SmoothVector3f globalOffset;
    public SmoothVector3f localOffset;
    public SmoothOrientation renderRotation;
    public SmoothOrientation centerRotation;

    public boolean onGround = true;
    public Boolean onGroundOverride = null;
    public Boolean stillnessOverride = null;

    public final PackAnimationState packAnimationState;

    public EntityData(E entity)
    {
        this.entity = entity;
        if (this.entity != null)
        {
            this.entityID = entity.getEntityId();
            this.positionX = this.entity.getX();
            this.positionY = this.entity.getY();
            this.positionZ = this.entity.getZ();
        }

        this.motionX = this.prevMotionX = 0.0D;
        this.motionY = this.prevMotionY = 1.0D;
        this.motionZ = this.prevMotionZ = 0.0D;

        this.packAnimationState = new PackAnimationState();

        this.initModelPose();
    }

    public void overrideOnGroundState(boolean state)
    {
        this.onGroundOverride = state;
    }

    public void unsetOnGroundStateOverride()
    {
        this.onGroundOverride = null;
    }

    public void overrideStillness(boolean stillness)
    {
        this.stillnessOverride = stillness;
    }

    public void unsetStillnessOverride()
    {
        this.stillnessOverride = null;
    }

    public void initModelPose()
    {
        this.globalOffset = new SmoothVector3f();
        this.localOffset = new SmoothVector3f();
        this.renderRotation = new SmoothOrientation();
        this.centerRotation = new SmoothOrientation();

        this.nameToPartMap.put("renderRotation", renderRotation);
        this.nameToPartMap.put("centerRotation", centerRotation);
    }

    /**
     * Updates all the model's parts to be in their next frame. Called in {@code EntityData.update()}
     */
    public void updateParts(float ticksPerFrame)
    {
        this.globalOffset.update(ticksPerFrame);
        this.localOffset.update(ticksPerFrame);
        this.renderRotation.update(ticksPerFrame);
        this.centerRotation.update(ticksPerFrame);
    }

    public boolean calcOnGround()
    {
        if (this.onGroundOverride != null)
            return this.onGroundOverride;

        // Checking if we're going down stairs.
        BlockPos position = new BlockPos(Math.floor(entity.getX()), Math.floor(entity.getY()), Math.floor(entity.getZ()));

        BlockState block = entity.world.getBlockState(position);
        BlockState blockBelow = entity.world.getBlockState(position.add(0, -1, 0));
    
        if (motionY <= 0 && (block.getBlock() instanceof BlockStairs || blockBelow.getBlock() instanceof BlockStairs))
            return true;

        // Checking collisions.
        List<AABB> list = entity.world.getCollisionBoxes(entity, entity.getEntityBoundingBox().offset(0, -0.125F, 0));
        return list.size() > 0;
    }

    public boolean calcCollidedHorizontally()
    {
        List<AABB> list = entity.world.getCollisionBoxes(entity,
                entity.getEntityBoundingBox().offset(this.motionX, 0, this.motionZ));

        return list.size() > 0;
    }

    public double getPositionX() { return this.positionX; }

    public double getPositionY() { return this.positionY; }

    public double getPositionZ() { return this.positionZ; }

    public double getMotionX() { return this.motionX; }

    public double getMotionY() { return this.motionY; }

    public double getMotionZ() { return this.motionZ; }

    public double getPrevMotionX() { return this.prevMotionX; }

    public double getPrevMotionY() { return this.prevMotionY; }

    public double getPrevMotionZ() { return this.prevMotionZ; }

    public double getInterpolatedMotionX() { return this.prevMotionX + (this.motionX - this.prevMotionX) * DataUpdateHandler.partialTicks; }

    public double getInterpolatedMotionY() { return this.prevMotionY + (this.motionY - this.prevMotionY) * DataUpdateHandler.partialTicks; }

    public double getInterpolatedMotionZ() { return this.prevMotionZ + (this.motionZ - this.prevMotionZ) * DataUpdateHandler.partialTicks; }

    public boolean isOnGround()
    {
        return this.onGround;
    }

    public boolean isStillHorizontally()
    {
        // The motion value that is the threshold for determining movement.
        final double deadZone = 0.0025;
        final double horizontalSqMagnitude = this.motionX * this.motionX + this.motionZ * this.motionZ;
        return this.stillnessOverride != null ? this.stillnessOverride : horizontalSqMagnitude < deadZone;
    }

    public abstract Object /* TODO: Implement IAnimationController interface */ getController();

    /**
     * Called during the render tick in {@code EntityDatabase.updateRender()}
     */
    public void update(float partialTicks)
    {
        if (this.entity == null)
            return;

        this.updateParts(DataUpdateHandler.ticksPerFrame);
    }

    public E getEntity()
    {
        return this.entity;
    }

    public float getLookAngle()
    {
        final Vec3 lookVec = this.entity.getLookVec();
        return (float) GUtil.angleFromCoordinates(lookVec.x, lookVec.z);
    }

    private float getWorldMovementAngle()
    {
        return (float) GUtil.angleFromCoordinates(this.motionX, this.motionZ);
    }

    public float getMovementAngle()
    {
        if (isStillHorizontally())
            return 0;
        return this.getWorldMovementAngle() - this.getLookAngle();
    }

    public double getForwardMomentum()
    {
        if (isStillHorizontally())
            return 0;

        final Vec3 lookVec = this.entity.getLookVec();
        final Vec3 lookVecHorizontal = new Vec3(lookVec.x, 0, lookVec.z).normalize();
        return lookVecHorizontal.x * this.motionX + lookVecHorizontal.z * this.motionZ;
    }

    public double getSidewaysMomentum()
    {
        if (isStillHorizontally())
            return 0;
        Vec3 rightVec = entity.getLookVec().rotateYaw(-GUtil.PI / 2.0F);
        Vec3 rightVecHorizontal = new Vec3(rightVec.x, 0, rightVec.z).normalize();
        return rightVecHorizontal.x * this.motionX + rightVecHorizontal.z * this.motionZ;
    }

    private static final float STRAFING_THRESHOLD = 30.0f;

    public boolean isStrafing()
    {
        float angle = this.getMovementAngle();
        return (angle >= STRAFING_THRESHOLD && angle <= 180.0F - STRAFING_THRESHOLD)
                || (angle >= -180.0F + STRAFING_THRESHOLD && angle <= -STRAFING_THRESHOLD);
    }

    /**
     * @return True, if the player is sufficiently underwater.
     */
    public boolean isUnderwater()
    {
        if (!this.entity.isInWater())
            return false;

        int blockX = Mth.floor(this.entity.getX());
        int blockY = Mth.floor(this.entity.getY() + 2);
        int blockZ = Mth.floor(this.entity.getZ());
        BlockState state = Minecraft.getMinecraft().world.getBlockState(new BlockPos(blockX, blockY, blockZ));
        return state.getBlock() instanceof BlockStaticLiquid;
    }

    public double getPrevMotionMagnitude()
    {
        return Math.sqrt(this.prevMotionX * this.prevMotionX + this.prevMotionY * this.prevMotionY + this.prevMotionZ * this.prevMotionZ);
    }

    public double getMotionMagnitude()
    {
        return Math.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
    }

    public double getInterpolatedMotionMagnitude()
    {
        return interpolateMagitude(this.getMotionMagnitude(), this.getPrevMotionMagnitude());
    }

    public double getXZMotionMagnitude()
    {
        return Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
    }

    public double getPrevXZMotionMagnitude()
    {
        return Math.sqrt(this.prevMotionX * this.prevMotionX + this.prevMotionZ * this.prevMotionZ);
    }

    public double getInterpolatedXZMotionMagnitude()
    {
        return interpolateMagitude(this.getXZMotionMagnitude(), this.getPrevXZMotionMagnitude());
    }

    private static double interpolateMagitude(double magnitude, double prevMagnitude)
    {
        return prevMagnitude + (magnitude - prevMagnitude) * DataUpdateHandler.partialTicks;
    }

    public void updateClient()
    {
        this.prevMotionX = this.motionX;
        this.prevMotionY = this.motionY;
        this.prevMotionZ = this.motionZ;

        this.motionX = this.entity.getX() - this.positionX;
        this.motionY = this.entity.getY() - this.positionY;
        this.motionZ = this.entity.getZ() - this.positionZ;

        this.positionX = this.entity.getX();
        this.positionY = this.entity.getY();
        this.positionZ = this.entity.getZ();
    }

    @Override
    public Object getPartForName(String name)
    {
        return nameToPartMap.get(name);
    }

    public abstract void onTicksRestart();

}
