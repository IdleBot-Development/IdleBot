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
import io.github.camshaft54.idlebot.util.EventUtils;
import io.github.camshaft54.idlebot.util.IdleCheck;
import io.github.camshaft54.idlebot.util.Messenger;
import io.github.camshaft54.idlebot.util.enums.DataValues;
import io.github.camshaft54.idlebot.util.enums.MessageLevel;
import org.bukkit.entity.Player;

public class InventoryFull implements IdleCheck {
    @Override
    public String getDataValue() {
        return DataValues.INVENTORY_FULL_ALERT.key();
    }

    public void check(Player player) {
        if (player.getInventory().firstEmpty() < 0 && !IdleBot.getEventManager().inventoryFullPlayers.contains(player)) {
            Messenger.sendMessage(player.getDisplayName() + " is idle and their inventory is full!", MessageLevel.INFO);
            EventUtils.sendPlayerMessage(player, player.getDisplayName() + "'s inventory is full! ");
            IdleBot.getEventManager().inventoryFullPlayers.add(player);
        }
    }
}
