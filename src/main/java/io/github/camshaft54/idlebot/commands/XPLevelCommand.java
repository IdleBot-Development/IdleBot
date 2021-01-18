package io.github.camshaft54.idlebot.commands;

import io.github.camshaft54.idlebot.util.CommandUtils;
import io.github.camshaft54.idlebot.util.enums.DataValues;
import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import org.bukkit.entity.Player;

public class XPLevelCommand implements IdleBotCommand {
    @Override
    public String getCommandName() {
        return "xplevel";
    }

    @Override
    public String getCommandUsage() {
        return "/idlebot xplevel <desired xp level>";
    }

    @Override
    public boolean runCommand(Player player, String[] args) {
        if (args.length >= 2 && CommandUtils.isInteger(args[1]) && Integer.parseInt(args[1]) > 0) {
            PersistentDataHandler.setData(player, DataValues.EXPERIENCE_LEVEL_DESIRED.key(), Integer.parseInt(args[1]));
            return true;
        }
        return false;
    }
}
