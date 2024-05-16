package test;

import java.util.Objects;

public class Tile {
    // Attributes
    public final char letter;
    public final int score;
    //CTORS
    private Tile(char l, int s){
        this.letter=l;
        this.score=s;
    }
	//Methods
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

    public static class Bag {
        //Attributes
        private final int[] topQuantities = {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1};
        private static Bag bag =null;
        private final Tile[] tiles = new Tile[26];
        private final int[] quantities;
        //CTORS
        private Bag(){
            int[] tileScore = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
            quantities = new int[26];
            System.arraycopy(topQuantities,0,quantities,0,topQuantities.length);
            for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new Tile((char)('A'+i),tileScore[i]);
            }
        }
        //Methods
        public static Bag getBag(){ // Singelton implentation
            if (bag ==null){
                bag = new Bag();
            }
            return bag;
        }

        public Tile getRand(){
            if (size()>0){
                int rand = (int)(Math.random()*25);
                if(quantities[rand]>0) {
                    quantities[rand]--;
                    return tiles[rand];
                }else
                    return getRand();
            }
            else
                return null;

        }
        public int size(){
            int sum=0;
            for (int i = 0; i <quantities.length; i++)
                sum+=quantities[i];
            return sum;
        }
        public Tile getTile(char c){
            if (c-'A' >= 0 && c-'A' <= 25)
            if (quantities[c-'A']>0){
                quantities[c-'A']--;
                return tiles[c-'A'];
            }
            return null;
        }
        public void put(Tile t){
            if(quantities[t.letter-'A'] + 1 <= topQuantities[t.letter-'A']) {
                quantities[t.letter - 'A']++;
            }
        }
        public int[] getQuantities(){
            int[] arr = new int[26];
            for (int i = 0; i < quantities.length; i++)
                arr[i]=quantities[i];
            return arr;
        }
    }
}
