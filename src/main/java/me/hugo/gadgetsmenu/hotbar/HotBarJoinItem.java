package me.hugo.gadgetsmenu.hotbar;

import me.hugo.gadgetsmenu.util.ClickAction;
import me.hugo.gadgetsmenu.util.ItemBuilder;
import me.hugo.gadgetsmenu.util.PlayerUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

public enum HotBarJoinItem {

    // Join Item Stacks, slots and click actions
    GADGETS_MENU(0, new ItemBuilder(Material.CHEST)
            .setName(Component.text("Toy Box").color(NamedTextColor.LIGHT_PURPLE).append(Component.text(" (Right Click)").color(NamedTextColor.GRAY)))
            .setLore(Component.text("Open a menu that will let").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
                    Component.text("you select a toy to play with!").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
                    Component.text("").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
                    Component.text("Click to open!").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false)).toItemStack(),
            (main, player, type) -> {
                player.openGadgetsInventory();
                PlayerUtil.playSound(player, Sound.BLOCK_CHEST_OPEN);
            });

    private final int slot;
    private final ItemStack item;
    private final ClickAction action;

    HotBarJoinItem(int slot, ItemStack item, ClickAction action) {
        this.slot = slot;
        this.item = item;
        this.action = action;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getItem() {
        return item;
    }

    public ClickAction getAction() {
        return action;
    }
}
