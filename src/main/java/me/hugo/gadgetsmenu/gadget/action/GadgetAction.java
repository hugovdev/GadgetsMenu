package me.hugo.gadgetsmenu.gadget.action;

import me.hugo.gadgetsmenu.GadgetsMenu;
import me.hugo.gadgetsmenu.player.PlayerData;

public interface GadgetAction {

    // Booleans return true if the gadget successfully worked. If not: Cooldown won't be applied.
    boolean onLeftClick(GadgetsMenu main, PlayerData player);

    boolean onRightClick(GadgetsMenu main, PlayerData player);

}
