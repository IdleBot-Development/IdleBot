package io.github.camshaft54.idlebot;

public class User {
    private String uuid;
    private String MCName;
    private Integer code;
    private String discordId;

    User(String uuid, String MCName, Integer code) {
        this.uuid = uuid;
        this.MCName = MCName;
        this.code = code;
    }

    public void setDiscordId(String discordId) {
        this.discordId = discordId;
    }

    public String getUUID() {
        return uuid;
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
