package me.hugo.gadgetsmenu.util;

import me.hugo.gadgetsmenu.player.GadgetPlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public final class PlayerUtil {

    public static void playSound(GadgetPlayer player, Sound sound) {
        Player bukkitPlayer = player.getPlayer();
        bukkitPlayer.playSound(bukkitPlayer.getLocation(), sound, 1.0f, 1.0f);
    }
}
