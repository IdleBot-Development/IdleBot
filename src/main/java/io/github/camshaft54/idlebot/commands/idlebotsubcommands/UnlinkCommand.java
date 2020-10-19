package io.github.camshaft54.idlebot.commands.idlebotsubcommands;

import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.commands.IdleBotCommand;
import org.bukkit.entity.Player;

public class UnlinkCommand extends IdleBotCommand {
    @Override
    public String getCommandName() {
        return "unlink";
    }

    @Override
    public void runCommand(Player player, String[] args) {
        if (!playerIsLinked(player.getUniqueId().toString())) {
            // Send a blurb about how they aren't linked so they can't unlink
            return;
        }
        IdleBot.pluginUsers.remove(player.getUniqueId().toString());
    }

    private boolean playerIsLinked(String UUID) {
        for (String eachUUID : IdleBot.pluginUsers.keySet()) {
            if (eachUUID.equalsIgnoreCase(UUID)) {
                return true;
            }
        }
        return false;
    }
}
