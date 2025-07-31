/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: RenderingEventHandler.java
 */

package goblinbob.mobends.standard.client.event;

import goblinbob.mobends.core.util.BenderHelper;
import goblinbob.mobends.standard.mutators.PlayerMutator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderingEventHandler
{

    @SubscribeEvent
    public void beforeHandRender(RenderHandEvent event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        Entity viewEntity = mc.getRenderViewEntity();

        if (!(viewEntity instanceof AbstractClientPlayer))
            return;

        AbstractClientPlayer player = (AbstractClientPlayer) viewEntity;

        if (!BenderHelper.isEntityAnimated(player))
        	return;

        RenderPlayer renderPlayer = (RenderPlayer) mc.getRenderManager().<AbstractClientPlayer>getEntityRenderObject(player);
        PlayerMutator mutator = (PlayerMutator) BenderHelper.getMutatorForRenderer(AbstractClientPlayer.class, renderPlayer);
        if (mutator != null)
            mutator.poseForFirstPersonView();
    }

}
