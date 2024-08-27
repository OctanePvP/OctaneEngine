package com.octanepvp.splityosis.octaneengine.oplayer;

import com.octanepvp.splityosis.octaneengine.OctaneEngine;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * An object that represents a player that has ever logged on the server, unlike {@link org.bukkit.OfflinePlayer}
 * this won't ping the Mojang-api and will only use data stored on local files.
 */
public class OPlayer {

    private ConfigurationSection dataSection;
    private UUID uniqueId;

    protected OPlayer(UUID uniqueId, ConfigurationSection dataSection){
        this.uniqueId = uniqueId;
        this.dataSection = dataSection;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return dataSection.getString("name");
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uniqueId);
    }

    public boolean isOnline() {
        return Bukkit.getPlayer(uniqueId) != null;
    }

    public long getFirstLoginTimeInMillis() {
        return dataSection.getLong("first-login-in-millis");
    }

    public long getLastLoginTimeInMillis() {
        return dataSection.getLong("last-login-in-millis");
    }

    public long getAmountOfLogins() {
        return dataSection.getLong("amount-of-logins");
    }

    /**
     * @param uniqueId UUID to search for OPlayer
     * @return OPlayer from a given UUID, Or null if the player was never online.
     */
    public static OPlayer getOPlayer(UUID uniqueId) {
        ConfigurationSection section = OctaneEngine.getInstance().getPlayerDataHandler().getPlayerSection(uniqueId);
        if (section == null) return null;
        return new OPlayer(uniqueId, section);
    }

    /**
     * @param name name to search for OPlayer
     * @return OPlayer from a given name, Or null if the player was never online.
     */
    public static OPlayer getOPlayer(String name) {
        return getOPlayer(OctaneEngine.getInstance().getPlayerDataHandler().getUUID(name));
    }

}
