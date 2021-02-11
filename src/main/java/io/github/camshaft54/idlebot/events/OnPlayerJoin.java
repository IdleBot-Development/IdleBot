/*
 *    Copyright (C) 2020-2021 Camshaft54, MetalTurtle18
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.camshaft54.idlebot.events;

import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.util.EventUtils;
import io.github.camshaft54.idlebot.util.MessageHelper;
import io.github.camshaft54.idlebot.util.PersistentDataUtils;
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
            if (new File(IdleBot.getPlugin().getDataFolder() + "/OfflinePlayersWhoNeedToHaveTheirDataCleared.txt").exists()) {
                FileReader fileReader = new FileReader(IdleBot.getPlugin().getDataFolder() + "/OfflinePlayersWhoNeedToHaveTheirDataCleared.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String rawFileData = bufferedReader.readLine();
                offlinePlayers = new ArrayList<>(Arrays.asList(rawFileData.substring(0, rawFileData.length()-1).split(",")));
                bufferedReader.close();
                // Remove duplicates in ArrayList
                offlinePlayers = (ArrayList<String>) offlinePlayers.stream().distinct().collect(Collectors.toList());
            }
        } catch (Exception exception) {
            MessageHelper.sendMessage("Error reading data file!", MessageLevel.FATAL_ERROR);
            exception.printStackTrace();
        }
        String joinedPlayerUUID = e.getPlayer().getUniqueId().toString();
        for (String offlinePlayer : offlinePlayers) {
            if (joinedPlayerUUID.equals(offlinePlayer)) {
                offlinePlayers.remove(offlinePlayer);
                PersistentDataUtils.removeAllData(e.getPlayer());
                EventUtils.saveListToDataFile(offlinePlayers, false);
                break;
            }
        }

        // Code to check for available update
        if (e.getPlayer().isOp() && !IdleBot.getLocalVersion().equals(IdleBot.getLatestVersion())
                && !(IdleBot.getLocalVersion() == null || IdleBot.getLatestVersion() == null)) {
                MessageHelper.sendMessage(e.getPlayer(), "You are running an outdated version! (You are running version " + IdleBot.getLocalVersion() + " but the latest version is " + IdleBot.getLatestVersion() + ". Go to https://www.spigotmc.org/resources/idlebot-step-up-your-afk-game.88778/ to download a new version", MessageLevel.IMPORTANT);
        }
    }
}
