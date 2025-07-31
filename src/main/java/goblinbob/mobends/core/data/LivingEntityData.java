/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: LivingEntityData.java
 */

package goblinbob.mobends.core.data;

import goblinbob.mobends.core.client.event.DataUpdateHandler;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public abstract class LivingEntityData<E extends LivingEntity> extends EntityData<E>
{

    protected float ticksInAir;
    protected float ticksAfterTouchdown;
    protected float ticksAfterAttack;
    protected float ticksFalling;
    protected float climbingCycle = 0F;
    protected boolean alreadyAttacked = false;
    protected boolean climbing = false;

    public OverridableProperty<Float> limbSwing = new OverridableProperty<>(0F);
    public OverridableProperty<Float> limbSwingAmount = new OverridableProperty<>(0F);
    public OverridableProperty<Float> swingProgress = new OverridableProperty<>(0F);
    public OverridableProperty<Float> headYaw = new OverridableProperty<>(0F);
    public OverridableProperty<Float> headPitch = new OverridableProperty<>(0F);

    public LivingEntityData(E entity)
    {
        super(entity);

        // Setting high values for ticks* variables
        // to avoid premature animation triggers.
        // (like the automatic attack stance on creation)
        this.ticksInAir = 100F;
        this.ticksAfterTouchdown = 100F;
        this.ticksAfterAttack = 100F;
        this.ticksFalling = 100F;
    }

    public void setClimbing(boolean flag)
    {
        this.climbing = flag;
    }

    public float getClimbingCycle() { return this.climbingCycle; }

    public float getTicksInAir() { return this.ticksInAir; }

    public float getTicksAfterTouchdown() { return this.ticksAfterTouchdown; }

    public float getTicksAfterAttack() { return this.ticksAfterAttack; }

    public float getTicksFalling() { return this.ticksFalling; }

    public boolean isClimbing() { return this.climbing; }

    @Override
    public void updateClient()
    {
        super.updateClient();

        final boolean calcOnGroundResult = this.calcOnGround();
        if (calcOnGroundResult & !this.onGround)
        {
            this.onTouchdown();
            this.onGround = true;
        }

        if ((!calcOnGroundResult & this.onGround) | (this.prevMotionY <= 0 && this.motionY - this.prevMotionY > 0.4D && this.ticksInAir > 2.0F))
        {
            this.onLiftoff();
            this.onGround = false;
        }

        if (this.calcClimbing())
        {
            this.climbingCycle += this.motionY * 2.6F;
            this.climbing = true;
        }
        else
        {
            this.climbing = false;
        }

        if (this.entity.isSwingInProgress)
        {
            if (!this.alreadyAttacked || this.ticksAfterAttack > 5.0F)
            {
                this.onAttack();
                this.alreadyAttacked = true;
            }
        }
        else
        {
            this.alreadyAttacked = false;
        }
    }

    @Override
    public void update(float partialTicks)
    {
        super.update(partialTicks);

        if (this.isOnGround())
        {
            this.ticksAfterTouchdown += DataUpdateHandler.ticksPerFrame;
        }
        else
        {
            this.ticksInAir += DataUpdateHandler.ticksPerFrame;

            if (this.motionY < 0.0D)
            {
                this.ticksFalling += DataUpdateHandler.ticksPerFrame;
            }
            else
            {
                this.ticksFalling = 0.0F;
            }
        }

        this.ticksAfterAttack += DataUpdateHandler.ticksPerFrame;
    }

    public void onTouchdown()
    {
        this.ticksAfterTouchdown = 0.0F;
        this.ticksFalling = 0.0F;
    }

    public void onLiftoff()
    {
        this.ticksInAir = 0.0F;
    }

    public void onAttack()
    {
        this.ticksAfterAttack = 0.0F;
    }

    public float getClimbingRotation()
    {
        return getLadderFacing().getHorizontalAngle() + 180.0F;
    }

    private static boolean isBlockClimbable(BlockState state)
    {
        return state.getBlock() instanceof BlockLadder || state.getBlock() instanceof BlockVine;
    }

    private static Direction getClimbableBlockFacing(BlockState state)
    {
        if (state.getBlock() instanceof BlockLadder)
        {
            return state.getValue(BlockLadder.FACING);
        }
        else if (state.getBlock() instanceof BlockVine)
        {
            if (state.getValue(BlockVine.EAST))
                return Direction.WEST;
            else if (state.getValue(BlockVine.WEST))
                return Direction.EAST;
            else if (state.getValue(BlockVine.NORTH))
                return Direction.SOUTH;
            else if (state.getValue(BlockVine.SOUTH))
                return Direction.NORTH;
        }

        return Direction.NORTH;
    }

    public Direction getLadderFacing()
    {
        BlockPos position = new BlockPos(Math.floor(entity.getX()), Math.floor(entity.getY()), Math.floor(entity.getZ()));

        BlockState block = entity.world.getBlockState(position);
        BlockState blockBelow = entity.world.getBlockState(position.add(0, -1, 0));
        BlockState blockBelow2 = entity.world.getBlockState(position.add(0, -2, 0));

        Direction facing = Direction.NORTH;
        facing = getClimbableBlockFacing(block);
        if (facing == Direction.NORTH)
            facing = getClimbableBlockFacing(blockBelow);
        if (facing == Direction.NORTH)
            facing = getClimbableBlockFacing(blockBelow2);

        return facing;
    }

    public boolean calcClimbing()
    {
        if (entity == null || entity.world == null)
            return false;

        BlockPos position = new BlockPos(Math.floor(entity.getX()), Math.floor(entity.getY()), Math.floor(entity.getZ()));

        BlockState block = entity.world.getBlockState(position);
        BlockState blockBelow = entity.world.getBlockState(position.add(0, -1, 0));
        BlockState blockBelow2 = entity.world.getBlockState(position.add(0, -2, 0));

        return entity.isOnLadder() && !this.isOnGround() && (isBlockClimbable(block) || isBlockClimbable(blockBelow) || isBlockClimbable(blockBelow2));
    }

    public float getLedgeHeight()
    {
        float clientY = (float) (entity.getY() + (entity.getY() - entity.prevPosY) * DataUpdateHandler.partialTicks);

        final BlockPos position = new BlockPos(Math.floor(entity.getX()), Math.floor(entity.getY()), Math.floor(entity.getZ()));

        BlockState block = entity.world.getBlockState(position.add(0, 2, 0));
        BlockState blockBelow = entity.world.getBlockState(position.add(0, 1, 0));
        BlockState blockBelow2 = entity.world.getBlockState(position.add(0, 0, 0));
        if (!isBlockClimbable(block))
        {
            if (!isBlockClimbable(blockBelow))
            {
                if (!isBlockClimbable(blockBelow2))
                    return (clientY - (int) clientY) + 2;
                else
                    return (clientY - (int) clientY) + 1;
            }
            else
            {
                return clientY - (int) clientY;
            }
        }

        return -2.0F;
    }

    public boolean isDrawingBow()
    {
        if (entity.getItemInUseCount() > 0)
        {
            ItemStack mainItemStack = entity.getHeldItemMainhand();
            ItemStack offItemStack = entity.getHeldItemOffhand();
            if ((!mainItemStack.isEmpty() && mainItemStack.getItemUseAction() == UseAnim.BOW)
                    || (!offItemStack.isEmpty() && offItemStack.getItemUseAction() == UseAnim.BOW))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public E getEntity()
    {
        return this.entity;
    }

}
