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

package io.github.idlebotdevelopment.idlebot;

import github.scarsz.configuralize.ParseException;
import io.github.idlebotdevelopment.idlebot.commands.IdleBotCommandManager;
import io.github.idlebotdevelopment.idlebot.commands.IdleBotTabCompleter;
import io.github.idlebotdevelopment.idlebot.discord.DiscordAPIManager;
import io.github.idlebotdevelopment.idlebot.events.*;
import io.github.idlebotdevelopment.idlebot.util.ConfigManager;
import io.github.idlebotdevelopment.idlebot.util.MessageHelper;
import io.github.idlebotdevelopment.idlebot.util.UpdateChecker;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.Callable;

public class IdleBot extends JavaPlugin {

    @Getter private static IdleBot plugin;
    @Getter private final HashMap<Integer, Player> linkCodes = new HashMap<>();
    @Getter private final HashMap<Player, Integer> idlePlayers = new HashMap<>();
    @Getter private ConfigManager configManager;
    @Getter private EventManager eventManager;
    @Getter private DiscordAPIManager discordAPIManager;
    @Getter private String localVersion;
    @Getter private String latestVersion;

    @Override
    public void onEnable() {
        plugin = this;
        eventManager = new EventManager();

        try {
            configManager = new ConfigManager();
        } catch (IOException | ParseException | InvalidConfigurationException e) {
            MessageHelper.sendMessage("Plugin configuration load failed! Plugin disabled. Try to fix the configuration file and try again or get support!", MessageLevel.FATAL_ERROR);
            e.printStackTrace();
            disablePlugin();
            return;
        }

        PluginCommand idleBotCommand = Objects.requireNonNull(this.getCommand("idlebot"));
        BukkitScheduler scheduler = getServer().getScheduler();
        PluginManager pluginManager = getServer().getPluginManager();

        idleBotCommand.setExecutor(new IdleBotCommandManager());
        idleBotCommand.setTabCompleter(new IdleBotTabCompleter());
        scheduler.runTaskTimer(this, new IdleChecker(), 20L, 20L); // Execute the idle checker every 20 ticks (1 second)
        scheduler.runTaskTimer(this, eventManager, 20L, 20L); // Check for all extra events (events that don't have official Bukkit events) every 20 ticks (1 second)

        pluginManager.registerEvents(new OnMovement(), this); // Register movement event
        pluginManager.registerEvents(new OnDamage(), this); // Register damage events
        pluginManager.registerEvents(new OnDeath(), this); // Register death event
        pluginManager.registerEvents(new OnPlayerQuit(), this); // Register player quit event
        pluginManager.registerEvents(new OnPlayerJoin(), this); // Register player join event
        pluginManager.registerEvents(new OnAdvancementDone(), this); // Register player advancement done event
        pluginManager.registerEvents(new OnItemBreak(), this); // Register item break event

        eventManager.registerCheck(new InventoryFull());
        eventManager.registerCheck(new XLocationReached());
        eventManager.registerCheck(new ZLocationReached());
        eventManager.registerCheck(new XPLevelReached());

        localVersion = this.getDescription().getVersion();
        new UpdateChecker(this).getVersion(version -> {
            latestVersion = version;
            if (!plugin.isEnabled()) return;
            if (localVersion.equals(latestVersion))
                MessageHelper.sendMessage("You are running the latest version! (" + localVersion + ")", MessageLevel.INFO);
            else
                MessageHelper.sendMessage("You are running an outdated version! (You are running version " + localVersion + " but the latest version is " + latestVersion + ")\nGo to https://www.spigotmc.org/resources/idlebot-step-up-your-afk-game.88778/ to download a new version", MessageLevel.IMPORTANT);
        });

        // Try to load JDA or connect to the DiscordSRV plugin
        try {
            discordAPIManager = new DiscordAPIManager();
        } catch (LoginException | InterruptedException e) {
            MessageHelper.sendMessage("Failed to load JDA or connect to DiscordSRV", MessageLevel.FATAL_ERROR);
            disablePlugin();
        }
    }

    @Override
    public void onDisable() {
        MessageHelper.sendMessage("All data saved. Plugin safely closed!", MessageLevel.INFO);
    }

    public void disablePlugin(boolean async) {
        if (async) {
            Bukkit.getScheduler().callSyncMethod(this, new Callable<Object>() {
                @Override
                public Object call() {
                    Bukkit.getPluginManager().disablePlugin(IdleBot.getPlugin());
                    return this;
                }
            });
        } else {
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public void disablePlugin() {
        disablePlugin(false);
    }
}

