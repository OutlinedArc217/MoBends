/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: KumoAnimatorState.java
 */

package goblinbob.mobends.core.kumo.state;

import goblinbob.mobends.core.data.EntityData;
import goblinbob.mobends.core.kumo.state.template.AnimatorTemplate;
import goblinbob.mobends.core.kumo.state.template.LayerTemplate;
import goblinbob.mobends.core.kumo.state.template.MalformedKumoTemplateException;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.Entity;

public class KumoAnimatorState<D extends EntityData<?>>
{

    private List<ILayerState> layerStates = new ArrayList<>();
    private KumoContext context = new KumoContext();
    private boolean started = false;

    public KumoAnimatorState(AnimatorTemplate animatorTemplate, IKumoInstancingContext dataProvider) throws MalformedKumoTemplateException
    {
        if (animatorTemplate.layers == null)
        {
            throw new MalformedKumoTemplateException("No layers were specified");
        }

        for (LayerTemplate template : animatorTemplate.layers)
        {
            layerStates.add(ILayerState.createFromTemplate(dataProvider, template));
        }
    }

    public void update(D entityData, float deltaTime) throws MalformedKumoTemplateException
    {
        // Populating the context.
        context.entityData = entityData;

        for (ILayerState layer : layerStates)
        {
            // Populating the context.
            context.layerState = layer;

            if (!started)
            {
                layer.start(context);
            }

            // Updating the layer.
            layer.update(context, deltaTime);
        }

        started = true;
    }

}
