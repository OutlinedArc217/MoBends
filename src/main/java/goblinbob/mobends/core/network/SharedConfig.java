/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: SharedConfig.java
 */

package goblinbob.mobends.core.network;

import net.minecraft.nbt.CompoundTag;

import java.util.LinkedList;

/**
 * Holds properties that are shared by the server with the client on world join.
 * These are usually permissions and restrictions.
 */
public class SharedConfig
{

    private LinkedList<SharedProperty<?>> properties = new LinkedList<>();

    public SharedConfig()
    {
    }

    public void addProperty(SharedProperty<?> property)
    {
        properties.add(property);
    }

    public Iterable<SharedProperty<?>> getProperties()
    {
        return properties;
    }

    public void writeToNBT(CompoundTag tag)
    {
        for (SharedProperty<?> property : properties)
        {
            property.save(tag);
        }
    }

    public void readFromNBT(CompoundTag tag)
    {
        for (SharedProperty<?> property : properties)
        {
            property.load(tag);
        }
    }

}
