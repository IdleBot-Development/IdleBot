package io.github.camshaft54.idlebot;

public class User {
    private final String MCName;
    private final Integer code;
    private String discordId;

    User(String MCName, Integer code) {
        this.MCName = MCName;
        this.code = code;
    }

    public void setDiscordId(String discordId) {
        this.discordId = discordId;
    }

    public String getMCName() {
        return MCName;
    }

    public int getCode() {
        return code;
    }

    public String getDiscordId() {
        return discordId;
    }
}
