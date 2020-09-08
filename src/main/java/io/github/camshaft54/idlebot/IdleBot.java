package io.github.camshaft54.idlebot;

import io.github.camshaft54.idlebot.events.IdleBotEvents;
import io.github.camshaft54.idlebot.events.IdleChecker;
import org.bukkit.plugin.java.JavaPlugin;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.ServerTextChannel;

public class IdleBot extends JavaPlugin {
    public static ServerTextChannel channel;

    @Override
    public void onEnable() {
        getLogger().info("[IdleBot]: THIS PLUGIN WORKS!!!");
        // Connect to Discord
        DiscordApi api = new DiscordApiBuilder()
                .setToken("NzUyOTE5OTMxMzc2MzY5NzE0.X1epVA.MOQxOQt8BoRmn8nV_zwgf4AGJMg") // Set the token of the bot here
                .login() // Log the bot in
                .join(); // Call #onConnectToDiscord(...) after a successful login
        getLogger().info("Connected to Discord as " + api.getYourself().getDiscriminatedName());
        getLogger().info("Open the following url to invite the bot: " + api.createBotInvite());

        if (api.getServerTextChannelById("752921018150027404").isPresent()) {
            channel = api.getServerTextChannelById("752921018150027404").get();
        }
        else {
            getLogger().info("Channel not present");
        }
        getServer().getScheduler().runTaskTimer(this, new IdleChecker(), 20L, 20L); // Code in task should execute every 20 ticks (1 second)
        getServer().getPluginManager().registerEvents(new IdleBotEvents(), this);
    }
}
