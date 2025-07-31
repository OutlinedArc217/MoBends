/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: KumoVariableEntry.java
 */

package goblinbob.mobends.core.kumo.variable;

import net.minecraft.network.chat.Component;

public class KumoVariableEntry
{

    private IKumoVariable variable;
    private String key;

    public KumoVariableEntry(IKumoVariable variable, String key)
    {
        this.variable = variable;
        this.key = key;
    }

    public String getLocalizedName()
    {
        return Component.format(String.format("mobends.variable.%s", this.key));
    }

}
