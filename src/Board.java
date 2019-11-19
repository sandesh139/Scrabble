import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * board class reads the text file to get the board and change it to the two-d arrays
 */
public class Board {

    /**initiating the two-d array to store the board information. */
    private char board[][] = null;

    /**initiating the boardstring to store all text into the string per line */
    private ArrayList<String> boardString = new ArrayList<>();

    /**initiating the boardStr to hold string in which each character represents the corresponding space in the board*/
    private String boardStr ="";

    /**
     * constructor helps getting the object with board setup
     */
    public Board(){
        getBoardString();
        board = getBoard(Integer.parseInt(boardString.get(0)),1);
    }

    /**
     * this method reads the file from the text and store in a arraylist of string, each element of arraylist stores the
     * a single line of text file.
     * @returns the arraylist as string of raw board
     */
    public ArrayList<String> getBoardString() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File("scrabble_board.txt")));
            String line = reader.readLine();
            while (line != null) {
                boardString.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return boardString;
    }


    /**
     * this method gets the size of the board and the countline where board starts from
     * this method converts the arraylist of string to the two-d array board
     * @param sizeOfBoard
     * @param countLine
     * @returns the two-d array as representation of board
     */
    public char[][] getBoard(int sizeOfBoard, int countLine) {
        int colx = 0;
        int column = sizeOfBoard;
        int countletter = 0;
        boolean isInteger = false;
        int position=0;
        board = new char[sizeOfBoard][sizeOfBoard];
        for (int i = 0; i < sizeOfBoard; i++) {
            String str = boardString.get(countLine);
            str =str.replaceAll(" ", "");
            for (int j = 0; j < column * 2 - countletter; j++) {
                if ((position  % 2 == 0)) {
                    if (Character.isDigit(str.charAt(j))) {
                        String temp;
                        temp = Character.toString(str.charAt(j));
                        int y=Integer.parseInt(temp) + 5;   //if the character is in left then we shift number by 5.
                        board[i][colx] = Integer.toString(y).charAt(0);
                        boardStr = boardStr+ Integer.toString(y);
                        isInteger = true;
                    }
                } else if ((position  % 2 == 1)) {
                    if (Character.isDigit(str.charAt(j))) {
                        board[i][colx] = str.charAt(j);
                        boardStr = boardStr+ Character.toString(str.charAt(j));
                        isInteger = true;
                    }
                }
                if (position  % 2 == 1&& isInteger==false) {
                    boardStr =boardStr+".";
                    board[i][colx] = ".".charAt(0);      //empty tile of the board
                }
                position++;


                if (Character.isLetter(str.charAt(j))) {
                    board[i][colx] = str.charAt(j);
                    boardStr =boardStr+ Character.toString(str.charAt(j));
                    countletter++;
                    colx++;
                    position =0;
                    isInteger = false;
                } else if (position % 2 == 0 && j > 0 && !Character.isLetter(str.charAt(j))) {
                    colx++;
                    isInteger = false;
                }
            }
            position =0;
            colx =0;
            countletter =0;
            countLine++;
        }
        return board;
    }

    /**
     *
     * @returns the two-d representation of the baord
     */
    public char[][] getBoard(){
        return board;
    }

}
