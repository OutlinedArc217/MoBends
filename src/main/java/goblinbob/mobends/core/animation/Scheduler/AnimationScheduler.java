/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: AnimationScheduler.java
 */

package goblinbob.mobends.core.animation.scheduler;

// TODO: Create Object /* TODO: Implement IAnimationController */ interface - package missing
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AnimationScheduler {
    private static final AnimationScheduler INSTANCE = new AnimationScheduler();
    
    private final Map<Entity, Queue<ScheduledAnimation>> scheduledAnimations;
    private final Map<Entity, ScheduledAnimation> currentAnimations;

    private AnimationScheduler() {
        this.scheduledAnimations = new ConcurrentHashMap<>();
        this.currentAnimations = new ConcurrentHashMap<>();
    }

    public static AnimationScheduler getInstance() {
        return INSTANCE;
    }

    public void scheduleAnimation(Entity entity, String animationId, 
                                float duration, float delay) {
        Queue<ScheduledAnimation> queue = scheduledAnimations
            .computeIfAbsent(entity, k -> new ConcurrentLinkedQueue<>());
            
        queue.offer(new ScheduledAnimation(animationId, duration, delay));
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END || 
            !Object /* TODO: Implement IAnimationController */.getGlobalAnimationsEnabled()) {
            return;
        }

        float currentTime = System.nanoTime() / 1_000_000_000.0f;
        
        currentAnimations.entrySet().removeIf(entry -> {
            ScheduledAnimation anim = entry.getValue();
            return currentTime >= anim.startTime + anim.duration;
        });

        scheduledAnimations.forEach((entity, queue) -> {
            if (!currentAnimations.containsKey(entity) && !queue.isEmpty()) {
                ScheduledAnimation nextAnim = queue.peek();
                if (currentTime >= nextAnim.startTime) {
                    currentAnimations.put(entity, queue.poll());
                }
            }
        });

        scheduledAnimations.entrySet().removeIf(entry -> entry.getValue().isEmpty());
    }

    public ScheduledAnimation getCurrentAnimation(Entity entity) {
        return currentAnimations.get(entity);
    }

    public static class ScheduledAnimation {
        public final String animationId;
        public final float duration;
        public final float startTime;

        public ScheduledAnimation(String animationId, float duration, float delay) {
            this.animationId = animationId;
            this.duration = duration;
            this.startTime = System.nanoTime() / 1_000_000_000.0f + delay;
        }
    }
}
