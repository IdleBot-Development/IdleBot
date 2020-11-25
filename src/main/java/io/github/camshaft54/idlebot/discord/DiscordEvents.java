package io.github.camshaft54.idlebot.discord;

import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class DiscordEvents implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getChannel().getType() == ChannelType.PRIVATE_CHANNEL && !event.getMessageAuthor().isBotUser()) {
            Message message = event.getMessage();
            TextChannel channel = message.getChannel();
            try {
                Integer code = Integer.parseInt(message.getContent());
                Bukkit.getLogger().info("Someone entered a code: " + code);
                if (IdleBot.linkCodes.containsKey(code)) {
                    Player player = IdleBot.linkCodes.get(code);
                    PersistentDataHandler.setData(player, "discordId", event.getMessageAuthor().getIdAsString());
                    channel.sendMessage("Successfully linked your Discord username to Minecraft username " + player.getDisplayName());
                    IdleBot.linkCodes.remove(code);
                    // Since the player just linked,
                    setDefaultSettings(player);
                } else {
                    channel.sendMessage("Invalid Code. To get code type `/idlebot link` in Minecraft");
                }
            } catch (NumberFormatException e) {
                channel.sendMessage("Invalid Code. To get code type `/idlebot link` in Minecraft");
            }
        }
    }

    private void setDefaultSettings(Player player) {
        // Set default for afktime
        PersistentDataHandler.setData(player, "afktime", IdleBot.getConfigManager().getDefaultIdleTime());
    }
}
