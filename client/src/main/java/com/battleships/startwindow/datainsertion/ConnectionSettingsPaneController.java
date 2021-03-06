package com.battleships.startwindow.datainsertion;

import com.battleships.Client;
import com.battleships.LogMessages;
import com.battleships.Translator;
import com.battleships.commands.CommandType;
import com.battleships.commands.Message;
import com.battleships.connection.Connection;
import com.battleships.connection.ConnectionInfo;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class ConnectionSettingsPaneController {
    private PlayerName playerName = Connection.INSTANCE.getPlayerName();

    private static final int WIDTH = 600;
    private static final int HEIGHT = 450;

    @FXML
    private Button connectToServerButton;
    @FXML
    private Button disconnectFromServerButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField ipTextField;
    @FXML
    private Button startGameButton;

    private final static Logger logger = LogManager.getLogger(ConnectionSettingsPaneController.class);

    /**
     * Sets text on buttons <b>connectToServerButton</b> and <b>disconnectFromServerButton</b> depending on chosen language settings and assigns action to each button
     * and sets text on <b>nameTextField</b>
     */
    public void initialize() {
        bindTextFieldsWithTranslation();
        bindConnectToServerButton();
        setOnActionToButtons();
        initPlayerName();
    }

    private void bindTextFieldsWithTranslation() {
        Translator.bind(connectToServerButton.textProperty(), "connect");
        Translator.bind(disconnectFromServerButton.textProperty(), "disconnect");
        Translator.bind(nameTextField.promptTextProperty(), "player_name");
        Translator.bind(startGameButton.textProperty(), "start_game");
    }

    private void bindConnectToServerButton() {
        connectToServerButton.disableProperty().bind(Bindings.isEmpty(ipTextField.textProperty()));
    }

    private void setOnActionToButtons() {
        connectToServerButton.disableProperty().bind(Connection.INSTANCE.connectedProperty());
        disconnectFromServerButton.disableProperty().bind(Connection.INSTANCE.connectedProperty().not());
        startGameButton.disableProperty().bind(Connection.INSTANCE.connectedProperty().not());
        startGameButton.setOnAction(e -> startGame(e));
    }

    @FXML
    private void disconnect() {
        Connection.INSTANCE.disconnect();
        nameTextField.setEditable(true);
    }

    @FXML
    private void connectToServerButtonAction() {
        if (isPortAndIPPresent()) {
            String ip = getOptionalIPIfInsertedCorrectly().get();
            int port = extractPortIfPlayerInserted().get();
            ConnectionInfo connectionInfo = new ConnectionInfo(ip, port);
            handleConnectButtonAction(connectionInfo);
        } else {
            logErrorsAboutIPAndPort();
        }
    }

    private void handleConnectButtonAction(ConnectionInfo connectionInfo) {
        Connection.INSTANCE.establishConnection(connectionInfo);
        if (!Connection.INSTANCE.isConnected()) {
            showConnectionFailedDialog();
        } else {
            Message setNameCommand = new Message(CommandType.REGISTER_NEW_PLAYER, nameTextField.getText());
            nameTextField.setEditable(false);
            Connection.INSTANCE.sendToServer(setNameCommand);
        }
    }

    private void openGameWindow(ActionEvent event) {
        try {
            URL resource = Client.class.getResource("gamewindow/game_window.fxml");
            Parent root = FXMLLoader.load(resource);
            Stage stage = new Stage();
            stage.titleProperty().bind(Translator.createStringBinding("game_window"));
            Scene scene = new Scene(root, WIDTH, HEIGHT);
            scene.getStylesheets().add(Client.class.getResource("gamewindow/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(event1 -> disconnect());

            stage.addEventHandler(KeyEvent.KEY_RELEASED, nextEvent -> {
                if (nextEvent.getCode() == KeyCode.ESCAPE) {
                    logger.info(LogMessages.QUIT_WITH_ESCAPE);
                    disconnect();
                    Platform.exit();
                }
            });

            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void showConnectionFailedDialog() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.titleProperty().bind(Translator.createStringBinding("connection_failed"));
        alert.contentTextProperty().bind(Translator.createStringBinding("connection_failed_instructions"));
        alert.showAndWait();
    }

    private void logErrorsAboutIPAndPort() {
        if (!getOptionalIPIfInsertedCorrectly().isPresent()) {
            logger.error(LogMessages.WRONG_IP_ADDRESS);
        }
        if (!extractPortIfPlayerInserted().isPresent()) {
            logger.error(LogMessages.WRONG_PORT_NUMBER);
        }
    }

    private void initPlayerName() {
        playerName.playerNameProperty().bindBidirectional(nameTextField.textProperty());
        nameTextField.textProperty()
                .addListener((observableValue, oldValue, newValue) ->
                        nameTextField.setText(String.valueOf(newValue)));
    }

    private boolean isPortAndIPPresent() {
        return getOptionalIPIfInsertedCorrectly().isPresent() && extractPortIfPlayerInserted().isPresent();
    }

    private Optional<String> getOptionalIPIfInsertedCorrectly() {
        String ip = extractIPFromIPTextFieldContent();
        if (!ip.isEmpty()) {
            return Optional.of(ipTextField.textProperty().get().split(":")[0]);
        } else {
            return Optional.empty();
        }
    }

    private String extractIPFromIPTextFieldContent() {
        return ipTextField.textProperty().get().split(":")[0].trim();
    }

    private Optional<Integer> extractPortIfPlayerInserted() {
        try {
            return Optional.of(Integer.valueOf(extractIPAndPortFromTextFieldContent()[1]));
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            return Optional.empty();
        }
    }

    private String[] extractIPAndPortFromTextFieldContent() {
        return ipTextField.getText().split(":");
    }

    public void startGame(ActionEvent event) {
        Message message = new Message(CommandType.MOVE_TO_GAME_STATE, playerName.getPlayerName());
        Connection.INSTANCE.sendToServer(message);
        openGameWindow(event);
    }
}
