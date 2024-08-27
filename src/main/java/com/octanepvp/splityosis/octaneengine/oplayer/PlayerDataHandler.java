package com.octanepvp.splityosis.octaneengine.oplayer;

import com.octanepvp.splityosis.octaneengine.OctaneEngine;
import com.octanepvp.splityosis.octaneengine.data.DataFile;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.util.UUID;

public class PlayerDataHandler implements Listener {

    public static PlayerDataHandler setup(OctaneEngine plugin) {
        if (plugin.getPlayerDataHandler() != null)
            throw new IllegalStateException("PlayerDataHandler is already setup");
        PlayerDataHandler playerDataHandler = new PlayerDataHandler(plugin);
        plugin.getServer().getPluginManager().registerEvents(playerDataHandler, plugin);
        return playerDataHandler;
    }

    private DataFile dataFile;

    private PlayerDataHandler(OctaneEngine plugin) {
        dataFile = new DataFile(new File(plugin.getEngineDataFolder(), "player-data.yml"));
        dataFile.startAutoSave(plugin, 20*60*5);
    }

    public String getName(UUID uuid){
        ConfigurationSection section = getPlayerSection(uuid);
        if (section == null) return null;
        return section.getString("name");
    }

    public UUID getUUID(String name){
        String uuidStr = dataFile.getConfig().getString("lower-name-to-uuid."+name.toLowerCase());
        if (uuidStr == null) return null;
        return UUID.fromString(uuidStr);
    }

    public boolean hasPlayedBefore(String name){
        return getUUID(name) != null;
    }

    public boolean hasPlayedBefore(UUID uuid){
        return getName(uuid) != null;
    }

    public ConfigurationSection getPlayerSection(UUID uuid) {
        return dataFile.getConfig().getConfigurationSection("player-sections."+uuid);
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e){
        dataFile.getConfig().set("lower-name-to-uuid."+e.getPlayer().getName().toLowerCase(), e.getPlayer().getUniqueId().toString());
        UUID uuid = e.getPlayer().getUniqueId();
        ConfigurationSection section = getPlayerSection(uuid);
        if (section == null) {
            // FIRST TIME JOINS
            section = dataFile.getConfig().createSection("player-sections." + uuid);
            section.set("first-login-in-millis", System.currentTimeMillis());
        }
        section.set("last-login-in-millis", System.currentTimeMillis());
        section.set("amount-of-logins", section.getLong("amount-of-logins") + 1);
        section.set("name", e.getPlayer().getName());
    }

    public DataFile getDataFile() {
        return dataFile;
    }
}
