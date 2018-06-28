package com.battleships.start_window;

import com.battleships.Translator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RoomListController {
    @FXML
    private Label roomListLabel;
    @FXML
    private Button createRoomButton;
    @FXML
    private Button selectRoomButton;

    @FXML
    public void initialize(){
        Translator.bind(selectRoomButton, "select_room");
        Translator.bind(createRoomButton, "create_room");
        Translator.bind(roomListLabel, "rooms_list");
    }
}
