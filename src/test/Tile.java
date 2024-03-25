package test;

import java.util.Objects;

public class Tile {
    // Attributes
    public final char letter;
    public final int score;
    //CTORS
    public Tile(char l, int s){
        this.letter=l;
        this.score=s;
    }
	//Methods
    public char getLetter() {
        return letter;
    }

    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter && score == tile.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, score);
    }
}
