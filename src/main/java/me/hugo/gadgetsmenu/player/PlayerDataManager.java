package me.hugo.gadgetsmenu.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {

    private HashMap<UUID, PlayerData> playerStorage = new HashMap<>();

    public PlayerData getPlayerData(Player player) {
        return getPlayerData(player.getUniqueId()).setPlayer(player);
    }

    public PlayerData getPlayerData(UUID playerUuid) {
        PlayerData playerData = this.playerStorage.get(playerUuid);

        if (playerData == null) {
            playerData = new PlayerData(playerUuid);
            this.playerStorage.put(playerUuid, playerData);
        }

        return playerData;
    }

    public void removePlayer(Player player) {
        this.playerStorage.remove(player.getUniqueId());
    }

    public void removePlayer(UUID playerUuid) {
        this.playerStorage.remove(playerUuid);
    }
}
