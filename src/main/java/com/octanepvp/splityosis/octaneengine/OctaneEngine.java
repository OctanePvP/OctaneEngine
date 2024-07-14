package com.octanepvp.splityosis.octaneengine;

import com.octanepvp.splityosis.octaneengine.actiontypes.PlayActionActionType;
import com.octanepvp.splityosis.octaneengine.actiontypes.PlayActionAllActionType;
import com.octanepvp.splityosis.octaneengine.commands.OctaneEngineCommandBranch;
import com.octanepvp.splityosis.octaneengine.files.ActionsBankConfig;
import com.octanepvp.splityosis.octaneengine.files.logics.ActionsMapLogic;
import com.octanepvp.splityosis.octaneengine.files.logics.IntegerActionsMap;
import com.octanepvp.splityosis.octaneengine.files.logics.IntegerActionsMapLogic;
import com.octanepvp.splityosis.octaneengine.function.logics.FunctionConfigLogic;
import com.octanepvp.splityosis.octaneengine.menus.logics.MenuStaticItemsMapLogic;
import dev.splityosis.configsystem.configsystem.ConfigSystem;
import dev.splityosis.configsystem.configsystem.actionsystem.Actions;
import dev.splityosis.menulib.MenuLib;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class OctaneEngine extends JavaPlugin {

    private Map<String, Actions> actionsMap = new HashMap<>();

    @Override
    public void onEnable() {

        MenuLib.setup(this);
        ConfigSystem.setup(this);

        // Logics
        new MenuStaticItemsMapLogic().register();
        new ActionsMapLogic().register();
        new IntegerActionsMapLogic().register();
        new FunctionConfigLogic().register();

        // ActionTypes
        new PlayActionActionType(this).register();
        new PlayActionAllActionType(this).register();

        // Commands
        new OctaneEngineCommandBranch(this).registerCommandBranch(this);

        reloadPlugin();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void reloadPlugin(){
        reloadActions();
    }

    public void reloadActions(){
        actionsMap.clear();

        if (!getDataFolder().exists())
            getDataFolder().mkdirs();

        File dir = new File(getDataFolder(), "action-bank");
        if (!dir.exists())
            dir.mkdirs();

        File[] files = dir.listFiles();
        if (files == null || files.length == 0){
            try {
                new ActionsBankConfig(new File(dir, "countdowns.yml")).initialize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        files = dir.listFiles();
        for (File file : files) {
            try {
                ActionsBankConfig actionsBankConfig = (ActionsBankConfig) new ActionsBankConfig(file);
                actionsBankConfig.initialize();
                actionsBankConfig.actionsMap.forEach((s, actions) -> {
                    actionsMap.put(s.toLowerCase(), actions);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, Actions> getActionsMap() {
        return actionsMap;
    }

    public Actions getAction(String identifier){
        return actionsMap.get(identifier.toLowerCase());
    }

    public Collection<Actions> getActions(){
        return new ArrayList<>(actionsMap.values());
    }
}
