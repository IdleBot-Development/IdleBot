package io.github.camshaft54.idlebot;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import io.github.camshaft54.idlebot.commands.MainCommandDispatcher;
import io.github.camshaft54.idlebot.events.DiscordEvents;
import io.github.camshaft54.idlebot.events.IdleBotEvents;
import io.github.camshaft54.idlebot.events.IdleChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.ServerTextChannel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class IdleBot extends JavaPlugin {
    public static ServerTextChannel channel;
    FileConfiguration config = this.getConfig();
    private static String botToken;
    private static String channelId;
    //public static HashMap<Player, Integer> linkTokens = new HashMap<>();
    public static HashMap<String,User> users = new HashMap<>();
    public static org.javacord.api.entity.user.User bot;
    public static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    public static File userFile = new File("plugins/IdleBot/users.yml");


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
        bot = api.getYourself();

        if (api.getServerTextChannelById(channelId).isPresent()) {
            channel = api.getServerTextChannelById(channelId).get();
        }
        else {
            getLogger().info("Channel not present");
        }
        Objects.requireNonNull(getCommand("idlebot")).setExecutor(new MainCommandDispatcher());
        getServer().getScheduler().runTaskTimer(this, new IdleChecker(), 20L, 20L); // Code in task should execute every 20 ticks (1 second)
        getServer().getPluginManager().registerEvents(new IdleBotEvents(), this);
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "[IdleBot] Plugin successfully loaded!");
    }

    @Override
    public void onDisable() {
        saveUsers();
    }

    private void configSetup() {
        config.addDefault("botToken", "<Bot Token Here>");
        config.addDefault("channelId", "<Channel Id Here>");
        config.options().copyDefaults(true);
        saveConfig();
        botToken = config.getString("botToken");
        channelId = config.getString("channelId");

        if (userFile.exists()) {
            try {
                userFile.createNewFile();
            } catch (IOException e) {
                getLogger().warning("Unable to create user file!");
                e.printStackTrace();
            }
        }
        mapper.findAndRegisterModules();
        mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.ANY);
        getUsers();
    }

    public static void saveUsers() {
        try {
            mapper.writeValue(userFile, users);
        } catch (IOException e) {
            Bukkit.getLogger().warning("Error saving user data to users.yml");
            e.printStackTrace();
        }
    }

    public static void getUsers() {
        try {
            users.clear();
            users = mapper.readValue(userFile, new TypeReference<HashMap<String, User>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

