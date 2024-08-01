package com.octanepvp.splityosis.octaneengine.menus.logics;

import com.octanepvp.splityosis.octaneengine.menus.MenuStaticItemsMap;
import com.octanepvp.splityosis.octaneengine.menus.StaticMenuItem;
import com.octanepvp.splityosis.configsystem.configsystem.ConfigTypeLogic;
import com.octanepvp.splityosis.configsystem.configsystem.actionsystem.Actions;
import com.octanepvp.splityosis.configsystem.configsystem.logics.ActionsConfigLogic;
import com.octanepvp.splityosis.configsystem.configsystem.logics.ItemStackConfigLogic;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class MenuStaticItemsMapLogic extends ConfigTypeLogic<MenuStaticItemsMap> {

    @Override
    public MenuStaticItemsMap getFromConfig(ConfigurationSection config, String path) {
        MenuStaticItemsMap menuStaticItemsMap = new MenuStaticItemsMap();
        ItemStackConfigLogic itemStackConfigLogic = new ItemStackConfigLogic();
        ActionsConfigLogic actionsConfigLogic = new ActionsConfigLogic();

        ConfigurationSection section = config.getConfigurationSection(path);
        if (section != null)
            for (String identifier : section.getKeys(false)) {
                String slotsStr = section.getString(identifier + ".slot");
                String[] splitSlots = slotsStr.replace(" ", "").split(",");
                int[] slots = new int[splitSlots.length];

                for (int i = 0; i < splitSlots.length; i++) {
                    try {
                        slots[i] = Integer.parseInt(splitSlots[i]);
                    } catch (Exception e){
                        slots[i] = -1;
                    }
                }
                ItemStack item = itemStackConfigLogic.getFromConfig(section, identifier + ".display-item");
                Actions onClick = actionsConfigLogic.getFromConfig(section, identifier + ".on-click-actions");
                menuStaticItemsMap.add(new StaticMenuItem(identifier, slots, item, onClick));
            }
        return menuStaticItemsMap;
    }

    @Override
    public void setInConfig(MenuStaticItemsMap menuStaticItemsMap, ConfigurationSection config, String path) {
        ItemStackConfigLogic itemStackConfigLogic = new ItemStackConfigLogic();
        ActionsConfigLogic actionsConfigLogic = new ActionsConfigLogic();

        menuStaticItemsMap.forEach((staticMenuItem) -> {
            String slots = arrayToString(staticMenuItem.getSlots());
            config.set(path + "." + staticMenuItem.getIdentifier() + ".slot", slots);
            itemStackConfigLogic.setInConfig(staticMenuItem.getItemStack(), config, path + "." + staticMenuItem.getIdentifier() + ".display-item");
            actionsConfigLogic.setInConfig(staticMenuItem.getOnClick(), config, path + "." + staticMenuItem.getIdentifier() + ".on-click-actions");
        });
    }

    private static String arrayToString(int[] arr){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            stringBuilder.append(arr[i]);
            if (i != arr.length - 1)
                stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }
}
