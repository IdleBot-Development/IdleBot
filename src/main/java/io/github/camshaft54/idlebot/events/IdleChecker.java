package io.github.camshaft54.idlebot.events;

import io.github.camshaft54.idlebot.IdleBot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class IdleChecker implements Runnable {

    public static HashMap<Player, Integer> playersIdling = new HashMap<>();
    public static HashMap<Player, Boolean> isFull = new HashMap<>();
    public static HashMap<Player, Boolean> atExpLevel = new HashMap<>();

    public void run() {
        IdleBot.getUsers();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!IdleBot.users.containsKey(player.getUniqueId().toString())) {
                continue;
            }
            if (playersIdling.containsKey(player)) { // If player is already in the hashmap
                playersIdling.put(player, playersIdling.get(player) + 1); // Add 1 to the player's value
                Bukkit.getLogger().info("player had been idle for " + playersIdling.get(player));
            } else { // If not
                // Put new player to the hashmap with value 1 (1 second)
                playersIdling.put(player, 0);
                IdleBotEvents.isDamaged.put(player, false);
                isFull.put(player, false);
                atExpLevel.put(player, false);
                Bukkit.getLogger().info("A new player joined");
            }
            if (playersIdling.get(player) == IdleBot.idleTime) { // If player has been idle for time specified in config
                player.sendMessage("You are idle!!!");
                Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle!!!");
            }
        }
        Bukkit.getLogger().info("[IdleBot]: " + "Hashmap:" + playersIdling.keySet().toString() + "Values: " + playersIdling.values().toString());
        inventoryFull();
        xpLevel();
    }

    private static void inventoryFull() {
        for (Player player : playersIdling.keySet()) {
            String playerId = player.getUniqueId().toString();
            String discordId = IdleBot.users.get(playerId).getDiscordId();
            if (playersIdling.get(player) >= IdleBot.idleTime && player.getInventory().firstEmpty() < 0 && discordId != null && !isFull.get(player)) {
                Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle and their inventory is full!");
                IdleBot.channel.sendMessage("<@!" + discordId + "> " + player.getDisplayName() + "'s inventory is full! ");
                isFull.put(player, true);
            }
        }
    }

    private static void xpLevel() {
        for (Player player : playersIdling.keySet()) {
            String playerId = player.getUniqueId().toString();
            String discordId = IdleBot.users.get(playerId).getDiscordId();
            //TODO: Replace "10" in if statement with variable for desired Exp level set by player
            if (playersIdling.get(player) >= IdleBot.idleTime && player.getLevel() == 10 && discordId != null && !atExpLevel.get(player)) {
                Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle and at the desired XP level!");
                IdleBot.channel.sendMessage("<@!" + discordId + "> " + player.getDisplayName() + " is at the desired XP level! ");
                atExpLevel.put(player, true);
            }
        }
    }
}
