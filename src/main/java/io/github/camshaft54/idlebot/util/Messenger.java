package io.github.camshaft54.idlebot.util;

import io.github.camshaft54.idlebot.util.enums.MessageLevel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Messenger {
    // Overload so you can just send to a player if there is a player argument otherwise send to console
    public static void sendMessage(String message, MessageLevel severity) {
        Bukkit.getConsoleSender().sendMessage(severity.getPrefix() + message);
    }

    public static void sendMessage(Player player, String message, MessageLevel severity) {
        player.sendMessage(severity.getPrefix() + message);
    }
}
