package io.github.camshaft54.idlebot.util;

@SuppressWarnings("SpellCheckingInspection")
public enum DataValues {
    AFKTIME ("afktime"),
    AFKMODE ("afkmode"),
    DISCORDID ("discordID"),
    ISSETAFK ("setafk"),
    LOCATIONALERT ("locationcheck"),
    DIRRECTMESSAGEMODE ("dmmode");

    private final String key;

    DataValues(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
