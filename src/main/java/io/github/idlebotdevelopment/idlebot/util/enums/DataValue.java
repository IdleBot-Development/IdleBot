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

package io.github.idlebotdevelopment.idlebot.util.enums;

@SuppressWarnings("SpellCheckingInspection")
public enum DataValue {
    AFK_TIME("afktime"),
    AUTO_AFK("autoafk"),
    DISCORD_ID("discordid"),
    IS_SET_AFK("setafk"),
    ADVANCEMENT_ALERT("advancementalert"),
    ADVANCEMENT_DESIRED("advancementdesired"),
    DAMAGE_ALERT("damagealert"),
    DEATH_ALERT("deathalert"),
    EXPERIENCE_ALERT("xpalert"),
    EXPERIENCE_LEVEL_DESIRED("xpleveldesired"),
    LOCATION_ALERT_X("locationcheckx"),
    LOCATION_ALERT_Z("locationcheckz"),
    LOCATION_X_DESIRED("locationx"),
    LOCATION_Z_DESIRED("locationz"),
    LOCATION_X_DIRECTION("locationxdir"), // e/w; example: player is at x = 100, wants alert x = 500, value = "e"
    LOCATION_Z_DIRECTION("locationzdir"), // s/n; example: player is at z = 100, wants alert z = -500, value = "n"
    INVENTORY_FULL_ALERT("inventoryalert"),
    DIRECT_MESSAGE_MODE("dmmode");

    private final String key;

    DataValue(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
