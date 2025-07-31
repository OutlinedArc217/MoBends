/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: ITriggerConditionContext.java
 */

package goblinbob.mobends.core.kumo.state.condition;

import goblinbob.mobends.core.data.EntityData;
import goblinbob.mobends.core.kumo.state.ILayerState;
import goblinbob.mobends.core.kumo.state.INodeState;
import net.minecraft.world.entity.Entity;

public interface ITriggerConditionContext
{

    /**
     * Returns data for the entity that's being animated.
     */
    EntityData<?> getEntityData();

    /**
     * Returns the layer this condition has to be met on.
     */
    ILayerState getLayerState();

    /**
     * Returns the current node.
     */
    INodeState getCurrentNode();

}
