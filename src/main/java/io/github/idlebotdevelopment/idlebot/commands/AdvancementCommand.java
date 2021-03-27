package io.github.idlebotdevelopment.idlebot.commands;

import io.github.idlebotdevelopment.idlebot.util.IdleBotCommand;
import io.github.idlebotdevelopment.idlebot.util.MessageHelper;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class AdvancementCommand implements IdleBotCommand {

    @Override
    public String getCommandName() {
        return "advancement";
    }

    @Override
    public String getCommandUsage() {
        return "/idlebot advancement <desired advancement>";
    }

    @Override
    public boolean runCommand(Player player, String[] args) {
        if (args.length >= 2) {
            Iterator<Advancement> iterator = Bukkit.advancementIterator();
            while (iterator.hasNext()) {
                NamespacedKey key = iterator.next().getKey();
                if (args[1].equalsIgnoreCase(key.getNamespace() + ":" + key.getKey())) {
                    PersistentDataUtils.setData(player, DataValue.ADVANCEMENT_DESIRED, key.getNamespace() + ":" + key.getKey());
                    MessageHelper.sendMessage(player, "Set your desired advancement to " + key.getNamespace() + ":" + key.getKey(), MessageLevel.INFO);
                    return true;
                } else if (args[1].equalsIgnoreCase("all") || args[1].equalsIgnoreCase("non-recipe")) {
                    PersistentDataUtils.setData(player, DataValue.ADVANCEMENT_DESIRED, args[1].toLowerCase());
                    MessageHelper.sendMessage(player, "Set your desired advancement type to " + ((args[1].equalsIgnoreCase("all")) ? "all advancements" : "non-recipe advancements"), MessageLevel.INFO);
                    return true;
                }
            }
        }
        return false;
    }
}
