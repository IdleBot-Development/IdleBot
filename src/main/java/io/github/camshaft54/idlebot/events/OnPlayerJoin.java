package io.github.camshaft54.idlebot.events;

import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.util.Messenger;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import io.github.camshaft54.idlebot.util.enums.MessageLevel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class OnPlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        ArrayList<String> offlinePlayers = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(IdleBot.getPlugin().getDataFolder() + "/data.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            offlinePlayers = (ArrayList<String>) Arrays.asList(bufferedReader.readLine().split(","));
        } catch (Exception exception) {
            Messenger.sendMessage("Error reading data file!", MessageLevel.FATAL_ERROR);
        }
        String joinedPlayerUUID = e.getPlayer().getUniqueId().toString();
        for (String offlinePlayer : offlinePlayers) {
            if (offlinePlayers.contains(joinedPlayerUUID)) {
                offlinePlayers.remove(offlinePlayer);
                PersistentDataHandler.removeAllData(e.getPlayer());
                break;
            }
        }
    }
}
