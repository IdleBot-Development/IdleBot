package io.github.camshaft54.idlebot.util;

@SuppressWarnings("SpellCheckingInspection")
public enum DataValues {
    AFK_TIME("afktime"),
    AFK_MODE("afkmode"),
    DISCORD_ID("discordID"),
    IS_SET_AFK("setafk"),
    LOCATION_ALERT("locationcheck"),
    DIRRECT_MESSAGE_MODE("dmmode");

    private final String key;

    DataValues(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
