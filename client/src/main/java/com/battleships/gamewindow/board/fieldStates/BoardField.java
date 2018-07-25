package com.battleships.gamewindow.board.fieldStates;


import com.battleships.Coordinate;
import javafx.scene.control.Button;

public abstract class BoardField extends Button {
    protected Coordinate coordinate;

    public BoardField(Coordinate coordinate) {
        this.coordinate = coordinate;
        refreshColor();
    }

    public abstract void hit();
    public abstract void refreshColor();

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
