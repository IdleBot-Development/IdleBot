package io.github.camshaft54.idlebot;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PersistentDataHandler {
    // Overload method to set String or Integer
    public static void setData(Player player, String key, String value) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(new NamespacedKey(IdleBot.getPlugin(), key), PersistentDataType.STRING, value);
    }

    public static void setData(Player player, String key, int value) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(new NamespacedKey(IdleBot.getPlugin(), key), PersistentDataType.INTEGER, value);
    }

    // Overload method to get String or Integer
    public static String getStringData(Player player, String key) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        return data.get(new NamespacedKey(IdleBot.getPlugin(), key), PersistentDataType.STRING);
    }

    public static int getIntData(Player player, String key) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (data.has(new NamespacedKey(IdleBot.getPlugin(), key), PersistentDataType.INTEGER))
            return data.get(new NamespacedKey(IdleBot.getPlugin(), key), PersistentDataType.INTEGER);
        else
            return -1;
    }

    // Method to remove data by key from a player
    public static void removeData(Player player, String key) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.remove(new NamespacedKey(IdleBot.getPlugin(), key));
    }
}
