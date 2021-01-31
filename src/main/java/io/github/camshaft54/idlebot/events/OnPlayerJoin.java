package io.github.camshaft54.idlebot.events;

import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.util.EventUtils;
import io.github.camshaft54.idlebot.util.Messenger;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import io.github.camshaft54.idlebot.util.enums.MessageLevel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class OnPlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        // Code to remove data if the player was offline when the command was run
        ArrayList<String> offlinePlayers = new ArrayList<>();
        try {
            if (new File(IdleBot.getPlugin().getDataFolder() + "/data.txt").exists()) {
                FileReader fileReader = new FileReader(IdleBot.getPlugin().getDataFolder() + "/data.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                offlinePlayers = (ArrayList<String>) Arrays.asList(bufferedReader.readLine().split(","));
                bufferedReader.close();
                // Remove duplicates in ArrayList
                offlinePlayers = (ArrayList<String>) offlinePlayers.stream().distinct().collect(Collectors.toList());
            }
        } catch (Exception exception) {
            Messenger.sendMessage("Error reading data file!", MessageLevel.FATAL_ERROR);
        }
        String joinedPlayerUUID = e.getPlayer().getUniqueId().toString();
        for (String offlinePlayer : offlinePlayers) {
            if (offlinePlayers.contains(joinedPlayerUUID)) {
                offlinePlayers.remove(offlinePlayer);
                PersistentDataHandler.removeAllData(e.getPlayer());
                EventUtils.saveListToDataFile(offlinePlayers, false);
                break;
            }
        }

        // Code to check for available update
        if (e.getPlayer().isOp()) {
            if (!IdleBot.getLocalVersion().equals(IdleBot.getLatestVersion()) && !(IdleBot.getLocalVersion() == null || IdleBot.getLatestVersion() == null))
                Messenger.sendMessage(e.getPlayer(), "You are running an outdated version! (You are running version " + IdleBot.getLocalVersion() + " but the latest version is " + IdleBot.getLatestVersion() + ". Go to https://github.com/CamShaft54/IdleBot/releases to download a new version", MessageLevel.IMPORTANT);
        }
    }
}
