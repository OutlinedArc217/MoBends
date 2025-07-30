package goblinbob.mobends.core;

import goblinbob.mobends.core.configuration.CoreConfig;
import goblinbob.mobends.core.module.IModule;
import goblinbob.mobends.core.network.NetworkConfiguration;
import goblinbob.mobends.core.network.NetworkHandler;
import goblinbob.mobends.standard.main.ModStatics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public abstract class Core<T extends CoreConfig> {
    private static Core<?> INSTANCE;
    public static final Logger LOGGER = LogManager.getLogger(ModStatics.MODID);
    private final Collection<IModule> modules = new ArrayList<>();
    private final NetworkHandler networkHandler;
    protected final IEventBus modEventBus;

    protected Core() {
        INSTANCE = this;
        modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        networkHandler = new NetworkHandler(new ResourceLocation(ModStatics.MODID, "main"));
        modEventBus.addListener(this::commonSetup);
        setupConfig();
    }

    protected void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            networkHandler.initialize();
            for (IModule module : modules) {
                module.initialize();
            }
        });
    }

    protected abstract void setupConfig();
    public abstract T getConfiguration();

    protected void registerModule(IModule module) {
        this.modules.add(module);
    }

    public void refreshModules() {
        for (IModule module : modules) {
            module.onRefresh();
        }
    }

    protected CompletableFuture<Void> loadResources(Executor executor) {
        return CompletableFuture.allOf(
            modules.stream()
                .map(module -> module.loadResources(executor))
                .toArray(CompletableFuture[]::new)
        );
    }

    public NetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Core<?>> T getInstance() {
        return (T) INSTANCE;
    }

    public static void createAsClient() {
        if (INSTANCE == null) INSTANCE = new CoreClient();
    }

    public static void createAsServer() {
        if (INSTANCE == null) INSTANCE = new CoreServer();
    }

    public static void saveConfiguration() {
        if (INSTANCE != null) INSTANCE.getConfiguration().save();
    }

    protected Collection<IModule> getModules() {
        return new ArrayList<>(modules);
    }
}
