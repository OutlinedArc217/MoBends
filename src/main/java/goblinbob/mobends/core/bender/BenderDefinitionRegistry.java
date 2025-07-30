package goblinbob.mobends.core.bender;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import goblinbob.mobends.core.data.EntityDataManager.EntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BenderDefinitionRegistry {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().create();
    
    private final Map<EntityType<?>, BenderDefinition<?>> definitions;
    private final Map<EntityType<?>, EntityDataFactory<?>> dataFactories;

    private BenderDefinitionRegistry() {
        this.definitions = new HashMap<>();
        this.dataFactories = new HashMap<>();
    }

    public <T extends Entity> void register(EntityType<T> entityType, 
                                          BenderDefinition<T> definition,
                                          EntityDataFactory<T> dataFactory) {
        definitions.put(entityType, definition);
        dataFactories.put(entityType, dataFactory);
        LOGGER.info("Registered bender definition for entity type: {}", entityType.getDescriptionId());
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> Optional<BenderDefinition<T>> getDefinition(EntityType<T> entityType) {
        return Optional.ofNullable((BenderDefinition<T>) definitions.get(entityType));
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> Optional<EntityDataFactory<T>> getDataFactory(EntityType<T> entityType) {
        return Optional.ofNullable((EntityDataFactory<T>) dataFactories.get(entityType));
    }

    public void handleRegisterEvent(RegisterEvent event) {
        event.register(EntityType.class, helper -> {
            definitions.forEach((type, definition) -> {
                ResourceLocation registryName = EntityType.getKey(type);
                if (registryName != null) {
                    definition.onRegister(helper);
                }
            });
        });
    }

    public interface EntityDataFactory<T extends Entity> {
        EntityData<T> create(T entity);
    }

    public interface BenderDefinition<T extends Entity> {
        void onRegister(RegisterEvent.RegisterHelper<?> helper);
        void applyAnimations(T entity, float partialTicks);
    }

    private static final BenderDefinitionRegistry INSTANCE = new BenderDefinitionRegistry();
    
    public static BenderDefinitionRegistry getInstance() {
        return INSTANCE;
    }
    }
