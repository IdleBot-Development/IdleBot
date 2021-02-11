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

package io.github.camshaft54.idlebot.commands;

import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.MessageHelper;
import io.github.camshaft54.idlebot.util.PersistentDataUtils;
import io.github.camshaft54.idlebot.util.enums.DataValues;
import io.github.camshaft54.idlebot.util.enums.MessageLevel;
import org.bukkit.entity.Player;

@SuppressWarnings("SpellCheckingInspection")
public class AlertCommand implements IdleBotCommand {
    @Override
    public String getCommandName() {
        return "alert";
    }

    @Override
    public String getCommandUsage() {
        return "/idlebot alert <damage|death|xlocation|zlocation|xp|inventory> <true|false>";
    }

    @Override
    public boolean runCommand(Player player, String[] args) {
        String dataKey;
        if (args.length < 3) {
            return false;
        }
        switch (args[1].toLowerCase()) {
            case "damage":
                dataKey = DataValues.DAMAGE_ALERT.key();
                break;
            case "death":
                dataKey = DataValues.DEATH_ALERT.key();
                break;
            case "xlocation":
                dataKey = DataValues.LOCATION_ALERT_X.key();
                break;
            case "zlocation":
                dataKey = DataValues.LOCATION_ALERT_Z.key();
                break;
            case "xp":
                dataKey = DataValues.EXPERIENCE_ALERT.key();
                break;
            case "inventory":
                dataKey = DataValues.INVENTORY_FULL_ALERT.key();
                break;
            default:
                return false;
        }
        if (args[2].equalsIgnoreCase("true")) {
            PersistentDataUtils.setData(player, dataKey, true);
            MessageHelper.sendMessage(player, "Turned on " + args[1].toLowerCase() + " alerts", MessageLevel.INFO);
            return true;
        } else if (args[2].equalsIgnoreCase("false")) {
            PersistentDataUtils.setData(player, dataKey, false);
            MessageHelper.sendMessage(player, "Turned off " + args[1].toLowerCase() + " alerts", MessageLevel.INFO);
            return true;
        }
        return false;
    }
}
