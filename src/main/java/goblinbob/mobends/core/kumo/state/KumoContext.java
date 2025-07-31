/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: KumoContext.java
 */

package goblinbob.mobends.core.kumo.state;

import goblinbob.mobends.core.data.EntityData;
import net.minecraft.world.entity.Entity;

/**
 * A simple implementation of the KUMO context.
 *
 * @author Iwo Plaza
 */
public class KumoContext implements IKumoContext
{

    public EntityData<?> entityData;

    public ILayerState layerState;

    public INodeState currentNode;

    @Override
    public EntityData<?> getEntityData()
    {
        return entityData;
    }

    @Override
    public ILayerState getLayerState()
    {
        return layerState;
    }

    @Override
    public INodeState getCurrentNode()
    {
        return currentNode;
    }

    @Override
    public void setCurrentNode(INodeState node)
    {
        currentNode = node;
    }

}
