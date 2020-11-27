package io.github.camshaft54.idlebot.commands.settings;

import io.github.camshaft54.idlebot.util.DataValues;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import io.github.camshaft54.idlebot.util.PlayerSettingSetter;
import org.bukkit.entity.Player;

public class DamageAlert extends PlayerSettingSetter {
    @Override
    public String getSettingName() {
        return "damage_alert";
    }

    @Override
    public void setSetting(Player player, String[] args) {
        if (args.length < 3) {
            // BLURB
            return;
        }
        if (args[2].equalsIgnoreCase("true")) {
            PersistentDataHandler.setData(player, DataValues.LOCATION_ALERT.key(), true);
        }
        else if (args[2].equalsIgnoreCase("false")) {
            PersistentDataHandler.setData(player, DataValues.LOCATION_ALERT.key(), false);
        }
    }
}
