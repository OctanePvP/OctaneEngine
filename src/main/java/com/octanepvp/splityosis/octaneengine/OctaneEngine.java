package com.octanepvp.splityosis.octaneengine;

import dev.splityosis.menulib.MenuLib;
import org.bukkit.plugin.java.JavaPlugin;

public final class OctaneEngine extends JavaPlugin {

    @Override
    public void onEnable() {
        MenuLib.setup(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
