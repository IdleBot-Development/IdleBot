package io.github.camshaft54.idlebot.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static io.github.camshaft54.idlebot.events.EventsUtil.isPlayerIdle;
import static io.github.camshaft54.idlebot.events.EventsUtil.sendPlayerMessage;

public class InventoryFull {
    public static HashMap<Player, Boolean> isFull = new HashMap<>();

    // Checks if players inventory is full and sends them a message if it is
    public static void inventoryFull() {
        for (Player player : IdleChecker.playersIdling.keySet()) {
            if (isPlayerIdle(player) && player.getInventory().firstEmpty() < 0 && !isFull.get(player)) {
                Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle and their inventory is full!");
                sendPlayerMessage(player, player.getDisplayName() + "'s inventory is full! ");
                isFull.put(player, true);
            }
        }
    }
}
