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

package io.github.idlebotdevelopment.idlebot.commands;

import io.github.idlebotdevelopment.idlebot.IdleBot;
import io.github.idlebotdevelopment.idlebot.util.IdleBotCommand;
import io.github.idlebotdevelopment.idlebot.util.IdleBotUtils;
import io.github.idlebotdevelopment.idlebot.util.MessageHelper;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import org.bukkit.Bukkit;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

public class ClearDataCommand implements IdleBotCommand {
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<String> offlinePlayers = new ArrayList<>();
    private String warningMessage = "WARNING: this command will clear all your data associated with IdleBot, type \"y\" to continue or \"n\" to cancel.";

    @Override
    public String getCommandName() {
        return "cleardata";
    }

    @Override
    public String getCommandUsage() {
        return "/idlebot cleardata [player name | @online | @offline]";
    }

    @Override
    public boolean runCommand(Player player, String[] args) {
        if (args.length > 1) {
            if (player.isOp()) {
                if (Bukkit.getPlayer(args[1]) != null) {
                    warningMessage = "WARNING: this command will clear all of " + args[1] + "'s data associated with IdleBot, type \"y\" to continue or \"n\" to cancel.";
                    players.add(Bukkit.getPlayer(args[1]));
                } else if (args[1].equalsIgnoreCase("@online")) {
                    warningMessage = "WARNING: this command will clear all of the data associated with IdleBot for ALL players currently online, type \"y\" to continue or \"n\" to cancel.";
                    players.addAll(Bukkit.getOnlinePlayers());
                } else if (args[1].equalsIgnoreCase("@offline")) {
                    warningMessage = "WARNING: this command will clear all of the data associated with IdleBot for ALL players who have ever joined the server, type \"y\" to continue or \"n\" to cancel.";
                    new ArrayList<>(Arrays.asList(Bukkit.getOfflinePlayers())).forEach(offlinePlayer -> {
                        Player onlinePlayer = offlinePlayer.getPlayer();
                        if (onlinePlayer != null) {
                            players.add(onlinePlayer);
                        } else {
                            offlinePlayers.add(offlinePlayer.getUniqueId().toString());
                        }
                    });
                } else {
                    MessageHelper.sendMessage(player, "Cannot find player \"" + args[1] + "\"", MessageLevel.INCORRECT_COMMAND_USAGE);
                    return true;
                }
            } else {
                MessageHelper.sendMessage(player, "You don't have permission to clear the data of other players!", MessageLevel.INCORRECT_COMMAND_USAGE);
                return true;
            }
        } else {
            players.add(player);
        }
        ConversationFactory factory = new ConversationFactory(IdleBot.getPlugin())
                .withFirstPrompt(new WarningPrompt())
                .withLocalEcho(false);
        Conversation conversation = factory.buildConversation(player);
        conversation.begin();
        return true;
    }

    private static class YesPrompt extends MessagePrompt {
        @Nullable
        @Override
        protected Prompt getNextPrompt(@NotNull ConversationContext conversationContext) {
            return Prompt.END_OF_CONVERSATION;
        }

        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext conversationContext) {
            return "Data successfully deleted, thank you for using IdleBot!";
        }
    }

    private static class NoPrompt extends MessagePrompt {
        @Nullable
        @Override
        protected Prompt getNextPrompt(@NotNull ConversationContext conversationContext) {
            return Prompt.END_OF_CONVERSATION;
        }

        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext conversationContext) {
            return "Request to clear data cancelled.";
        }
    }

    private class WarningPrompt extends StringPrompt {
        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext conversationContext) {
            return warningMessage;
        }

        @Nullable
        @Override
        public Prompt acceptInput(@NotNull ConversationContext conversationContext, @Nullable String s) {
            if (s != null && (s.equalsIgnoreCase("y") || s.equalsIgnoreCase("yes"))) {
                for (Player player : players) {
                    PersistentDataUtils.removeAllData(player);
                }
                IdleBotUtils.saveListToDataFile(offlinePlayers, true);
                return new YesPrompt();
            } else {
                return new NoPrompt();
            }
        }
    }
}
