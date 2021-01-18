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
import io.github.camshaft54.idlebot.util.Messenger;
import io.github.camshaft54.idlebot.util.enums.MessageLevel;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.ServerTextChannel;

public class DiscordAPIManager {

    public static DiscordApi api;
    public static org.javacord.api.entity.user.User bot;
    public static ServerTextChannel channel;

    private final IdleBot plugin;

    public DiscordAPIManager(IdleBot plugin) {
        this.plugin = plugin;
    }

    public void consoleInfo() {
        Messenger.sendMessage("Success! Connected to Discord as " + api.getYourself().getDiscriminatedName(), MessageLevel.INFO);
        Messenger.sendMessage("Open the following url to invite the bot: " + api.createBotInvite(), MessageLevel.INFO);
    }

    public void connectToChannel() {
        if (api.getServerTextChannelById(IdleBot.getConfigManager().CHANNEL_ID).isPresent()) {
            channel = api.getServerTextChannelById(IdleBot.getConfigManager().CHANNEL_ID).get();
        } else {
            Messenger.sendMessage("Invalid Discord channel specified in config", MessageLevel.FATAL_ERROR);
            plugin.disablePlugin();
        }
    }

    public void setActivity() {
        switch (IdleBot.getConfigManager().ACTIVITY_TYPE) {
            case "PLAYING":
                api.updateActivity(ActivityType.PLAYING, IdleBot.getConfigManager().ACTIVITY_MESSAGE);
                break;
            case "LISTENING":
                api.updateActivity(ActivityType.LISTENING, IdleBot.getConfigManager().ACTIVITY_MESSAGE);
                break;
            case "WATCHING":
                api.updateActivity(ActivityType.WATCHING, IdleBot.getConfigManager().ACTIVITY_MESSAGE);
                break;
        }
    }

    public void setDiscordIsReady() {
        IdleBot.setDiscordAPIIsReady(true);
    }

}
