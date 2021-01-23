/*
 *    Copyright (C) 2020 Camshaft54, MetalTurtle18
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.camshaft54.idlebot.discord;

import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.util.Messenger;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import io.github.camshaft54.idlebot.util.enums.DataValues;
import io.github.camshaft54.idlebot.util.enums.MessageLevel;
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
                Messenger.sendMessage(event.getMessageAuthor().getDiscriminatedName() + " entered a code: " + code, MessageLevel.INFO);
                if (IdleBot.linkCodes.containsKey(code)) {
                    Player player = IdleBot.linkCodes.get(code);
                    PersistentDataHandler.setData(player, DataValues.DISCORD_ID.key(), event.getMessageAuthor().getIdAsString());
                    channel.sendMessage("Successfully linked your Discord username to Minecraft username " + player.getDisplayName());
                    Messenger.sendMessage(player, "Successfully linked your Minecraft username to Discord username " + event.getMessageAuthor().getDiscriminatedName(), MessageLevel.INFO);
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

    // This method is to set up default values for every player when they link their account
    private void setDefaultSettings(Player player) {
        PersistentDataHandler.setData(player, DataValues.AFK_TIME.key(), IdleBot.getConfigManager().DEFAULT_IDLE_TIME);
        PersistentDataHandler.setData(player, DataValues.AUTO_AFK.key(), false);
    }
}
