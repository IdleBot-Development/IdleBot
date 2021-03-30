package io.github.idlebotdevelopment.idlebot.events;

import io.github.idlebotdevelopment.idlebot.util.IdleBotUtils;
import io.github.idlebotdevelopment.idlebot.util.MessageHelper;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

public class OnItemBreak implements Listener {
    @EventHandler
    public void onItemBreak(PlayerItemBreakEvent e) {
        Player player = e.getPlayer();
        if (IdleBotUtils.isIdle(player) && PersistentDataUtils.getBooleanData(player, DataValue.TOOL_BREAK_ALERT)) {
            ItemStack item = e.getBrokenItem();
            MessageHelper.sendMessage(player.getDisplayName() + " is idle and broke a tool!", MessageLevel.INFO);
            IdleBotUtils.sendPlayerMessage(player, player.getDisplayName() + " broke " + ((item.getItemMeta() != null) ? item.getItemMeta().getDisplayName() : item.getType().name()) + ".", player.getDisplayName() + " broke a tool");
        }
    }
}
