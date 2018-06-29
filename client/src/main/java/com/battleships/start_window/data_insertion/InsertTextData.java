package com.battleships.start_window.data_insertion;

import com.battleships.Translator;
import com.battleships.start_window.connection.ConnectInfo;
import com.battleships.start_window.connection.Connection;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class InsertTextData {
    @FXML
    private Button connectToServerButton;
    @FXML
    private Button disconnectButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField ipTextField;

    @FXML
    public void initialize() {
        bindTextFieldsWithTranslation();
    }

    private void bindTextFieldsWithTranslation() {
        Translator.bind(connectToServerButton, "connect");
        Translator.bind(disconnectButton, "disconnect");

        connectToServerButton.disableProperty().bind(Bindings.isEmpty(ipTextField.textProperty()));

        nameTextField.promptTextProperty().bind(Translator.createStringBinding("player_name"));
        connectToServerButton.setOnAction(e -> Connection.INSTANCE.connect(getConnectInfo(), nameTextField.getText()));
        disconnectButton.setOnAction(e -> Connection.INSTANCE.disconnect());
    }

    private ConnectInfo getConnectInfo() {
        ConnectInfo connectInfo = new ConnectInfo();
        String[] ipAndPort = ipTextField.getText().split(":");
        connectInfo.ip = ipAndPort[0];
        connectInfo.port = Integer.valueOf(ipAndPort[1]);
        return connectInfo;
    }

}