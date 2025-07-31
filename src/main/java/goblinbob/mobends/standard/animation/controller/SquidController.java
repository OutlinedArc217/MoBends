/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: SquidController.java
 */

package goblinbob.mobends.standard.animation.controller;

// TODO: Create Object /* TODO: Implement IAnimationController */ interface - package missing
import goblinbob.mobends.core.client.event.DataUpdateHandler;
import goblinbob.mobends.core.util.GUtil;
import goblinbob.mobends.standard.data.SquidData;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;
import java.util.Collection;
import net.minecraft.world.entity.Entity;

/**
 * This is an animation controller for a squid instance. It's a part of the EntityData structure.
 *
 * @author Iwo Plaza
 */
public class SquidController implements Object /* TODO: Implement IAnimationController */<SquidData>
{

    @Override
    @Nullable
    public Collection<String> perform(SquidData data)
    {
        EntitySquid squid = data.getEntity();

        float squidRotation = squid.prevSquidRotation + (squid.squidRotation - squid.prevSquidRotation) * DataUpdateHandler.partialTicks + 1.1F;
        float f = squidRotation / GUtil.PI;
        f = Math.max(0.0F, f);
        float baseTentacleAngle = 0.0F;
        if (squid.prevSquidRotation < GUtil.PI)
        {
            baseTentacleAngle = Mth.sin(f * f * (float) Math.PI) * 60.0f;
        }

        for (int i = 0; i < data.squidTentacles.length; ++i)
        {

            double d0 = (double) i * -360.0D / (double) data.squidTentacles.length + 90.0D;
            data.squidTentacles[i][0].rotation.setSmoothness(0.1F).orientX(baseTentacleAngle).rotateY((float) d0);

            float f2 = squidRotation / (GUtil.PI * 2);
            f2 = Math.max(0.0F, f2);
            for (int j = 1; j < SquidData.TENTACLE_SECTIONS; ++j)
            {
                float tentacleAngle = 0;
                if (squid.squidRotation < GUtil.PI)
                {
                    tentacleAngle = Mth.sin(f2 * GUtil.PI * 2 + j * 0.1F) * 10.0F;
                }
                data.squidTentacles[i][j].rotation.setSmoothness(0.1F).orientX(-tentacleAngle);
            }
        }

        return null;
    }

}
