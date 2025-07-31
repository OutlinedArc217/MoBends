/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: NodeAnimationLayer.java
 */

package goblinbob.mobends.core.animation.layer;

import goblinbob.mobends.core.data.EntityData;
import goblinbob.mobends.core.kumo.state.INodeState;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.Entity;

public class NodeAnimationLayer<T extends EntityData<?>> extends AnimationLayer<T>
{

    private final List<INodeState> nodeStates = new ArrayList<>();
    private INodeState currentNode;

    @Override
    public String[] getActions(T entityData)
    {
        return new String[0];
    }

    @Override
    public void perform(T entityData)
    {

    }

}
