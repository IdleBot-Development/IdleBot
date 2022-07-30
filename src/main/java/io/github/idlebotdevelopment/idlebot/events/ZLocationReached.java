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
import io.github.idlebotdevelopment.idlebot.util.IdleCheck;
import io.github.idlebotdevelopment.idlebot.util.MessageHelper;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import org.bukkit.entity.Player;

public class ZLocationReached implements IdleCheck {
    @Override
    public DataValue getDataValue() {
        return DataValue.LOCATION_ALERT_Z;
    }

    // Sends a player a message if they have reached their desired location
    public void check(Player player) {
        String direction = PersistentDataUtils.getStringData(player, DataValue.LOCATION_Z_DIRECTION);
        int desiredLocation = PersistentDataUtils.getIntData(player, DataValue.LOCATION_Z_DESIRED);
        double playerLocation = player.getLocation().getZ();
        if (direction != null && !IdleBot.getPlugin().getEventManager().locationReachedPlayersZ.containsKey(player) && ((direction.equals("s") && playerLocation >= desiredLocation) || (direction.equals("n") && playerLocation <= desiredLocation))) {
            MessageHelper.sendMessage(player.getDisplayName() + " is idle and reached the desired Z coordinate!", MessageLevel.INFO);
            IdleBotUtils.sendPlayerMessage(player, player.getDisplayName() + " has reached the desired Z coordinate!", player.getDisplayName() + " reached the desired location!");
            IdleBot.getPlugin().getEventManager().locationReachedPlayersZ.put(player, 0);
        }
    }
}
