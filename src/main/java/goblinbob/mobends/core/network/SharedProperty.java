/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: SharedProperty.java
 */

package goblinbob.mobends.core.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.ForgeConfigSpec;

/**
 * Represents a value that is stored in the server's config, then shared with clients
 * once they join.
 */
public abstract class SharedProperty<T>
{

    protected final String key;
    protected final String description;
    protected final T defaultValue;
    protected T value;

    public SharedProperty(String key, T defaultValue, String description)
    {
        this.key = key;
        this.description = description;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public String getKey()
    {
        return key;
    }

    public String getDescription()
    {
        return description;
    }

    public T getDefaultValue()
    {
        return defaultValue;
    }

    public T getValue()
    {
        return value;
    }

    public void setValue(T value)
    {
        this.value = value;
    }

    public abstract void writeToNBT(CompoundTag tag);

    public abstract void readFromNBT(CompoundTag tag);

    public abstract void updateWithConfig(Configuration configuration, String category);

}
