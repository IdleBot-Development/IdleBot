package io.github.idlebotdevelopment.idlebot.events;

import io.github.idlebotdevelopment.idlebot.util.IdleBotUtils;
import io.github.idlebotdevelopment.idlebot.util.MessageHelper;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import org.apache.commons.lang.WordUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Stream;

public class OnItemBreak implements Listener {
    @EventHandler
    public void onItemBreak(PlayerItemBreakEvent e) {
        Player player = e.getPlayer();
        ItemStack item = e.getBrokenItem();
        boolean isTool = Stream.of(e.getBrokenItem().getType().toString().split("_"))
                .noneMatch(s -> s.equalsIgnoreCase("helmet") || s.equalsIgnoreCase("chestplate") ||
                        s.equalsIgnoreCase("leggings") || s.equalsIgnoreCase("boots") || s.equalsIgnoreCase("elytra") || s.equalsIgnoreCase("shield"));
        if (IdleBotUtils.isIdle(player) && PersistentDataUtils.getBooleanData(player, DataValue.TOOL_BREAK_ALERT) && isTool) {
            MessageHelper.sendMessage(player.getDisplayName() + " is idle and broke a tool!", MessageLevel.INFO);
            IdleBotUtils.sendPlayerMessage(player, player.getDisplayName() + " broke " + ((item.getItemMeta() != null && !item.getItemMeta().getDisplayName().equals("")) ? item.getItemMeta().getDisplayName() : "a(n) " + WordUtils.capitalize(item.getType().toString().replace("_", " ").toLowerCase())) + ".", player.getDisplayName() + " broke a tool");
        }
    }
}
