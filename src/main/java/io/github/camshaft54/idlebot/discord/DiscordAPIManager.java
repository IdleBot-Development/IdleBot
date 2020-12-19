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

package io.github.camshaft54.idlebot.discord;

import io.github.camshaft54.idlebot.IdleBot;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.ServerTextChannel;

public class DiscordAPIManager {

    public static DiscordApi api;
    public static org.javacord.api.entity.user.User bot;
    public static ServerTextChannel channel;

    private final Plugin plugin;

    public DiscordAPIManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void consoleInfo() {
        plugin.getLogger().info(ChatColor.DARK_PURPLE + "[IdleBot] " + ChatColor.AQUA + "Success! Connected to Discord as " + api.getYourself().getDiscriminatedName());
        plugin.getLogger().info(ChatColor.DARK_PURPLE + "[IdleBot] " + ChatColor.AQUA + "Open the following url to invite the bot: " + api.createBotInvite());
    }

    public void connectToChannel() {
        if (api.getServerTextChannelById(IdleBot.getConfigManager().getChannelID()).isPresent()) {
            channel = api.getServerTextChannelById(IdleBot.getConfigManager().getChannelID()).get();
        } else { // TODO: Should this disable the plugin?
            plugin.getLogger().warning(ChatColor.DARK_PURPLE + "[IdleBot] " + ChatColor.DARK_RED + "Invalid Discord channel specified in config");
        }
    }

    public void setActivity() {
        switch (IdleBot.getConfigManager().getActivityType()) {
            case "PLAYING":
                api.updateActivity(ActivityType.PLAYING, IdleBot.getConfigManager().getActivityMessage());
                break;
            case "LISTENING":
                api.updateActivity(ActivityType.LISTENING, IdleBot.getConfigManager().getActivityMessage());
                break;
            case "WATCHING":
                api.updateActivity(ActivityType.WATCHING, IdleBot.getConfigManager().getActivityMessage());
                break;
        }
    }

    public void setDiscordIsReady() {
        IdleBot.setDiscordAPIIsReady(true);
    }

}
