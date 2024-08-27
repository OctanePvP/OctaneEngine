package com.octanepvp.splityosis.octaneengine.data;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class DataFile {

    private final File file;
    private final FileConfiguration config;
    private double autoSaveCode = 0;

    public DataFile(File file) {
        this.file = file;
        try {
            if (!file.exists())
                file.createNewFile();
            config = new YamlConfiguration();
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(){
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startAutoSave(JavaPlugin plugin, long delay){
        // Start autosave after a specific interval to minimize instances saving at the same time
        Random random = new Random();
        long saveOffset = random.nextLong(delay);

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
        }.runTaskTimerAsynchronously(plugin, saveOffset, delay);
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


    public void reload(){
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
