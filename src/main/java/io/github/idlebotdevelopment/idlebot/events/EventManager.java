/*
 * Copyright (C) 2020-2022 Camshaft54, MetalTurtle18
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.idlebotdevelopment.idlebot.events;

import io.github.idlebotdevelopment.idlebot.util.IdleBotUtils;
import io.github.idlebotdevelopment.idlebot.util.IdleCheck;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class EventManager implements Runnable {
    // Lists to store the players who have already been pinged
    public final HashMap<Player, Integer> inventoryFullPlayers = new HashMap<>();
    public final HashMap<Player, Integer> locationReachedPlayersX = new HashMap<>();
    public final HashMap<Player, Integer> locationReachedPlayersZ = new HashMap<>();
    public final HashMap<Player, Integer> damagedPlayers = new HashMap<>();
    public final HashMap<Player, Integer> XPLevelReachedPlayers = new HashMap<>();

    public final ArrayList<HashMap<Player, Integer>> alertedLists = new ArrayList<>();

    private final ArrayList<IdleCheck> idleChecks = new ArrayList<>();

    public EventManager() {
        alertedLists.add(inventoryFullPlayers);
        alertedLists.add(locationReachedPlayersX);
        alertedLists.add(locationReachedPlayersZ);
        alertedLists.add(damagedPlayers);
        alertedLists.add(XPLevelReachedPlayers);
    }

    @Override
    public void run() {
        // Remove players from alerted lists if their alert timeout has been reached
        for (HashMap<Player, Integer> map : alertedLists) {
            for (Player player : map.keySet()) {
                map.put(player, map.get(player) + 1); // Increment the timeout tracker
                int timeout = PersistentDataUtils.getIntData(player, DataValue.ALERT_REPEAT_TIMEOUT);
                if (timeout > 0 && map.get(player) >= timeout) {
                    map.remove(player);
                }
            }
        }

        // Check extra events
        Bukkit.getOnlinePlayers().forEach(player -> // TODO: aren't streams slower? Why is this a stream?
                idleChecks.forEach(check -> {
                    if (PersistentDataUtils.getBooleanData(player, check.getDataValue()) && IdleBotUtils.isIdle(player))
                        check.check(player);
                })
        );
    }

    public void registerCheck(IdleCheck check) {
        idleChecks.add(check);
    }
}