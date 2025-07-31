/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: HardAnimationLayer.java
 */

package goblinbob.mobends.core.animation.layer;

import goblinbob.mobends.core.animation.bit.AnimationBit;
import goblinbob.mobends.core.data.EntityData;
import net.minecraft.world.entity.Entity;

public class HardAnimationLayer<T extends EntityData<?>> extends AnimationLayer<T>
{
	protected AnimationBit<T> performedBit;
	protected AnimationBit<T> previousBit;
	
	@SuppressWarnings("unchecked")
	public void playBit(AnimationBit<? extends T> bit, T entityData)
	{
		this.previousBit = this.performedBit;
		this.performedBit = (AnimationBit<T>) bit;
		this.performedBit.setupForPlay(this, entityData);
	}
	
	public void playOrContinueBit(AnimationBit<? extends T> bit, T entityData)
	{
		if (!this.isPlaying(bit))
			this.playBit(bit, entityData);
	}

	@Override
	public void perform(T entityData)
	{
		if (performedBit != null)
			performedBit.perform(entityData);
	}

	public boolean isPlaying(AnimationBit<? extends T> bit)
	{
		return bit == this.performedBit;
	}
	
	public boolean isPlaying()
	{
		return this.performedBit != null;
	}

	public void clearAnimation()
	{
		this.performedBit = null;
	}

	public AnimationBit<T> getPerformedBit()
	{
		return this.performedBit;
	}

	@Override
	public String[] getActions(T entityData)
	{
		if (this.isPlaying())
		{
			return this.getPerformedBit().getActions(entityData);
		}
		return new String[] {};
	}
}
