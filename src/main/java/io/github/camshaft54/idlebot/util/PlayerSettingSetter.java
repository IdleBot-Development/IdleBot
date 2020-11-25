package io.github.camshaft54.idlebot.util;

import org.bukkit.entity.Player;

public abstract class PlayerSettingSetter {

    public abstract String getSettingName();

    public abstract void setSetting(Player player, String[] args);
}
