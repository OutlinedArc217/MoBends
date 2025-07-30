package goblinbob.mobends.core.animation.cache;

import goblinbob.mobends.core.math.matrix.IMat4x4d;
import net.minecraft.world.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AnimationCache {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final AnimationCache INSTANCE = new AnimationCache();
    
    private final Map<CacheKey, CachedAnimation> cache;
    private final Map<Entity, Float> lastUpdateTimes;

    private AnimationCache() {
        this.cache = new ConcurrentHashMap<>();
        this.lastUpdateTimes = new ConcurrentHashMap<>();
    }

    public static AnimationCache getInstance() {
        return INSTANCE;
    }

    public void cacheAnimation(Entity entity, String animationId, 
                             IMat4x4d transform, float time) {
        CacheKey key = new CacheKey(entity.getId(), animationId);
        cache.put(key, new CachedAnimation(transform, time));
        lastUpdateTimes.put(entity, time);
    }

    public CachedAnimation getCache(Entity entity, String animationId) {
        CacheKey key = new CacheKey(entity.getId(), animationId);
        return cache.get(key);
    }

    public void cleanup() {
        float currentTime = System.nanoTime() / 1_000_000_000.0f;
        lastUpdateTimes.entrySet().removeIf(entry -> {
            if (currentTime - entry.getValue() > 30.0f) {
                cache.keySet().removeIf(key -> key.entityId == entry.getKey().getId());
                return true;
            }
            return false;
        });
    }

    private record CacheKey(int entityId, String animationId) {}

    public static class CachedAnimation {
        public final IMat4x4d transform;
        public final float timestamp;

        public CachedAnimation(IMat4x4d transform, float timestamp) {
            this.transform = transform;
            this.timestamp = timestamp;
        }
    }
          }
