package goblinbob.mobends.core.animation.state;

import goblinbob.mobends.core.math.Transform;
import net.minecraft.world.entity.Entity;

public abstract class AnimationState {
    protected float progress;
    protected boolean finished;

    public AnimationState() {
        this.progress = 0.0f;
        this.finished = false;
    }

    public abstract void update(Entity entity, float deltaTime, float partialTicks);
    public abstract void applyToTransform(Transform transform, Entity entity, float partialTicks);

    public float getProgress() {
        return progress;
    }

    public boolean isFinished() {
        return finished;
    }

    public void reset() {
        progress = 0.0f;
        finished = false;
    }
}
