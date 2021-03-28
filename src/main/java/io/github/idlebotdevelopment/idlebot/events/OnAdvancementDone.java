package io.github.idlebotdevelopment.idlebot.events;

import io.github.idlebotdevelopment.idlebot.util.IdleBotUtils;
import io.github.idlebotdevelopment.idlebot.util.MessageHelper;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class OnAdvancementDone implements Listener {
    @EventHandler
    public void onAdvancementDone(PlayerAdvancementDoneEvent e) {
        Player player = e.getPlayer();
        if (IdleBotUtils.isIdle(player) && PersistentDataUtils.getBooleanData(player, DataValue.ADVANCEMENT_ALERT)) {
            NamespacedKey rawAdvancementKey = e.getAdvancement().getKey();
            String rawAdvancementName = rawAdvancementKey.getNamespace() + ":" + rawAdvancementKey.getKey();
            String desiredAdvancementName = PersistentDataUtils.getStringData(player, DataValue.ADVANCEMENT_DESIRED);
            if (((desiredAdvancementName == null || desiredAdvancementName.equals("non-recipe")) && !rawAdvancementName.startsWith("minecraft:recipes")) || (desiredAdvancementName != null && (desiredAdvancementName.equals(rawAdvancementName) || desiredAdvancementName.equals("all")))) {
                String prettyAdvancementName = IdleBotUtils.prettyPrintPlayerDesiredAdvancement(rawAdvancementName);
                MessageHelper.sendMessage(player.getDisplayName() + " is idle and completed the \"" + prettyAdvancementName + "\" advancement!", MessageLevel.INFO);
                IdleBotUtils.sendPlayerMessage(player, player.getDisplayName() + " completed the \"" + prettyAdvancementName + "\" advancement.", player.getDisplayName() + " completed an advancement");
            }
        }
    }
}
