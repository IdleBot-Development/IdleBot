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

package io.github.idlebotdevelopment.idlebot.commands;

import io.github.idlebotdevelopment.idlebot.IdleBot;
import io.github.idlebotdevelopment.idlebot.util.IdleBotCommand;
import io.github.idlebotdevelopment.idlebot.util.MessageHelper;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import org.bukkit.entity.Player;

import java.util.Random;

public class LinkCommand implements IdleBotCommand {

    @Override
    public String getCommandName() {
        return "link";
    }

    @Override
    public String getCommandUsage() {
        return "/idlebot link";
    }

    @Override
    public boolean runCommand(Player player, String[] args) {
        if (playerIsLinked(player)) {
            MessageHelper.sendMessage(player, "Your account is already linked!", MessageLevel.INCORRECT_COMMAND_USAGE);
            return true;
        }
        if (IdleBot.getConfigManager().DISCORDSRV_MODE) {
            MessageHelper.sendMessage(player, "This server is using DiscordSRV to link accounts. Please run \"/discord link\" to link your account instead", MessageLevel.INCORRECT_COMMAND_USAGE);
            return true;
        }
        Random rng = new Random();
        String botName = IdleBot.getDiscordAPIManager().bot.getSelfUser().getAsTag();
        int code;
        do {
            code = rng.nextInt(999);
        } while (isDuplicateToken(code) || code < 100);
        MessageHelper.sendMessage(player, "Your link code is " + code + ". Send this code in a private message to " + botName + " to link your account.", MessageLevel.IMPORTANT);
        IdleBot.linkCodes.put(code, player);
        return true;
    }

    private boolean isDuplicateToken(int code) {
        for (int eachCode : IdleBot.linkCodes.keySet()) {
            if (eachCode == code) return true;
        }
        return false;
    }

    private boolean playerIsLinked(Player player) {
        return PersistentDataUtils.getStringData(player, DataValue.DISCORD_ID) != null; // Returns true if the player already has an account linked
    }
}
