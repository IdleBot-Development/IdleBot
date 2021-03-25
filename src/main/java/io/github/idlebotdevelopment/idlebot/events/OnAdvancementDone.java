package io.github.idlebotdevelopment.idlebot.events;

import io.github.idlebotdevelopment.idlebot.util.EventUtils;
import io.github.idlebotdevelopment.idlebot.util.MessageHelper;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class OnAdvancementDone implements Listener {
    @EventHandler
    public void onAdvancementDone(PlayerAdvancementDoneEvent e) {
        Player player = e.getPlayer();
        if (EventUtils.isIdle(player) && PersistentDataUtils.getBooleanData(player, DataValue.ADVANCEMENT_ALERT)) {
            MessageHelper.sendMessage(player.getDisplayName() + " is idle and completed the \"" + e.getAdvancement() + "\" advancement!", MessageLevel.INFO);
            EventUtils.sendPlayerMessage(player, player.getDisplayName() + " completed the \"" + e.getAdvancement() + "\" advancement.", player.getDisplayName() + " completed an advancement");
        }
    }
}
