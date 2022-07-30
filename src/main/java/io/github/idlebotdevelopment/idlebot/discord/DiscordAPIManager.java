/*
 * Copyright (C) 2020-2022 Camshaft54, MetalTurtle18
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.idlebotdevelopment.idlebot.discord;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.AccountLinkedEvent;
import github.scarsz.discordsrv.api.events.AccountUnlinkedEvent;
import github.scarsz.discordsrv.api.events.DiscordReadyEvent;
import github.scarsz.discordsrv.util.DiscordUtil;
import io.github.idlebotdevelopment.idlebot.IdleBot;
import io.github.idlebotdevelopment.idlebot.util.ConfigManager;
import io.github.idlebotdevelopment.idlebot.util.MessageHelper;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;

import javax.security.auth.login.LoginException;

public class DiscordAPIManager implements Listener {
    @Getter private JDA jda;
    @Getter private TextChannel channel;

    public DiscordAPIManager() throws LoginException, InterruptedException {
        IdleBot plugin = IdleBot.getPlugin();
        ConfigManager configManager = plugin.getConfigManager();
        if (configManager.DISCORDSRV_MODE) {
            final PluginManager pluginManager = Bukkit.getServer().getPluginManager();
            if (pluginManager.isPluginEnabled("DiscordSRV")) {
                MessageHelper.sendMessage("Starting communication with DiscordSRV plugin", MessageLevel.INFO);
                hookIntoDiscordSRV();
            } else { // This shouldn't happen since DiscordSRV is a dependency, but it seems to happen anyway!
                MessageHelper.sendMessage("DiscordSRV plugin not yet loaded. Waiting for it to load", MessageLevel.INFO);
                Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
                // This will run after all plugins load to check if DiscordSRV has still not been enabled
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!pluginManager.isPluginEnabled("DiscordSRV")) {
                            MessageHelper.sendMessage("DiscordSRV mode is enabled but DiscordSRV was never loaded!", MessageLevel.FATAL_ERROR);
                            plugin.disablePlugin(true);
                        }
                    }
                }.runTask(plugin);
            }
        } else {
            jda = JDABuilder.createDefault(configManager.BOT_TOKEN).build();
            jda.awaitReady();
            jda.addEventListener(new DiscordMessageEvent());
            switch (configManager.ACTIVITY_TYPE.toLowerCase()) {
                case "watching":
                    jda.getPresence().setPresence(Activity.watching(configManager.ACTIVITY_MESSAGE), false);
                    break;
                case "playing":
                    jda.getPresence().setPresence(Activity.playing(configManager.ACTIVITY_MESSAGE), false);
                    break;
                case "listening":
                    jda.getPresence().setPresence(Activity.listening(configManager.ACTIVITY_MESSAGE), false);
                    break;
                default:
                    break;
            }
            TextChannel nullableChannel = jda.getTextChannelById(configManager.CHANNEL_ID);
            if (configManager.PUBLIC_CHANNEL_MESSAGES_ENABLED && nullableChannel != null)
                channel = nullableChannel;
            else if (configManager.PUBLIC_CHANNEL_MESSAGES_ENABLED) {
                MessageHelper.sendMessage("Invalid Discord channel specified in config", MessageLevel.FATAL_ERROR);
                plugin.disablePlugin(true);
            }
        }
    }

    private void hookIntoDiscordSRV() {
        DiscordSRV.api.subscribe(this);
    }

    @EventHandler
    public void onPluginEnable(PluginEnableEvent event) {
        if (event.getPlugin().getName().equals("DiscordSRV")) {
            MessageHelper.sendMessage("DiscordSRV plugin enabled. Starting communication", MessageLevel.INFO);
            hookIntoDiscordSRV();
        }
    }

    @Subscribe
    public void onDiscordReady(DiscordReadyEvent event) {
        IdleBot plugin = IdleBot.getPlugin();
        try {
            jda = JDABuilder.createDefault(DiscordUtil.getJda().getToken().substring(4)).build(); // Builds a new JDA instance with the same bot token as DiscordSRV is using
            jda.awaitReady();
            MessageHelper.sendMessage("Successfully hooked into DiscordSRV", MessageLevel.INFO);
        } catch (LoginException | InterruptedException e) {
            MessageHelper.sendMessage("Failed to hook into DiscordSRV", MessageLevel.FATAL_ERROR);
            plugin.disablePlugin(true);
            return;
        }
        ConfigManager configManager = plugin.getConfigManager();
        // Get the channel if applicable
        TextChannel nullableChannel = jda.getTextChannelById(configManager.CHANNEL_ID);
        if (configManager.PUBLIC_CHANNEL_MESSAGES_ENABLED && nullableChannel != null)
            channel = nullableChannel;
        else if (configManager.PUBLIC_CHANNEL_MESSAGES_ENABLED) {
            MessageHelper.sendMessage("Invalid Discord channel specified in config", MessageLevel.FATAL_ERROR);
            plugin.disablePlugin(true);
        }
    }

    @Subscribe
    public void onAccountLinked(AccountLinkedEvent event) {
        Player player = event.getPlayer().getPlayer();
        if (player == null) {
            MessageHelper.sendMessage("Someone just linked their account but something went wrong!", MessageLevel.FATAL_ERROR);
            return;
        }
        PersistentDataUtils.setData(player, DataValue.DISCORD_ID, event.getUser().getId());
        DiscordMessageEvent.setDefaultSettings(player);
    }

    @Subscribe
    public void onAccountUnlinked(AccountUnlinkedEvent event) {
        Player player = event.getPlayer().getPlayer();
        if (player == null) {
            MessageHelper.sendMessage("Someone just unlinked their account but something went wrong!", MessageLevel.FATAL_ERROR);
            return;
        }
        PersistentDataUtils.removeAllData(player);
        MessageHelper.sendMessage(player, "Unlinked your Discord username", MessageLevel.INFO);
    }
}
