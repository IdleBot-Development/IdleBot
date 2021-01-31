package io.github.camshaft54.idlebot.commands;

import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.Messenger;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import io.github.camshaft54.idlebot.util.enums.DataValues;
import io.github.camshaft54.idlebot.util.enums.MessageLevel;
import org.bukkit.entity.Player;

public class ChannelCommand implements IdleBotCommand {
    @Override
    public String getCommandName() {
        return "channel";
    }

    @Override
    public String getCommandUsage() {
        return "/idlebot channel <public|private>";
    }

    @Override
    public boolean runCommand(Player player, String[] args) {
        if (args.length < 2) return false;
        if (args[1].equalsIgnoreCase("public")) {
            PersistentDataHandler.setData(player, DataValues.DIRECT_MESSAGE_MODE.key(), false);
            Messenger.sendMessage(player, "Set your alerts channel to public", MessageLevel.INFO);
            return true;
        } else if (args[1].equalsIgnoreCase("private")) {
            PersistentDataHandler.setData(player, DataValues.DIRECT_MESSAGE_MODE.key(), true);
            Messenger.sendMessage(player, "Set your alerts channel to private", MessageLevel.INFO);
            return true;
        }
        return false;
    }
}
