package io.github.camshaft54.idlebot.events;

import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static io.github.camshaft54.idlebot.events.InventoryFull.inventoryFull;
import static io.github.camshaft54.idlebot.events.LocationReached.locationReached;
import static io.github.camshaft54.idlebot.events.XpLevel.xpLevel;

public class IdleChecker implements Runnable {
    public static HashMap<Player, Integer> playersIdling = new HashMap<>();

    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (PersistentDataHandler.getStringData(player, "discordId") != null) {
                if (playersIdling.containsKey(player)) { // If player is already in the hashmap
                    playersIdling.put(player, playersIdling.get(player) + 1); // Add 1 to the player's value
                } else { // If not
                    // Put new player into the playersIdling hashmap with value 0 (0 seconds)
                    playersIdling.put(player, 0);
                    // Add player to the various condition hashmaps
                    OnDamage.isDamaged.put(player, false);
                    InventoryFull.isFull.put(player, false);
                    XpLevel.atExpLevel.put(player, false);
                    LocationReached.atLocation.put(player, false);
                }
                if (EventsUtil.isPlayerIdle(player)) { // If player has been idle for time specified in config
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
}
