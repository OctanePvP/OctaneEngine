package com.octanepvp.splityosis.octaneengine.files.logics;

import dev.splityosis.configsystem.configsystem.ConfigTypeLogic;
import dev.splityosis.configsystem.configsystem.actionsystem.Actions;
import dev.splityosis.configsystem.configsystem.logics.ActionsConfigLogic;
import org.bukkit.configuration.ConfigurationSection;

public class ActionsMapLogic extends ConfigTypeLogic<ActionsMap> {

    @Override
    public ActionsMap getFromConfig(ConfigurationSection configurationSection, String s) {
        ActionsConfigLogic actionsConfigLogic = new ActionsConfigLogic();
        ActionsMap actionsMap = new ActionsMap();

        ConfigurationSection section = configurationSection.getConfigurationSection(s);

        if (section != null)
            for (String key : section.getKeys(false)) {
                Actions actions = actionsConfigLogic.getFromConfig(section, key);
                actionsMap.put(key.toLowerCase(), actions);
            }
        return actionsMap;
    }

    @Override
    public void setInConfig(ActionsMap actionsMap, ConfigurationSection config, String s) {
        ActionsConfigLogic actionsConfigLogic = new ActionsConfigLogic();
        actionsMap.forEach((s1, actions) -> {
            actionsConfigLogic.setInConfig(actions, config, s + "."+s1);
        });
    }
}
