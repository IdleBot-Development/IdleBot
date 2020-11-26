package io.github.camshaft54.idlebot.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static io.github.camshaft54.idlebot.events.EventsUtil.isPlayerIdle;
import static io.github.camshaft54.idlebot.events.EventsUtil.sendPlayerMessage;

public class LocationReached {
    public static HashMap<Player, Boolean> atLocation = new HashMap<>();

    // Checks if a player has reached a certain location and sends them a message if they have
    public static void locationReached() {
        for (Player player : IdleChecker.playersIdling.keySet()) {
            // TODO: Replace "100"s with the XYZ coordinates with variable for desired location set by player
            boolean reachedLocation = player.getLocation().getBlockX() == 100 && player.getLocation().getBlockY() == 100 && player.getLocation().getBlockZ() == 100;
            if (isPlayerIdle(player) && reachedLocation && !atLocation.get(player)) {
                Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle and they reached their desired location!");
                sendPlayerMessage(player, player.getDisplayName() + "'s reached the desired location! ");
                atLocation.put(player, true);
            }
        }
    }
}
