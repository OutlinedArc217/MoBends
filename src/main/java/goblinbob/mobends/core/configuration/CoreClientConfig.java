/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: CoreClientConfig.java
 */

package goblinbob.mobends.core.configuration;

import goblinbob.mobends.core.bender.EntityBender;
import goblinbob.mobends.core.bender.EntityBenderRegistry;

import java.io.File;
import java.util.Collection;
import net.minecraft.world.entity.Entity;

public class CoreClientConfig extends CoreConfig
{
    private static final String[] emptyStringList = new String[0];

    // General
    private static final String CATEGORY_GENERAL = "General";
    private static final String PROP_APPLIED_PACKS = "AppliedPacks";

    // Animated
    private static final String CATEGORY_ANIMATED = "Animated";

    public String[] appliedPackKeys;

    public CoreClientConfig(File file)
    {
        super(file);
        appliedPackKeys = new String[] {};
        load();
    }

    public void save()
    {
        for (EntityBender<?> entityBender : EntityBenderRegistry.instance.getRegistered())
        {
            configuration.get(CATEGORY_ANIMATED, entityBender.getKey(), true).setValue(entityBender.isAnimated());
        }

        configuration.save();
    }

    public void load()
    {
        appliedPackKeys = configuration.get(CATEGORY_GENERAL, PROP_APPLIED_PACKS, emptyStringList).getStringList();
    }

    public String[] getAppliedPacks()
    {
        return appliedPackKeys;
    }

    public void setAppliedPacks(String[] packNames)
    {
        appliedPackKeys = packNames;
        configuration.get(CATEGORY_GENERAL, PROP_APPLIED_PACKS, emptyStringList).set(packNames);
    }

    public void setAppliedPacks(Collection<String> packNames)
    {
        setAppliedPacks(packNames.toArray(new String[0]));
    }

    public boolean isEntityAnimated(String alterEntryKey)
    {
        return configuration.get(CATEGORY_ANIMATED, alterEntryKey, true).getBoolean();
    }
}