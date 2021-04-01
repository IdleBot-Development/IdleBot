package io.github.idlebotdevelopment.idlebot.discord;

import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.AccountLinkedEvent;
import github.scarsz.discordsrv.api.events.AccountUnlinkedEvent;
import github.scarsz.discordsrv.api.events.DiscordReadyEvent;
import io.github.idlebotdevelopment.idlebot.IdleBot;
import io.github.idlebotdevelopment.idlebot.discord.DiscordAPIManager;
import io.github.idlebotdevelopment.idlebot.discord.DiscordMessageEvent;
import io.github.idlebotdevelopment.idlebot.util.MessageHelper;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
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
        if (player == null) {
            MessageHelper.sendMessage("Someone just linked their account but something went wrong!", MessageLevel.FATAL_ERROR);
            return;
        }
        PersistentDataUtils.setData(player, DataValue.DISCORD_ID, event.getUser().getId());
        DiscordMessageEvent.setDefaultSettings(player);
    }

    @Subscribe
    public void accountsUnlinked(AccountUnlinkedEvent event) {
        Player player = event.getPlayer().getPlayer();
        if (player == null) {
            MessageHelper.sendMessage("Someone just unlinked their account but something went wrong!", MessageLevel.FATAL_ERROR);
            return;
        }
        PersistentDataUtils.removeAllData(player);
        MessageHelper.sendMessage(player, "Unlinked your Discord username", MessageLevel.INFO);
    }
}
