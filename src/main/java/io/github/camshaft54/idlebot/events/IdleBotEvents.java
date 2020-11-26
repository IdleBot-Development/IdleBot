package io.github.camshaft54.idlebot.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class IdleBotEvents implements Listener {

    // If player has moved, reset playersIdling and the other condition hashmaps
    // The
    @EventHandler
    public static void onMovement(PlayerMoveEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();
        assert to != null;
        boolean difMove = from.getX() - to.getX() + from.getY() - to.getY() + from.getZ() - to.getZ() != 0;
        boolean difOrien = to.getPitch() != from.getPitch() || to.getYaw() != from.getYaw();
        if (difOrien && difMove) {
            Player player = e.getPlayer();
            // If player is not moving
            Bukkit.getLogger().info(player.getDisplayName() + " stopped being idle.");
            // Set all idle hashmaps to false
            IdleChecker.playersIdling.put(player, 0);
            InventoryFull.isFull.put(player, false);
            LocationReached.atLocation.put(player, false);
            OnDamage.isDamaged.put(player, false);
            XpLevel.atExpLevel.put(player, false);
        }
    }

    // If player leaves game, remove them from playersIdling and the other condition hashmaps
    @EventHandler
    public static void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        IdleChecker.playersIdling.remove(player);
        InventoryFull.isFull.remove(player);
        LocationReached.atLocation.remove(player);
        OnDamage.isDamaged.remove(player);
        XpLevel.atExpLevel.remove(player);
    }
}

