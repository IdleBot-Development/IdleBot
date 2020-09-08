package io.github.camshaft54.idlebot.events;

import io.github.camshaft54.idlebot.IdleBot;
import org.bukkit.Bukkit;
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
            player.sendMessage("[IdleBot]: You are idle and dead!");
            Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle and dead!");
            IdleBot.channel.sendMessage("Player Died.");
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
}

