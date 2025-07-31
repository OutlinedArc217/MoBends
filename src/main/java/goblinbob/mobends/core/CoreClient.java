package goblinbob.mobends.core;

import goblinbob.mobends.core.asset.AssetReloadListener;
import goblinbob.mobends.core.asset.AssetsModule;
import goblinbob.mobends.core.bender.EntityBenderRegistry;
import goblinbob.mobends.core.client.event.*;
import goblinbob.mobends.core.configuration.CoreClientConfig;
import goblinbob.mobends.core.connection.ConnectionManager;
import goblinbob.mobends.core.env.EnvironmentModule;
import goblinbob.mobends.core.supporters.SupporterContent;
import goblinbob.mobends.core.pack.PackManager;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class CoreClient extends Core<CoreClientConfig> {
    private static CoreClient INSTANCE;
    private CoreClientConfig configuration;

    CoreClient() {
        INSTANCE = this;
        
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        modEventBus.addListener(this::clientSetup);
    
        registerModule(new EnvironmentModule.Factory());
        registerModule(new ConnectionManager.Factory());
        registerModule(new AssetsModule.Factory());
        registerModule(new SupporterContent.Factory());
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            initializeClient();
        });
    }

    private void initializeClient() {
        PackManager.INSTANCE.initialize(configuration);
        KeyboardHandler.initKeyBindings();

        MinecraftForge.EVENT_BUS.register(new EntityRenderHandler());
        MinecraftForge.EVENT_BUS.register(new DataUpdateHandler());
        MinecraftForge.EVENT_BUS.register(new KeyboardHandler());
        MinecraftForge.EVENT_BUS.register(new FluxHandler());
        MinecraftForge.EVENT_BUS.register(new WorldJoinHandler());
        
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.getResourceManager() instanceof ReloadableResourceManager resourceManager) {
            resourceManager.registerReloadListener(new AssetReloadListener());
        }

        MinecraftForge.EVENT_BUS.addListener(this::onClientTick);
    }

    private void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            updateClient();
        }
    }

    private void updateClient() {
    }

    @Override
    public void setupConfig() {
        configuration = new CoreClientConfig();
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.CLIENT, configuration.getSpec());
    }

    @Override
    public CoreClientConfig getConfiguration() {
        return configuration;
    }

    public void refreshModules() {
        super.refreshModules();
        EntityBenderRegistry.instance.applyConfiguration(configuration);
    }

    @Nullable
    public static CoreClient getInstance() {
        return INSTANCE;
    }
}
