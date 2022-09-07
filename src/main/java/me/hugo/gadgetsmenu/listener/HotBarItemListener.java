package me.hugo.gadgetsmenu.listener;

import me.hugo.gadgetsmenu.GadgetsMenu;
import me.hugo.gadgetsmenu.gadget.GadgetType;
import me.hugo.gadgetsmenu.gadget.action.GadgetAction;
import me.hugo.gadgetsmenu.hotbar.HotBarJoinItem;
import me.hugo.gadgetsmenu.util.ClickAction;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
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

            itemActions.put(gadget.getGadgetItem(), (instance, player, type) -> {
                if (player.isOnGadgetCooldown()) {
                    double remainingTime = (double) (player.getLastGadgetUseTime() - System.currentTimeMillis()) / 1000;

                    player.getPlayer().sendMessage(Component.text("You have to wait ").color(NamedTextColor.RED)
                            .append(Component.text(new DecimalFormat("0.0").format(remainingTime)).color(NamedTextColor.AQUA))
                            .append(Component.text(" to use "))
                            .append(Component.text(gadget.getName()).color(NamedTextColor.AQUA))
                            .append(Component.text("!")));

                    player.playSound(Sound.ENTITY_PANDA_EAT);
                    return;
                }

                if (type.isLeftClick()) {
                    if (!gadgetAction.onLeftClick(instance, player)) return;
                } else if (!gadgetAction.onRightClick(instance, player)) return;

                player.setGadgetCooldown(gadget.getCooldown());
            });
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
