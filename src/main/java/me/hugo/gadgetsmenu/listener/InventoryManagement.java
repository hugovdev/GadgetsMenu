package me.hugo.gadgetsmenu.listener;

import me.hugo.gadgetsmenu.GadgetsMenu;
import me.hugo.gadgetsmenu.util.ClickAction;
import me.hugo.gadgetsmenu.util.gui.Icon;
import me.hugo.gadgetsmenu.util.gui.IconMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryManagement implements Listener {

    private final GadgetsMenu main;

    public InventoryManagement(GadgetsMenu main) {
        this.main = main;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);

        if (!(event.getView().getTopInventory().getHolder() instanceof IconMenu customHolder) || !(event.getWhoClicked() instanceof Player))
            return;

        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();

        if (itemStack == null || itemStack.getType() == Material.AIR) return;

        Icon icon = customHolder.getIcon(event.getRawSlot());
        if (icon == null) return;

        for (ClickAction clickAction : icon.getClickActions()) {
            clickAction.execute(main, main.getPlayerDataManager().getPlayerData(player), event.getClick());
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof IconMenu menu) || !(event.getPlayer() instanceof Player player))
            return;

        if (menu.isRemoveInventory()) main.getPlayerDataManager().getPlayerData(player).restoreInventory();
    }

}
