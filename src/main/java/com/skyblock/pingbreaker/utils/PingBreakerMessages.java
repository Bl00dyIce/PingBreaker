package com.skyblock.pingbreaker.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class PingBreakerMessages {
    public static void printInfo(String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(
            new ChatComponentText(String.format("§b[Pingbreaker] §f%s", message))
        );
    }
}
