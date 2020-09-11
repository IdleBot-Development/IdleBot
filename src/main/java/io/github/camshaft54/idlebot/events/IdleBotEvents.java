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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class IdleBotEvents implements Listener {
    public static HashMap<Player, Boolean> isDamaged = new HashMap<>();

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        HashMap<Player, String> MCtoDiscord = getMCtoDiscordHashMap();
        assert MCtoDiscord != null;
        if (IdleChecker.idlePlayers.contains(player) && MCtoDiscord.containsKey(player)) {
            Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle and dead!");
            IdleBot.channel.sendMessage("<@!" + MCtoDiscord.get(player) + "> " + player.getDisplayName() + " died at " + locationCleanup(player.getLocation()) + " (" + e.getDeathMessage() + ").");
        }
        else {
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
        IdleChecker.playersIdling.put(player,0);
        isDamaged.put(player, false);
    }

    private static String locationCleanup(Location l) {
        return "(" + Math.round(l.getX()) + ", " + Math.round(l.getY()) + ", " + Math.round(l.getZ()) + ")";
    }

    private static HashMap<Player, String> getMCtoDiscordHashMap() {
        HashMap<Player, String> MCtoDiscord = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get("plugins/IdleBot/playerLinks.txt"), Charset.defaultCharset());
            for (String line : lines) {
                String[] current_line = line.split(" : ");
                MCtoDiscord.put(Bukkit.getPlayer(UUID.fromString(current_line[0])), current_line[1]);
            }
            return MCtoDiscord;
        } catch (IOException e) {
            Bukkit.getLogger().warning("Unable to access playerLinks.yml");
        }
        return null;
    }
}

