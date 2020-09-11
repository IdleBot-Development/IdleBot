package io.github.camshaft54.idlebot.events;

import io.github.camshaft54.idlebot.IdleBot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.io.FileWriter;
import java.io.IOException;

public class DiscordEvents implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getChannel().getType() == ChannelType.PRIVATE_CHANNEL && !event.getMessageAuthor().isBotUser()) {
            Message message = event.getMessage();
            TextChannel channel = message.getChannel();
            try {
                int code = Integer.parseInt(message.getContent());
                Bukkit.getLogger().info("Someone entered a code: " + code);
                if (IdleBot.MCtoCode.containsKey(code)) {
                    try {
                        FileWriter writer = new FileWriter(IdleBot.playerLinks);
                        Player p = IdleBot.MCtoCode.get(code);
                        assert p != null;
                        String str = p.getDisplayName();
                        str += " : ";
                        str += message.getAuthor().getIdAsString();
                        writer.write(str);
                        writer.flush();
                        writer.close();
                        channel.sendMessage("Successfully linked your Discord user to " + IdleBot.MCtoCode.get(code).getDisplayName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    IdleBot.MCtoCode.remove(code);
                } else {
                    channel.sendMessage("Invalid Code. To get code type /idlebot link in Minecraft");
                }
            } catch (NumberFormatException e) {
            channel.sendMessage("Invalid Code. To get code type /idlebot link in Minecraft");
            }
        }
    }
}
