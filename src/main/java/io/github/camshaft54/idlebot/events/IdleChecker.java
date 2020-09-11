package io.github.camshaft54.idlebot.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class IdleChecker implements Runnable {

    public static HashMap<Player, Integer> playersIdling = new HashMap<>();
    public static ArrayList<Player> idlePlayers = new ArrayList<>();

    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (playersIdling.containsKey(player)) { // If player is already in the hashmap
                playersIdling.put(player, playersIdling.get(player) + 1); // Add 1 to the player's value
                Bukkit.getLogger().info("player had been idle for " + playersIdling.get(player));
            } else { // If not
                // Put new player to the hashmap with value 1 (1 second)
                playersIdling.put(player, 0);
                IdleBotEvents.isDamaged.put(player, false);
                Bukkit.getLogger().info("A new player joined");
            }
            if (playersIdling.get(player) == 30) { // If player's value is 120 (120 seconds)
                player.sendMessage("You are idle!!!");
                Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle!!!");
                idlePlayers.add(player);
            }
        }
        Bukkit.getLogger().info("[IdleBot]: " + "Hashmap:" + playersIdling.keySet().toString() + "Values: " + playersIdling.values().toString());
    }

}
