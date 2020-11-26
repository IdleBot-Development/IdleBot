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

package io.github.camshaft54.idlebot.commands.settings;

import io.github.camshaft54.idlebot.util.DataValues;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import io.github.camshaft54.idlebot.util.PlayerSettingSetter;
import org.bukkit.entity.Player;

public class AFKMode extends PlayerSettingSetter {
    @Override
    public String getSettingName() {
        return "afkmode";
    }

    @Override
    public void setSetting(Player player, String[] args) {
        if (args.length < 3) {
            // Information about the setting
            return;
        }
        if (args[2].equalsIgnoreCase("manual")) {
            PersistentDataHandler.setData(player, DataValues.AFKMODE.key(), "manual");
        } else if (args[2].equalsIgnoreCase("auto")) {
            PersistentDataHandler.setData(player, DataValues.AFKMODE.key(), "auto");
            if (args.length >= 4) {
                try {
                    if (Integer.parseInt(args[3]) >= 10 && Integer.parseInt(args[3]) <= 120) {
                        PersistentDataHandler.setData(player, DataValues.AFKTIME.key(), Integer.parseInt(args[3]));
                    } else {
                        // Say it has to be 10 seconds to 2 minutes
                        return;
                    }
                } catch (NumberFormatException nfe) {
                    // Send the player message about needs to be "auto <number>"
                    return;
                }
            }
        }
    }
}
