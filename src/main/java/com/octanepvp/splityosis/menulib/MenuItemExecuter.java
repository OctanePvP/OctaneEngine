package com.octanepvp.splityosis.menulib;
import com.octanepvp.splityosis.menulib.Menu;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface MenuItemExecuter {

    void onClick(InventoryClickEvent event, Menu menu);
}
