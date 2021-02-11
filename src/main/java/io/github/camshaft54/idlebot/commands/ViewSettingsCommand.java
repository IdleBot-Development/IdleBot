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

import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.discord.DiscordAPIManager;
import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.MessageHelper;
import io.github.camshaft54.idlebot.util.PersistentDataUtils;
import io.github.camshaft54.idlebot.util.enums.DataValues;
import io.github.camshaft54.idlebot.util.enums.MessageLevel;
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
        String discordID = PersistentDataUtils.getStringData(player, DataValues.DISCORD_ID.key());
        player.sendMessage(ChatColor.AQUA + "Account linked: " + (discordID == null ? "false" : ("true (" + Objects.requireNonNull(IdleBot.getDiscordAPIManager().bot.retrieveUserById(discordID)).complete().getAsTag() + ")")));
        player.sendMessage(ChatColor.AQUA + "Message channel: " + (PersistentDataUtils.getBooleanData(player, DataValues.DIRECT_MESSAGE_MODE.key()) ? "direct message" : "public channel"));
        player.sendMessage(ChatColor.AQUA + "AFK mode: " + (PersistentDataUtils.getBooleanData(player, DataValues.AUTO_AFK.key()) ? "auto" : ("manual (Set AFK: " + PersistentDataUtils.getBooleanData(player, DataValues.IS_SET_AFK.key()) + ")")));
        player.sendMessage(ChatColor.AQUA + "AFK time: " + PersistentDataUtils.getIntData(player, DataValues.AFK_TIME.key()));
        player.sendMessage(ChatColor.AQUA + "Damage alert: " + PersistentDataUtils.getBooleanData(player, DataValues.DAMAGE_ALERT.key()));
        player.sendMessage(ChatColor.AQUA + "Death alert: " + PersistentDataUtils.getBooleanData(player, DataValues.DEATH_ALERT.key()));
        boolean locationAlertX = PersistentDataUtils.getBooleanData(player, DataValues.LOCATION_ALERT_X.key());
        int locationX = PersistentDataUtils.getIntData(player, DataValues.LOCATION_X_DESIRED.key());
        player.sendMessage(ChatColor.AQUA + "Location alert (X): " + locationAlertX + (locationAlertX ? (locationX == -1 ? " (no location set)" : (" (" + locationX + ")")) : ""));
        boolean locationAlertZ = PersistentDataUtils.getBooleanData(player, DataValues.LOCATION_ALERT_Z.key());
        int locationZ = PersistentDataUtils.getIntData(player, DataValues.LOCATION_Z_DESIRED.key());
        player.sendMessage(ChatColor.AQUA + "Location alert (Z): " + locationAlertZ + (locationAlertZ ? (locationZ == -1 ? " (no location set)" : (" (" + locationZ + ")")) : ""));
        boolean xpAlert = PersistentDataUtils.getBooleanData(player, DataValues.EXPERIENCE_ALERT.key());
        int xpLevel = PersistentDataUtils.getIntData(player, DataValues.EXPERIENCE_LEVEL_DESIRED.key());
        player.sendMessage(ChatColor.AQUA + "Experience alert: " + xpAlert + (xpAlert ? (xpLevel == -1 ? " (no XP level set)" : " (" + xpLevel + ")") : ""));
        player.sendMessage(ChatColor.AQUA + "Inventory fill alert: " + PersistentDataUtils.getBooleanData(player, DataValues.INVENTORY_FULL_ALERT.key()));
        return true;
    }
}
