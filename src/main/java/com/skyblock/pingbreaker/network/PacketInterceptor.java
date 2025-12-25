package com.skyblock.pingbreaker.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.play.client.C07PacketPlayerDigging;

public class PacketInterceptor extends ChannelOutboundHandlerAdapter {

    private final PacketInterceptorCallback callback;

    public PacketInterceptor(PacketInterceptorCallback callback) {
        this.callback = callback;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof C07PacketPlayerDigging) {
            C07PacketPlayerDigging diggingPacket = (C07PacketPlayerDigging) msg;
            // Call callback so you can decide what to do with this packet
            if (!callback.onDiggingPacket(diggingPacket)) {
                // If callback returns false, cancel sending this packet by just returning here
                return;
            }
        }
        super.write(ctx, msg, promise);
    }

    public interface PacketInterceptorCallback {
        // Return true to let packet go through, false to cancel it
        boolean onDiggingPacket(C07PacketPlayerDigging packet);
    }
}