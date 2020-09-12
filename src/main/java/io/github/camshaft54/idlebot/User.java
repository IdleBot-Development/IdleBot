package io.github.camshaft54.idlebot;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String MCName;
    private Integer code;
    private String discordId;

    public User(String MCName, Integer code) {
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
