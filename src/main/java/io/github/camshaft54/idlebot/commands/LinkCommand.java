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

import io.github.camshaft54.idlebot.discord.DiscordAPIManager;
import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.util.DataValues;
import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Random;

public class LinkCommand extends IdleBotCommand {

    @Override
    public String getCommandName() {
        return "link";
    }

    @Override
    public void runCommand(Player player, String[] args) {
        if (playerIsLinked(player)) {
            // Send a blurb about they are already linked and they need to /unlink if they want to relink or something
            return;
        }
        Random rng = new Random();
        String botName = DiscordAPIManager.bot.getDiscriminatedName();

        int code = rng.nextInt(999); // Generate a random token
        while (isDuplicateToken(code)) { // Keep getting new codes until it is unique (very unlikely to ever run)
            code = rng.nextInt(999);
        }
        player.sendMessage(ChatColor.DARK_PURPLE + "[IdleBot] " + ChatColor.AQUA + "Your link code is " + code + ". Send this code in a private message to " + botName + " to link your account.");
        IdleBot.linkCodes.put(code, player);
    }

    private boolean isDuplicateToken(int code) {
        for (int eachCode  : IdleBot.linkCodes.keySet()) {
            if (eachCode == code) return true;
        }
        return false;
    }

    private boolean playerIsLinked(Player player) {
        return PersistentDataHandler.getStringData(player, DataValues.DISCORD_ID.key()) != null; // Returns true if the player already has an account linked
    }
}
