package io.github.camshaft54.idlebot.util.enums;

import org.bukkit.ChatColor;

public enum MessageLevel {
    INFO(ChatColor.AQUA),
    FATAL_ERROR(ChatColor.DARK_RED),
    INCORRECT_COMMAND_USAGE(ChatColor.BLUE),
    IMPORTANT(ChatColor.GREEN);

    private final ChatColor color;
    MessageLevel(ChatColor color) {
        this.color = color;
    }

    public String getPrefix() {
        return ChatColor.DARK_PURPLE + "[IdleBot] " + color;
    }
}
