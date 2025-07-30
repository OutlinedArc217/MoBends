package goblinbob.mobends.core.animation;

import goblinbob.mobends.core.animation.state.AnimationState;
import goblinbob.mobends.core.math.Transform;
import goblinbob.mobends.core.util.TimeUtils;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Optional;
import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class AnimationController implements IAnimationController {
    private static boolean globalAnimationsEnabled = true;
    private final ConcurrentHashMap<String, AnimationState> states;
    private final Transform globalTransform;
    private long lastUpdateTime;

    public AnimationController() {
        this.states = new ConcurrentHashMap<>();
        this.globalTransform = new Transform();
        this.lastUpdateTime = TimeUtils.getCurrentTime();
    }

    @Override
    public void update(Entity entity, float partialTicks) {
        if (!globalAnimationsEnabled) return;

        long currentTime = TimeUtils.getCurrentTime();
        float deltaTime = (currentTime - lastUpdateTime) / 1000.0f;
        lastUpdateTime = currentTime;

        states.values().forEach(state -> state.update(entity, deltaTime, partialTicks));
        updateGlobalTransform(entity, partialTicks);
    }

    private void updateGlobalTransform(Entity entity, float partialTicks) {
        globalTransform.reset();
        states.values().forEach(state -> 
            state.applyToTransform(globalTransform, entity, partialTicks));
    }

    @Override
    public Optional<AnimationState> getState(String name) {
        return Optional.ofNullable(states.get(name));
    }

    @Override
    public void setState(String name, AnimationState state) {
        states.put(name, state);
    }

    @Override
    public void removeState(String name) {
        states.remove(name);
    }

    @Override
    public Transform getGlobalTransform() {
        return globalTransform;
    }

    public static boolean getGlobalAnimationsEnabled() {
        return globalAnimationsEnabled;
    }

    public static void setGlobalAnimationsEnabled(boolean enabled) {
        globalAnimationsEnabled = enabled;
    }
                                       }
