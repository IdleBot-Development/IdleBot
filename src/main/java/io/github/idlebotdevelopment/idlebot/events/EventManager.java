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

package io.github.idlebotdevelopment.idlebot.events;

import io.github.idlebotdevelopment.idlebot.util.IdleBotUtils;
import io.github.idlebotdevelopment.idlebot.util.IdleCheck;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class EventManager implements Runnable {
    // Lists to store the players who have already been pinged
    public final ArrayList<Player> inventoryFullPlayers = new ArrayList<>();
    public final ArrayList<Player> locationReachedPlayersX = new ArrayList<>();
    public final ArrayList<Player> locationReachedPlayersZ = new ArrayList<>();
    public final ArrayList<Player> damagedPlayers = new ArrayList<>();
    public final ArrayList<Player> XPLevelReachedPlayers = new ArrayList<>();

    private final ArrayList<IdleCheck> idleChecks = new ArrayList<>();

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player ->
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
