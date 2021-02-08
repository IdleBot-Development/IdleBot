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

import io.github.camshaft54.idlebot.discord.DiscordAPIManager;
import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.Messenger;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import io.github.camshaft54.idlebot.util.enums.DataValues;
import io.github.camshaft54.idlebot.util.enums.MessageLevel;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
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
        Messenger.sendMessage(player, "Your current settings: ", MessageLevel.INFO);
        String discordID = PersistentDataHandler.getStringData(player, DataValues.DISCORD_ID.key());
        boolean locationAlertX = PersistentDataHandler.getBooleanData(player, DataValues.LOCATION_ALERT_X.key());
        int locationX = PersistentDataHandler.getIntData(player, DataValues.LOCATION_X_DESIRED.key());
        boolean locationAlertZ = PersistentDataHandler.getBooleanData(player, DataValues.LOCATION_ALERT_Z.key());
        int locationZ = PersistentDataHandler.getIntData(player, DataValues.LOCATION_Z_DESIRED.key());
        boolean xpAlert = PersistentDataHandler.getBooleanData(player, DataValues.EXPERIENCE_ALERT.key());
        int xpLevel = PersistentDataHandler.getIntData(player, DataValues.EXPERIENCE_LEVEL_DESIRED.key());
        ArrayList<String> settingsMsg  = new ArrayList<>(Arrays.asList(
                "Account linked: " + (discordID == null ? "false" : ("true (" + Objects.requireNonNull(DiscordAPIManager.bot.retrieveUserById(discordID)).complete().getAsTag() + ")")),
                "Message channel: " + (PersistentDataHandler.getBooleanData(player, DataValues.DIRECT_MESSAGE_MODE.key()) ? "direct message" : "public channel"),
                "AFK mode: " + (PersistentDataHandler.getBooleanData(player, DataValues.AUTO_AFK.key()) ? "auto" : ("manual (Set AFK: " + PersistentDataHandler.getBooleanData(player, DataValues.IS_SET_AFK.key()) + ")")),
                "AFK time: " + PersistentDataHandler.getIntData(player, DataValues.AFK_TIME.key()),
                "Damage alert: " + PersistentDataHandler.getBooleanData(player, DataValues.DAMAGE_ALERT.key()),
                "Death alert: " + PersistentDataHandler.getBooleanData(player, DataValues.DEATH_ALERT.key()),
                "Location alert (X): " + locationAlertX + (locationAlertX ? (locationX == -1 ? " (no location set)" : (" (" + locationX + ")")) : ""),
                "Location alert (Z): " + locationAlertZ + (locationAlertZ ? (locationZ == -1 ? " (no location set)" : (" (" + locationZ + ")")) : ""),
                "Experience alert: " + xpAlert + (xpAlert ? (xpLevel == -1 ? " (no XP level set)" : " (" + xpLevel + ")") : ""),
                "Inventory fill alert: " + PersistentDataHandler.getBooleanData(player, DataValues.INVENTORY_FULL_ALERT.key())));
        String[] settingsMsgArray = settingsMsg.stream().map(s -> ChatColor.AQUA + s).toArray(String[]::new);
        player.sendMessage(settingsMsgArray);
        return true;
    }
}
