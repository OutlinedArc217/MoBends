/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: PartBoxes.java
 */

package goblinbob.mobends.standard.client.model.armor;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;

import java.util.*;

public class PartBoxes
{
    protected HashMap<ModelPart, List<ModelBox>> modelToBoxesMap = new HashMap<>();

    public void put(ModelPart renderer, ModelBox box)
    {
        if (!modelToBoxesMap.containsKey(renderer))
        {
            modelToBoxesMap.put(renderer, new LinkedList<>());
        }

        modelToBoxesMap.get(renderer).add(box);
    }

    public void clear()
    {
        this.modelToBoxesMap.clear();
    }

    public void clearRenderer(ModelPart renderer)
    {
        modelToBoxesMap.remove(renderer);
    }

    public Set<Map.Entry<ModelPart, List<ModelBox>>> entrySet()
    {
        return modelToBoxesMap.entrySet();
    }

    public Set<ModelPart> keySet()
    {
        return modelToBoxesMap.keySet();
    }
}
