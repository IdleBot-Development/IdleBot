package io.github.camshaft54.idlebot.commands;

import io.github.camshaft54.idlebot.IdleBot;
import org.bukkit.entity.Player;

import java.util.Random;

public class LinkCommand {
    public void generateLinkToken(Player player) {
        Random rng = new Random();
        int token = rng.nextInt(999); // Generate a random token
        while (IdleBot.linkTokens.containsValue(token)) { // Keep getting new codes until it is unique (very unlikely to ever run)
            token = rng.nextInt(999);
        }
        IdleBot.linkTokens.put(player, token); // Put the generated token on the HashMap
    }
}
