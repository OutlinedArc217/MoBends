/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: ZombieVillagerMutator.java
 */

package goblinbob.mobends.standard.mutators;

import goblinbob.mobends.core.client.model.ModelPart;
import goblinbob.mobends.core.data.IEntityDataFactory;
import goblinbob.mobends.standard.data.ZombieVillagerData;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.entity.monster.EntityZombieVillager;
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
import net.minecraft.world.entity.Entity;

public class ZombieVillagerMutator extends ZombieMutatorBase<ZombieVillagerData, EntityZombieVillager, ModelZombieVillager>
{

	public ZombieVillagerMutator(IEntityDataFactory<EntityZombieVillager> dataFactory)
	{
		super(dataFactory);
	}
	
	@Override
	public void storeVanillaModel(ModelZombieVillager model)
	{
		ModelZombieVillager vanillaModel = new ModelZombieVillager(0.0F, 0.0F, this.halfTexture);
		this.vanillaModel = vanillaModel;
		
		// Calling the super method here, since it
		// requires the vanillaModel property to be
		// set.
		super.storeVanillaModel(model);
	}
	
	@Override
	public boolean createParts(ModelZombieVillager original, float scaleFactor)
	{
		boolean success = super.createParts(original, scaleFactor);
		
		original.bipedHead = this.head = new ModelPart(original, 0, 0)
				.setParent(body)
				.setPosition(0.0F, -12.0F, 0.0F);
		
		this.head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, scaleFactor);
		this.head.setTextureOffset(24, 0).addBox(-1.0F, -3.0F, -6.0F, 2, 4, 2, scaleFactor);
		
		return success;
	}

	@Override
	public boolean shouldModelBeSkipped(Model model)
	{
		return !(model instanceof ModelZombieVillager);
	}
	
}
