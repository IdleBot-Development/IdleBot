package io.github.camshaft54.idlebot.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;

import static io.github.camshaft54.idlebot.events.EventsUtil.isPlayerIdle;
import static io.github.camshaft54.idlebot.events.EventsUtil.sendPlayerMessage;

public class OnDamage implements Listener {
    public static HashMap<Player, Boolean> isDamaged = new HashMap<>();

    // If player was damaged, send them a message
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (isPlayerIdle(player) && !isDamaged.get(player)) {
                Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle and taking damage!");
                sendPlayerMessage(player, player.getDisplayName() + " is taking damage " + " (" + e.getCause().name() + ").");
                isDamaged.put(player, true);
            }
        }
    }
}
