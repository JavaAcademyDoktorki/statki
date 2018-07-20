package com.battleships.models.board;

import javafx.scene.layout.GridPane;

public class Boards {

    private final GridPane myBoard;
    private final GridPane opponentsBoard;

    public Boards(GridPane myBoard, GridPane opponentsBoard) {
        this.myBoard = myBoard;
        this.opponentsBoard = opponentsBoard;
    }

    public GridPane getMyBoard() {
        return myBoard;
    }

    public GridPane getOpponentsBoard() {
        return opponentsBoard;
    }
}