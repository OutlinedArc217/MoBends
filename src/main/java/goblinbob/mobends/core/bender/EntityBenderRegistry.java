package goblinbob.mobends.core.bender;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraftforge.registries.ForgeRegistries;
import goblinbob.mobends.core.configuration.CoreConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EntityBenderRegistry {
    public static final EntityBenderRegistry instance = new EntityBenderRegistry();
    
    private final Map<EntityType<?>, EntityBenderDefinition<?>> definitions;
    private final Map<EntityType<?>, BenderEntityData<?>> dataProviders;

    private EntityBenderRegistry() {
        this.definitions = new HashMap<>();
        this.dataProviders = new HashMap<>();
    }

    public <T extends Entity> void registerBender(EntityType<T> entityType, 
                                                EntityBenderDefinition<T> definition,
                                                BenderEntityData<T> dataProvider) {
        definitions.put(entityType, definition);
        dataProviders.put(entityType, dataProvider);
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> Optional<EntityBenderDefinition<T>> getDefinition(EntityType<T> entityType) {
        return Optional.ofNullable((EntityBenderDefinition<T>) definitions.get(entityType));
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> Optional<BenderEntityData<T>> getDataProvider(EntityType<T> entityType) {
        return Optional.ofNullable((BenderEntityData<T>) dataProviders.get(entityType));
    }

    public void applyConfiguration(CoreConfig config) {
        definitions.forEach((type, definition) -> {
            ResourceLocation registryName = ForgeRegistries.ENTITY_TYPES.getKey(type);
            if (registryName != null) {
                definition.applyConfig(config);
            }
        });
    }

    public void clearRegistries() {
        definitions.clear();
        dataProviders.clear();
    }
}
