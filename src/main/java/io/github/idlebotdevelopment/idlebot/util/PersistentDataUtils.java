/*
 *    Copyright (C) 2020-2021 Camshaft54, MetalTurtle18
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.idlebotdevelopment.idlebot.util;

import io.github.idlebotdevelopment.idlebot.IdleBot;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PersistentDataUtils {

    // Overload method to set String or Integer
    public static void setData(Player player, DataValue key, String value) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(new NamespacedKey(IdleBot.getPlugin(), key.key()), PersistentDataType.STRING, value);
    }

    public static void setData(Player player, DataValue key, int value) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(new NamespacedKey(IdleBot.getPlugin(), key.key()), PersistentDataType.INTEGER, value);
    }

    // Set "boolean" data (0 for false, 1 for true)
    public static void setData(Player player, DataValue key, boolean value) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(new NamespacedKey(IdleBot.getPlugin(), key.key()), PersistentDataType.INTEGER, value ? 1 : 0);
    }

    public static String getStringData(Player player, DataValue key) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        try {
            return data.get(new NamespacedKey(IdleBot.getPlugin(), key.key()), PersistentDataType.STRING);
        } catch (NullPointerException npe) {
            return null;
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static boolean getBooleanData(Player player, DataValue key) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        try {
            return data.get(new NamespacedKey(IdleBot.getPlugin(), key.key()), PersistentDataType.INTEGER) == 1;
        } catch (NullPointerException npe) {
            return false;
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static int getIntData(Player player, DataValue key) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        try {
            return data.get(new NamespacedKey(IdleBot.getPlugin(), key.key()), PersistentDataType.INTEGER);
        } catch (NullPointerException npe) {
            return -1;
        }
    }

    // Method to remove data by key from a player
    public static void removeData(Player player, DataValue key) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        try {
            data.remove(new NamespacedKey(IdleBot.getPlugin(), key.key()));
        } catch (NullPointerException npe) {
            // The player didn't have the value set (nothing else to do)
        }
    }

    public static void removeAllData(Player player) {
        for (DataValue dataValue : DataValue.values()) {
            removeData(player, dataValue);
        }
    }
}
