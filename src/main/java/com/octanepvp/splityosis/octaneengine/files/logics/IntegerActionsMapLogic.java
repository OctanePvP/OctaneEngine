package com.octanepvp.splityosis.octaneengine.files.logics;

import com.octanepvp.splityosis.configsystem.configsystem.ConfigTypeLogic;
import com.octanepvp.splityosis.configsystem.configsystem.actionsystem.Actions;
import com.octanepvp.splityosis.configsystem.configsystem.logics.ActionsConfigLogic;
import org.bukkit.configuration.ConfigurationSection;

public class IntegerActionsMapLogic extends ConfigTypeLogic<IntegerActionsMap> {
    @Override
    public IntegerActionsMap getFromConfig(ConfigurationSection configurationSection, String s) {
        ActionsConfigLogic actionsConfigLogic = new ActionsConfigLogic();
        IntegerActionsMap integerActionsMap = new IntegerActionsMap();

        ConfigurationSection section = configurationSection.getConfigurationSection(s);

        if (section != null)
            for (String key : section.getKeys(false)) {
                Actions actions = actionsConfigLogic.getFromConfig(section, key);
                integerActionsMap.put(Integer.parseInt(key), actions);
            }
        return integerActionsMap;
    }

    @Override
    public void setInConfig(IntegerActionsMap integerActionsMap, ConfigurationSection configurationSection, String s) {
        ActionsConfigLogic actionsConfigLogic = new ActionsConfigLogic();
        integerActionsMap.forEach((integer, actions) -> {
            actionsConfigLogic.setInConfig(actions, configurationSection, s + "." + integer);
        });
    }
}
