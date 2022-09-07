package me.hugo.gadgetsmenu.listener;

import me.hugo.gadgetsmenu.GadgetsMenu;
import me.hugo.gadgetsmenu.gadget.GadgetType;
import me.hugo.gadgetsmenu.gadget.action.GadgetAction;
import me.hugo.gadgetsmenu.hotbar.HotBarJoinItem;
import me.hugo.gadgetsmenu.util.ClickAction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class HotBarItemListener implements Listener {

    private final GadgetsMenu main;
    private final HashMap<ItemStack, ClickAction> itemActions = new HashMap<>();

    public HotBarItemListener(GadgetsMenu main) {
        this.main = main;

        for (HotBarJoinItem item : HotBarJoinItem.values()) itemActions.put(item.getItem(), item.getAction());

        for (GadgetType gadget : GadgetType.values()) {
            GadgetAction gadgetAction = gadget.getGadgetAction();

            if (gadgetAction == null) continue;

            itemActions.put(gadget.getGadgetItem(), gadget.getGadgetInteraction());
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (item == null || item.getType() == Material.AIR || !event.getAction().name().contains("CLICK")) return;
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;

        Player player = event.getPlayer();

        ClickAction clickAction = itemActions.get(item);

        if (clickAction == null) return;

        event.setCancelled(true);
        clickAction.execute(main, main.getPlayerRegistry().get(player), event.getAction().name().contains("RIGHT") ? ClickType.RIGHT : ClickType.LEFT);
        player.updateInventory();
    }

}
