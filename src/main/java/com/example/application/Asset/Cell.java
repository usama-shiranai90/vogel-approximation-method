package com.example.application.Asset;

public class Cell {

    private int valuePerCell;
    private int minimumValue;

    public Cell() {
        minimumValue =  -1 ;
    }
    public Cell(int valuePerCell) {
        this.valuePerCell = valuePerCell;
        minimumValue = -1;
    }

    public Cell(int valuePerCell, int minimumValue) {
        this.valuePerCell = valuePerCell;
        this.minimumValue = minimumValue;
    }

    public int getValuePerCell() {
        return valuePerCell;
    }

    public void setValuePerCell(int valuePerCell) {
        this.valuePerCell = valuePerCell;
    }

    public int getMinimumValue() {
        return minimumValue;
    }

    public void setMinimumValue(int minimumValue) {
        this.minimumValue = minimumValue;
    }
}
