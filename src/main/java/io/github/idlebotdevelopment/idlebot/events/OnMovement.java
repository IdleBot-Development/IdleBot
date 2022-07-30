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
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;


public class OnMovement implements Listener {

    // The following method is a derivative work of AFKPlus by Dart2112 (https://github.com/LapisPlugins/AFKPlus) under the Apache License
    // If player has moved and looked in a different direction, reset playersIdling and the other condition hashmaps
    @EventHandler
    public void onMovement(PlayerMoveEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();
        if (to == null)
            return;
        if (to.getPitch() != from.getPitch() || to.getYaw() != from.getYaw()) {
            Player player = e.getPlayer();
            if (!PersistentDataUtils.getBooleanData(player, DataValue.IS_SET_AFK))
                IdleBotUtils.clearPlayerIdleStats(player);
        }
    }

}
