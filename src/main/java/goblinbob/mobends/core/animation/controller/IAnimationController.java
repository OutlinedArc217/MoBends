package goblinbob.mobends.core.animation;

import goblinbob.mobends.core.animation.state.AnimationState;
import goblinbob.mobends.core.math.Transform;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

public interface IAnimationController {
    void update(Entity entity, float partialTicks);
    Optional<AnimationState> getState(String name);
    void setState(String name, AnimationState state);
    void removeState(String name);
    Transform getGlobalTransform();
    
    static boolean getGlobalAnimationsEnabled() {
        return AnimationController.getGlobalAnimationsEnabled();
    }
    
    static void setGlobalAnimationsEnabled(boolean enabled) {
        AnimationController.setGlobalAnimationsEnabled(enabled);
    }
}
