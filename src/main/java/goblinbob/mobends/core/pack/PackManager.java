package goblinbob.mobends.core.pack;

import goblinbob.mobends.core.configuration.CoreConfig;
import goblinbob.mobends.core.data.IDataSyncable;
import goblinbob.mobends.standard.main.ModStatics;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class PackManager implements IDataSyncable {
    public static final PackManager INSTANCE = new PackManager();
    private static final Logger LOGGER = LogManager.getLogger();
    
    private final Map<ResourceLocation, BendsPack> loadedPacks;
    private final Map<ResourceLocation, PackState> packStates;
    private boolean initialized;

    private PackManager() {
        this.loadedPacks = new ConcurrentHashMap<>();
        this.packStates = new ConcurrentHashMap<>();
        this.initialized = false;
    }

    public void initialize(CoreConfig config) {
        if (initialized) return;
        initialized = true;

        loadDefaultPacks();
        applyConfig(config);
    }

    private void loadDefaultPacks() {
        ResourceLocation defaultPackId = new ResourceLocation(ModStatics.MODID, "default");
        BendsPack defaultPack = new BendsPack(defaultPackId);
        loadedPacks.put(defaultPackId, defaultPack);
        packStates.put(defaultPackId, PackState.ENABLED);
    }

    public CompletableFuture<Void> reloadPacks() {
        return CompletableFuture.runAsync(() -> {
            loadedPacks.clear();
            loadDefaultPacks();
            if (Minecraft.getInstance().getResourceManager() != null) {
                Minecraft.getInstance().reloadResourcePacks();
            }
        });
    }

    public Optional<BendsPack> getPack(ResourceLocation location) {
        return Optional.ofNullable(loadedPacks.get(location));
    }

    public PackState getPackState(ResourceLocation location) {
        return packStates.getOrDefault(location, PackState.DISABLED);
    }

    public void setPackState(ResourceLocation location, PackState state) {
        packStates.put(location, state);
    }

    public Collection<BendsPack> getLoadedPacks() {
        return Collections.unmodifiableCollection(loadedPacks.values());
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(packStates.size());
        packStates.forEach((location, state) -> {
            buffer.writeResourceLocation(location);
            buffer.writeEnum(state);
        });
    }

    @Override
    public void decode(FriendlyByteBuf buffer) {
        packStates.clear();
        int size = buffer.readVarInt();
        for (int i = 0; i < size; i++) {
            ResourceLocation location = buffer.readResourceLocation();
            PackState state = buffer.readEnum(PackState.class);
            packStates.put(location, state);
        }
    }

    public static void handleSync(PackManager packManager, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (context.getDirection().getReceptionSide().isClient()) {
                INSTANCE.packStates.clear();
                INSTANCE.packStates.putAll(packManager.packStates);
            }
        });
        context.setPacketHandled(true);
    }

    private void applyConfig(CoreConfig config) {
        // Apply configuration settings to packs
        loadedPacks.values().forEach(pack -> pack.applyConfig(config));
    }

    public enum PackState {
        ENABLED,
        DISABLED,
        SERVER_DISABLED
    }
}
