package io.github.camshaft54.idlebot.commands.settings;

import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.PlayerSettingSetter;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SettingsCommand extends IdleBotCommand {

    ArrayList<PlayerSettingSetter> settings = new ArrayList<>();

    public SettingsCommand() {
        settings.add(new AFKMode());
        settings.add(new AFKTime());
    }

    @Override
    public String getCommandName() {
        return "settings";
    }

    @Override
    public void runCommand(Player player, String[] args) {
        if (args.length < 2) {
            // Information about the command
            return;
        }
        for (PlayerSettingSetter setting : settings) {
            if (args[1].equalsIgnoreCase(setting.getSettingName())) {
                setting.setSetting(player, args);
            }
        }
    }
}
