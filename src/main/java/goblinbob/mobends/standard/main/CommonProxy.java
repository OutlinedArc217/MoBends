package goblinbob.mobends.standard.main;

import goblinbob.mobends.core.Core;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.eventbus.api.IEventBus;

public class CommonProxy
{
    protected void setup(final FMLCommonSetupEvent event)
    {
        // Common setup code
    }

    protected void loadComplete(final FMLLoadCompleteEvent event)
    {
        // Load complete code
    }

    protected void enqueueIMC(final InterModProcessEvent event)
    {
        // Inter-mod communication
    }

    public void initialize()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get() /* TODO: Verify this is still available */ /* TODO: Verify this is still available in your Forge version */.getModEventBus();
        
        // Register the setup methods
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::loadComplete);
        modEventBus.addListener(this::enqueueIMC);
        
        // Create core
        createCore();
    }

    public void createCore()
    {
        Core.createAsServer();
    }

    // Optional method for compatibility with older code during transition
    @Deprecated
    public void preInit() {
        // Deprecated - use setup() instead
    }

    @Deprecated
    public void init() {
        // Deprecated - use setup() instead
    }

    @Deprecated
    public void postInit() {
        // Deprecated - use loadComplete() instead
    }
}
