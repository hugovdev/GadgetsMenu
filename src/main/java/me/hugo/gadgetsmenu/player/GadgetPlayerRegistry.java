package me.hugo.gadgetsmenu.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public final class GadgetPlayerRegistry {

    private static final HashMap<UUID, GadgetPlayer> gadgetPlayerStorage = new HashMap<>();

    public static GadgetPlayer get(UUID playerUuid) {
        GadgetPlayer gadgetPlayer = gadgetPlayerStorage.get(playerUuid);

        if (gadgetPlayer == null) {
            gadgetPlayer = new GadgetPlayer(playerUuid);
            gadgetPlayerStorage.put(playerUuid, gadgetPlayer);
        }

        return gadgetPlayer;
    }

    public static GadgetPlayer get(Player player) {
        return get(player.getUniqueId());
    }

    public static void remove(Player player) {
        gadgetPlayerStorage.remove(player.getUniqueId());
    }

    public static void remove(UUID playerUuid) {
        gadgetPlayerStorage.remove(playerUuid);
    }

}
