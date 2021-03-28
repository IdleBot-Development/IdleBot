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

package io.github.idlebotdevelopment.idlebot.events;

import io.github.idlebotdevelopment.idlebot.IdleBot;
import io.github.idlebotdevelopment.idlebot.util.IdleBotUtils;
import io.github.idlebotdevelopment.idlebot.util.MessageHelper;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class OnDamage implements Listener {

    private final HashMap<String, String> damageCauseDictionary;

    private final List<String> typesOfDamageByEntities = Arrays.asList("ENTITY_ATTACK", "ENTITY_SWEEPING_ATTACK", "ENTITY_EXPLOSION");

    public OnDamage() {
        List<String> keys = Stream.of(EntityDamageEvent.DamageCause.values()).map(Enum::name).collect(Collectors.toList());
        List<String> values = keys.stream().map(s -> StringUtils.capitalize(s.replace("_", " ").toLowerCase())).collect(Collectors.toList());
        damageCauseDictionary = new HashMap<>(IntStream.range(0, keys.size()).boxed().collect(Collectors.toMap(keys::get, values::get)));
        damageCauseDictionary.replace("CONTACT", "Touching Block");
        damageCauseDictionary.replace("CUSTOM", "Unknown");
        damageCauseDictionary.replace("FIRE_TICK", "Fire");
        damageCauseDictionary.replace("FLY_INTO_WALL", "Flying");
        damageCauseDictionary.replace("HOT_FLOOR", "Magma Block");
        damageCauseDictionary.replace("MAGIC", "Potion");
        damageCauseDictionary.replace("SUICIDE", "\"/kill\"");
        damageCauseDictionary.replace("ENTITY_ATTACK", "Attack");
        damageCauseDictionary.replace("ENTITY_SWEEPING_ATTACK", "Sweeping Attack");
        damageCauseDictionary.replace("ENTITY_EXPLOSION", "Explosion");
    }

    // If player was damaged, send them a message
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (IdleBotUtils.isIdle(player) && !IdleBot.getEventManager().damagedPlayers.contains(player) && PersistentDataUtils.getBooleanData(player, DataValue.DAMAGE_ALERT)) {
                MessageHelper.sendMessage(player.getDisplayName() + " is idle and taking damage!", MessageLevel.INFO);
                IdleBotUtils.sendPlayerMessage(player, player.getDisplayName() + " is taking " + damageCauseDictionary.get(e.getCause().name()) + " damage from " + e.getDamager().getName() + ".", player.getDisplayName() + " is taking damage!");
                IdleBot.getEventManager().damagedPlayers.add(player);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (IdleBotUtils.isIdle(player) && !IdleBot.getEventManager().damagedPlayers.contains(player) && PersistentDataUtils.getBooleanData(player, DataValue.DAMAGE_ALERT) && !typesOfDamageByEntities.contains(e.getCause().name())) {
                MessageHelper.sendMessage(player.getDisplayName() + " is idle and taking damage!", MessageLevel.INFO);
                IdleBotUtils.sendPlayerMessage(player, player.getDisplayName() + " is taking " + damageCauseDictionary.get(e.getCause().name()) + " damage.", player.getDisplayName() + " is taking damage!");
                IdleBot.getEventManager().damagedPlayers.add(player);
            }
        }
    }
}
