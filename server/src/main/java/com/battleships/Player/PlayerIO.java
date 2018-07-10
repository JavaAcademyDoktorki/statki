package com.battleships.Player;

import com.battleships.commands.PlayerCommand;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

class PlayerIO {
    private final PrintWriter clientWriter;
    private final ObjectInputStream clientObjectReader;

    PlayerIO(Socket playerSocket) throws IOException {
        clientWriter = new PrintWriter(playerSocket.getOutputStream());
        clientObjectReader = new ObjectInputStream(playerSocket.getInputStream());
    }

    void sendCommand(String command) {
        clientWriter.println(command);
        clientWriter.flush();
    }

    <V> PlayerCommand<V> nextUserCommand() {
        try {
            return (PlayerCommand<V>) clientObjectReader.readObject(); // TODO 16.07 fix unchecked cast - krzychu
        } catch (IOException | ClassNotFoundException e) {
            // TODO 16.07.2018 handle - Damian
            e.printStackTrace();
        }
        return null; // TODO 16.07.2018 do not return null - Damian
    }
}