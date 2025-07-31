/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: StateCondition.java
 */

package goblinbob.mobends.core.kumo.state.condition;

import goblinbob.mobends.core.data.EntityData;
import goblinbob.mobends.core.kumo.state.template.MalformedKumoTemplateException;
import goblinbob.mobends.core.kumo.state.template.TriggerConditionTemplate;
import net.minecraft.world.entity.Entity;

/**
 * This condition is met once the entity is in the provided state.
 * (e.g. ON_GROUND, AIRBORNE, etc.)
 *
 * @author Iwo Plaza
 */
public class StateCondition implements ITriggerCondition
{

    private final State state;

    public StateCondition(Template template) throws MalformedKumoTemplateException
    {
        if (template.state == null)
        {
            throw new MalformedKumoTemplateException("No 'state' property given for trigger condition.");
        }

        this.state = template.state;
    }

    @Override
    public boolean isConditionMet(ITriggerConditionContext context)
    {
        EntityData<?> entityData = context.getEntityData();

        switch (this.state)
        {
            case ON_GROUND:
                return entityData.isOnGround();
            case AIRBORNE:
                return !entityData.isOnGround();
            case SPRINTING:
                return entityData.getEntity().isSprinting();
            case STANDING_STILL:
                return entityData.isStillHorizontally();
            case MOVING_HORIZONTALLY:
                return !entityData.isStillHorizontally();
            default:
                return false;
        }
    }

    public static class Template extends TriggerConditionTemplate
    {

        public State state;

    }

    public enum State
    {
        ON_GROUND,
        AIRBORNE,
        SPRINTING,
        STANDING_STILL,
        MOVING_HORIZONTALLY,
    }


}
