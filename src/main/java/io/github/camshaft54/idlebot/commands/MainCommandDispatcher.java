package io.github.camshaft54.idlebot.commands;

import io.github.camshaft54.idlebot.IdleBot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommandDispatcher implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String str, String[] args) {
        if (!(commandSender instanceof Player)) {
            return true;
        }
        Player player = (Player) commandSender;
        // Execute link command
        if (args[0].toLowerCase().equals("link")) {
            LinkCommand link = new LinkCommand();
            link.generateLinkToken(player);
        }
        return false;
    }
}
