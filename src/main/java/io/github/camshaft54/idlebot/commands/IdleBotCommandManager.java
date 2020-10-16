package io.github.camshaft54.idlebot.commands;

import io.github.camshaft54.idlebot.commands.idlebotsubcommands.LinkCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class IdleBotCommandManager implements CommandExecutor {

    ArrayList<IdleBotCommand> idleBotCommands = new ArrayList<>();

    public IdleBotCommandManager() {
        idleBotCommands.add(new LinkCommand());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String str, String[] args) {
        if (!(commandSender instanceof Player)) {
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
