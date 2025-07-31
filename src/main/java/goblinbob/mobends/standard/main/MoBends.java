/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: MoBends.java
 */

package goblinbob.mobends.standard.main;

import goblinbob.mobends.core.Core;
import goblinbob.mobends.core.addon.AddonHelper;
import goblinbob.mobends.core.addon.Addons;
import goblinbob.mobends.core.animation.keyframe.AnimationLoader;
import goblinbob.mobends.core.asset.AssetReloadListener;
import goblinbob.mobends.core.bender.EntityBenderRegistry;
import goblinbob.mobends.core.data.EntityDatabase;
import goblinbob.mobends.core.pack.PackDataProvider;
import goblinbob.mobends.core.util.GsonResources;
import goblinbob.mobends.standard.DefaultAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.logging.Logger;
import net.minecraft.world.entity.Entity;

@Mod(modid = ModStatics.MODID)
public class MoBends
{
    @SidedProxy(serverSide = "goblinbob.mobends.standard.main.CommonProxy",
                clientSide = "goblinbob.mobends.standard.client.ClientProxy")
    public static CommonProxy proxy;

    @Instance(value = ModStatics.MODID)
    public static MoBends instance;

    public static final Logger LOG = Logger.getLogger(ModStatics.MODID);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.createCore();
        Core.getInstance().preInit(event);
        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        Core.getInstance().init(event);
        proxy.init();

        // Registering the standard set of animations.
        AddonHelper.registerAddon(ModStatics.MODID, new DefaultAddon());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        Core.getInstance().postInit(event);
        proxy.postInit();
    }

    /**
     * Used to refresh all systems, clear caches. Usually performed when configuration changes.
     */
    public static void refreshSystems()
    {
        AnimationLoader.clearCache();
        GsonResources.clearCache();
        PackDataProvider.INSTANCE.clearCache();
        EntityDatabase.instance.refresh();
        EntityBenderRegistry.instance.refreshMutators();
        Addons.onRefresh();

        Core.getInstance().refreshModules();
    }
}
