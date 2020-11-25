package io.github.camshaft54.idlebot.events;

import io.github.camshaft54.idlebot.PersistentDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class IdleChecker implements Runnable {

    public static HashMap<Player, Integer> playersIdling = new HashMap<>();
    public static HashMap<Player, Boolean> isFull = new HashMap<>();
    public static HashMap<Player, Boolean> atExpLevel = new HashMap<>();
    public static HashMap<Player, Boolean> atLocation = new HashMap<>();

    // Checks if players inventory is full and sends them a message if it is
    private static void inventoryFull() {
        for (Player player : playersIdling.keySet()) {
            if (IdleChecker.isPlayerIdle(player) && player.getInventory().firstEmpty() < 0 && !isFull.get(player)) {
                Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle and their inventory is full!");
                IdleBotEvents.sendPlayerMessage(player, player.getDisplayName() + "'s inventory is full! ");
                isFull.put(player, true);
            }
        }
    }

    // Checks if player has reached a certain xp level and sends them a message if they have
    private static void xpLevel() {
        for (Player player : playersIdling.keySet()) {
            //TODO: Replace "10" in if statement with variable for desired xp level set by player
            if (IdleChecker.isPlayerIdle(player) && player.getLevel() == 10 && !atExpLevel.get(player)) {
                Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle and at the desired XP level!");
                IdleBotEvents.sendPlayerMessage(player, player.getDisplayName() + " is at the desired XP level! ");
                atExpLevel.put(player, true);
            }
        }
    }

    // Checks if a player has reached a certain location and sends them a message if they have
    private static void locationReached() {
        for (Player player : playersIdling.keySet()) {
            // TODO: Replace "100"s with the XYZ coordinates with variable for desired location set by player
            boolean reachedLocation = player.getLocation().getBlockX() == 100 && player.getLocation().getBlockY() == 100 && player.getLocation().getBlockZ() == 100;
            if (IdleChecker.isPlayerIdle(player) && reachedLocation && !atLocation.get(player)) {
                Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle and they reached their desired location!");
                IdleBotEvents.sendPlayerMessage(player, player.getDisplayName() + "'s reached the desired location! ");
                atLocation.put(player, true);
            }
        }
    }

    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (PersistentDataHandler.getStringData(player, "discordId") != null) {
                if (playersIdling.containsKey(player)) { // If player is already in the hashmap
                    playersIdling.put(player, playersIdling.get(player) + 1); // Add 1 to the player's value
                } else { // If not
                    // Put new player into the playersIdling hashmap with value 0 (0 seconds)
                    playersIdling.put(player, 0);
                    // Add player to the various condition hashmaps
                    IdleBotEvents.isDamaged.put(player, false);
                    isFull.put(player, false);
                    atExpLevel.put(player, false);
                    atLocation.put(player, false);
                }
                if (IdleChecker.isPlayerIdle(player)) { // If player has been idle for time specified in config
                    Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle.");
                }
            }
        }
        // Run methods to check for certain conditions
        inventoryFull();
        xpLevel();
        locationReached();
        // For debugging purposes
        // Bukkit.getLogger().info("[IdleBot]: " + Arrays.toString(playersIdling.keySet().toArray()) + ", " + Arrays.toString(playersIdling.values().toArray()));
    }

    public static boolean isPlayerIdle(Player player) {
        int time = playersIdling.get(player);
        String afkmode = PersistentDataHandler.getStringData(player, "afkmode");
        int afktime = PersistentDataHandler.getIntData(player, "afktime");
        // For debugging purposes
        //Bukkit.getLogger().info("Afkmode: " + afkmode + " Afktime: " + afktime + " Time: " + time + " ID: " + PersistentDataHandler.getStringData(player, "discordID"));
        if (afkmode == null) {
            return false;
        }
        return afkmode.equals("manual") || afktime <= time;
    }
}
