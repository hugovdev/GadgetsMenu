package me.hugo.gadgetsmenu.listener;

import me.hugo.gadgetsmenu.GadgetsMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerJoinLeave implements Listener {

    private final GadgetsMenu main;

    public PlayerJoinLeave(GadgetsMenu main) {
        this.main = main;
    }

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        UUID playerUuid = event.getPlayerProfile().getId();

        if (playerUuid == null) {
            event.kickMessage(Component.text("Your profile failed to load!", NamedTextColor.RED));
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);

            return;
        }

        // Create / Load player's profile!
        main.getPlayerRegistry().create(playerUuid);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        main.getPlayerRegistry().get(player).setPlayer(player).giveJoinItems(true);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        main.getPlayerRegistry().remove(event.getPlayer());
    }

}
