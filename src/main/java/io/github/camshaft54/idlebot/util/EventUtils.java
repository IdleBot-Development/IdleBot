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
import org.bukkit.entity.Player;

import java.util.concurrent.ExecutionException;

public class EventUtils {
    // Check if a player is idle based on the player's settings and the time they have spent idle
    public static boolean isIdle(Player player) {
        int time = IdleBot.idlePlayers.get(player);
        String afkmode = PersistentDataHandler.getStringData(player, DataValues.AFK_MODE.key());
        boolean setafk = PersistentDataHandler.getBooleanData(player, DataValues.IS_SET_AFK.key());
        int afktime = PersistentDataHandler.getIntData(player, DataValues.AFK_TIME.key());
        if (afkmode == null) {
            return false;
        }
        return (afkmode.equals("manual") && setafk) || afktime <= time;
    }

    // Sends player a message on Discord, if player has linked account
    public static void sendPlayerMessage(Player player, String message, String alertType) {
        String discordID = PersistentDataHandler.getStringData(player, DataValues.DISCORD_ID.key());
        if (discordID != null && PersistentDataHandler.getBooleanData(player, alertType)) {
            if (PersistentDataHandler.getBooleanData(player, DataValues.DIRECT_MESSAGE_MODE.key())) {
                try {
                    DiscordAPIManager.api.getUserById(PersistentDataHandler.getStringData(player,
                            DataValues.DISCORD_ID.key())).get().getPrivateChannel()
                            .ifPresent(channel -> channel.sendMessage(formatUserID(discordID) + message));
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            } else {
                DiscordAPIManager.channel.sendMessage(formatUserID(discordID) + message);
            }
        }
    }

    // Because why not
    private static String formatUserID(String ID) {
        return "<@!" + ID + ">";
    }
}
