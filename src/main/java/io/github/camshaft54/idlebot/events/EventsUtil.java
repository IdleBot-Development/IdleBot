package io.github.camshaft54.idlebot.events;

import io.github.camshaft54.idlebot.discord.DiscordAPIManager;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import org.bukkit.entity.Player;

public class EventsUtil {
    // Check if a player is idle based on the player's settings and the time they have spent idle
    public static boolean isPlayerIdle(Player player) {
        int time = IdleChecker.playersIdling.get(player);
        String afkmode = PersistentDataHandler.getStringData(player, "afkmode");
        int afktime = PersistentDataHandler.getIntData(player, "afktime");
        // For debugging purposes
        //Bukkit.getLogger().info("Afkmode: " + afkmode + " Afktime: " + afktime + " Time: " + time + " ID: " + PersistentDataHandler.getStringData(player, "discordID"));
        if (afkmode == null) {
            return false;
        }
        return afkmode.equals("manual") || afktime <= time;
    }

    // Sends player a message on Discord, if player has linked account
    public static void sendPlayerMessage(Player player, String message) {
        String discordId = PersistentDataHandler.getStringData(player, "discordId");
        if (discordId != null)
            DiscordAPIManager.channel.sendMessage("<@!" + discordId + "> " + message);
    }
}
