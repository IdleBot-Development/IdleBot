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
import io.github.camshaft54.idlebot.util.ConfigManager;
import io.github.camshaft54.idlebot.util.Messenger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class DiscordAPIManager {

//    public static DiscordApi api;
//    public static org.javacord.api.entity.user.User bot;
//    public static ServerTextChannel channel;
//
//    private final IdleBot plugin;
//
//    public DiscordAPIManager(IdleBot plugin) {
//        this.plugin = plugin;
//    }
//
//    public void consoleInfo() {
//        Messenger.sendMessage("Success! Connected to Discord as " + api.getYourself().getDiscriminatedName(), MessageLevel.INFO);
//        Messenger.sendMessage("Open the following url to invite the bot: " + api.createBotInvite(), MessageLevel.INFO);
//    }
//
//    public void connectToChannel() {
//        if (api.getServerTextChannelById(IdleBot.getConfigManager().CHANNEL_ID).isPresent()) {
//            channel = api.getServerTextChannelById(IdleBot.getConfigManager().CHANNEL_ID).get();
//        } else {
//            Messenger.sendMessage("Invalid Discord channel specified in config", MessageLevel.FATAL_ERROR);
//            plugin.disablePlugin();
//        }
//    }
//
//    public void setActivity() {
//        switch (IdleBot.getConfigManager().ACTIVITY_TYPE) {
//            case "PLAYING":
//                api.updateActivity(ActivityType.PLAYING, IdleBot.getConfigManager().ACTIVITY_MESSAGE);
//                break;
//            case "LISTENING":
//                api.updateActivity(ActivityType.LISTENING, IdleBot.getConfigManager().ACTIVITY_MESSAGE);
//                break;
//            case "WATCHING":
//                api.updateActivity(ActivityType.WATCHING, IdleBot.getConfigManager().ACTIVITY_MESSAGE);
//                break;
//        }
//    }
//
//    public void setDiscordIsReady() {
//        IdleBot.setDiscordAPIIsReady(true);
//    }
    private ConfigManager config;
    private JDA bot;

    public DiscordAPIManager() {
        config = IdleBot.getConfigManager();
        try {
            bot = setActivity(JDABuilder.createDefault(config.BOT_TOKEN)).build();
        } catch (LoginException e) {
            pass;
        }
    }

    private JDABuilder setActivity(JDABuilder jdaBuilder) {
        Activity activity;
        switch (config.ACTIVITY_TYPE) {
            case "WATCHING":
                activity = Activity.watching(config.ACTIVITY_MESSAGE);
            case "PLAYING":
                activity = Activity.playing(config.ACTIVITY_MESSAGE);
            case "LISTENING":
                activity = Activity.listening(config.ACTIVITY_MESSAGE);
            default:
                activity = null;
        }
        if (activity != null) {
            jdaBuilder.setActivity(activity);
        }
        return jdaBuilder;
    }
}
