package me.hugo.gadgetsmenu.player;

import me.hugo.gadgetsmenu.gadget.GadgetType;
import me.hugo.gadgetsmenu.hotbar.HotBarJoinItem;
import me.hugo.gadgetsmenu.util.ItemBuilder;
import me.hugo.gadgetsmenu.util.gui.CustomUI;
import me.hugo.gadgetsmenu.util.gui.Icon;
import me.hugo.gadgetsmenu.util.gui.IconMenu;
import me.hugo.gadgetsmenu.util.gui.PageFormat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.UUID;

public class GadgetPlayer {

    private final UUID playerUuid;
    private Player player;
    private HashMap<Integer, ItemStack> lastInventory = new HashMap<>();

    private long lastGadgetUseTime = 0L;

    public GadgetPlayer(UUID uuid) {
        this.playerUuid = uuid;
    }

    public void playSound(Sound sound) {
        if (player == null) return;

        player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
    }

    public void openGadgetsInventory() {
        createInventoryBackup(true);

        IconMenu gadgetsMenu = new IconMenu(9 * 6, CustomUI.GADGETS_MENU.getFinalTitle(), PageFormat.FIVE_ROWS, null, true);

        gadgetsMenu.setIcon(0, new Icon(new ItemBuilder(Material.CONDUIT).setName(Component.text("Close").color(NamedTextColor.RED)).toItemStack())
                .addClickAction((main, clicker, type) -> clicker.getPlayer().closeInventory()));

        for (GadgetType gadgets : GadgetType.values()) gadgetsMenu.addItem(gadgets.getMenuIcon(this));

        player.openInventory(gadgetsMenu.getInventory());
    }

    private void createInventoryBackup(boolean clear) {
        lastInventory = new HashMap<>();

        PlayerInventory inventory = player.getInventory();

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack currentItem = inventory.getItem(i);

            if (currentItem != null && currentItem.getType() != Material.AIR) {
                lastInventory.put(i, currentItem);

                if (clear) inventory.setItem(i, null);
            }
        }
    }

    public void setRestoreItem(int slot, ItemStack item) {
        lastInventory.put(slot, item);
    }

    public void restoreInventory() {
        for (Integer slot : lastInventory.keySet()) {
            player.getInventory().setItem(slot, lastInventory.get(slot));
        }
    }

    public void giveJoinItems(boolean clearInventory) {
        if (player == null) return;

        PlayerInventory inventory = player.getInventory();

        if (clearInventory) {
            inventory.clear();
            inventory.setArmorContents(null);
        }

        for (HotBarJoinItem hotBarJoinItem : HotBarJoinItem.values())
            inventory.setItem(hotBarJoinItem.getSlot(), hotBarJoinItem.getItem());
    }

    public boolean isOnGadgetCooldown() {
        return System.currentTimeMillis() < this.lastGadgetUseTime;
    }

    public Long getLastGadgetUseTime() {
        return lastGadgetUseTime;
    }

    public GadgetPlayer setGadgetCooldown(double seconds) {
        this.lastGadgetUseTime = System.currentTimeMillis() + (long) (seconds * 1000L);

        return this;
    }

    public Player getPlayer() {
        return player;
    }

    public GadgetPlayer setPlayer(Player player) {
        this.player = player;
        return this;
    }
}
