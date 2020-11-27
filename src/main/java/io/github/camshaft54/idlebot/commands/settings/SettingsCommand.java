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

import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.PlayerSettingSetter;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SettingsCommand extends IdleBotCommand {

    ArrayList<PlayerSettingSetter> settings = new ArrayList<>();

    public SettingsCommand() {
        settings.add(new AFKMode());
        settings.add(new AFKTime());
        settings.add(new DamageAlert());
    }

    @Override
    public String getCommandName() {
        return "settings";
    }

    @Override
    public void runCommand(Player player, String[] args) {
        if (args.length < 2) {
            // Information about the command
            return;
        }
        for (PlayerSettingSetter setting : settings) {
            if (args[1].equalsIgnoreCase(setting.getSettingName())) {
                setting.setSetting(player, args);
            }
        }
    }
}
