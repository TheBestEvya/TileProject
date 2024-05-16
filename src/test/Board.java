package test;

import java.util.ArrayList;

public class Board {
    public static final int BOARD_SIZE = 15;
    private static Board board = null;
    public Tile[][] gameboard;
    private char[][] bonusBoard;

    private Board() {
        this.gameboard = new Tile[BOARD_SIZE][BOARD_SIZE];
        bonusBoard = new char[][]{
                //R = RED    = TRIPLE WORD SCORE
                //A = AZURE  = DOUBLE LETTER SCORE
                //Y = YELLOW = DOUBLE WORD SCORE
                //B = BLUE   = TRIPLE LETTER SCORE
                //S = STAR   = DOUBLE WORD SCORE
                {'R', ' ', ' ', 'A', ' ', ' ', ' ', 'R', ' ', ' ', ' ', 'A', ' ', ' ', 'R'},
                {' ', 'Y', ' ', ' ', ' ', 'B', ' ', ' ', ' ', 'B', ' ', ' ', ' ', 'Y', ' '},
                {' ', ' ', 'Y', ' ', ' ', ' ', 'A', ' ', 'A', ' ', ' ', ' ', 'Y', ' ', ' '},
                {'A', ' ', ' ', 'Y', ' ', ' ', ' ', 'A', ' ', ' ', ' ', 'Y', ' ', ' ', 'A'},
                {' ', ' ', ' ', ' ', 'Y', ' ', ' ', ' ', ' ', ' ', 'Y', ' ', ' ', ' ', ' '},
                {' ', 'B', ' ', ' ', ' ', 'B', ' ', ' ', ' ', 'B', ' ', ' ', ' ', 'B', ' '},
                {' ', ' ', 'A', ' ', ' ', ' ', 'A', ' ', 'A', ' ', ' ', ' ', 'A', ' ', ' '},
                {'R', ' ', ' ', 'A', ' ', ' ', ' ', 'S', ' ', ' ', ' ', 'A', ' ', ' ', 'R'},
                {' ', ' ', 'A', ' ', ' ', ' ', 'A', ' ', 'A', ' ', ' ', ' ', 'A', ' ', ' '},
                {' ', 'B', ' ', ' ', ' ', 'B', ' ', ' ', ' ', 'B', ' ', ' ', ' ', 'B', ' '},
                {' ', ' ', ' ', ' ', 'Y', ' ', ' ', ' ', ' ', ' ', 'Y', ' ', ' ', ' ', ' '},
                {'A', ' ', ' ', 'Y', ' ', ' ', ' ', 'A', ' ', ' ', ' ', 'Y', ' ', ' ', 'A'},
                {' ', ' ', 'Y', ' ', ' ', ' ', 'A', ' ', 'A', ' ', ' ', ' ', 'Y', ' ', ' '},
                {' ', 'Y', ' ', ' ', ' ', 'B', ' ', ' ', ' ', 'B', ' ', ' ', ' ', 'Y', ' '},
                {'R', ' ', ' ', 'A', ' ', ' ', ' ', 'R', ' ', ' ', ' ', 'A', ' ', ' ', 'R'}
        };
    }

    public static Board getBoard() {
        if (board == null)
            board = new Board();
        return board;
    }

    public Tile[][] getTiles() {
        return gameboard.clone();
    }

    public boolean boardLegal(Word w) {
        if (w.getTiles().length<2)
            return false;
        if (!isWordInBoard(w))
            return false;
//        if ()
//            return false;
        if (!isWordOverlapping(w)&&!isFirstWordOnStar(w))
            if (!isWordAdj(w))
                return false;

        return true;
    }

    public boolean dictionaryLegal(Word w) {
        return true;
    }

    public int getScore(Word w){
        int sum = 0;

        int row = w.getRow();
        int col = w.getColumn();
        Tile[] tempTiles = new Tile[w.getTiles().length];
        int count = 0;
        for (Tile tile : w.getTiles()) {

            if (gameboard[row][col] == null) {
                tempTiles[count] = tile;
            }
            else {
                tempTiles[count] = gameboard[row][col];
            }

            count++;

            if (w.isVertical()) {
                row++;
            }
            else {
                col++;
            }
        }

        Word tempWord = new Word(tempTiles, w.getRow(), w.getColumn(), w.isVertical());
        if (!dictionaryLegal(tempWord)) {
            return 0;
        }

        ArrayList<Word> words = new ArrayList<>();
            words = getWords(w);
            if (words == null) {
                return 0;
            }

        words.add(tempWord);

        for (Word word : words) {

            int i = 0;
            int wordBonus = 1; // total word bonuses
            int wordScore = 0;

            for (Tile t : word.getTiles()) {

                if (word.isVertical()) {

                    switch (bonusBoard[word.getRow() + i][word.getColumn()]) {

                        case 'R':
                            wordScore += t.score;
                            wordBonus *= 3;
                            break;

                        case 'Y':
                        case 'S':
                            wordScore += t.score;
                            wordBonus *= 2;
                            bonusBoard[7][7] = ' ';
                            break;

                        case 'B':
                            wordScore += t.score * 3;
                            break;

                        case 'A':
                            wordScore += t.score * 2;
                            break;

                        default:
                            wordScore += t.score;
                            break;
                    }
                }
                if (!word.isVertical()) {

                    switch (bonusBoard[word.getRow()][word.getColumn() + i]) {

                        case 'R':
                            wordScore += t.score;
                            wordBonus *= 3;
                            break;

                        case 'Y':
                        case 'S':
                            wordScore += t.score;
                            wordBonus *= 2;
                            bonusBoard[7][7] = ' ';
                            break;

                        case 'B':
                            wordScore += t.score * 3;
                            break;

                        case 'A':
                            wordScore += t.score * 2;
                            break;

                        default:
                            wordScore += t.score;
                            break;
                    }
                }
                i++;
            }
            sum += (wordScore * wordBonus);
        }
        return sum;
    }

    public int tryPlaceWord(Word w){
        if (boardLegal(w)) {
            int score = getScore(w);
            if (score > 0) {

                int row = w.getRow();
                int col = w.getColumn();
                for (Tile t : w.getTiles()) {

                    if (t != null) {
                        gameboard[row][col] = t;
                    }
                    if (w.isVertical()) {
                        row++;
                    }
                    else {
                        col++;
                    }
                }
            }
            return score;
        }
        return 0;
    }

    private boolean isWordInBoard(Word w) {
        int col = w.getColumn();
        int row = w.getRow();
        int length = w.getTiles().length;

        if (length == 0)
            return false;
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE)
            return false;

        if (w.isVertical()) { //check if word is out of board's range
            if (row + length >= BOARD_SIZE)
                return false;
        } else {
            if (col + length >= BOARD_SIZE)
                return false;
        }
        return true;
    }

    private boolean isFirstWordOnStar(Word w) {
        int col = w.getColumn();
        int row = w.getRow();
        int length = w.getTiles().length;
        if (this.gameboard[7][7] == null) { // checks if w is the first word
            if (w.isVertical()) { // from top to bottom
                if (col != 7)
                    return false;
                if (row > 7 || row + length < 7)
                    return false;
            } else {               // from left to right
                if (row != 7)
                    return false;
                if (col > 7 || col + length < 7)
                    return false;
            }
        }
        return true;
    }

    private boolean isWordAdj(Word w) {
        int col = w.getColumn();
        int row = w.getRow();
        int length = w.getTiles().length;
        if (w.isVertical()) { //from top to bottom
            if (row != 0)
                if (gameboard[row - 1][col] != null)
                    return true;
            if (row != 14)
                if (gameboard[row + 1][col] != null)
                    return true;
            for (int i = row; i < length + row; i++) {
                if (col == 0)
                    if (gameboard[i][col + 1] != null)
                        return true;
                if (col == 14)
                    if (gameboard[i][col - 1] != null)
                        return true;
            }
        } else {               //from left to right
            if (col != 0)
                if (gameboard[row][col - 1] != null)
                    return true;
            if (col != 14)
                if (gameboard[row][col + 1] != null)
                    return true;
            for (int i = col; i < length + col; i++) {
                if (row == 0)
                    if (gameboard[row + 1][i] != null)
                        return true;
                if (row == 14)
                    if (gameboard[row - 1][i] != null)
                        return true;
            }
        }
        return false;
    }

    private boolean isWordOverlapping(Word w){
        int col = w.getColumn();
        int row = w.getRow();
        int length = w.getTiles().length;

        if (w.isVertical()){ // from top to bottom
            for (int i = row; i < length+row; i++) {
                if (gameboard[i][col]!= null){
                    if ( w.getTiles()[i-row] == null || w.getTiles()[i-row].letter == '_')
                    return true;
                }
            }
        }else{               // from left to right
            for (int i = col; i <length+col ; i++) {
                if (gameboard[row][i]!=null){
                    if (w.getTiles()[i-col] == null||w.getTiles()[i-col].letter== '_')
                        return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Word> getWords(Word w) {

        ArrayList<Word> words = new ArrayList<>();

        if (w.isVertical()) {

            int i = w.getRow();

            for (Tile t : w.getTiles()) {

                int j = w.getColumn();
                ArrayList<Tile> temp = new ArrayList<>();

                // add only new words
                if (t != null) {

                    while (j > 0 && gameboard[i][j - 1] != null)
                        j--;
                    int tempCol = j;
                    while (j < 15 && (gameboard[i][j] != null || gameboard[i][j+1] != null && j==w.getColumn())) {
                        if (gameboard[i][j] != null) {
                            temp.add(gameboard[i][j]);
                            j++;
                            }
                        if (gameboard[i][j]==null && j==w.getColumn()){
                            temp.add(t);
                            j++;
                        }
                    }

                    Tile[] tempT = temp.toArray(new Tile[0]);
                    Word tempWord = new Word(tempT, i, tempCol, false);
                    if (!tempWord.toString().equals("")) {
                        if (dictionaryLegal(tempWord) && boardLegal(tempWord)) {
                            words.add(tempWord);
                        }
                        else if (tempWord.getTiles().length > 1) {
                            return null;
                        }
                    }
                }
                i++;
            }
        }
        if (!w.isVertical()) {

            int i = w.getColumn();

            for (Tile t : w.getTiles()) {

                int j = w.getRow();
                ArrayList<Tile> temp = new ArrayList<>();

                if (t != null) {

                    while (j > 0 && gameboard[j - 1][i] != null)
                        j--;

                    int tempRow = j;
                    while (j < 15 && (gameboard[j][i] != null ||gameboard[j+1][i]!=null && j==w.getRow())) {
                        if (gameboard[j][i] != null) {
                            temp.add(gameboard[j][i]);
                            j++;
                        }
                        if (gameboard[j][i]==null && j==w.getRow()){
                            temp.add(t);
                            j++;
                        }
                    }
                    Tile[] tempT = temp.toArray(new Tile[0]);
                    Word tempWord = new Word(tempT, tempRow, i, true);
                    if (!tempWord.toString().equals("")) {
                        if (dictionaryLegal(tempWord) && boardLegal(tempWord)) {
                            words.add(tempWord);
                        }
                        else if (tempWord.getTiles().length > 1) {
                            return null;
                        }
                    }
                }
                i++;
            }
        }

        return words;
    }
}






















