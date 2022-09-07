package me.hugo.gadgetsmenu;

import me.hugo.gadgetsmenu.gadget.GadgetType;
import me.hugo.gadgetsmenu.listener.HotBarItemListener;
import me.hugo.gadgetsmenu.listener.InventoryListener;
import me.hugo.gadgetsmenu.listener.PlayerJoinLeave;
import me.hugo.gadgetsmenu.player.GadgetPlayerRegistry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class GadgetsMenu extends JavaPlugin {

    private final GadgetPlayerRegistry playerRegistry = new GadgetPlayerRegistry();

    @Override
    public void onEnable() {
        Logger logger = getLogger();
        logger.info("Starting up GadgetsMenu...");

        logger.info("Registering gadgets...");
        for (GadgetType gadget : GadgetType.values()) {
            gadget.register(this);
        }

        logger.info("Registering listeners...");
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryListener(this), this);
        pluginManager.registerEvents(new HotBarItemListener(this), this);
        pluginManager.registerEvents(new PlayerJoinLeave(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public GadgetPlayerRegistry getPlayerRegistry() {
        return playerRegistry;
    }
}
