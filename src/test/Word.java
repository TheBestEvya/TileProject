package test;

import java.util.Arrays;

public class Word {
    //Attributes
    private Tile[] tiles;
    private int row, column;
    private boolean vertical;
    //CTOR
    public Word(Tile[] tiles, int row, int column, boolean vertical) {
        this.tiles = tiles;
        this.row = row;
        this.column = column;
        this.vertical = vertical;
    }
    //Methods
    public Tile[] getTiles() {
        return tiles;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isVertical() {
        return vertical;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return row == word.row && column == word.column && vertical == word.vertical && Arrays.equals(tiles, word.tiles);
    }
}
