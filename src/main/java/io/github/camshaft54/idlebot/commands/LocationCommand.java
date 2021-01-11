package io.github.camshaft54.idlebot.commands;

import io.github.camshaft54.idlebot.util.IdleBotCommand;
import org.bukkit.entity.Player;

public class LocationCommand implements IdleBotCommand {
    @Override
    public String getCommandName() {
        return "location";
    }

    @Override
    public String getCommandUsage() {
        return "/idlebot location <x|z> <coordinate>";
    }

    @Override
    public boolean runCommand(Player player, String[] args) {
        if (args.length < 2) {
            return false;
        }
    }
}
