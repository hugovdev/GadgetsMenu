package me.hugo.gadgetsmenu.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public final class GadgetPlayerRegistry {

    private final HashMap<UUID, GadgetPlayer> gadgetPlayerStorage = new HashMap<>();

    public GadgetPlayer get(UUID playerUuid) {
        GadgetPlayer gadgetPlayer = gadgetPlayerStorage.get(playerUuid);

        if (gadgetPlayer == null) {
            gadgetPlayer = new GadgetPlayer(playerUuid);
            gadgetPlayerStorage.put(playerUuid, gadgetPlayer);
        }

        return gadgetPlayer;
    }

    public GadgetPlayer get(Player player) {
        return get(player.getUniqueId());
    }

    public void remove(Player player) {
        gadgetPlayerStorage.remove(player.getUniqueId());
    }

    public void remove(UUID playerUuid) {
        gadgetPlayerStorage.remove(playerUuid);
    }
}
