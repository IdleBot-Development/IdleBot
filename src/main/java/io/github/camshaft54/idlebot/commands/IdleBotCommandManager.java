package io.github.camshaft54.idlebot.commands;

import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.commands.idlebotsubcommands.LinkCommand;
import io.github.camshaft54.idlebot.commands.idlebotsubcommands.SettingsCommand;
import io.github.camshaft54.idlebot.commands.idlebotsubcommands.UnlinkCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class IdleBotCommandManager implements CommandExecutor {

    ArrayList<IdleBotCommand> idleBotCommands = new ArrayList<>();

    public IdleBotCommandManager() {
        idleBotCommands.add(new LinkCommand());
        idleBotCommands.add(new UnlinkCommand());
        idleBotCommands.add(new SettingsCommand());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String str, String[] args) {
        if (!(commandSender instanceof Player)) {
            IdleBot.getPlugin().getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "[IdleBot] This command can be run only by players!");
            return true;
        }
        Player player = (Player) commandSender;

        if (args.length <= 0) {
            // send an information "packet"
            return true;
        }
        for (IdleBotCommand eachCommand : idleBotCommands) {
            if (eachCommand.getCommandName().equalsIgnoreCase(args[0])) {
                eachCommand.runCommand(player, args);
                return true;
            }
        }
        return false;
    }
}
