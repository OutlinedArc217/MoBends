/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: PreviewHelper.java
 */

package goblinbob.mobends.core.bender;

import net.minecraft.world.entity.Entity;

import java.util.HashSet;
import java.util.Set;

public class PreviewHelper
{

    /**
     * The set of registered preview entities. This is used to determine if the system
     * should refrain from removing an entity's data, since they aren't a part of the world
     * and the system will think of them as dead.
     */
    private static final Set<Entity> previewEntities = new HashSet<>();

    public static void registerPreviewEntity(Entity entity)
    {
        previewEntities.add(entity);
    }

    public static boolean isPreviewEntity(Entity entity)
    {
        return previewEntities.contains(entity);
    }

}
