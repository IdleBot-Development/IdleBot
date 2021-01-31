package io.github.camshaft54.idlebot.commands;

import io.github.camshaft54.idlebot.util.EventUtils;
import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.Messenger;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import io.github.camshaft54.idlebot.util.enums.DataValues;
import io.github.camshaft54.idlebot.util.enums.MessageLevel;
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
            Messenger.sendMessage(player, "Set " + player.getDisplayName() + "'s afk status to true", MessageLevel.INFO);
            return true;
        } else if (args.length >= 2 && args[1].equalsIgnoreCase("false")) {
            PersistentDataHandler.setData(player, DataValues.IS_SET_AFK.key(), false);
            EventUtils.clearPlayerIdleStats(player);
            Messenger.sendMessage(player, "Set " + player.getDisplayName() + "'s afk status to false", MessageLevel.INFO);
            return true;
        }
        return false;
    }
}
