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

import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.util.DataValues;
import io.github.camshaft54.idlebot.util.EventUtils;
import io.github.camshaft54.idlebot.util.IdleCheck;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class XPLevelReached implements IdleCheck {
    // Checks if player has reached a certain xp level and sends them a message if they have
    public void check(Player player) {
        if (player.getLevel() >= PersistentDataHandler.getIntData(player, DataValues.EXPERIENCE_LEVEL_DESIRED.key()) && !IdleBot.getEventManager().XPLevelReachedPlayers.contains(player)) {
            Bukkit.getLogger().info(ChatColor.DARK_PURPLE + "[IdleBot] " + ChatColor.AQUA + player.getDisplayName() + " is idle and at the desired XP level!");
            EventUtils.sendPlayerMessage(player, player.getDisplayName() + " is at the desired XP level! ");
            IdleBot.getEventManager().XPLevelReachedPlayers.add(player);
        }
    }
}
