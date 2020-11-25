package io.github.camshaft54.idlebot.commands.settings;

import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import io.github.camshaft54.idlebot.util.PlayerSettingSetter;
import org.bukkit.entity.Player;

public class AFKMode extends PlayerSettingSetter {
    @Override
    public String getSettingName() {
        return "afkmode";
    }

    @Override
    public void setSetting(Player player, String[] args) {
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
}
