package goblinbob.mobends.core.animation.event;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;

public class AnimationEvent extends Event implements IModBusEvent {
    private final Entity entity;
    private final String animationId;
    private final float progress;

    public static class Start extends AnimationEvent {
        public Start(Entity entity, String animationId) {
            super(entity, animationId, 0.0f);
        }
    }

    public static class Update extends AnimationEvent {
        public Update(Entity entity, String animationId, float progress) {
            super(entity, animationId, progress);
        }
    }

    public static class End extends AnimationEvent {
        public End(Entity entity, String animationId) {
            super(entity, animationId, 1.0f);
        }
    }

    protected AnimationEvent(Entity entity, String animationId, float progress) {
        this.entity = entity;
        this.animationId = animationId;
        this.progress = progress;
    }

    public Entity getEntity() {
        return entity;
    }

    public String getAnimationId() {
        return animationId;
    }

    public float getProgress() {
        return progress;
    }
}
