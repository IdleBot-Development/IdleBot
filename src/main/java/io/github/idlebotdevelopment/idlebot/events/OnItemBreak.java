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
import io.github.idlebotdevelopment.idlebot.util.MessageHelper;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import org.apache.commons.lang.WordUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Stream;

public class OnItemBreak implements Listener {
    @EventHandler
    public void onItemBreak(PlayerItemBreakEvent e) {
        Player player = e.getPlayer();
        ItemStack item = e.getBrokenItem();
        boolean isTool = Stream.of(e.getBrokenItem().getType().toString().split("_"))
                .noneMatch(s -> s.equalsIgnoreCase("helmet") || s.equalsIgnoreCase("chestplate") ||
                        s.equalsIgnoreCase("leggings") || s.equalsIgnoreCase("boots") || s.equalsIgnoreCase("elytra") || s.equalsIgnoreCase("shield"));
        if (IdleBotUtils.isIdle(player) && PersistentDataUtils.getBooleanData(player, DataValue.TOOL_BREAK_ALERT) && isTool) {
            MessageHelper.sendMessage(player.getDisplayName() + " is idle and broke a tool!", MessageLevel.INFO);
            IdleBotUtils.sendPlayerMessage(player, player.getDisplayName() + " broke " + ((item.getItemMeta() != null && !item.getItemMeta().getDisplayName().equals("")) ? item.getItemMeta().getDisplayName() : "a(n) " + WordUtils.capitalize(item.getType().toString().replace("_", " ").toLowerCase())) + ".", player.getDisplayName() + " broke a tool");
        }
    }
}
