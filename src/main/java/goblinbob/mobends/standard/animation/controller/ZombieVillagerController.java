/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: ZombieVillagerController.java
 */

package goblinbob.mobends.standard.animation.controller;

import goblinbob.mobends.core.animation.bit.AnimationBit;
// TODO: Create Object /* TODO: Implement IAnimationController */ interface - package missing
import goblinbob.mobends.core.animation.layer.HardAnimationLayer;
import goblinbob.mobends.standard.animation.bit.biped.JumpAnimationBit;
import goblinbob.mobends.standard.animation.bit.biped.StandAnimationBit;
import goblinbob.mobends.standard.animation.bit.biped.WalkAnimationBit;
import goblinbob.mobends.standard.animation.bit.zombie_base.ZombieLeanAnimationBit;
import goblinbob.mobends.standard.animation.bit.zombie_base.ZombieStumblingAnimationBit;
import goblinbob.mobends.standard.data.ZombieVillagerData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.world.entity.Entity;

/**
 * This is an animation controller for a zombie villager instance.
 * It's a part of the EntityData structure.
 * 
 * @author Iwo Plaza
 *
 */
public class ZombieVillagerController implements Object /* TODO: Implement IAnimationController */<ZombieVillagerData>
{
	
	protected HardAnimationLayer<ZombieVillagerData> layerBase;
	protected HardAnimationLayer<ZombieVillagerData> layerSet;
	protected AnimationBit<ZombieVillagerData> bitStand, bitWalk, bitJump;
	protected AnimationBit<ZombieVillagerData>[] bitAnimationSet;
	
	public ZombieVillagerController()
	{
		this.layerBase = new HardAnimationLayer<>();
		this.layerSet = new HardAnimationLayer<>();
		this.bitStand = new StandAnimationBit<>();
		this.bitWalk = new WalkAnimationBit<>();
		this.bitJump = new JumpAnimationBit<>();
		this.bitAnimationSet = new AnimationBit[] {
			new ZombieLeanAnimationBit(),
			new ZombieStumblingAnimationBit()
		};
	}
	
	@Override
	public Collection<String> perform(ZombieVillagerData zombieData)
	{
		if (!zombieData.isOnGround() || zombieData.getTicksAfterTouchdown() < 1)
		{
			this.layerBase.playOrContinueBit(bitJump, zombieData);
		}
		else
		{
			if (zombieData.isStillHorizontally())
			{
				this.layerBase.playOrContinueBit(bitStand, zombieData);
			}
			else
			{
				this.layerBase.playOrContinueBit(bitWalk, zombieData);
			}
		}
		
		this.layerSet.playOrContinueBit(bitAnimationSet[zombieData.getAnimationSet()], zombieData);
		
		List<String> actions = new ArrayList<String>();
		this.layerBase.perform(zombieData, actions);
		this.layerSet.perform(zombieData, actions);
		return actions;
	}
	
}
