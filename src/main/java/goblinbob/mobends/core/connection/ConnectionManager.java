/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: ConnectionManager.java
 */

package goblinbob.mobends.core.connection;

import goblinbob.mobends.core.module.IModule;
import goblinbob.mobends.core.network.NetworkConfiguration;
import goblinbob.mobends.core.pack.PackManager;
import goblinbob.mobends.standard.main.ModStatics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;

public class ConnectionManager implements IModule {
    private static final String PROTOCOL_VERSION = ModStatics.PROTOCOL_VERSION;
    private final NetworkConfiguration networkConfig;

    public ConnectionManager(NetworkConfiguration networkConfig) {
        this.networkConfig = networkConfig;
    }

    @Override
    public void initialize() {
        registerHandlers();
    }

    private void registerHandlers() {
        networkConfig.NETWORK.messageBuilder(HandshakeMessage.class, 0, NetworkDirection.PLAY_TO_SERVER)
            .encoder(HandshakeMessage::encode)
            .decoder(HandshakeMessage::decode)
            .consumerMainThread(this::handleHandshake)
            .add();
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            sendHandshake(player);
        }
    }

    private void sendHandshake(ServerPlayer player) {
        HandshakeMessage message = new HandshakeMessage(PROTOCOL_VERSION);
        networkConfig.NETWORK.sendTo(message, player.connection.connection, NetworkDirection.PLAY_TO_SERVER);
    }

    private void handleHandshake(HandshakeMessage message, NetworkEvent.Context context) {
        if (!PROTOCOL_VERSION.equals(message.protocolVersion())) {
            String errorMessage = String.format("Protocol version mismatch! Server: %s, Client: %s", 
                PROTOCOL_VERSION, message.protocolVersion());
            context.enqueueWork(() -> {
                context.getSender().disconnect(Component.literal(errorMessage));
            });
        }
        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    public void onClientWorldJoin(ClientLevel world) {
        PackManager.INSTANCE.reloadPacks()
            .thenRun(() -> networkConfig.onWorldJoin());
    }

    public static class Factory implements IModule.Factory {
        @Override
        public IModule create() {
            return new ConnectionManager(NetworkConfiguration.instance);
        }
    }

    public record HandshakeMessage(String protocolVersion) {
        public void encode(FriendlyByteBuf buf) {
            buf.writeUtf(protocolVersion);
        }

        public static HandshakeMessage decode(FriendlyByteBuf buf) {
            return new HandshakeMessage(buf.readUtf());
        }
    }
}
