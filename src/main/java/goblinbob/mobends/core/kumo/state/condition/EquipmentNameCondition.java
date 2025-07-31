/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: EquipmentNameCondition.java
 */

package goblinbob.mobends.core.kumo.state.condition;

import goblinbob.mobends.core.kumo.state.template.TriggerConditionTemplate;
import net.minecraft.world.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.world.item.ItemStack;

/**
 *
 * @author Iwo Plaza
 */
public class EquipmentNameCondition implements ITriggerCondition
{

    private String namePattern;
    private EntityEquipmentSlot slot;

    public EquipmentNameCondition(Template template)
    {
        this.namePattern = template.namePattern;
        this.slot = template.slot;
    }

    @Override
    public boolean isConditionMet(ITriggerConditionContext context)
    {
        Entity entity = context.getEntityData().getEntity();

        if (entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            ItemStack itemStack = player.getItemStackFromSlot(this.slot);
            return itemStack.getDisplayName().matches(namePattern);
        }

        return false;
    }

    public static class Template extends TriggerConditionTemplate
    {

        public String namePattern;
        public EntityEquipmentSlot slot;

    }

}
