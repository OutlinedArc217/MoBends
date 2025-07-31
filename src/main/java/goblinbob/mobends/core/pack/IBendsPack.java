/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: IBendsPack.java
 */

package goblinbob.mobends.core.pack;

import net.minecraft.resources.ResourceLocation;

public interface IBendsPack
{

    String getKey();

    String getDisplayName();

    String getAuthor();

    String getDescription();

    ResourceLocation getThumbnail();

    boolean canPackBeEdited();

}
