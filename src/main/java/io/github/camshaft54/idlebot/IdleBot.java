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

package io.github.camshaft54.idlebot;

import github.scarsz.configuralize.ParseException;
import io.github.camshaft54.idlebot.commands.IdleBotCommandManager;
import io.github.camshaft54.idlebot.events.*;
import io.github.camshaft54.idlebot.util.ConfigManager;
import io.github.camshaft54.idlebot.util.Messenger;
import io.github.camshaft54.idlebot.util.enums.MessageLevel;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class IdleBot extends JavaPlugin {

    @Getter private static IdleBot plugin;
    @Getter private static ConfigManager configManager;
    @Getter private static EventManager eventManager;
    @Getter @Setter private static boolean discordAPIIsReady;

    public static HashMap<Integer, Player> linkCodes = new HashMap<>();
    public static HashMap<Player, Integer> idlePlayers = new HashMap<>();

    public JDA bot;

    @Override
    public void onEnable() {
        plugin = this;
        eventManager = new EventManager();
        try {
            configManager = new ConfigManager();
        }
        catch (IOException | ParseException e) {
            Messenger.sendMessage("Plugin configuration load failed! Plugin disabled. Try to fix the configuration file and try again or get support!", MessageLevel.FATAL_ERROR);
            disablePlugin();
        }
        if (plugin.isEnabled()) {
            BukkitScheduler scheduler = getServer().getScheduler();
            Objects.requireNonNull(plugin.getCommand("idlebot")).setExecutor(new IdleBotCommandManager());
            plugin.getServer().getScheduler().runTaskTimer(plugin, new IdleChecker(), 20L, 20L); // Execute the idle checker every 20 ticks (1 second)
            plugin.getServer().getScheduler().runTaskTimer(plugin, eventManager, 20L, 20L); // Check for all extra events (events that don't have official Bukkit events) every 20 ticks (1 second)
            plugin.getServer().getPluginManager().registerEvents(new OnMovement(), plugin); // Register movement event
            plugin.getServer().getPluginManager().registerEvents(new OnDamage(), plugin); // Register damage event
            plugin.getServer().getPluginManager().registerEvents(new OnDeath(), plugin); // Register death event
            plugin.getServer().getPluginManager().registerEvents(new OnPlayerQuit(), plugin); // Register player quit event
            discordAPIIsReady = false;
            //scheduler.runTaskAsynchronously(plugin, new DiscordAPIRunnable(plugin));
            Messenger.sendMessage("Starting to load JDA", MessageLevel.INFO);
            try {
                bot = JDABuilder.createDefault(IdleBot.getConfigManager().BOT_TOKEN)
                        .setActivity(Activity.watching("YAYAYAY JDA"))
                        .build();
                bot.awaitReady();
            } catch(LoginException | InterruptedException e) {
                Messenger.sendMessage("Failed to initialize JDA!", MessageLevel.FATAL_ERROR);
                this.disablePlugin();
            }
            MessageChannel channel = bot.getTextChannelById(configManager.CHANNEL_ID);
            assert channel != null;
            channel.sendMessage(new EmbedBuilder().setTitle("EMBED TITILEI").setAuthor("MR IDLE GBTO").setColor(Color.ORANGE).setFooter("ASBHDVCUYITFUOAYGIDUhkj").build()).queue();
            Messenger.sendMessage("Plugin successfully loaded", MessageLevel.INFO);
            // Messenger.sendMessage("Plugin has not finished initializing Discord API! Discord functionality is not yet ready!", MessageLevel.IMPORTANT);
        }
    }

    @Override
    public void onDisable() {
        Messenger.sendMessage("All data saved. Plugin safely closed!", MessageLevel.INFO);
    }

    public void disablePlugin() {
        Bukkit.getPluginManager().disablePlugin(plugin);
    }
}

