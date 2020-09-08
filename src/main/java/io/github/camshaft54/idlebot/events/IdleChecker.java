package io.github.camshaft54.idlebot.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class IdleChecker implements Runnable {

    public static HashMap<Player, Integer> playersIdle = new HashMap<>();
    public static ArrayList<Player> idlePlayers = new ArrayList<>();

    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (playersIdle.containsKey(player)) { // If player is already in the hashmap
                playersIdle.put(player, playersIdle.get(player) + 1); // Add 1 to the player's value
                Bukkit.getLogger().info("player had been idle for " + playersIdle.get(player));
            } else { // If not
                // Put new player to the hashmap with value 1 (1 second)
                playersIdle.put(player, 0);
                Bukkit.getLogger().info("A new player joined");
            }
            if (playersIdle.get(player) == 30) { // If player's value is 120 (120 seconds)
                player.sendMessage("You are idle!!!");
                Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle!!!");
                idlePlayers.add(player);
            }
        }
        Bukkit.getLogger().info("[IdleBot]: " + "Hashmap:" + playersIdle.keySet().toString() + "Values: " + playersIdle.values().toString());

    }

}
