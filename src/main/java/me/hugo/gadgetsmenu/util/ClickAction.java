package me.hugo.gadgetsmenu.util;

import me.hugo.gadgetsmenu.GadgetsMenu;
import me.hugo.gadgetsmenu.player.GadgetPlayer;
import org.bukkit.event.inventory.ClickType;

public interface ClickAction {

    void execute(GadgetsMenu main, GadgetPlayer player, ClickType type);

}
