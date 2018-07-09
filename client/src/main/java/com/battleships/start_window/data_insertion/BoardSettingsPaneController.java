package com.battleships.start_window.data_insertion;

import com.battleships.Translator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class BoardSettingsPaneController {
    private BoardSettings boardSettings = new BoardSettings();

    @FXML
    private Label rowsTextLabel;
    @FXML
    private Slider rowsProgressBar;
    @FXML
    private Label rowsAmountDisplayer;

    @FXML
    private Label columnsTextLabel;
    @FXML
    private Slider columnsProgressBar;
    @FXML
    private Label columnsAmountDisplayer;


    public void initialize() {
        initRowsProgressBar();
        initColumnsProgressBar();
    }

    private void initRowsProgressBar() {
        Translator.bind(rowsTextLabel.textProperty(), "rows_count");
        boardSettings.rowCountProperty().bind(rowsProgressBar.valueProperty());
        rowsProgressBar.valueProperty()
                .addListener((observableValue, oldValue, newValue) ->
                        rowsAmountDisplayer.setText(String.valueOf(newValue.intValue())));
    }

    private void initColumnsProgressBar() {
        Translator.bind(columnsTextLabel.textProperty(), "columns_count");
        boardSettings.columnCountProperty().bind(columnsProgressBar.valueProperty());
        columnsProgressBar.valueProperty()
                .addListener((observableValue, oldValue, newValue) ->
                        columnsAmountDisplayer.setText(String.valueOf(newValue.intValue())));
    }

    public BoardSettings getBoardSettings() {
        return boardSettings;
    }
}