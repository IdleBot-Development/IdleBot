package io.github.camshaft54.idlebot.commands;

import org.bukkit.entity.Player;

public abstract class IdleBotCommand {

    public abstract String getCommandName();

    public abstract  void runCommand(Player player, String[] args);
}
