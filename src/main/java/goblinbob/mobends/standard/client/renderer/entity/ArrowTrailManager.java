/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: ArrowTrailManager.java
 */

package goblinbob.mobends.standard.client.renderer.entity;

import net.minecraft.entity.projectile.EntityArrow;

import java.util.HashMap;
import net.minecraft.world.entity.Entity;

public class ArrowTrailManager
{
    private static HashMap<EntityArrow, ArrowTrail> trailMap = new HashMap<>();
    public static long time, lastTime;

    static
    {
        time = System.nanoTime() / 1000;
        lastTime = System.nanoTime() / 1000;
    }

    public static ArrowTrail getOrMake(EntityArrow arrow)
    {
        ArrowTrail trail;
        if (!trailMap.containsKey(arrow))
        {
            trail = new ArrowTrail(arrow);
            trailMap.put(arrow, trail);
        }
        else
        {
            trail = trailMap.get(arrow);
        }

        return trail;
    }

    public static void renderTrail(EntityArrow entity, double x, double y, double z, float partialTicks)
    {
        getOrMake(entity).render(x, y, z, partialTicks);
    }

    public static void cleanup()
    {
        trailMap.entrySet().removeIf(e -> e.getValue().shouldBeRemoved());
    }

    public static void onRenderTick()
    {
        for (final ArrowTrail trail : trailMap.values())
        {
            trail.onRenderTick();
        }

        cleanup();
    }
}
