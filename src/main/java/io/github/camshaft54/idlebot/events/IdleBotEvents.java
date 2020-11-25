package io.github.camshaft54.idlebot.events;

import io.github.camshaft54.idlebot.DiscordAPIManager;
import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.PersistentDataHandler;
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
        Location from = e.getFrom();
        Location to = e.getTo();
        //boolean difOrien = from.getPitch() != to.getPitch() || from.getYaw() != to.getYaw();
        boolean difMove = from.getX() - to.getX() + from.getY() - to.getY() + from.getZ() - to.getZ() != 0;
        boolean difDir = from.getDirection().equals(to.getDirection());
        if (difDir || difMove) {
            Player player = e.getPlayer();
            // If player is not moving
            Bukkit.getLogger().info(player.getDisplayName() + " stopped being idle.");
            // Set all idle hashmaps to false
            IdleChecker.playersIdling.put(player, 0);
            isDamaged.put(player, false);
            IdleChecker.isFull.put(player, false);
            IdleChecker.atExpLevel.put(player, false);
            IdleChecker.atLocation.put(player, false);
        }
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

    // sends player a message on Discord, if player has linked account
    public static void sendPlayerMessage(Player player, String message) {
        String discordId = PersistentDataHandler.getStringData(player, "discordId");
        if (discordId != null)
            DiscordAPIManager.channel.sendMessage("<@!" + discordId + "> " + message);
    }

    // If player has died, send them a message
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        if (IdleChecker.playersIdling.get(player) >= IdleBot.idleTime) {
            Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle and dead!");
            sendPlayerMessage(player, player.getDisplayName() + " died at " + locationCleanup(player.getLocation()) + " (" + e.getDeathMessage() + ").");
        }
    }

    // If player was damaged, send them a message
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (IdleChecker.playersIdling.get(player) >= IdleBot.idleTime && !isDamaged.get(player)) {
                Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle and taking damage!");
                sendPlayerMessage(player, player.getDisplayName() + " is taking damage " + " (" + e.getCause().name() + ").");
                isDamaged.put(player, true);
            }
        }
    }
}

