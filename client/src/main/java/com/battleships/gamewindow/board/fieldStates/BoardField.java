package com.battleships.gamewindow.board.fieldStates;


import com.battleships.Coordinate;
import javafx.scene.control.Button;

import java.io.Serializable;
import java.util.Objects;

public class BoardField extends Button implements Serializable {
    protected Coordinate coordinate;
    private FieldState fieldState;

    public BoardField(Coordinate coordinate, FieldState fieldState) {
        this.coordinate = coordinate;
        this.fieldState = fieldState;
        refreshStyle();

    }

    public boolean isHit() {
        return fieldState == FieldState.HIT_MAST || fieldState == FieldState.SUNK_MAST;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void hit() {
        fieldState = fieldState.afterHitState();
        this.refreshStyle();
    }

    private void refreshStyle() {
        this.setStyle(fieldState.getStyle());
    }

    public void setFieldState(FieldState fieldState) {
        this.fieldState = fieldState;
        this.refreshStyle();
    }

    public boolean isSea() {
        return fieldState == FieldState.SEA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardField that = (BoardField) o;
        return Objects.equals(coordinate, that.coordinate) &&
                fieldState == that.fieldState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate, fieldState);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BoardField{");
        sb.append("coordinate=").append(coordinate);
        sb.append(", fieldState=").append(fieldState);
        sb.append('}');
        return sb.toString();
    }

    public boolean isSunk() {
        return fieldState == FieldState.SUNK_MAST;
    }

    public FieldState getFieldState() {
        return fieldState;
    }
}

