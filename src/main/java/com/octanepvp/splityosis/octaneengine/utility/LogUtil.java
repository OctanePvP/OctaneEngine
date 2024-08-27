package com.octanepvp.splityosis.octaneengine.utility;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtil {

    private final JavaPlugin plugin;
    private final Logger logger;


    /**
     * Constructor for LogUtil
     * Construct onEnable() in your main class and use throughout plugin
     * @param plugin
     */
    public LogUtil(JavaPlugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
    }

    public Logger getLogger() {
        return logger;
    }

    public void log(String... message) {
        List<String> msg = ColorUtil.colorize(message);
        for (String s : msg) {
            getLogger().log(Level.INFO, s);
        }
    }

    public void error(String... message) {
        List<String> msg = ColorUtil.colorize(message);
        for (String s : msg) {
            getLogger().log(Level.SEVERE, s);
        }
    }

    public void warn(String... message) {
        List<String> msg = ColorUtil.colorize(message);
        for (String s : msg) {
            getLogger().log(Level.WARNING, s);
        }
    }

    public void info(String... message) {
        log(message);
    }
}
