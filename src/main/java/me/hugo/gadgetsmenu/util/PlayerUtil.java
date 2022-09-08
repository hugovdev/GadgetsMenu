package me.hugo.gadgetsmenu.util;

import me.hugo.gadgetsmenu.hotbar.HotBarJoinItem;
import me.hugo.gadgetsmenu.player.GadgetPlayer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public final class PlayerUtil {

    public static void playSound(GadgetPlayer player, Sound sound) {
        Player bukkitPlayer = player.getPlayer();
        bukkitPlayer.playSound(bukkitPlayer.getLocation(), sound, 1.0f, 1.0f);
    }

    public static void createInventoryBackup(GadgetPlayer gadgetPlayer, boolean clear) {
        Player player = gadgetPlayer.getPlayer();
        gadgetPlayer.resetLastInventory();

        PlayerInventory inventory = player.getInventory();

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack currentItem = inventory.getItem(i);

            if (currentItem != null && currentItem.getType() != Material.AIR) {
                gadgetPlayer.getLastInventory().put(i, currentItem);

                if (clear) inventory.setItem(i, null);
            }
        }
    }

    public static void setRestoreItem(GadgetPlayer gadgetPlayer, int slot, ItemStack item) {
        gadgetPlayer.getLastInventory().put(slot, item);
    }

    public static void restoreInventory(GadgetPlayer gadgetPlayer) {
        for (Integer slot : gadgetPlayer.getLastInventory().keySet()) {
            gadgetPlayer.getPlayer().getInventory().setItem(slot, gadgetPlayer.getLastInventory().get(slot));
        }
    }

    public static void giveJoinItems(GadgetPlayer gadgetPlayer, boolean clearInventory) {
        Player player = gadgetPlayer.getPlayer();
        if (player == null) return;

        PlayerInventory inventory = player.getInventory();

        if (clearInventory) {
            inventory.clear();
            inventory.setArmorContents(null);
        }

        for (HotBarJoinItem hotBarJoinItem : HotBarJoinItem.values())
            inventory.setItem(hotBarJoinItem.getSlot(), hotBarJoinItem.getItem());
    }
}
