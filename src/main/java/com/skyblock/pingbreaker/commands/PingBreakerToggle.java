package com.skyblock.pingbreaker.commands;

import com.skyblock.pingbreaker.utils.PingBreakerMessages;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.Collections;
import java.util.List;

public class PingBreakerToggle extends CommandBase {
    private static boolean enabled = false;
    private static final List<String> aliases = Collections.singletonList("pb");

    public static boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getCommandName() {
        return "pingbreaker";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] args) throws CommandException {
        if(args.length == 0) {
            return;
        }

        if(args[0].equals("toggle")) {
            enabled = !enabled;
            String message = "toggled §aon";

            if(!enabled) {
                message = "toggled §coff";
            }
            PingBreakerMessages.printInfo(message);
        }
    }

    @Override
    public List<String> getCommandAliases() {
        return aliases;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0; // 0 = no permission required
    }
}
