package io.github.camshaft54.idlebot.events;

import io.github.camshaft54.idlebot.IdleBot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;


public class IdleBotEvents implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        if (IdleChecker.idlePlayers.contains(player)) {
            player.sendMessage("[IdleBot]: You were idle and died!");
            Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " was idle and died!");
            IdleBot.channel.sendMessage(player.getDisplayName() + " died at " + locationCleanup(player.getLocation()) + " (" + e.getDeathMessage() + ").");
        }
        else {
            Bukkit.getLogger().info("some player died.");
        }
    }

    @EventHandler
    public static void onMovement(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Bukkit.getLogger().info(player.getDisplayName() + " stopped being idle.");
        IdleChecker.playersIdle.put(player,0);
    }

    public static String locationCleanup(Location l) {
        return "(" + Math.round(l.getX()) + ", " + Math.round(l.getY()) + ", " + Math.round(l.getZ()) + ")";
    }
}

