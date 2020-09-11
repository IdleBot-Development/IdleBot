package io.github.camshaft54.idlebot.events;

import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.User;
import org.bukkit.Bukkit;
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
                int code = Integer.parseInt(message.getContent());
                Bukkit.getLogger().info("Someone entered a code: " + code);
                String playerId = getUserFromCode(code);
                User user = IdleBot.users.get(playerId);
                if (user != null) {
                    user.setDiscordId(event.getMessageAuthor().getIdAsString());
                    IdleBot.users.replace(playerId, user);
                    channel.sendMessage("Successfully linked your Discord username to Minecraft username " + user.getMCName());
                } else {
                    channel.sendMessage("Invalid Code. To get code type /idlebot link in Minecraft");
                }
            } catch (NumberFormatException e) {
                channel.sendMessage("Invalid Code. To get code type /idlebot link in Minecraft");
            }
        }
    }

    private String getUserFromCode(int code) {
        for (String key : IdleBot.users.keySet()) {
            User value = IdleBot.users.get(key);
            if (value.getCode() == code) {
                return key;
            }
        }
        return null;
    }
}
