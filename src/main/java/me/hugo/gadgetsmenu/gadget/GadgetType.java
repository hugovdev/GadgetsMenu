package me.hugo.gadgetsmenu.gadget;

import me.hugo.gadgetsmenu.GadgetsMenu;
import me.hugo.gadgetsmenu.gadget.list.ChickenShooterAction;
import me.hugo.gadgetsmenu.player.PlayerData;
import me.hugo.gadgetsmenu.util.ClickAction;
import me.hugo.gadgetsmenu.util.ItemBuilder;
import me.hugo.gadgetsmenu.util.gui.Icon;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum GadgetType {

    CHICKEN_SHOOTER("Chicken Shooter",
            Arrays.asList("Shoot explosive chickens or", "eggs throughout the lobby!"),
            Material.GOLDEN_HORSE_ARMOR, new ChickenShooterAction(), "gadgets.chicken-shooter"),

    // Add any new gadgets here for example:
    TRAIN("Train",
            Arrays.asList("Create a train everyone", "can join and ride!"),
            Material.RAIL, null, "gadgets.train");

    private final String name;
    private final List<String> description;
    private final Material icon;
    private final ClickAction gadgetAction;
    private final String permission;

    private final ItemStack gadgetItem;

    GadgetType(String name, List<String> description, Material icon, ClickAction gadgetAction, String permission) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.gadgetAction = gadgetAction;
        this.permission = permission;

        List<Component> itemDescription = this.description.stream().map(line -> Component.text(line).color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)).collect(Collectors.toList());

        itemDescription.add(Component.text(""));
        itemDescription.add(Component.text("Click to use!").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));


        this.gadgetItem = new ItemBuilder(this.icon).setName(Component.text(this.name).color(NamedTextColor.GREEN))
                .setLore(itemDescription).hideAttributes().toItemStack();
    }

    public void register(GadgetsMenu main) {
        // If the gadget action has events to be handled, we register them :)
        if (gadgetAction instanceof Listener listener) Bukkit.getPluginManager().registerEvents(listener, main);
    }

    public Icon getMenuIcon(PlayerData player) {
        boolean hasPermission = player.getPlayer().hasPermission(this.permission);
        List<Component> itemDescription = this.description.stream().map(line -> Component.text(line).color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)).collect(Collectors.toList());

        itemDescription.add(Component.text(""));
        itemDescription.add(Component.text(hasPermission ? "Click to select!" : "Gadget locked!").color(hasPermission ? NamedTextColor.YELLOW : NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));

        // Building a custom Icon for the player depending on their permissions.
        return new Icon(
                new ItemBuilder(hasPermission ? this.icon : Material.BLACK_STAINED_GLASS_PANE)
                        .setName(Component.text(this.name).color(hasPermission ? NamedTextColor.GREEN : NamedTextColor.RED))
                        .setLore(itemDescription)
                        .hideAttributes()
                        .toItemStack()
        ).addClickAction(hasPermission ?

                // Player has permission :::::::::::
                ((main, clicker, type) -> {
                    clicker.getPlayer().sendMessage(Component.text("You selected ").color(NamedTextColor.GREEN)
                            .append(Component.text(this.name).color(NamedTextColor.AQUA)).append(Component.text(" as your gadget!")));

                    // We set the new gadget item to slot 1, so when the inventory restores, player receives the gadget.
                    clicker.setRestoreItem(1, this.gadgetItem);
                    clicker.playSound(Sound.BLOCK_NOTE_BLOCK_HAT);
                    clicker.getPlayer().closeInventory();
                }) :

                // Player doesn't have permission :::::::::
                ((main, clicker, type) -> {
                    clicker.getPlayer().sendMessage(Component.text("You haven't unlocked ").color(NamedTextColor.RED)
                            .append(Component.text(this.name).color(NamedTextColor.AQUA)).append(Component.text(" yet!")));

                    clicker.playSound(Sound.ENTITY_ENDERMAN_TELEPORT);
                })
        );
    }

    public ClickAction getGadgetAction() {
        return gadgetAction;
    }

    public ItemStack getGadgetItem() {
        return gadgetItem;
    }
}
