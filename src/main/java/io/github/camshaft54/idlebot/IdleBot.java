package io.github.camshaft54.idlebot;

import io.github.camshaft54.idlebot.events.DiscordEvents;
import io.github.camshaft54.idlebot.events.IdleBotEvents;
import io.github.camshaft54.idlebot.events.IdleChecker;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.ServerTextChannel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class IdleBot extends JavaPlugin {
    public static ServerTextChannel channel;
    FileConfiguration config = this.getConfig();
    private static String botToken;
    private static String channelId;
    public static HashMap<Integer, Player> MCtoCode = new HashMap<>(); // Placeholder Hashmap
    public static File playerLinks = new File("plugins/IdleBot/playerLinks.txt");


    @Override
    public void onEnable() {
        configSetup();
        // Connect to Discord
        DiscordApi api = new DiscordApiBuilder()
                .setToken(botToken) // Set the token of the bot here
                .login() // Log the bot in
                .join(); // Call #onConnectToDiscord(...) after a successful login
        getLogger().info("Connected to Discord as " + api.getYourself().getDiscriminatedName());
        getLogger().info("Open the following url to invite the bot: " + api.createBotInvite());
        api.addListener(new DiscordEvents());

        if (api.getServerTextChannelById(channelId).isPresent()) {
            channel = api.getServerTextChannelById(channelId).get();
        }
        else {
            getLogger().info("Channel not present");
        }
        getServer().getScheduler().runTaskTimer(this, new IdleChecker(), 20L, 20L); // Code in task should execute every 20 ticks (1 second)
        getServer().getPluginManager().registerEvents(new IdleBotEvents(), this);
    }

    private void configSetup() {
        config.addDefault("botToken", "<Bot Token Here>");
        config.addDefault("channelId", "<Channel Id Here>");
        config.options().copyDefaults(true);
        saveConfig();
        botToken = config.getString("botToken");
        channelId = config.getString("channelId");
        if (!playerLinks.exists()) {
            try {
                playerLinks.createNewFile();
            }
            catch (IOException e) {
                getLogger().warning("Error creating playerLinks.yml file!");
                e.printStackTrace();
            }
        }
        MCtoCode.put(1234, Bukkit.getPlayer("8df17d3f18dd4ce2a41b1a5c3cc3cd40"));
    }
}
