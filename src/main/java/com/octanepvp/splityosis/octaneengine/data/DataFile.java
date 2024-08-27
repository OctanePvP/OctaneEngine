package com.octanepvp.splityosis.octaneengine.data;

import com.octanepvp.splityosis.octaneengine.OctaneEngine;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public abstract class DataFile {

    private final JavaPlugin plugin;
    private final File file;
    private final FileConfiguration config;
    private double autoSaveCode = 0;
    private long autoSaveDelay;

    public DataFile(File file, JavaPlugin plugin, long autoSaveDelay) {
        this.plugin = plugin;
        this.file = file;
        this.autoSaveDelay = autoSaveDelay;
        try {
            if (!file.exists())
                file.createNewFile();
            config = new YamlConfiguration();
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        // Start autosave after a specific interval to prevent saving at same time
        Random random = new Random();
        int saveOffset = random.nextInt(61);
        Bukkit.getScheduler().runTaskLaterAsynchronously(OctaneEngine.getInstance(), this::startAutoSave, saveOffset * 20);
        startAutoSave();
    }

    public void save(){
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startAutoSave(){
        if (isAutoSave())
            return;
        autoSaveCode = Math.random();
        new BukkitRunnable(){
            private final double sessionCode = autoSaveCode;
            @Override
            public void run() {
                if (sessionCode != autoSaveCode){
                    cancel();
                    return;
                }
                save();
            }
        }.runTaskTimerAsynchronously(plugin, 0, autoSaveDelay);
    }

    public void stopAutoSave(){
        autoSaveCode = 0;
    }

    public boolean isAutoSave() {
        return autoSaveCode != 0;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public void reload(){
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public void setAutoSaveDelay(long autoSaveDelay) {
        this.autoSaveDelay = autoSaveDelay;
    }
}
