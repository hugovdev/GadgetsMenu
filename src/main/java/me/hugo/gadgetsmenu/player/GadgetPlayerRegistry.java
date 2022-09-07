package me.hugo.gadgetsmenu.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class GadgetPlayerRegistry {

    private final Map<UUID, GadgetPlayer> gadgetPlayerStorage = new HashMap<>();

    public GadgetPlayer create(UUID playerUuid) {
        GadgetPlayer gadgetPlayer = new GadgetPlayer(playerUuid);

        gadgetPlayerStorage.put(playerUuid, gadgetPlayer);

        return gadgetPlayer;
    }

    public GadgetPlayer get(Player player) {
        return gadgetPlayerStorage.get(player.getUniqueId());
    }

    public void remove(Player player) {
        gadgetPlayerStorage.remove(player.getUniqueId());
    }
}
