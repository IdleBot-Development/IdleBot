package io.github.camshaft54.idlebot.events;

import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.util.EventUtils;
import io.github.camshaft54.idlebot.util.IdleCheck;
import io.github.camshaft54.idlebot.util.Messenger;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import io.github.camshaft54.idlebot.util.enums.DataValues;
import io.github.camshaft54.idlebot.util.enums.MessageLevel;
import org.bukkit.entity.Player;

public class ZLocationReached implements IdleCheck {
    @Override
    public String getDataValue() {
        return DataValues.LOCATION_ALERT_Z.key();
    }

    // Sends a player a message if they have reached their desired location
    public void check(Player player) {
        String direction = PersistentDataHandler.getStringData(player, DataValues.LOCATION_Z_DIRECTION.key());
        int desiredLocation = PersistentDataHandler.getIntData(player, DataValues.LOCATION_Z_DESIRED.key());
        double playerLocation = player.getLocation().getZ();
        if (direction != null && !IdleBot.getEventManager().locationReachedPlayersZ.contains(player) && ((direction.equals("s") && playerLocation >= desiredLocation) || (direction.equals("n") && playerLocation <= desiredLocation))) {
            Messenger.sendMessage(player.getDisplayName() + " is idle and reached the desired Z coordinate!", MessageLevel.INFO);
            EventUtils.sendPlayerMessage(player, player.getDisplayName() + " has reached the desired Z coordinate! ");
            IdleBot.getEventManager().locationReachedPlayersZ.add(player);
        }
    }
}
