package io.github.camshaft54.idlebot.commands;

import io.github.camshaft54.idlebot.util.EventUtils;
import io.github.camshaft54.idlebot.util.enums.DataValues;
import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import org.bukkit.entity.Player;

public class SetAFKCommand implements IdleBotCommand {
    @Override
    public String getCommandName() {
        return "afk";
    }

    @Override
    public String getCommandUsage() {
        return "/idlebot afk <true|false>";
    }

    @Override
    public boolean runCommand(Player player, String[] args) {
        if (args.length >= 2 && args[1].equalsIgnoreCase("true")) {
            PersistentDataHandler.setData(player, DataValues.IS_SET_AFK.key(), true);
            return true;
        } else if (args.length >= 2 && args[1].equalsIgnoreCase("false")) {
            PersistentDataHandler.setData(player, DataValues.IS_SET_AFK.key(), false);
            EventUtils.clearPlayerIdleStats(player);
            return true;
        }
        return false;
    }
}
