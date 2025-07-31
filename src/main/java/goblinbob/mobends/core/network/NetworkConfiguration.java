package goblinbob.mobends.core.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import goblinbob.mobends.standard.main.ModStatics;
import java.util.concurrent.atomic.AtomicInteger;

public class NetworkConfiguration {
    private static final String PROTOCOL_VERSION = ModStatics.PROTOCOL_VERSION;
    public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(
        ResourceLocation.fromNamespaceAndPath(ModStatics.MODID, "network"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );
    
    private static final AtomicInteger packetId = new AtomicInteger(0);
    public static NetworkConfiguration instance = new NetworkConfiguration();

    private final SharedConfig sharedConfig = new SharedConfig();
    private final SharedProperty<Boolean> modelScalingAllowed;
    private final SharedProperty<Boolean> bendsPacksAllowed;
    private final SharedProperty<Boolean> movementLimited;

    public NetworkConfiguration() {
        sharedConfig.addProperty(modelScalingAllowed = new SharedBooleanProp(
                "modelScalingAllowed",
                false,
                "Does the server allow scaling of the player model more than the normal size?"));
        sharedConfig.addProperty(bendsPacksAllowed = new SharedBooleanProp(
                "bendsPacksAllowed",
                true,
                "Does the server allow the use of bends packs?"));
        sharedConfig.addProperty(movementLimited = new SharedBooleanProp(
                "movementLimited",
                true,
                "Does the server limit excessive bends pack transformation?"));
        
        registerPackets();
    }

    private void registerPackets() {
        // Register network packets
        NETWORK.registerMessage(
            nextId(),
            ConfigSyncPacket.class,
            ConfigSyncPacket::encode,
            ConfigSyncPacket::decode,
            ConfigSyncPacket::handle
        );
    }

    private static int nextId() {
        return packetId.getAndIncrement();
    }

    public void onWorldJoin() {
        boolean isSingleplayer = Minecraft.getInstance().isSingleplayer();
        this.modelScalingAllowed.setValue(isSingleplayer);
        this.bendsPacksAllowed.setValue(true);
        this.movementLimited.setValue(!isSingleplayer);
    }

    public SharedConfig getSharedConfig() {
        return sharedConfig;
    }

    public boolean isModelScalingAllowed() {
        return modelScalingAllowed.getValue();
    }

    public boolean areBendsPacksAllowed() {
        return bendsPacksAllowed.getValue();
    }

    public boolean isMovementLimited() {
        return movementLimited.getValue();
    }

    public static class ConfigSyncPacket {
        private final SharedConfig config;

        public ConfigSyncPacket(SharedConfig config) {
            this.config = config;
        }

        public static void encode(ConfigSyncPacket packet, FriendlyByteBuf buffer) {
            packet.config.serialize(buffer);
        }

        public static ConfigSyncPacket decode(FriendlyByteBuf buffer) {
            SharedConfig config = new SharedConfig();
            config.deserialize(buffer);
            return new ConfigSyncPacket(config);
        }

        public static void handle(ConfigSyncPacket packet, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> {
                NetworkConfiguration.instance.sharedConfig.applyFrom(packet.config);
            });
            context.get().setPacketHandled(true);
        }
    }
}
