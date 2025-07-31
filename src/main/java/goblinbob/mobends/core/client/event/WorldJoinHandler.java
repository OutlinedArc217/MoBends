/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: WorldJoinHandler.java
 */

package goblinbob.mobends.core.client.event;

import goblinbob.mobends.core.Core;
import goblinbob.mobends.core.network.NetworkConfiguration;
import goblinbob.mobends.core.network.msg.MessageConfigRequest;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.entity.Entity;

public class WorldJoinHandler
{

    @SubscribeEvent
    public void onPlayerJoinedServer(EntityJoinWorldEvent event)
    {
        if (event.getEntity() instanceof AbstractClientPlayer)
        {
            // Setting the most restrictive configuration
            NetworkConfiguration.instance.onWorldJoin();

            // Sending a request to the server for the server-specific config.
            Core.getNetworkWrapper().sendToServer(new MessageConfigRequest());
        }
    }

}
