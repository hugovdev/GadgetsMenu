package me.hugo.gadgetsmenu.util;

import me.hugo.gadgetsmenu.GadgetsMenu;
import me.hugo.gadgetsmenu.player.PlayerData;
import org.bukkit.event.inventory.ClickType;

public interface ClickAction {

    void execute(GadgetsMenu main, PlayerData player, ClickType type);

}
