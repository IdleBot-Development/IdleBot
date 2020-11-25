package io.github.camshaft54.idlebot.commands.idlebotsubcommands;

import io.github.camshaft54.idlebot.PersistentDataHandler;
import io.github.camshaft54.idlebot.commands.IdleBotCommand;
import org.bukkit.entity.Player;

public class SettingsCommand extends IdleBotCommand {

    @Override
    public String getCommandName() {
        return "settings";
    }

    @Override
    public void runCommand(Player player, String[] args) {
        if (args.length < 2) {
            // Information about the command
            return;
        }
        if (args[1].equalsIgnoreCase("afkmode")) {
            if (args.length < 3) {
                // Information about the setting
                return;
            }
            if (args[2].equalsIgnoreCase("manual")) {
                PersistentDataHandler.setData(player, "afkmode", "manual");
            } else if (args[2].equalsIgnoreCase("auto")) {
                PersistentDataHandler.setData(player, "afkmode", "auto");
                if (args.length >= 4) {
                    try {
                        if (Integer.parseInt(args[3]) >= 10 && Integer.parseInt(args[3]) <= 120) {
                            PersistentDataHandler.setData(player, "afktime", Integer.parseInt(args[3]));
                        } else {
                            // Say it has to be 10 seconds to 2 minutes
                            return;
                        }
                    } catch (NumberFormatException nfe) {
                        // Send the player message about needs to be "auto <number>"
                        return;
                    }
                }
            }
        }
        else if (args[1].equalsIgnoreCase("afktime")) {
            if (args.length < 3) {
                // Information about the setting
                return;
            }
            try {
                if (Integer.parseInt(args[2]) >= 10 && Integer.parseInt(args[2]) <= 120) {
                    PersistentDataHandler.setData(player, "afktime", Integer.parseInt(args[2]));
                } else {
                    // Say it has to be 10 seconds to 2 minutes
                    return;
                }
            } catch (NumberFormatException nfe) {
                // Send the player message about needs to be "<number>"
                return;
            }
        }
    }
}
