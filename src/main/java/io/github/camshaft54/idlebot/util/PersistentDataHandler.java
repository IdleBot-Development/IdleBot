/*
 *    Copyright (C) 2020 Camshaft54, MetalTurtle18
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
