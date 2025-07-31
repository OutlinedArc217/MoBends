/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: GsonResources.java
 */

package goblinbob.mobends.core.util;

import goblinbob.mobends.core.kumo.KumoSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class GsonResources
{

    private static Map<ResourceLocation, Object> cache = new HashMap<>();

    public static void clearCache()
    {
        cache.clear();
    }

    public static <T> T get(ResourceLocation location, Class<T> classOfT) throws IOException
    {
        final InputStream stream = Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream();

        if (cache.containsKey(location))
        {
            //noinspection unchecked
            return (T) cache.get(location);
        }
        else
        {
            T resource = KumoSerializer.INSTANCE.gson.fromJson(new InputStreamReader(stream), classOfT);
            cache.put(location, resource);
            return resource;
        }
    }

}
