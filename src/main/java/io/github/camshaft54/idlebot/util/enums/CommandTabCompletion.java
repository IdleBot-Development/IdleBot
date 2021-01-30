package io.github.camshaft54.idlebot.util.enums;

import lombok.Getter;

@SuppressWarnings("SpellCheckingInspection")
public enum CommandTabCompletion {
    AFKMODE(new String[]{"auto", "manual"}),
    AFKTIME(),
    ALERT(new String[]{"damage", "death", "xlocation", "zlocation", "xp", "inventory"}, new String[]{"true", "false"}),
    CHANNEL(new String[]{"public", "private"}),
    CLEARDATA(),
    LINK(),
    LOCATION(new String[]{"x", "z"}),
    AFK(new String[]{"true", "false"}),
    UNLINK(),
    XPLEVEL();

    @Getter final String[][] args;

    CommandTabCompletion(String[]... args) {
        this.args = args;
    }

    public static CommandTabCompletion get(String s) {
        for (CommandTabCompletion command : CommandTabCompletion.values()) {
            if (command.name().equalsIgnoreCase(s)) {
                return command;
            }
        }
        return null;
    }
}
