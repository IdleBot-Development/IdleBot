package io.github.camshaft54.idlebot.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static io.github.camshaft54.idlebot.events.EventsUtil.isPlayerIdle;
import static io.github.camshaft54.idlebot.events.EventsUtil.sendPlayerMessage;

public class OnDeath implements Listener {
    // If player has died, send them a message
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        if (isPlayerIdle(player)) {
            Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle and dead!");
            sendPlayerMessage(player, player.getDisplayName() + " died at " + locationCleanup(player.getLocation()) + " (" + e.getDeathMessage() + ").");
        }
    }

    // Take player's location and return a clean string of coordinates
    private static String locationCleanup(Location l) {
        return "(" + Math.round(l.getX()) + ", " + Math.round(l.getY()) + ", " + Math.round(l.getZ()) + ")";
    }
}
