package com.octanepvp.splityosis.octaneengine;

import com.octanepvp.splityosis.octaneengine.menus.logics.MenuStaticItemsMapLogic;
import dev.splityosis.menulib.MenuLib;
import org.bukkit.plugin.java.JavaPlugin;

public final class OctaneEngine extends JavaPlugin {

    @Override
    public void onEnable() {
        MenuLib.setup(this);

        // Logics
        new MenuStaticItemsMapLogic().register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
