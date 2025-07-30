package goblinbob.mobends.core.module;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public interface IModule {
    default void initialize() {}
    
    default CompletableFuture<Void> loadResources(Executor executor) {
        return CompletableFuture.completedFuture(null);
    }

    default void onRefresh() {}
    
    default void onLoadComplete(FMLLoadCompleteEvent event) {}
    
    interface Factory {
        IModule create();
    }
}
