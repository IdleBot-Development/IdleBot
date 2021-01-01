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

package io.github.camshaft54.idlebot.events;

import io.github.camshaft54.idlebot.util.EventUtils;
import io.github.camshaft54.idlebot.util.IdleCheck;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class EventManager implements Runnable {
    // Lists to store the players who have already been pinged
    public ArrayList<Player> inventoryFullPlayers = new ArrayList<>();
    public ArrayList<Player> locationReachedPlayers = new ArrayList<>();
    public ArrayList<Player> damagedPlayers = new ArrayList<>();
    public ArrayList<Player> XPLevelReachedPlayers = new ArrayList<>();

    private final ArrayList<IdleCheck> idleChecks = new ArrayList<>();

    public EventManager() {
        idleChecks.add(new InventoryFull());
        idleChecks.add(new LocationReached());
        idleChecks.add(new XPLevelReached());
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player ->
            idleChecks.forEach(check -> {
                if (PersistentDataHandler.getBooleanData(player, check.getDataValue()) && EventUtils.isIdle(player))
                    check.check(player);
            })
        );
    }
}
