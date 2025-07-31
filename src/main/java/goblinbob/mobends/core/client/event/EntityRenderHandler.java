/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: EntityRenderHandler.java
 */

package goblinbob.mobends.core.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
// TODO: Create Object /* TODO: Implement IAnimationController */ interface - package missing
import goblinbob.mobends.core.bender.EntityBenderRegistry;
import goblinbob.mobends.core.client.model.ModelPart;
import goblinbob.mobends.core.data.EntityData;
import goblinbob.mobends.core.math.matrix.IMat4x4d;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.WeakHashMap;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.joml.Matrix4f;
import org.joml.Matrix3f;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;

@OnlyIn(Dist.CLIENT)
public class EntityRenderHandler {
    private static final WeakHashMap<Entity, EntityData<?>> ENTITY_DATA_CACHE = new WeakHashMap<>();
    private static final ModelPart MODEL_RENDERER = new ModelPart();
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderLivingPre(RenderLivingEvent.Pre<?, ?> event) {
        if (!Object /* TODO: Implement IAnimationController */.getGlobalAnimationsEnabled()) return;

        LivingEntity entity = event.getEntity();
        var definition = EntityBenderRegistry.instance.getDefinition(entity.getType());
        
        if (definition.isEmpty()) return;

        PoseStack matrixStack = event.getPoseStack();
        float partialTick = event.getPartialTick();
        
        var entityData = getOrCreateEntityData(entity);
        if (entityData == null) return;

        entityData.updateClient(partialTick);
        
        matrixStack.pushPose();
        IMat4x4d transform = entityData.getGlobalTransform();
        if (transform != null) {
            applyTransform(matrixStack, transform);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRenderLivingPost(RenderLivingEvent.Post<?, ?> event) {
        if (!Object /* TODO: Implement IAnimationController */.getGlobalAnimationsEnabled()) return;

        LivingEntity entity = event.getEntity();
        var definition = EntityBenderRegistry.instance.getDefinition(entity.getType());
        
        if (definition.isEmpty()) return;

        event.getPoseStack().popPose();
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;

        MODEL_RENDERER.prepare();
        
        // Clean up stale entity data
        ENTITY_DATA_CACHE.entrySet().removeIf(entry -> 
            entry.getKey().isRemoved() || !entry.getKey().isAlive());
    }

    private <T extends LivingEntity> EntityData<T> getOrCreateEntityData(T entity) {
        @SuppressWarnings("unchecked")
        EntityData<T> data = (EntityData<T>) ENTITY_DATA_CACHE.get(entity);
        
        if (data == null) {
            var dataProvider = EntityBenderRegistry.instance.getDataProvider(entity.getType());
            if (dataProvider.isPresent()) {
                data = dataProvider.get().createData(entity);
                ENTITY_DATA_CACHE.put(entity, data);
            }
        }
        
        return data;
    }

    private void applyTransform(PoseStack matrixStack, IMat4x4d transform) {
        matrixStack.last().pose().multiply(transform.toMatrix4f());
    }
}
