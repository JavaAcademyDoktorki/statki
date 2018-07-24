package com.battleships.gamewindow.board.fieldStates;

import com.battleships.models.board.Coordinate;

public class SunkMastField extends BoardField {
    public SunkMastField(Coordinate coordinate) {
        super(coordinate);
    }

    @Override
    public BoardField hit() {
        return null;
    }

    @Override
    public void refreshColor() {
        this.setStyle(FieldStyles.SunkMast.getStyle());
    }
}
