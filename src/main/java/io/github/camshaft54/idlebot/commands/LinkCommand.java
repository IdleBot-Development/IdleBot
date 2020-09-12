package io.github.camshaft54.idlebot.commands;

import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.User;
import org.bukkit.entity.Player;

import java.util.Random;

public class LinkCommand {
    public void generateLinkToken(Player player) {
        Random rng = new Random();
        String botName = IdleBot.bot.getDiscriminatedName();
        int code = rng.nextInt(999); // Generate a random token
        while (checkForDuplicateToken(code)) { // Keep getting new codes until it is unique (very unlikely to ever run)
            code = rng.nextInt(999);
        }
        player.sendMessage("Your link code is " + code + ". Send this code in a private message to " + botName + " to link your account.");
        IdleBot.users.put(player.getUniqueId().toString(), new User(player.getDisplayName(), code));
        IdleBot.saveUsers();
    }

    private boolean checkForDuplicateToken(int code) {
        for (User eachUser : IdleBot.users.values()) {
            if (eachUser.getCode() == code) return true;
        }
        return false;
    }
}
