package com.battleships;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class ConnectedPlayers {
    private final List<Player> playerList;

    ConnectedPlayers() {
        this.playerList = new CopyOnWriteArrayList<>();
    }

    void add(Player player) {
        playerList.add(player);
    }

    void remove(Player player) {
        playerList.remove(player);
    }
}