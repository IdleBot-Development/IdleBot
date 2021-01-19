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
import net.dv8tion.jda.api.JDABuilder;
import org.javacord.api.DiscordApiBuilder;

public class DiscordAPIRunnable implements Runnable {
    private final DiscordAPIManager manager;
    private final IdleBot plugin;

    public DiscordAPIRunnable(IdleBot plugin) {
        this.manager = new DiscordAPIManager(plugin);
        this.plugin = plugin;
    }

    @Override
    public void run() {
        try {
            DiscordAPIManager.api = new DiscordApiBuilder().setToken(IdleBot.getConfigManager().BOT_TOKEN).login().join(); // Call #onConnectToDiscord(...) after a successful login
            DiscordAPIManager.api.addListener(new DiscordEvents());
            DiscordAPIManager.bot = DiscordAPIManager.api.getYourself();
            manager.consoleInfo(); // Send some information to the console once the API is functional
            manager.connectToChannel(); // Connect the bot to the text channel specified in config
            manager.setActivity(); // Set the status of the bot in Discord
            manager.setDiscordIsReady();

        } catch (Exception e) {
            Messenger.sendMessage("Error connecting to Discord! Plugin configuration file may be invalid. botToken and/or channelToken need to be set to valid values in config.yml.", MessageLevel.FATAL_ERROR);
            plugin.disablePlugin();
        }
    }
}
