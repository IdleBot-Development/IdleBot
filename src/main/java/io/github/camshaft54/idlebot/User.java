package io.github.camshaft54.idlebot;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String MCName;
    private String UUID;
    private Integer code;
    private String discordId;

    private HashMap<String, String> settings = new HashMap<>();

    public User(String MCName, String UUID, Integer code) {
        this.MCName = MCName;
        this.UUID = UUID;
        this.code = code;
    }

    public void setDiscordId(String discordId) {
        this.discordId = discordId;
    }

    public String getMCName() {
        return MCName;
    }

    public String getUUID() {
        return UUID;
    }

    public int getCode() {
        return code;
    }

    public String getDiscordId() {
        return discordId;
    }

    public void saveSetting(String key, String value) {
        settings.put(key, value);
    }

    public String getSetting(String key) {
        return settings.get(key);
    }
}
