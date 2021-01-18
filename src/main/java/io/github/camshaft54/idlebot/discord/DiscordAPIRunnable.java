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
import org.bukkit.plugin.Plugin;
import org.javacord.api.DiscordApiBuilder;

public class DiscordAPIRunnable implements Runnable {
    private final DiscordAPIManager manager;

    public DiscordAPIRunnable(IdleBot plugin) {
        this.manager = new DiscordAPIManager(plugin);
    }

    @Override
    public void run() {
        DiscordAPIManager.api = new DiscordApiBuilder().setToken(IdleBot.getConfigManager().BOT_TOKEN).login().join(); // Call #onConnectToDiscord(...) after a successful login
        DiscordAPIManager.api.addListener(new DiscordEvents());
        DiscordAPIManager.bot = DiscordAPIManager.api.getYourself();
        manager.consoleInfo(); // Send some information to the console once the API is functional
        manager.connectToChannel(); // Connect the bot to the text channel specified in config
        manager.setActivity(); // Set the status of the bot in Discord
        manager.setDiscordIsReady();
    }
}
