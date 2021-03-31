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
import io.github.idlebotdevelopment.idlebot.util.IdleBotUtils;
import io.github.idlebotdevelopment.idlebot.util.MessageHelper;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Objects;

public class ViewSettingsCommand implements IdleBotCommand {

    @Override
    public String getCommandName() {
        return "info";
    }

    @Override
    public String getCommandUsage() {
        return "/idlebot info";
    }

    @Override
    public boolean runCommand(Player player, String[] args) {
        MessageHelper.sendMessage(player, "Your current settings: ", MessageLevel.INFO);
        String discordID = PersistentDataUtils.getStringData(player, DataValue.DISCORD_ID);
        if (discordID == null) {
            player.sendMessage(ChatColor.AQUA + "Account linked: false");
            return true;
        }
        Objects.requireNonNull(IdleBot.getDiscordAPIManager().bot.retrieveUserById(discordID, false)).queue(u -> player.sendMessage(ChatColor.AQUA + "Account linked: true (" + u.getAsTag() + ")"));
        player.sendMessage(ChatColor.AQUA + "Message channel: " + (PersistentDataUtils.getBooleanData(player, DataValue.DIRECT_MESSAGE_MODE) ? "direct message" : "public channel"));
        player.sendMessage(ChatColor.AQUA + "AFK mode: " + (PersistentDataUtils.getBooleanData(player, DataValue.AUTO_AFK) ? "auto" : ("manual (Set AFK: " + PersistentDataUtils.getBooleanData(player, DataValue.IS_SET_AFK) + ")")));
        player.sendMessage(ChatColor.AQUA + "AFK time: " + PersistentDataUtils.getIntData(player, DataValue.AFK_TIME));
        player.sendMessage(ChatColor.AQUA + "Damage alert: " + PersistentDataUtils.getBooleanData(player, DataValue.DAMAGE_ALERT));
        player.sendMessage(ChatColor.AQUA + "Death alert: " + PersistentDataUtils.getBooleanData(player, DataValue.DEATH_ALERT));
        boolean locationAlertX = PersistentDataUtils.getBooleanData(player, DataValue.LOCATION_ALERT_X);
        int locationX = PersistentDataUtils.getIntData(player, DataValue.LOCATION_X_DESIRED);
        player.sendMessage(ChatColor.AQUA + "Location alert (X): " + locationAlertX + (locationAlertX ? (locationX == -1 ? " (no location set)" : (" (" + locationX + ")")) : ""));
        boolean locationAlertZ = PersistentDataUtils.getBooleanData(player, DataValue.LOCATION_ALERT_Z);
        int locationZ = PersistentDataUtils.getIntData(player, DataValue.LOCATION_Z_DESIRED);
        player.sendMessage(ChatColor.AQUA + "Location alert (Z): " + locationAlertZ + (locationAlertZ ? (locationZ == -1 ? " (no location set)" : (" (" + locationZ + ")")) : ""));
        boolean xpAlert = PersistentDataUtils.getBooleanData(player, DataValue.EXPERIENCE_ALERT);
        int xpLevel = PersistentDataUtils.getIntData(player, DataValue.EXPERIENCE_LEVEL_DESIRED);
        player.sendMessage(ChatColor.AQUA + "Experience alert: " + xpAlert + (xpAlert ? (xpLevel == -1 ? " (no XP level set)" : " (" + xpLevel + ")") : ""));
        player.sendMessage(ChatColor.AQUA + "Inventory fill alert: " + PersistentDataUtils.getBooleanData(player, DataValue.INVENTORY_FULL_ALERT));
        boolean advancementAlert = PersistentDataUtils.getBooleanData(player, DataValue.ADVANCEMENT_ALERT);
        String advancementName = IdleBotUtils.prettyPrintPlayerDesiredAdvancement(PersistentDataUtils.getStringData(player, DataValue.ADVANCEMENT_DESIRED));
        player.sendMessage(ChatColor.AQUA + "Advancement completed alert: " + advancementAlert + ((advancementAlert) ? " (" + advancementName + ")" : ""));
        player.sendMessage(ChatColor.AQUA + "Tool break alert: " + PersistentDataUtils.getBooleanData(player, DataValue.TOOL_BREAK_ALERT));
        return true;
    }
}
