package io.github.camshaft54.idlebot.commands;

import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ClearDataCommand implements IdleBotCommand {
    private ArrayList<Player> players = new ArrayList<>();

    @Override
    public String getCommandName() {
        return "cleardata";
    }

    @Override
    public String getCommandUsage() {
        return "/idlebot cleardata [player name (admin-only)]";
    }

    @Override
    public boolean runCommand(Player commandSender, String[] args) {
        if (args[1] != null) {
            if (commandSender.isOp()) {
                if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
                    players.add(Bukkit.getPlayer(args[1]));
                } else if (args[1].equalsIgnoreCase("@a")) {
                    players.addAll(Bukkit.getOnlinePlayers());
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            players.add(commandSender);
        }
        ConversationFactory factory = new ConversationFactory(IdleBot.getPlugin())
                .withFirstPrompt(new WarningPrompt())
                .withLocalEcho(false);
        Conversation conversation = factory.buildConversation(commandSender);
        conversation.begin();
        return true;
    }

    private class WarningPrompt extends StringPrompt {
        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext conversationContext) {
            return "WARNING: this command will clear all your data associated with IdleBot, type \"y\" to continue or \"n\" to cancel.";
        }

        @Nullable
        @Override
        public Prompt acceptInput(@NotNull ConversationContext conversationContext, @Nullable String s) {
            if (s != null && (s.equalsIgnoreCase("y") || s.equalsIgnoreCase("yes"))) {
                for (Player player : players) {
                    PersistentDataHandler.removeAllData(player);
                }
                return new YesPrompt();
            } else {
                return new NoPrompt();
            }
        }
    }

    private class YesPrompt extends MessagePrompt {
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

    private class NoPrompt extends MessagePrompt {
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
}
