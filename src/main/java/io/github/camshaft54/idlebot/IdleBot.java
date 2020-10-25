package io.github.camshaft54.idlebot;

import io.github.camshaft54.idlebot.commands.IdleBotCommandManager;
import io.github.camshaft54.idlebot.events.DiscordEvents;
import io.github.camshaft54.idlebot.events.IdleBotEvents;
import io.github.camshaft54.idlebot.events.IdleChecker;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.ServerTextChannel;

import java.util.HashMap;
import java.util.Objects;

public class IdleBot extends JavaPlugin {

    FileConfiguration config = this.getConfig();

    private static String botToken;
    private static String channelId;
    private static String activityType;
    private static String activityMessage;
    private static IdleBot plugin;

    // Declare global static variables
    public static ServerTextChannel channel;
    public static int idleTime;
    public static HashMap<Integer, Player> linkCodes = new HashMap<>();
    public static DiscordApi api;
    public static org.javacord.api.entity.user.User bot;

    public static IdleBot getPlugin() {
        return plugin;
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "[IdleBot] All data saved. Plugin can safely close!");
    }

    private void configSetup() {
        config.addDefault("botToken", "<Bot Token Here>");
        config.addDefault("channelId", "<Channel Id Here>");
        //config.addDefault("customBotActivity");
        config.addDefault("customBotActivity.type", "WATCHING");
        config.addDefault("customBotActivity.message", "idle players");
        config.addDefault("idleTime", 120);
        config.options().copyDefaults(true);
        saveConfig();
        botToken = config.getString("botToken");
        channelId = config.getString("channelId");
        activityType = config.getString("customBotActivity.type");
        activityMessage = config.getString("customBotActivity.message");
        idleTime = config.getInt("idleTime");
    }

    @Override
    public void onEnable() {
        plugin = this;
        configSetup();
        // Connect to Discord
        api = new DiscordApiBuilder()
                .setToken(botToken) // Set the token of the bot here
                .login() // Log the bot in
                .join(); // Call #onConnectToDiscord(...) after a successful login
        getLogger().info("Connected to Discord as " + api.getYourself().getDiscriminatedName());
        getLogger().info("Open the following url to invite the bot: " + api.createBotInvite());
        if (api.getServerTextChannelById(channelId).isPresent()) {
            channel = api.getServerTextChannelById(channelId).get();
        } else {
            getLogger().info("Channel not present");
        }
        api.addListener(new DiscordEvents());
        bot = api.getYourself();
        switch (activityType) {
            case "PLAYING":
                api.updateActivity(ActivityType.PLAYING, activityMessage);
                break;
            case "LISTENING":
                api.updateActivity(ActivityType.LISTENING, activityMessage);
                break;
            case "WATCHING":
                api.updateActivity(ActivityType.WATCHING, activityMessage);
                break;
        }
        Objects.requireNonNull(getCommand("idlebot")).setExecutor(new IdleBotCommandManager());
        getServer().getScheduler().runTaskTimer(this, new IdleChecker(), 20L, 20L); // Code in task should execute every 20 ticks (1 second)
        getServer().getPluginManager().registerEvents(new IdleBotEvents(), this);
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "[IdleBot] Plugin successfully loaded!");
    }
}

