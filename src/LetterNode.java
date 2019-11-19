/**
 * this class provides the object which the letters can map to.
 * So, each letter will have their own object storing the frequency and score to it.
 */
public class LetterNode {

    /**initiating the score to store score for a letter */
    private int score;

    /**initiating the frequency to store the frequency of a letter */
    private int frequency;

    /**constructor to set the score and frequency for the object instantiated*/
    public LetterNode(int score, int frequency){
        this.score = score;
        this.frequency = frequency;
    }


    /**
     *
     * @returns the frequency of the letter
     */
    public int  getFrequency(){
        return frequency;
    }

    /**
     *
     * @returns the score of the letter
     */
    public int getScore(){
        return score;
    }
}
