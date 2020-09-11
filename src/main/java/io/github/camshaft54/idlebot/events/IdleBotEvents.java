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

import java.util.HashMap;


public class IdleBotEvents implements Listener {
    public static HashMap<Player, Boolean> isDamaged = new HashMap<>();

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        String playerId = player.getUniqueId().toString();
        if (IdleChecker.idlePlayers.contains(player) && IdleBot.users.containsKey(playerId)) {
            String discordId = IdleBot.users.get(playerId).getDiscordId();
            if (discordId != null) {
                Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle and dead!");
                IdleBot.channel.sendMessage("<@!" + discordId + "> " + player.getDisplayName() + " died at " + locationCleanup(player.getLocation()) + " (" + e.getDeathMessage() + ").");
            }
        } else {
            Bukkit.getLogger().info("some player died.");
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (IdleChecker.idlePlayers.contains(player)) {
                isDamaged.put(player, true);
                Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle and taking damage!");
                IdleBot.channel.sendMessage(player.getDisplayName() + " is taking damage " + " (" + e.getCause().name() + ").");
            }
        }
    }

    @EventHandler
    public static void onMovement(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Bukkit.getLogger().info(player.getDisplayName() + " stopped being idle.");
        IdleChecker.playersIdling.put(player, 0);
        isDamaged.put(player, false);
    }

    private static String locationCleanup(Location l) {
        return "(" + Math.round(l.getX()) + ", " + Math.round(l.getY()) + ", " + Math.round(l.getZ()) + ")";
    }
}

