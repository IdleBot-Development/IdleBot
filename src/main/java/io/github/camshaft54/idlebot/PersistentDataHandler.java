package io.github.camshaft54.idlebot;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PersistentDataHandler {
    public static void setData(Player player, String key, String value) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(new NamespacedKey(IdleBot.getPlugin(), key), PersistentDataType.STRING, value);
    }

    public static void setData(Player player, String key, int value) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(new NamespacedKey(IdleBot.getPlugin(), key), PersistentDataType.INTEGER, value);
    }

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
}
