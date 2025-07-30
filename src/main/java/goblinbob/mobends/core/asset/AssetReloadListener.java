package goblinbob.mobends.core.asset;

import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.resource.ResourceManagerHelper;
import net.minecraftforge.resource.VanillaResourceType;
import goblinbob.mobends.standard.main.ModStatics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class AssetReloadListener implements ResourceManagerReloadListener {
    private static final Gson GSON = new GsonBuilder().create();
    private final AssetsModule module;

    public AssetReloadListener(AssetsModule module) {
        this.module = module;
        ResourceManagerHelper.get(VanillaResourceType.CLIENT_RESOURCES)
            .registerReloadListener(this);
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        reload(resourceManager, Runnable::run);
    }

    public CompletableFuture<Void> reload(ResourceManager manager, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            module.clearAssets();
            
            manager.listResources("mobends/animations", 
                location -> location.getPath().endsWith(".json"))
                .forEach((location, resource) -> {
                    try (var reader = resource.openAsReader()) {
                        AnimationData animation = GSON.fromJson(reader, AnimationData.class);
                        module.registerAnimation(getAnimationId(location), animation);
                    } catch (Exception e) {
                        ModStatics.LOGGER.error("Failed to load animation: " + location, e);
                    }
                });

            return null;
        }, executor);
    }

    private static ResourceLocation getAnimationId(ResourceLocation fullPath) {
        String path = fullPath.getPath();
        String name = path.substring(path.lastIndexOf('/') + 1, path.length() - 5);
        return new ResourceLocation(ModStatics.MODID, name);
    }
}
