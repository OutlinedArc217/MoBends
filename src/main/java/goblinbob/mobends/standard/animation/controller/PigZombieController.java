/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: PigZombieController.java
 */

package goblinbob.mobends.standard.animation.controller;

import goblinbob.mobends.core.animation.bit.AnimationBit;
// TODO: Create Object /* TODO: Implement IAnimationController */ interface - package missing
import goblinbob.mobends.core.animation.layer.HardAnimationLayer;
import goblinbob.mobends.standard.animation.bit.biped.AttackSlashInwardAnimationBit;
import goblinbob.mobends.standard.animation.bit.biped.JumpAnimationBit;
import goblinbob.mobends.standard.animation.bit.pigzombie.StandAnimationBit;
import goblinbob.mobends.standard.animation.bit.pigzombie.WalkAnimationBit;
import goblinbob.mobends.standard.data.BipedEntityData;
import goblinbob.mobends.standard.data.PigZombieData;
import net.minecraft.entity.monster.EntityPigZombie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.world.entity.Entity;

/**
 * This is an animation controller for a zombie instance.
 * It's a part of the EntityData structure.
 * 
 * @author Iwo Plaza
 *
 */
public class PigZombieController implements Object /* TODO: Implement IAnimationController */<PigZombieData>
{
	
	protected HardAnimationLayer<BipedEntityData<EntityPigZombie>> layerBase;
	protected HardAnimationLayer<BipedEntityData<?>> layerAction;
	protected AnimationBit<? extends BipedEntityData<EntityPigZombie>> bitStand, bitWalk, bitJump;
	protected AttackSlashInwardAnimationBit bitAttack;
	
	public PigZombieController()
	{
		this.layerBase = new HardAnimationLayer<>();
		this.layerAction = new HardAnimationLayer<>();
		this.bitStand = new StandAnimationBit();
		this.bitWalk = new WalkAnimationBit();
		this.bitJump = new JumpAnimationBit<>();
		this.bitAttack = new AttackSlashInwardAnimationBit();
	}
	
	@Override
	public Collection<String> perform(PigZombieData pigZombieData)
	{
		EntityPigZombie pigZombie =  pigZombieData.getEntity();
		
		if (!pigZombieData.isOnGround() || pigZombieData.getTicksAfterTouchdown() < 1)
		{
			this.layerBase.playOrContinueBit(bitJump, pigZombieData);
		}
		else
		{
			if (pigZombieData.isStillHorizontally())
			{
				this.layerBase.playOrContinueBit(bitStand, pigZombieData);
			}
			else
			{
				this.layerBase.playOrContinueBit(bitWalk, pigZombieData);
			}
		}
		
		if (pigZombie.swingProgress > 0)
		{
			this.layerAction.playOrContinueBit(this.bitAttack, pigZombieData);
		}
		else
		{
			this.layerAction.clearAnimation();
		}
		
		List<String> actions = new ArrayList<>();
		this.layerBase.perform(pigZombieData, actions);
		this.layerAction.perform(pigZombieData, actions);
		return actions;
	}

}
