package me.hugo.gadgetsmenu.listener;

import me.hugo.gadgetsmenu.player.GadgetPlayerRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinLeave implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        GadgetPlayerRegistry.getInstance().get(player).setPlayer(player).giveJoinItems(true);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        GadgetPlayerRegistry.getInstance().remove(event.getPlayer());
    }

}
