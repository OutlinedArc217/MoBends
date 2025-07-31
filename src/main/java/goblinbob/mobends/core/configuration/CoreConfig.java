/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: CoreConfig.java
 */

package goblinbob.mobends.core.configuration;

import net.minecraftforge.common.ForgeConfigSpec;

import java.io.File;

public abstract class CoreConfig
{
    protected ForgeConfigSpec configuration;

    CoreConfig(File file)
    {
        configuration = new ForgeConfigSpec(file);
    }

    public abstract void save();
}
