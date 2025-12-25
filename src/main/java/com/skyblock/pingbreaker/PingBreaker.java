package com.skyblock.pingbreaker;

import com.skyblock.pingbreaker.commands.PingBreakerToggle;
import com.skyblock.pingbreaker.network.NetworkInterceptor;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = PingBreaker.MODID, name = PingBreaker.NAME, version = PingBreaker.VERSION)
public class PingBreaker {
    public static final String MODID = "kuhiw5g4ukizw4g53zuikw54gzuk";
    public static final String NAME = "asojhfajsfhdjaksdfhojaiksdfh";
    public static final String VERSION = "1.0";

    private NetworkInterceptor interceptor;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new NetworkInterceptor());
        ClientCommandHandler.instance.registerCommand(new PingBreakerToggle());
    }
}