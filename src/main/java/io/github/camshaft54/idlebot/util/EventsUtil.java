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

package io.github.camshaft54.idlebot.util;

import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.discord.DiscordAPIManager;
import io.github.camshaft54.idlebot.events.IdleChecker;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import org.bukkit.entity.Player;

public class EventsUtil {
    // Check if a player is idle based on the player's settings and the time they have spent idle
    public static boolean isPlayerIdle(Player player) {
        int time = IdleBot.idlePlayers.get(player);
        String afkmode = PersistentDataHandler.getStringData(player, "afkmode");
        int afktime = PersistentDataHandler.getIntData(player, "afktime");
        // For debugging purposes
        //Bukkit.getLogger().info("Afkmode: " + afkmode + " Afktime: " + afktime + " Time: " + time + " ID: " + PersistentDataHandler.getStringData(player, "discordID"));
        if (afkmode == null) {
            return false;
        }
        return afkmode.equals("manual") || afktime <= time;
    }

    // Sends player a message on Discord, if player has linked account
    public static void sendPlayerMessage(Player player, String message) {
        String discordId = PersistentDataHandler.getStringData(player, "discordId");
        if (discordId != null)
            DiscordAPIManager.channel.sendMessage("<@!" + discordId + "> " + message);
    }
}
