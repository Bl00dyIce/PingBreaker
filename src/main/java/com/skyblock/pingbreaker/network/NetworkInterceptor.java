package com.skyblock.pingbreaker.network;

import com.skyblock.pingbreaker.blacklist.BlockBlacklist;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class NetworkInterceptor {

    private boolean injected = false;
    private boolean delete = false;
    private BlockPos ghostPosition = null;

    @SubscribeEvent
    public void onTickEvent(TickEvent.ClientTickEvent event) {
        if (delete && event.phase == TickEvent.Phase.START) {

            Minecraft mc = Minecraft.getMinecraft();
            World world = mc.theWorld;
            if(world == null) return;

            if(ghostPosition != null) {
                if(mc.gameSettings.keyBindAttack.isKeyDown()) {
                    MovingObjectPosition mop = mc.objectMouseOver;
                    if(mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && mop.getBlockPos().equals(ghostPosition)) {
                        mc.addScheduledTask(() -> {
                            world.setBlockToAir(ghostPosition);
                        });
                    }
                }

                ghostPosition = null;
                delete = false;
            }
        }
    }

    @SubscribeEvent
    public void onClientConnected(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        inject(event.manager);
    }

    @SubscribeEvent
    public void onClientDisconnected(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        injected = false;
    }

    public void inject(NetworkManager networkManager) {
        if (injected || networkManager == null) return;

        Channel channel = networkManager.channel();
        if (channel == null) return;

        ChannelPipeline pipeline = channel.pipeline();
        if (pipeline.get("pingbreaker_packet_interceptor") != null) return;

        pipeline.addBefore("packet_handler", "pingbreaker_packet_interceptor", new PacketInterceptor(packet -> {
            // Intercept block digging packets
            if(!delete) {
                if (packet != null) {

                    if (packet.getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
                        Minecraft mc = Minecraft.getMinecraft();
                        EntityPlayer player = mc.thePlayer;
                        if (player == null) return true;

                        ItemStack heldItem = player.getHeldItem();

                        if (heldItem != null && (heldItem.getDisplayName().equals("§cDungeonbreaker") && mc.thePlayer.getActivePotionEffects().stream().noneMatch(potionEffect -> potionEffect.getPotionID() == 4))) {
                            World world = mc.theWorld;

                            if (world != null) {
                                BlockPos pos = packet.getPosition();
                                if (BlockBlacklist.isBlacklisted( world.getBlockState(pos).getBlock())) return true;
                                delete = true;
                                ghostPosition = pos;
                            }
                        }
                    }
                }
            }


            return true; // allow the packet through
        }));

        injected = true;
    }
}
