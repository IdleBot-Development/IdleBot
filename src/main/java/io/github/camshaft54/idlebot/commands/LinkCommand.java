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

package io.github.camshaft54.idlebot.commands;

import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.discord.DiscordAPIManager;
import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.Messenger;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import io.github.camshaft54.idlebot.util.enums.DataValues;
import io.github.camshaft54.idlebot.util.enums.MessageLevel;
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
            Messenger.sendMessage(player, "Your account is already linked!", MessageLevel.INCORRECT_COMMAND_USAGE);
            return true;
        }
        Random rng = new Random();
        String botName = DiscordAPIManager.bot.getSelfUser().getAsTag();
        int code;
        do {
            code = rng.nextInt(999);
        } while (isDuplicateToken(code) || code < 100);
        Messenger.sendMessage(player, "Your link code is " + code + ". Send this code in a private message to " + botName + " to link your account.", MessageLevel.IMPORTANT);
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
        return PersistentDataHandler.getStringData(player, DataValues.DISCORD_ID.key()) != null; // Returns true if the player already has an account linked
    }
}
