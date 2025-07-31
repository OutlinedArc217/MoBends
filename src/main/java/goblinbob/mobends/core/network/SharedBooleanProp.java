/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: SharedBooleanProp.java
 */

package goblinbob.mobends.core.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.ForgeConfigSpec;

public class SharedBooleanProp extends SharedProperty<Boolean>
{

    public SharedBooleanProp(String key, Boolean value, String description)
    {
        super(key, value, description);
    }

    @Override
    public Boolean getValue()
    {
        return super.getValue();
    }

    @Override
    public void writeToNBT(CompoundTag tag)
    {
        tag.setBoolean(key, value);
    }

    @Override
    public void readFromNBT(CompoundTag tag)
    {
        value = tag.getBoolean(key);
    }

    @Override
    public void updateWithConfig(Configuration configuration, String category)
    {
        value = configuration.get(category, key, defaultValue, description).getBoolean();
    }

}
