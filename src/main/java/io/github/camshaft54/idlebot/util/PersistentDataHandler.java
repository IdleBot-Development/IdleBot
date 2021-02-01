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

package io.github.camshaft54.idlebot.util;

import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.util.enums.DataValues;
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

    // Set "boolean" data (0 for false, 1 for true)
    public static void setData(Player player, String key, boolean value) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(new NamespacedKey(IdleBot.getPlugin(), key), PersistentDataType.INTEGER, value ? 1 : 0);
    }

    public static String getStringData(Player player, String key) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        try {
            return data.get(new NamespacedKey(IdleBot.getPlugin(), key), PersistentDataType.STRING);
        }
        catch (NullPointerException npe) {
            return null;
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static boolean getBooleanData(Player player, String key) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        try {
            return data.get(new NamespacedKey(IdleBot.getPlugin(), key), PersistentDataType.INTEGER) == 1;
        }
        catch (NullPointerException npe) {
            return false;
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static int getIntData(Player player, String key) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        try {
            return data.get(new NamespacedKey(IdleBot.getPlugin(), key), PersistentDataType.INTEGER);
        }
        catch (NullPointerException npe) {
            return -1;
        }
    }

    // Method to remove data by key from a player
    public static void removeData(Player player, String key) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        try {
            data.remove(new NamespacedKey(IdleBot.getPlugin(), key));
        } catch (NullPointerException npe) {
            // The player didn't have the value set (nothing else to do)
        }
    }

    public static void removeAllData(Player player) {
        for (DataValues dataValue : DataValues.values()) {
            removeData(player, dataValue.key());
        }
    }
}
