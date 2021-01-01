package io.github.camshaft54.idlebot.util;

import org.bukkit.entity.Player;

public interface IdleCheck {
    String getDataValue();
    void check(Player player);
}
