package me.hugo.gadgetsmenu.util.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IconMenu implements InventoryHolder {

    private final Map<Integer, Icon> icons = new HashMap<>();

    private final int size;
    private final Component title;
    private String format;
    private ItemStack indicator;
    private int index;
    private Inventory inventory;

    private boolean removeInventory = false;

    public IconMenu(int size, Component title, String format, ItemStack indicator) {
        this.size = size;
        this.title = title;
        this.format = format;
        this.indicator = indicator;
        this.inventory = Bukkit.createInventory(this, this.size, this.title);

        if (indicator != null) setIcon(size - 5, new Icon(indicator));
        index = 0;
    }

    public IconMenu(int size, Component title, PageFormat format, ItemStack indicator) {
        this.size = size;
        this.title = title;
        this.format = format.getFormat();
        this.indicator = indicator;
        this.inventory = Bukkit.createInventory(this, this.size, this.title);

        if (indicator != null) setIcon(size - 5, new Icon(indicator));
        index = 0;
    }

    public IconMenu(int size, Component title, PageFormat format, ItemStack indicator, boolean removeInventory) {
        this.size = size;
        this.title = title;
        this.format = format.getFormat();
        this.indicator = indicator;
        this.inventory = Bukkit.createInventory(this, this.size, this.title);
        this.removeInventory = removeInventory;

        if (indicator != null) setIcon(size - 5, new Icon(indicator));
        index = 0;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setFormat(PageFormat format) {
        this.format = format.getFormat();
    }

    public Map<Integer, Icon> getIcons() {
        return icons;
    }

    public int getSize() {
        return size;
    }

    public Component getTitle() {
        return title;
    }

    public String getFormat() {
        return format;
    }

    public ItemStack getIndicator() {
        return indicator;
    }

    public int getIndex() {
        return index;
    }

    public void setIndicator(ItemStack indicator) {
        this.indicator = indicator;
        setIcon(size - 5, new Icon(indicator));
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public List<Integer> getFormatSlots() {
        char[] charArray = format.toCharArray();
        List<Integer> slots = new ArrayList<>();

        for (int i = 0; i < charArray.length - 1; i++) {
            if (charArray[i] == 'X') {
                slots.add(i);
            }
        }
        return slots;
    }

    public int addItem(Icon icon) {
        List<Integer> slots = getFormatSlots();
        int slot = slots.get(index);

        this.icons.put(slot, icon);
        inventory.setItem(slot, icon.getItemStack());
        index++;

        return slot;
    }

    public void setIcon(int position, Icon icon) {
        this.icons.put(position, icon);
        inventory.setItem(position, icon.getItemStack());
    }

    public void reset() {
        icons.clear();
        inventory.clear();
        setIcon(size - 5, new Icon(indicator));
        index = 0;
    }

    public boolean isRemoveInventory() {
        return removeInventory;
    }

    public Icon getIcon(int position) {
        return this.icons.get(position);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
