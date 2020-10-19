package io.github.camshaft54.idlebot.events;

import io.github.camshaft54.idlebot.IdleBot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;


public class IdleBotEvents implements Listener {
    public static HashMap<Player, Boolean> isDamaged = new HashMap<>();

    // If player has moved, reset playersIdling and the other condition hashmaps
    @EventHandler
    public static void onMovement(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Bukkit.getLogger().info(player.getDisplayName() + " stopped being idle.");
        IdleChecker.playersIdling.put(player, 0);
        isDamaged.put(player, false);
        IdleChecker.isFull.put(player, false);
        IdleChecker.atExpLevel.put(player, false);
        IdleChecker.atLocation.put(player, false);
    }

    // If player leaves game, remove them from playersIdling and the other condition hashmaps
    @EventHandler
    public static void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        IdleChecker.playersIdling.remove(player);
        IdleChecker.isFull.remove(player);
        IdleChecker.atExpLevel.remove(player);
        isDamaged.remove(player);
    }

    // Take player's location and return a clean string of coordinates
    private static String locationCleanup(Location l) {
        return "(" + Math.round(l.getX()) + ", " + Math.round(l.getY()) + ", " + Math.round(l.getZ()) + ")";
    }

    // If player has died, send them a message
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        String playerId = player.getUniqueId().toString();
        if (IdleChecker.playersIdling.get(player) >= IdleBot.idleTime) {
            String discordId = IdleBot.pluginUsers.get(playerId).getDiscordId();
            if (discordId != null) {
                Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle and dead!");
                IdleBot.channel.sendMessage("<@!" + discordId + "> " + player.getDisplayName() + " died at " + locationCleanup(player.getLocation()) + " (" + e.getDeathMessage() + ").");
            }
        }
    }

    // If player was damaged, send them a message
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            String playerId = player.getUniqueId().toString();
            if (IdleChecker.playersIdling.get(player) >= IdleBot.idleTime) {
                String discordId = IdleBot.pluginUsers.get(playerId).getDiscordId();
                if (discordId != null && !isDamaged.get(player)) {
                    Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle and taking damage!");
                    IdleBot.channel.sendMessage("<@!" + discordId + "> " + player.getDisplayName() + " is taking damage " + " (" + e.getCause().name() + ").");
                    isDamaged.put(player, true);
                }
            }
        }
    }
}

