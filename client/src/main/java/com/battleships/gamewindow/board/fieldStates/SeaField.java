package com.battleships.gamewindow.board.fieldStates;

public class SeaField extends BoardField {
    @Override
    public BoardField hit() {
        return null;
    }

    @Override
    public void refreshColor() {
        this.setStyle("-fx-background-color: #4abbf7");
    }
}