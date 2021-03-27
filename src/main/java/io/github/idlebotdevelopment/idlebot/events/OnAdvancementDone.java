package io.github.idlebotdevelopment.idlebot.events;

import io.github.idlebotdevelopment.idlebot.util.EventUtils;
import io.github.idlebotdevelopment.idlebot.util.MessageHelper;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import org.apache.commons.lang.StringUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OnAdvancementDone implements Listener {
    @EventHandler
    public void onAdvancementDone(PlayerAdvancementDoneEvent e) {
        Player player = e.getPlayer();
        if (EventUtils.isIdle(player) && PersistentDataUtils.getBooleanData(player, DataValue.ADVANCEMENT_ALERT)) {
            NamespacedKey rawAdvancementKey = e.getAdvancement().getKey();
            String rawAdvancementName = rawAdvancementKey.getNamespace() + ":" + rawAdvancementKey.getKey();
            String desiredAdvancementName = PersistentDataUtils.getStringData(player, DataValue.ADVANCEMENT_DESIRED);
            if (((desiredAdvancementName == null || desiredAdvancementName.equals("non-recipe")) && !rawAdvancementName.startsWith("minecraft:recipes")) || (desiredAdvancementName != null && (desiredAdvancementName.equals(rawAdvancementName) || desiredAdvancementName.equals("all")))) {
                String advancementName = "";
                if (rawAdvancementName.startsWith("minecraft:recipes")) {
                    advancementName = "Recipe for ";
                }
                advancementName += Stream.of(rawAdvancementName.substring(rawAdvancementName.lastIndexOf("/") + 1).split("_")).map(StringUtils::capitalize).collect(Collectors.joining(" "));
                MessageHelper.sendMessage(player.getDisplayName() + " is idle and completed the \"" + advancementName + "\" advancement!", MessageLevel.INFO);
                EventUtils.sendPlayerMessage(player, player.getDisplayName() + " completed the \"" + advancementName + "\" advancement.", player.getDisplayName() + " completed an advancement");
            }
        }
    }
}
