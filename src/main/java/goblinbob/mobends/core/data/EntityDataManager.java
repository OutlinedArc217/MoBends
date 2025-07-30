package goblinbob.mobends.core.data;

import goblinbob.mobends.core.math.Transform;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityDataManager {
    private static final EntityDataManager INSTANCE = new EntityDataManager();
    private final Map<Integer, EntityData<?>> entityDataMap;

    private EntityDataManager() {
        this.entityDataMap = new ConcurrentHashMap<>();
    }

    public static EntityDataManager getInstance() {
        return INSTANCE;
    }

    public <T extends Entity> void registerData(T entity, EntityData<T> data) {
        entityDataMap.put(entity.getId(), data);
    }

    public <T extends Entity> void unregisterData(T entity) {
        entityDataMap.remove(entity.getId());
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> EntityData<T> getData(T entity) {
        return (EntityData<T>) entityDataMap.get(entity.getId());
    }

    public void updateAll(float partialTicks) {
        entityDataMap.values().forEach(data -> data.update(partialTicks));
    }

    public void cleanUp() {
        entityDataMap.entrySet().removeIf(entry -> {
            Entity entity = entry.getValue().getEntity();
            return entity == null || !entity.isAlive();
        });
    }

    public static abstract class EntityData<T extends Entity> implements INBTSerializable<CompoundTag> {
        protected final T entity;
        protected final Transform transform;
        protected boolean dirty;

        protected EntityData(T entity) {
            this.entity = entity;
            this.transform = new Transform();
            this.dirty = true;
        }

        public abstract void update(float partialTicks);
        public abstract void updateClient(float partialTicks);
        public abstract void reset();

        public T getEntity() {
            return entity;
        }

        public Transform getTransform() {
            return transform;
        }

        public boolean isDirty() {
            return dirty;
        }

        public void setDirty(boolean dirty) {
            this.dirty = dirty;
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag tag = new CompoundTag();
            transform.save(tag);
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag tag) {
            transform.load(tag);
        }
    }
      }
