package goblinbob.mobends.core.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import goblinbob.mobends.standard.main.ModStatics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class ResourceHelper {
    private static final Gson GSON = new GsonBuilder().create();

    public static ResourceLocation createLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(ModStatics.MODID, path);
    }

    public static CompletableFuture<Optional<Resource>> getResourceAsync(
            ResourceManager manager, ResourceLocation location, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return Optional.of(manager.getResource(location));
            } catch (Exception e) {
                ModStatics.LOGGER.warn("Failed to load resource: " + location, e);
                return Optional.empty();
            }
        }, executor);
    }

    public static <T> CompletableFuture<Optional<T>> loadJsonAsync(
            ResourceManager manager, 
            ResourceLocation location, 
            Class<T> type,
            Executor executor) {
        return getResourceAsync(manager, location, executor)
            .thenApplyAsync(optionalResource -> {
                if (optionalResource.isEmpty()) {
                    return Optional.empty();
                }

                try (Resource resource = optionalResource.get();
                     InputStream stream = resource.open();
                     BufferedReader reader = new BufferedReader(
                         new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                    return Optional.of(GSON.fromJson(reader, type));
                } catch (Exception e) {
                    ModStatics.LOGGER.error("Failed to parse JSON: " + location, e);
                    return Optional.empty();
                }
            }, executor);
    }

    public static void loadTextureAsync(
            ResourceLocation location, 
            Consumer<ResourceLocation> callback,
            Executor executor) {
        CompletableFuture.runAsync(() -> {
            try {
                callback.accept(location);
            } catch (Exception e) {
                ModStatics.LOGGER.error("Failed to load texture: " + location, e);
            }
        }, executor);
    }
                     }
