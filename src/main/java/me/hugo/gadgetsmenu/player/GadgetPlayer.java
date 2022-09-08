package me.hugo.gadgetsmenu.player;

import me.hugo.gadgetsmenu.gadget.GadgetType;
import me.hugo.gadgetsmenu.util.ItemBuilder;
import me.hugo.gadgetsmenu.util.PlayerUtil;
import me.hugo.gadgetsmenu.util.gui.CustomUI;
import me.hugo.gadgetsmenu.util.gui.Icon;
import me.hugo.gadgetsmenu.util.gui.IconMenu;
import me.hugo.gadgetsmenu.util.gui.PageFormat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class GadgetPlayer {

    private final UUID playerUuid;
    private Player player;
    private final HashMap<Integer, ItemStack> lastInventory = new HashMap<>();

    private long lastGadgetUseTime = 0L;

    public GadgetPlayer(UUID uuid) {
        this.playerUuid = uuid;
    }

    public void openGadgetsInventory() {
        PlayerUtil.createInventoryBackup(this, true);

        IconMenu gadgetsMenu = new IconMenu(9 * 6, CustomUI.GADGETS_MENU.getFinalTitle(), PageFormat.FIVE_ROWS, null, true);

        gadgetsMenu.setIcon(0, new Icon(new ItemBuilder(Material.CONDUIT).setName(Component.text("Close").color(NamedTextColor.RED)).toItemStack())
                .addClickAction((main, clicker, type) -> clicker.getPlayer().closeInventory()));

        for (GadgetType gadgets : GadgetType.values()) gadgetsMenu.addItem(gadgets.getMenuIcon(this));

        player.openInventory(gadgetsMenu.getInventory());
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

    public HashMap<Integer, ItemStack> getLastInventory() {
        return lastInventory;
    }

    public void resetLastInventory() {
        lastInventory.clear();
    }

    public Player getPlayer() {
        return player;
    }

    public GadgetPlayer setPlayer(Player player) {
        this.player = player;
        return this;
    }
}
