package com.octanepvp.splityosis.octaneengine;

import com.octanepvp.splityosis.nucleuscore.Nucleus;
import com.octanepvp.splityosis.octaneengine.actiontypes.MessageIteratorActionType;
import com.octanepvp.splityosis.octaneengine.actiontypes.PlayActionActionType;
import com.octanepvp.splityosis.octaneengine.actiontypes.PlayActionAllActionType;
import com.octanepvp.splityosis.octaneengine.commands.OctaneEngineCommandBranch;
import com.octanepvp.splityosis.octaneengine.files.ActionsBankConfig;
import com.octanepvp.splityosis.octaneengine.files.logics.ActionsMapLogic;
import com.octanepvp.splityosis.octaneengine.files.logics.IntegerActionsMapLogic;
import com.octanepvp.splityosis.octaneengine.function.logics.FunctionConfigLogic;
import com.octanepvp.splityosis.octaneengine.listeners.LoginListener;
import com.octanepvp.splityosis.octaneengine.menus.logics.MenuStaticItemsMapLogic;
import com.octanepvp.splityosis.configsystem.configsystem.ConfigSystem;
import com.octanepvp.splityosis.configsystem.configsystem.actionsystem.Actions;
import com.octanepvp.splityosis.menulib.MenuLib;
import com.octanepvp.splityosis.octaneengine.oplayer.PlayerDataHandler;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class OctaneEngine extends JavaPlugin {

    private Map<String, Actions> actionsMap = new HashMap<>();
    private File modulesFolder;
    private static OctaneEngine instance;
    private BukkitAudiences adventure;
    private File engineDataFolder;
    private PlayerDataHandler playerDataHandler;


    @Override
    public void onEnable() {

        instance = this;
        this.adventure = BukkitAudiences.create(this);

        engineDataFolder = new File(getDataFolder(), "engine-data");
        if (!engineDataFolder.exists())
            engineDataFolder.mkdirs();

        setupOPlayerEnvironment();

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
        new MessageIteratorActionType().register();

        // Commands
        new OctaneEngineCommandBranch(this).registerCommandBranch(this);

        // Listeners
        getServer().getPluginManager().registerEvents(new LoginListener(), this);

        reloadPlugin();

        setupNucleus();
    }

    @Override
    public void onDisable() {
        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }

        getPlayerDataHandler().getDataFile().stopAutoSave();
        getPlayerDataHandler().getDataFile().save();
    }

    public void reloadPlugin(){
        if (!getDataFolder().exists())
            getDataFolder().mkdirs();
        reloadActions();
    }

    public void reloadActions(){
        actionsMap.clear();

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

    private void setupNucleus(){
        modulesFolder = new File(getDataFolder().getParentFile(), ".OctaneEngineModules");
        if (!modulesFolder.exists())
            modulesFolder.mkdirs();

        Nucleus nucleus = new Nucleus(this, "&8[&6OctaneEngine&8]", "&8[&6OctaneEngine - %module%&8]", modulesFolder, new File(getDataFolder(), "database.yml"));

        nucleus.initializeSettings(new File(getDataFolder(), "database-settings.yml"));
        nucleus.initializeDatabase();

        nucleus.initializeCommandBranch("OctaneEngine", "OctaneEngine", "OEngine");

        File[] childs = modulesFolder.listFiles();
        if (childs != null)
            for (File child : childs) {
                if (child.getName().endsWith(".jar")) {
                    try {
                        nucleus.getModuleLoader().loadModules(child, null);
                    }catch (Exception e){
                        nucleus.log("&cSomething went wrong with loading the module jarfile "+child.getName());
                        e.printStackTrace();
                    }
                }
            }
        nucleus.getModuleLoader().initializeModulesState();
    }

    public @NonNull BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    private void setupOPlayerEnvironment() {
        playerDataHandler = PlayerDataHandler.setup(this);
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

    public static OctaneEngine getInstance() {
        return instance;
    }

    public File getEngineDataFolder() {
        return engineDataFolder;
    }

    public PlayerDataHandler getPlayerDataHandler() {
        return playerDataHandler;
    }
}
