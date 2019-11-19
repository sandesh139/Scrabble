/**
 * class WordScore is used for keeping the information of the best move by the computer.
 *
 */
public class WordScore {

    /** initiating the board position to store the board position of the first letter */
    private BoardPosition firstLetter;

    /**Declaring the String to store the best word for the computer*/
    private String word;

    /**Declaring an integer to store the best score for the computer*/
    private int score;

    /**declaring an integer to store the best direction for the computer */
    private String direction;


    /**this method is called to reset the position and word and score*/
    public void resetAll(){
        this.word ="";
        this.score =0;
        this.firstLetter = new BoardPosition(0,0);
    }

    /**this method returns the score */
    public int getScore(){
        return score;
    }

    /** this method is called every time when we have the best  score for the computer to store the information */
    public void setAll(BoardPosition firstLetter,String word, int score, String direction){
        this.score= score;
        this.firstLetter = firstLetter;
        this.word =word;
        this.direction = direction;
    }

    /**
     * this method prints the variables we have in the word score object
     */
    public void print(){
        System.out.println("this is the best word " + word);
        System.out.println("this is firstletter x= "+firstLetter.getY()+" and y = "+firstLetter.getX());
        System.out.println("this is score "+ score);
        System.out.println("this is the direction "+ direction);
    }

    /**
     * this method returns the x-coordinate of the first letter
     * @return
     */
    public int row(){
        return firstLetter.getX();
    }

    /**
     * this method returns y coordinates of the first letter
     * @return
     */
    public int col(){
        return firstLetter.getY();
    }
}
