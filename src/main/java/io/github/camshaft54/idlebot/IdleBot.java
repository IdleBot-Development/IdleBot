package io.github.camshaft54.idlebot;

import io.github.camshaft54.idlebot.commands.IdleBotCommandManager;
import io.github.camshaft54.idlebot.events.DiscordEvents;
import io.github.camshaft54.idlebot.events.IdleBotEvents;
import io.github.camshaft54.idlebot.events.IdleChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.ServerTextChannel;

import java.util.HashMap;
import java.util.Objects;

public class IdleBot extends JavaPlugin {

    FileConfiguration config = this.getConfig();
    private static IdleBot plugin;

    public static boolean discordAPIIsReady;
    public static int idleTime;
    public static HashMap<Integer, Player> linkCodes = new HashMap<>();
    public static org.javacord.api.entity.user.User bot;

    public static IdleBot getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        BukkitScheduler scheduler = getServer().getScheduler();
        Objects.requireNonNull(plugin.getCommand("idlebot")).setExecutor(new IdleBotCommandManager());
        plugin.getServer().getScheduler().runTaskTimer(plugin, new IdleChecker(), 20L, 20L); // Code in task should execute every 20 ticks (1 second)
        plugin.getServer().getPluginManager().registerEvents(new IdleBotEvents(), plugin);
        discordAPIIsReady = false;
        scheduler.runTaskAsynchronously(plugin, new DiscordAPIRunnable(plugin));
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "[IdleBot] Plugin successfully loaded");
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "[IdleBot] Note: Plugin has not finished initializing Discord API! Discord functionality is not yet ready!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "[IdleBot] All data saved. Plugin can safely close!");
    }

    public void disablePlugin() {
        Bukkit.getPluginManager().disablePlugin(plugin);
    }
}

