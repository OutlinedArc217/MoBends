package goblinbob.mobends.core.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import goblinbob.mobends.core.animation.controller.IAnimationController;
import goblinbob.mobends.core.bender.EntityBenderRegistry;
import goblinbob.mobends.core.client.model.ModelRenderer;
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

@OnlyIn(Dist.CLIENT)
public class EntityRenderHandler {
    private static final WeakHashMap<Entity, EntityData<?>> ENTITY_DATA_CACHE = new WeakHashMap<>();
    private static final ModelRenderer MODEL_RENDERER = new ModelRenderer();
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderLivingPre(RenderLivingEvent.Pre<?, ?> event) {
        if (!IAnimationController.getGlobalAnimationsEnabled()) return;

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
        if (!IAnimationController.getGlobalAnimationsEnabled()) return;

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
