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

package io.github.idlebotdevelopment.idlebot.discord;

import io.github.idlebotdevelopment.idlebot.IdleBot;
import io.github.idlebotdevelopment.idlebot.util.ConfigManager;
import io.github.idlebotdevelopment.idlebot.util.MessageHelper;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.Presence;

import javax.security.auth.login.LoginException;

public class DiscordAPIManager {
    private final ConfigManager config;
    private final IdleBot plugin;
    public JDA bot;
    public static TextChannel channel;

    public DiscordAPIManager(IdleBot plugin) {
        config = IdleBot.getConfigManager();
        this.plugin = plugin;
        try {
            bot = JDABuilder.createDefault(config.BOT_TOKEN).build();
            bot.awaitReady();
        } catch (LoginException | InterruptedException e) {
            MessageHelper.sendMessage("Failed to initialize JDA!", MessageLevel.FATAL_ERROR);
            e.printStackTrace();
            plugin.disablePlugin();
        }
        bot.addEventListener(new DiscordMessageEvent());
        setActivity();
        getChannel();
        MessageHelper.sendMessage("Successfully connected to Discord as " + bot.getSelfUser().getAsTag(), MessageLevel.INFO);
        MessageHelper.sendMessage("Open the following url to invite the bot: " + bot.getInviteUrl(), MessageLevel.INFO);
    }

    private void setActivity() {
        Presence presence = bot.getPresence();
        switch (config.ACTIVITY_TYPE.toUpperCase()) {
            case "WATCHING" -> presence.setPresence(Activity.watching(config.ACTIVITY_MESSAGE), false);
            case "PLAYING" -> presence.setPresence(Activity.playing(config.ACTIVITY_MESSAGE), false);
            case "LISTENING" -> presence.setPresence(Activity.listening(config.ACTIVITY_MESSAGE), false);
            default -> {}
        }
    }

    private void getChannel() {
        if (!config.PUBLIC_CHANNEL_MESSAGES_ENABLED) return;
        if (bot.getTextChannelById(config.CHANNEL_ID) != null) {
            channel = bot.getTextChannelById(config.CHANNEL_ID);
        } else {
            MessageHelper.sendMessage("Invalid Discord channel specified in config", MessageLevel.FATAL_ERROR);
            plugin.disablePlugin();
        }
    }
}
