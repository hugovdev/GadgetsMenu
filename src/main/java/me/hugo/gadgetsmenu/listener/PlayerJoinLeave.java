package me.hugo.gadgetsmenu.listener;

import me.hugo.gadgetsmenu.GadgetsMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinLeave implements Listener {

    private final GadgetsMenu main;

    public PlayerJoinLeave(GadgetsMenu main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        main.getPlayerDataManager().getPlayerData(event.getPlayer()).giveJoinItems(true);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        main.getPlayerDataManager().removePlayer(event.getPlayer());
    }

}
