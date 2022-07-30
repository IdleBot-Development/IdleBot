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

import io.github.idlebotdevelopment.idlebot.IdleBot;
import io.github.idlebotdevelopment.idlebot.util.IdleBotUtils;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class IdleChecker implements Runnable {

    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (PersistentDataUtils.getStringData(player, DataValue.DISCORD_ID) != null) {
                HashMap<Player, Integer> idlePlayers = IdleBot.getPlugin().getIdlePlayers();
                if (!IdleBotUtils.isIdle(player) && idlePlayers.containsKey(player)) {
                    idlePlayers.put(player, idlePlayers.get(player) + 1); // Increase by one
                } else if (!IdleBotUtils.isIdle(player) && PersistentDataUtils.getBooleanData(player, DataValue.AUTO_AFK)) {
                    idlePlayers.put(player, 0);
                }
            }
        }
    }
}
