/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: MessageConfigResponse.java
 */

package goblinbob.mobends.core.network.msg;

import goblinbob.mobends.core.Core;
import goblinbob.mobends.core.network.NetworkForgeConfigSpec;
import goblinbob.mobends.core.network.SharedProperty;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.common.ForgeConfigSpec;

/**
 * This message is sent by the server to a client as a response
 * to a {@link MessageConfigRequest} message.
 */
public class MessageConfigResponse implements IMessage
{

    /**
     * Necessary empty constructor, because of dynamic instancing.
     */
    public MessageConfigResponse() {}

    @Override
    public void toBytes(ByteBuf buf)
    {
        CompoundTag tag = new CompoundTag();

        NetworkForgeConfigSpec.instance.getSharedConfig().save(tag);

        ByteBufUtils.writeTag(buf, tag);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        CompoundTag tag = ByteBufUtils.readTag(buf);
        if (tag == null)
        {
            Core.LOG.severe("An error occurred while receiving server configuration.");
            return;
        }

        NetworkForgeConfigSpec.instance.getSharedConfig().load(tag);
    }

    public static class Handler implements IMessageHandler<MessageConfigResponse, IMessage>
    {

        @Override
        public IMessage onMessage(MessageConfigResponse message, MessageContext ctx)
        {
            final StringBuilder builder = new StringBuilder("Received Mo' Bends server configuration.\n");
            final Iterable<SharedProperty<?>> properties = NetworkForgeConfigSpec.instance.getSharedConfig().getProperties();
            for (SharedProperty<?> property : properties)
            {
                builder.append(String.format(" - %s: %b\n", property.getKey(), property.getValue()));
            }
            Core.LOG.info(builder.toString());

            return null;
        }

    }

}