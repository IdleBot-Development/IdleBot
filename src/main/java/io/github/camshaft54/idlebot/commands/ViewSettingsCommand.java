package io.github.camshaft54.idlebot.commands;

import io.github.camshaft54.idlebot.discord.DiscordAPIManager;
import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.Messenger;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import io.github.camshaft54.idlebot.util.enums.DataValues;
import io.github.camshaft54.idlebot.util.enums.MessageLevel;
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
        Messenger.sendMessage(player, "Your current settings: ", MessageLevel.INFO);
        String discordID = PersistentDataHandler.getStringData(player, DataValues.DISCORD_ID.key());
        player.sendMessage("Account linked: " + (discordID == null ? "false" : ("true (" + Objects.requireNonNull(DiscordAPIManager.bot.getUserById(discordID)).getAsTag() + ")")));
        player.sendMessage("Message channel: " + (PersistentDataHandler.getBooleanData(player, DataValues.DIRECT_MESSAGE_MODE.key()) ? "direct message" : "public channel"));
        player.sendMessage("AFK mode: " + (PersistentDataHandler.getBooleanData(player, DataValues.AUTO_AFK.key()) ? "auto" : ("manual (Set AFK: " + PersistentDataHandler.getBooleanData(player, DataValues.IS_SET_AFK.key()) + ")")));
        player.sendMessage("AFK time: " + PersistentDataHandler.getIntData(player, DataValues.AFK_TIME.key()));
        player.sendMessage("Damage alert: " + PersistentDataHandler.getBooleanData(player, DataValues.DAMAGE_ALERT.key()));
        player.sendMessage("Death alert: " + PersistentDataHandler.getBooleanData(player, DataValues.DEATH_ALERT.key()));
        boolean locationAlertX = PersistentDataHandler.getBooleanData(player, DataValues.LOCATION_ALERT_X.key());
        int locationX = PersistentDataHandler.getIntData(player, DataValues.LOCATION_X_DESIRED.key());
        player.sendMessage("Location alert (X): " + locationAlertX + (locationAlertX ? (locationX == -1 ? "" : (" (" + locationX + ")")) : ""));
        boolean locationAlertZ = PersistentDataHandler.getBooleanData(player, DataValues.LOCATION_ALERT_Z.key());
        int locationZ = PersistentDataHandler.getIntData(player, DataValues.LOCATION_Z_DESIRED.key());
        player.sendMessage("Location alert (Z): " + locationAlertZ + (locationAlertZ ? (locationZ == -1 ? "" : (" (" + locationZ + ")")) : ""));
        boolean xpAlert = PersistentDataHandler.getBooleanData(player, DataValues.EXPERIENCE_ALERT.key());
        int xpLevel = PersistentDataHandler.getIntData(player, DataValues.EXPERIENCE_LEVEL_DESIRED.key());
        player.sendMessage("Experience alert: " + xpAlert + (xpAlert ? (xpLevel == -1 ? "" : " (" + xpLevel + ")") : ""));
        player.sendMessage("Inventory fill alert: " + PersistentDataHandler.getBooleanData(player, DataValues.INVENTORY_FULL_ALERT.key()));
        return true;
    }
}
