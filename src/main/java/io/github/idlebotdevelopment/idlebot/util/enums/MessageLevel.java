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

package io.github.idlebotdevelopment.idlebot.util.enums;

import org.bukkit.ChatColor;

public enum MessageLevel {
    INFO(ChatColor.AQUA),
    FATAL_ERROR(ChatColor.DARK_RED),
    INCORRECT_COMMAND_USAGE(ChatColor.BLUE),
    IMPORTANT(ChatColor.GREEN);

    private final ChatColor color;
    MessageLevel(ChatColor color) {
        this.color = color;
    }

    public String getPrefix() {
        return ChatColor.DARK_PURPLE + "[IdleBot] " + color;
    }
}
