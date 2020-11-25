package io.github.camshaft54.idlebot.commands;

import io.github.camshaft54.idlebot.discord.DiscordAPIManager;
import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import org.bukkit.entity.Player;

import java.util.Random;

public class LinkCommand extends IdleBotCommand {

    @Override
    public String getCommandName() {
        return "link";
    }

    @Override
    public void runCommand(Player player, String[] args) {
        if (playerIsLinked(player)) {
            // Send a blurb about they are already linked and they need to /unlink if they want to relink or something
            return;
        }
        Random rng = new Random();
        String botName = DiscordAPIManager.bot.getDiscriminatedName();

        int code = rng.nextInt(999); // Generate a random token
        while (isDuplicateToken(code)) { // Keep getting new codes until it is unique (very unlikely to ever run)
            code = rng.nextInt(999);
        }
        player.sendMessage("Your link code is " + code + ". Send this code in a private message to " + botName + " to link your account.");
        IdleBot.linkCodes.put(code, player);
    }

    private boolean isDuplicateToken(int code) {
        for (int eachCode  : IdleBot.linkCodes.keySet()) {
            if (eachCode == code) return true;
        }
        return false;
    }

    private boolean playerIsLinked(Player player) {
        return PersistentDataHandler.getStringData(player, "discordID") != null; // Returns true if the player already has an account linked
    }
}
