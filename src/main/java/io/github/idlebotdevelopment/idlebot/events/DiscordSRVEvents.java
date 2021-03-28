package io.github.idlebotdevelopment.idlebot.events;

import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.AccountLinkedEvent;
import github.scarsz.discordsrv.api.events.DiscordReadyEvent;
import io.github.idlebotdevelopment.idlebot.IdleBot;
import io.github.idlebotdevelopment.idlebot.discord.DiscordAPIManager;
import io.github.idlebotdevelopment.idlebot.discord.DiscordMessageEvent;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import org.bukkit.entity.Player;

public class DiscordSRVEvents {

    private final IdleBot plugin;

    public DiscordSRVEvents(IdleBot plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void discordReadyEvent(DiscordReadyEvent event) {
        plugin.setDiscordAPIManager(new DiscordAPIManager(plugin, true));
    }

    @Subscribe
    public void accountsLinked(AccountLinkedEvent event) {
        Player player = event.getPlayer().getPlayer();
        assert player != null;
        PersistentDataUtils.setData(player, DataValue.DISCORD_ID, event.getUser().getId());
        DiscordMessageEvent.setDefaultSettings(player);
    }
}
