package io.github.camshaft54.idlebot.commands.settings;

import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import io.github.camshaft54.idlebot.util.PlayerSettingSetter;
import org.bukkit.entity.Player;

public class AFKTime extends PlayerSettingSetter {
    @Override
    public String getSettingName() {
        return "afktime";
    }

    @Override
    public void setSetting(Player player, String[] args) {
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
