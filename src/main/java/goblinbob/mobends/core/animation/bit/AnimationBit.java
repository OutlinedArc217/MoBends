/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: AnimationBit.java
 */

package goblinbob.mobends.core.animation.bit;

import goblinbob.mobends.core.animation.layer.AnimationLayer;
import goblinbob.mobends.core.data.EntityData;
import net.minecraft.world.entity.Entity;

public abstract class AnimationBit<T extends EntityData<?>>
{

    /**
     * The layer that this bit is performed by. Used to callback, e.g. when the animation is finished.
     */
    protected AnimationLayer<? extends T> layer;

    /**
     * Called by the AnimationLayer before it plays this bit.
     */
    public void setupForPlay(AnimationLayer<? extends T> layer, T entityData)
    {
        this.layer = layer;
        this.onPlay(entityData);
    }

    /**
     * Returns the actions currently being performed by the entityData. Used by BendsPacks
     */
    public abstract String[] getActions(T entityData);

    /**
     * Called by setupForPlay to setup the beginning of this animation bit.
     */
    public void onPlay(T entityData) {}

    /**
     * Called by an AnimationLayer to perform a continuous animation.
     */
    public abstract void perform(T entityData);

}