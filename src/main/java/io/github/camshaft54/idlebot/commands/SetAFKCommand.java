package io.github.camshaft54.idlebot.commands;

import io.github.camshaft54.idlebot.util.DataValues;
import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import org.bukkit.entity.Player;

public class SetAFKCommand implements IdleBotCommand {
    @Override
    public String getCommandName() {
        return "afk";
    }

    @Override
    public void runCommand(Player player, String[] args) {
        if (args.length < 2) {
            // Say how command must contain another argument
            return;
        }
        if (!PersistentDataHandler.getBooleanData(player, DataValues.AUTO_AFK.key())) {
            if (args[1].equalsIgnoreCase("true")) {
                PersistentDataHandler.setData(player, DataValues.IS_SET_AFK.key(), true);
            } else if (args[1].equalsIgnoreCase("false")) {
                PersistentDataHandler.setData(player, DataValues.IS_SET_AFK.key(), false);
            } else {
                // Say how arg must be true/false
                return;
            }
        } else {
            // Say how you need to have afkmode set to manual to manually set afk
            return;
        }
    }
}
