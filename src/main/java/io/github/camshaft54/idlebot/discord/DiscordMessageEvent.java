/*
 *    Copyright (C) 2020-2021 Camshaft54, MetalTurtle18
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
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.entity.Player;

public class DiscordMessageEvent extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getChannelType() == ChannelType.PRIVATE && !event.getAuthor().isBot()) {
            Message message = event.getMessage();
            MessageChannel channel = event.getChannel();
            try {
                int code = Integer.parseInt(message.getContentRaw());
                Messenger.sendMessage(event.getAuthor().getAsTag() + " entered a code: " + code, MessageLevel.INFO);
                if (IdleBot.linkCodes.containsKey(code)) {
                    Player player = IdleBot.linkCodes.get(code);
                    PersistentDataHandler.setData(player, DataValues.DISCORD_ID.key(), event.getAuthor().getId());
                    channel.sendMessage("Successfully linked your Discord username to Minecraft username " + player.getDisplayName()).queue();
                    message.addReaction("U+1F517").queue();
                    Messenger.sendMessage(player, "Successfully linked your Minecraft username to Discord username " + event.getAuthor().getAsTag(), MessageLevel.INFO);
                    IdleBot.linkCodes.remove(code);
                    // Since the player just linked,
                    setDefaultSettings(player);
                } else {
                    channel.sendMessage("Invalid Code. To get code type `/idlebot link` in Minecraft").queue();
                }
            } catch (NumberFormatException nfe) {
                channel.sendMessage("Invalid Code. To get code type `/idlebot link` in Minecraft").queue();
                nfe.printStackTrace();
            }
        }
    }

    // This method is to set up default values for every player when they link their account
    private void setDefaultSettings(Player player) {
        PersistentDataHandler.setData(player, DataValues.AFK_TIME.key(), IdleBot.getConfigManager().DEFAULT_IDLE_TIME);
        PersistentDataHandler.setData(player, DataValues.AUTO_AFK.key(), true);
    }
}
