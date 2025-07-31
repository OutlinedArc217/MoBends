/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: TorchHoldingAnimationBit.java
 */

package goblinbob.mobends.standard.animation.bit.biped;

import goblinbob.mobends.core.animation.bit.AnimationBit;
import goblinbob.mobends.core.client.model.IModelPart;
import goblinbob.mobends.standard.data.BipedEntityData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
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

public class TorchHoldingAnimationBit extends AnimationBit<BipedEntityData<?>>
{

	private static final String[] ACTIONS = new String[] { "torch_holding" };
	
	@Override
	public String[] getActions(BipedEntityData<?> data)
	{
		return ACTIONS;
	}

	private EnumHandSide getTorchHand(LivingEntity living)
	{
		final EnumHandSide mainHand = living.getPrimaryHand();
		final EnumHandSide offHand = mainHand == EnumHandSide.LEFT ? EnumHandSide.RIGHT : EnumHandSide.LEFT;

		final Item mainItem = living.getHeldItem(EnumHand.MAIN_HAND).getItem();
		final Item offItem = living.getHeldItem(EnumHand.OFF_HAND).getItem();
		final Item torch = Item.getItemFromBlock(Blocks.TORCH);

		if (mainItem.equals(torch))
			return mainHand;
		else if (offItem.equals(torch))
			return offHand;
		else
			return null;
	}

	@Override
	public void perform(BipedEntityData<?> data)
	{
		final LivingEntity living = data.getEntity();
		final EnumHandSide torchHand = getTorchHand(living);

		if (torchHand == null)
			return;

		final IModelPart mainArm = torchHand == EnumHandSide.RIGHT ? data.rightArm : data.leftArm;
		final IModelPart mainForeArm = torchHand == EnumHandSide.RIGHT ? data.rightForeArm : data.leftForeArm;
		
		mainArm.getRotation().orientX(-90.0F + data.headPitch.get() * 0.5F)
							 .rotateY(data.headYaw.get() * 0.7F);
		mainForeArm.getRotation().orientX(-5.0F);
	}

}
