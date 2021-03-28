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

package io.github.idlebotdevelopment.idlebot.util;

import io.github.idlebotdevelopment.idlebot.IdleBot;
import io.github.idlebotdevelopment.idlebot.discord.DiscordAPIManager;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IdleBotUtils {
    // Check if a player is idle based on the player's settings and the time they have spent idle
    public static boolean isIdle(Player player) {
        int time = (IdleBot.idlePlayers.get(player) != null) ? IdleBot.idlePlayers.get(player) : -1;
        boolean autoAFK = PersistentDataUtils.getBooleanData(player, DataValue.AUTO_AFK);
        boolean setafk = PersistentDataUtils.getBooleanData(player, DataValue.IS_SET_AFK);
        int afktime = PersistentDataUtils.getIntData(player, DataValue.AFK_TIME);
        return (!autoAFK && setafk) || (time != -1 && afktime <= time);
    }

    // Sends player a message on Discord, if player has linked account
    public static void sendPlayerMessage(Player player, String message, String previewMessage) {
        String discordID = PersistentDataUtils.getStringData(player, DataValue.DISCORD_ID);
        if (discordID != null) {
            EmbedBuilder eb = new EmbedBuilder().setAuthor(player.getDisplayName(), null, "https://minotar.net/helm/" + player.getUniqueId())
                    .setTitle(message)
                    .setColor(Color.RED);
            MessageBuilder mb = new MessageBuilder().append("<@!").append(discordID).append(">, ").append(previewMessage).setEmbed(eb.build());
            if (PersistentDataUtils.getBooleanData(player, DataValue.DIRECT_MESSAGE_MODE)) {
                IdleBot.getDiscordAPIManager().bot.retrieveUserById(
                        Objects.requireNonNull(PersistentDataUtils.getStringData(player, DataValue.DISCORD_ID)), false)
                        .queue(user -> user.openPrivateChannel().queue(channel -> channel.sendMessage(mb.build()).queue()));
            } else {
                DiscordAPIManager.channel.sendMessage(mb.build()).queue();
            }
        }
    }

    public static void clearPlayerIdleStats(Player player) {
        IdleBot.idlePlayers.remove(player);
        IdleBot.getEventManager().inventoryFullPlayers.remove(player);
        IdleBot.getEventManager().damagedPlayers.remove(player);
        IdleBot.getEventManager().locationReachedPlayersX.remove(player);
        IdleBot.getEventManager().locationReachedPlayersZ.remove(player);
        IdleBot.getEventManager().XPLevelReachedPlayers.remove(player);
    }

    public static void saveListToDataFile(ArrayList<String> playerList, boolean append) {
        String playersString = Arrays.toString(playerList.toArray());
        playersString = playersString.substring(1, playersString.length() - 1).replace(" ", "") + ",";
        if (!playersString.equals(",")) {
            try {
                FileWriter writer = new FileWriter(IdleBot.getPlugin().getDataFolder() + "/OfflinePlayersWhoNeedToHaveTheirDataCleared.txt", append);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                bufferedWriter.write(playersString);
                bufferedWriter.close();
            } catch (Exception e) {
                MessageHelper.sendMessage("Error writing to data file!", MessageLevel.FATAL_ERROR);
                e.printStackTrace();
            }
        }
    }

    public static boolean isInteger(String str) {
        if (str.length() > 11) return false;
        try {
            BigInteger bigInt = new BigInteger(str);
            return bigInt.bitCount() <= 32;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String prettyPrintPlayerDesiredAdvancement(String inputAdvancementName) {
        String advancementName = "";
        if (inputAdvancementName != null) {
            if (inputAdvancementName.startsWith("minecraft:recipes")) {
                advancementName = "Recipe for ";
            }
            advancementName += Stream.of(inputAdvancementName.substring(inputAdvancementName.lastIndexOf("/") + 1).split("_")).map(StringUtils::capitalize).collect(Collectors.joining(" "));
        } else {
            advancementName = "Non-recipe";
        }
        return advancementName;
    }
}
