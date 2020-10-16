package io.github.camshaft54.idlebot.commands.idlebotsubcommands;

import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.User;
import io.github.camshaft54.idlebot.commands.IdleBotCommand;
import org.bukkit.entity.Player;

import java.util.Random;

public class LinkCommand extends IdleBotCommand {

    @Override
    public String getCommandName() {
        return "link";
    }

    @Override
    public void runCommand(Player player, String[] args) {
        if (playerIsLinked(player.getUniqueId().toString())) {
            // Send a blurb about they are already linked and they need to /unilnk if they want to relink or something
            return;
        }
        Random rng = new Random();
        String botName = IdleBot.bot.getDiscriminatedName();
        int code = rng.nextInt(999); // Generate a random token
        while (isDuplicateToken(code)) { // Keep getting new codes until it is unique (very unlikely to ever run)
            code = rng.nextInt(999);
        }
        player.sendMessage("Your link code is " + code + ". Send this code in a private message to " + botName + " to link your account.");
        IdleBot.users.put(player.getUniqueId().toString(), new User(player.getDisplayName(), player.getUniqueId().toString(), code));
        IdleBot.saveUsers();
    }

    private boolean isDuplicateToken(int code) {
        for (User eachUser : IdleBot.users.values()) {
            if (eachUser.getCode() == code) return true;
        }
        return false;
    }

    private boolean playerIsLinked(String UUID) {
        for (String eachUUID : IdleBot.users.keySet()) {
            if (eachUUID.equalsIgnoreCase(UUID)) {
                return true;
            }
        }
        return false;
    }
}
