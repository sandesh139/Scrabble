import java.util.HashMap;

/**
 * this class does the hard coding for the frequency of letters and the score assigned to the corresponding letters.
 * And, also
 */
public class Score {

    /**initiating the letters we have as possible tiles in the game */
    private char[] letters = {'*','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r',
            's','t','u','v','w','x','y','z'};

    /**initiating the letter scores for each letters */
    private int[] letterScore= {0,1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};

    /**initiating the frequency of each letters */
    private int[] frequency =  {2,12,9,9,8,6,6,6,4,4,4,4,3,2,2,2,2,2,2,2,2,2,1,1,1,1,1};
    //int[] frequency = {0,4,0,0,0,4,0,0,1,4,0,0,0,0,0,4,0,0,0,0,0,1,0,0,0,0,0};

    /**declaring a String to store the total number of letters available in the bag. */
    String leftOverString = "**";

    /**initiating a hasmap to map unique letters with its frequency and score */
    private HashMap<Character,LetterNode> scoreMap = new HashMap();


    /**
     * this sets the score for each characters we have available
     */
    public void setScore(){
        for(int i =0; i<letters.length;i++){
            scoreMap.put(letters[i],new LetterNode(letterScore[i],frequency[i]));
        }
    }

    /**
     * this sets the leftoverstring for the representation of letters in the bag
     */
    public void setLeftString(){
        for (int i =0;i<scoreMap.size()-1;i++){
            for (int j =0; j<scoreMap.get((char)('a'+i)).getFrequency();j++){
                leftOverString =  leftOverString+ Character.toString((char)('a'+i));
            }
        }
    }

    /**
     *
     * @return the left over string or letters in the bag.
     */
    public String  getStringTile(){
        return leftOverString;
    }


    /**
     * this method takes the character and puts back that character in the bag
     * @param c
     */
    public void putBackLetter(char c){
        leftOverString =leftOverString +Character.toString(c);
    }

    /**
     * this method takes the letters  in str out of the bag.
     * @param str
     */
    public void setStringTile(String str) {
        if (str.equals("*")) {
            leftOverString = leftOverString.replaceFirst("\\*", "");
        } else {
            leftOverString = leftOverString.replaceFirst(str, "");
        }
    }

    /**
     * this method takes in the character c and returns the corresponding score assigned to it
     * @param c
     * @return
     */
    public int getScore(char c){
        return scoreMap.get(c).getScore();
    }
}
