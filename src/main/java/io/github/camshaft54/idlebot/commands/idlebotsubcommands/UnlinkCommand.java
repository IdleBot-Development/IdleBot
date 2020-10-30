package io.github.camshaft54.idlebot.commands.idlebotsubcommands;

import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.PersistentDataHandler;
import io.github.camshaft54.idlebot.commands.IdleBotCommand;
import org.bukkit.entity.Player;

public class UnlinkCommand extends IdleBotCommand {
    @Override
    public String getCommandName() {
        return "unlink";
    }

    @Override
    public void runCommand(Player player, String[] args) {
        if (!playerIsLinked(player)) {
            // Send a blurb about how they aren't linked so they can't unlink
            return;
        }
        PersistentDataHandler.removeData(player, "discordID");
    }

    private boolean playerIsLinked(Player player) {
        return PersistentDataHandler.getStringData(player, "discordID") != null; // Returns true if the player already has an account linked
    }
}
