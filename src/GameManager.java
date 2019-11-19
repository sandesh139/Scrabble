import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 * This class handles the logic part of the game, specially for the computer moves.
 * this class gets score, board, and datastructure and perform the best move computattion.
 */
public class GameManager {

    /**declaring an integer to store the size of the board */
    private int boardSize;

    /**creating the DataStructur object. This manages the data structure of dictionary in trie node */
    private DataStructure data;

    /**declaring two-d array to store the information of game board. */
    private char[][] board;

    /**declaring and initiating score to hold the score gained by left anchor*/
    private int scoreLeft =0;

    /**declaring and initiating score to hold the score gained by right anchor*/
    private int scoreRight =0;

    /**initiating the transpose boolean to get the case if the board is transfered */
    private boolean transpose =false;

    /**initiating the arraylist to hold the x and y position of character in board */
    private ArrayList<BoardPosition> characterPositions = new ArrayList<BoardPosition>();

    /**initiating the arraylist to hold the x and y position of left anchor in board */
    private ArrayList<BoardPosition> anchorLeft = new ArrayList<>();

    /**initiating the arraylist to hold the x and y position of right anchor in board */
    private ArrayList<BoardPosition> anchorRight = new ArrayList<>();

    /**initiating the arraylist to hold the x and y position of up anchor in board */
    private ArrayList<BoardPosition> anchorUp = new ArrayList<>();

    /**initiating the arraylist to hold the x and y position of down anchor in board */
    private ArrayList<BoardPosition> anchorDown = new ArrayList<>();

    /**initiating the arraylist to hold the x and y position of human play in board */
    private ArrayList<BoardPosition> humanPlayPosition = new ArrayList<>();

    /**initiating the arraylist of wordscore to hold the best score made by computer. */
    private ArrayList<WordScore> allScores = new ArrayList<>();

    /**initiating the arraylist to hold the strings that are found by the computer using its trays */
    private ArrayList<String> allFoundWords = new ArrayList<>();

    /**initiating the string to hold the fixed string found in the computer */
    private String fixedString ="";

    /**initiating the string direction to hold the direction computer is making the word */
    private String direction = "";

    /**declaring the two-d array to store the copy of the board **/
    private char[][] boardStore;

    /**initiating the arraylist to hold the position of letters that were placed in using the left anchor */
    private ArrayList<BoardPosition> stringPositionLeft = new ArrayList<>();

    /**initiating the arraylist to hold the position of letters that were placed in using the left anchor */
    private ArrayList<BoardPosition> stringPositionRight = new ArrayList<>();

    /**declaring the position Y of the first letter of the string got by the computer */
    private int firstPositionY;

    /**declaring the position X of the first letter of the string got by the computer */
    private int firstPositionX;

    /**declaring the string tray to hold the letters that computer has it in tray */
    private String tray;

    /**initiating the boolean to see if the computer tray has asterik in it */
    private boolean asterik = false;

    /**initiating the hashset totalwords to get the unique word to be tried by computer */
    private HashSet<String> totalWords = new HashSet<>();

    /**initiating the arraylist of string to get the total list of possible word for the computer */
    private ArrayList<String> totalList = new ArrayList<>();

    /**declaring the temporary board used to have trials of the words to get the best word */
    private char[][] temp;

    /**initiating the Score object to have the information of letter score and the total letters in the game*/
    public Score letterScore = new Score();

    /**declaring the integer to hold the number of letters that was picked up by the computer */
    private int letterFromTray =0;

    /**creating the human object to have it as a Player one in the game */
    public PlayerOne human = new PlayerOne();

    /**creating the computer object to have it as a Player two in the game */
    ComputerPlayer computer = new ComputerPlayer();

    /**creating rand as Random object used to get random letter out of the bag */
    Random rand = new Random();

    /**initiating the human score integer used to hold the human score through out the game */
    private int humanScore = 0;

    /**initiating the computer score integer used to hold the computer score through out the game */
    private int computerScore =0;

    /**declaring the oldboard two-d char to store the copy of the board */
    char[][] oldBoard;

    /**declaring the integer eachscore to store the individual score gained by human in each play */
    int eachScore =0;

    /**declaring the highestBoard to store the board with the highestScore made by the computer */
    char[][] highestBoard;

    /**initiating the computer played string to hold the computer played string in each move for getting best move */
    String computerPlayed ="";

    /**initiating the computer played string to hold the computer best played string in the board */
    String computerBestPlayed ="";


    /**Game Manager constructor to provide the properties to the board by the MainClass */
    GameManager( char[][] board, String dictionary) {
        data = new DataStructure(dictionary);
        this.boardSize = board[0].length;
        data.getStructure();
        this.boardStore = board;
        this.board = new char[boardSize][boardSize];
        this.temp = new char[boardSize][boardSize];
        oldBoard = new char[boardSize][boardSize];
        letterScore.setScore();
        letterScore.setLeftString();
    }

    /**
     * setting the old board.
     * @param oldBoard
     */
    public void setOldBoard(char[][] oldBoard) {
        this.oldBoard = oldBoard;
    }

    /**
     * setting the board
     * @param newBoard
     */
    public void setNewBoard(char[][] newBoard){
        this.board = newBoard;
    }


    /**
     * setting the final human score once the game ends
     * this is only called if the human tray is empty at the time of game ending
     * this method is called from MainClass using the game Object
     */
    public void setFinalHumanScore(){
        for(int i = 0; i<computer.getComputerTray().length();i++){
            humanScore = humanScore + letterScore.getScore(computer.getComputerTray().charAt(i));
        }
    }

    /**
     * setting the final computer score once the game ends
     * this is only called if the computer tray is empty at the time of game ending
     * this method is called from MainClass using the game Object
     */
    public void setComputerFinalScore(){
        for(int i = 0; i<human.getHumanTray().length();i++){
            computerScore = computerScore + letterScore.getScore(human.getHumanTray().charAt(i));
        }
    }

    /**
     * setting the final computer score once the game ends
     * this method is called from MainClass using the game Object
     * this is called only when game ends an both players has letters in their tray
     */
    public void setComputerHumanFinalScore(){
        for(int i = 0; i<human.getHumanTray().length();i++){
            humanScore = humanScore - letterScore.getScore(human.getHumanTray().charAt(i));
        }

        for(int i = 0; i<computer.getComputerTray().length();i++){
            computerScore = computerScore - letterScore.getScore(computer.getComputerTray().charAt(i));
        }


    }


    /**
     * this method is called from the Main Class to get the human score when human makes the valid move.
     * this method finds if the human move was made horizontal or verticle
     * if the move was horizontal, then we check each clicked position if that makes the word vertically.
     * if the move was vertical, then we check each clicked position if that makes the word horizontally.
     * @return the human score
     */
    public int getHumanScore(){
        for (int i =0; i<boardSize;i++){
            for(int j = 0; j<boardSize;j++){
                if(!(oldBoard[i][j]==board[i][j])){
                    humanPlayPosition.add(new BoardPosition(i,j));
                }
            }
        }

        String direction = "";
        if(humanPlayPosition.size()>1){
            if(humanPlayPosition.get(0).getX()==humanPlayPosition.get(1).getX()){
                direction = "horizontal";   //if human has same x, then direction is horizontal
            } else {
                direction ="vertical";
            }
        }


        //check verticle
        if(direction.equals("vertical")) {
            checkVertical(humanPlayPosition.get(0));

            //also check sidewise for each element
            for(int j =0; j<humanPlayPosition.size(); j++){
                checkSideWise(humanPlayPosition.get(j));
            }
        }


        //check horizontal
        if(direction.equals("horizontal")) {
            checkSideWise(humanPlayPosition.get(0));
            //also check sidewise for each element
            for(int j =0; j<humanPlayPosition.size(); j++){
                checkVertical(humanPlayPosition.get(j));
            }
        }
        if(direction.equals("")){
            checkSideWise(humanPlayPosition.get(0));
            checkVertical(humanPlayPosition.get(0));
        }
        humanScore = humanScore +eachScore;
        humanPlayPosition.clear();
        return humanScore;
    }

    /**
     * this methods checks if there is any valid verticle word to be considered for scoring.
     * @param position
     */
    public void checkVertical(BoardPosition position){
        eachScore =0;
        int mulitplier = 1;
        int localScoreX = 0;
        int i = 0;
        int x = position.getX();
        int y = position.getY();
        int counter =0;

        /*this while loops checks the connect string we have in up direction. we keep moving until the end of board or
        or until the we get blank board space
         */
        while (x - i >= 0 && Character.isAlphabetic(board[x - i][y])) {
            counter++;
            if (Character.isDigit(oldBoard[x - i][y])) {
                if (Character.getNumericValue(oldBoard[x - i][y]) > 5) {
                    mulitplier = Character.getNumericValue(oldBoard[x - i][y]) - 5;
                } else {
                    localScoreX = localScoreX + letterScore.getScore(board[x - i][y]) * Character.getNumericValue(oldBoard[x - i][y]);
                }
            } else {
                localScoreX = localScoreX + letterScore.getScore(board[x - i][y]);
            }
            i++;
        }


        i = 1;
        /*this while loops checks the connect string we have in down direction. we keep moving until the end of board or
        or until the we get blank board space
         */
        while (x + i < boardSize && Character.isAlphabetic(board[x + i][y])) {
            counter++;
            if (Character.isDigit(oldBoard[x + i][y])) {
                if (Character.getNumericValue(oldBoard[x + i][y]) > 5) {
                    mulitplier = Character.getNumericValue(oldBoard[x + i][y]) - 5;
                    localScoreX = localScoreX + letterScore.getScore(board[x + i][y]);
                } else {
                    localScoreX = localScoreX + letterScore.getScore(board[x + i][y]) * Character.getNumericValue(oldBoard[x + i][y]);
                }
            } else {
                localScoreX = localScoreX + letterScore.getScore(board[x + i][y]);
            }
            i++;
        }

        localScoreX = localScoreX * mulitplier;

        if(counter ==1){
            localScoreX =0;
        }
        humanScore =humanScore +localScoreX;
    }



    public void checkSideWise(BoardPosition position){
        eachScore =0;
        int i = 0;
        int counter =0;
        int mulitplier =1;
        int x = position.getX();
        int y = position.getY();
        int localScoreX =0;
         /*this while loops checks the connect string we have in left direction. we keep moving until the end of board or
        or until the we get blank board space
         */
        while (y - i >= 0 && Character.isAlphabetic(board[x ][y-i])) {
            counter++;
            //up = up +Character.toString(board[x-i][y]);
            if (Character.isDigit(oldBoard[x][y-i])) {
                if (Character.getNumericValue(oldBoard[x][y-i]) > 5) {
                    mulitplier = Character.getNumericValue(oldBoard[x][y-i]) - 5;
                } else {
                    localScoreX = localScoreX + letterScore.getScore(board[x][y-i]) * Character.getNumericValue(oldBoard[x][y-i]);
                }
            } else {
                localScoreX = localScoreX + letterScore.getScore(board[x][y-i]);
            }
            i++;
        }
        i = 1;
         /*this while loops checks the connect string we have in right direction. we keep moving until the end of board or
        or until the we get blank board space
         */
        while (y +i < boardSize && Character.isAlphabetic(board[x][y+i])) {
            //up = up +Character.toString(board[x+i][y]);
            counter ++;
            if (Character.isDigit(oldBoard[x][y+i])) {
                if (Character.getNumericValue(oldBoard[x][y+i]) > 5) {
                    mulitplier = Character.getNumericValue(oldBoard[x][y+i]) - 5;
                    localScoreX = localScoreX + letterScore.getScore(board[x][y+i]);
                } else {
                    localScoreX = localScoreX + letterScore.getScore(board[x][y+i]) * Character.getNumericValue(oldBoard[x][y+i]);
                }
            } else {
                localScoreX = localScoreX + letterScore.getScore(board[x][y+i]);
            }
            i++;
        }

        if(counter ==1){
            localScoreX =0;
        }

        localScoreX = localScoreX * mulitplier;
        humanScore =humanScore +localScoreX;

    }


    /**
     * this returns the human score
     * @return
     */
    public  int getHumanFinalScore(){
        return humanScore;
    }

    /**
     * this method returns the computer score
     * @return
     */
    public int getComputerSCore(){
        return computerScore;
    }

    public void setUpTiles(){

        while (human.getHumanTray().length()<7 &&letterScore.leftOverString.length()>0){
            String localStr = letterScore.getStringTile();
            int randomInteger = rand.nextInt(localStr.length());
            human.setHumanTray(localStr.charAt(randomInteger));
            letterScore.setStringTile(Character.toString(localStr.charAt(randomInteger)));
        }

        while (computer.getComputerTray().length()<7 &&letterScore.leftOverString.length()>0){
            String localStr = letterScore.getStringTile();
            int randomInteger = rand.nextInt(localStr.length());
            computer.setComputerTray(localStr.charAt(randomInteger));
            letterScore.setStringTile(Character.toString(localStr.charAt(randomInteger)));
        }
    }

    /**
     * this method is called from the MainClass to get the update board in computer's play.
     * this method now calls for finding anchor
     */
    public void updateBoard(){
        temp = new char[boardSize][boardSize];
        highestBoard= new char[boardSize][boardSize];
        for(int g =0;g<boardSize;g++){
            for(int h =0; h<boardSize;h++){
                highestBoard[h][g]= board[h][g];
                boardStore[h][g]= board[h][g];
                temp[h][g]= board[h][g];
                oldBoard[h][g]= board[h][g];
            }
        }
        System.out.println("board used for computation is given below");
        printBoard(board);
        System.out.println("printing best information below");
        findAnchor();
    }


    /**
     * this method sets the board that came out as a highest scoring board by the computer.
     * @returns the two-d board with the highest score
     */
    public char[][] getHighestBoard(){
        setBoardStore();
        drawLetters();
        if(computerBestPlayed.length()==0){     //if no best move of computer is found then we exchange computers tray
            exchangeTray();
        }
        return highestBoard;
    }


    /**
     * this method is called to exchange computer's tray.
     * here we put back the computer's tray to bag and get the new tray for it
     */
    public void exchangeTray(){
        for(int i =0; i <computer.getComputerTray().length(); i++){
            letterScore.putBackLetter(computer.getComputerTray().charAt(i));
        }
        computer.removeAll();

        //this while loop stops if computer gets 7 letters or the bag does not have the letters in it.
        while (computer.getComputerTray().length()<7 && letterScore.leftOverString.length()>0) {
            String localStr = letterScore.getStringTile();
            int randomInteger = rand.nextInt(localStr.length() - 1);
            computer.setComputerTray(localStr.charAt(randomInteger));
            letterScore.setStringTile(Character.toString(localStr.charAt(randomInteger)));
        }

    }

    /**
     * this is called to store the original board as a copy of the board
     */
    public void setBoardStore(){
        for(int g =0;g<boardSize;g++){
            for(int h =0; h<boardSize;h++){
                boardStore[h][g]= highestBoard[h][g];
            }
        }


    }


    /**
     * this is called after the computer makes a move to get back the letters from tray
     */
    public void drawLetters(){


        for (int i = 0; i< computerBestPlayed.length(); i++){
            computer.removeLetter(computerBestPlayed.charAt(i));
        }

        //this while loop stops if computer gets 7 letters or the bag does not have the letters in it.
        while (computer.getComputerTray().length()<7 && letterScore.leftOverString.length()>0){
            String localStr = letterScore.getStringTile();
            int randomInteger = rand.nextInt(localStr.length());
            computer.setComputerTray(localStr.charAt(randomInteger));
            letterScore.setStringTile(Character.toString(localStr.charAt(randomInteger)));
        }

    }

    /**
     * this method finds the anchors of the board
     */
    public void findAnchor(){
        characterPositions.clear();
        anchorUp.clear();
        anchorRight.clear();
        anchorDown.clear();
        anchorLeft.clear();
        allScores.clear();




        for(int g =0;g<boardSize;g++){
            for(int h =0; h<boardSize;h++){
                board[h][g]= boardStore[h][g];
            }
        }

        for(int i = 0; i<boardSize;i++){
            for (int j = 0; j<boardSize;j++){
                if(Character.isLetter(board[i][j])){
                    characterPositions.add(new BoardPosition(i,j));
                }
            }
        }

        for(int i =0; i<characterPositions.size();i++){
            int leftX =characterPositions.get(i).getX();
            int leftY = characterPositions.get(i).getY()-1;

            int rightX = characterPositions.get(i).getX();
            int rightY = characterPositions.get(i).getY()+1;

            int upX = characterPositions.get(i).getX()-1;
            int upY = characterPositions.get(i).getY();

            int downX = characterPositions.get(i).getX()+1;
            int downY = characterPositions.get(i).getY();
            if(leftX>=0&&leftY>=0 && !Character.isLetter(board[leftX][leftY])){
                anchorLeft.add(new BoardPosition(leftX,leftY));
                anchorUp.add(new BoardPosition(leftX,leftY));
               // System.out.println("x is "+ (leftX)+ " and  y is "+leftY);
            }
            if(rightX<boardSize && rightY<boardSize&&!Character.isLetter(board[rightX][rightY])){
                anchorRight.add(new BoardPosition(rightX,rightY));
                anchorDown.add(new BoardPosition(rightX,rightY));
               // System.out.println("x is "+ (rightX)+ " and  y is "+rightY);
            }

            if(upY>=0 && upX>=0 && !Character.isLetter(board[upX][upY])) {
                anchorUp.add(new BoardPosition(upX,upY));
                anchorLeft.add(new BoardPosition(upX,upY));
            }
            if(downY<boardSize && downX<boardSize&&!Character.isLetter(board[downX][downY])){
                anchorDown.add(new BoardPosition(downX,downY));
                anchorRight.add(new BoardPosition(downX,downY));
            }
        }

        //this finds words using the anchors
        findAllPossibleWords();

    }


    /**
     * here the computer finds the possible words using the tray letters and letters of corresponding anchors.
     */
    public void findAllPossibleWords() {
        tray = computer.getComputerTray();

        //this handles if computer tray has one asterik in it
        for (int i = 0; i < tray.length(); i++) {
            if (tray.charAt(i) == '*') {
                tray = tray.replaceAll("\\*", "");
                asterik = true;
            }
        }

        allScores.clear();
        allScores.add(new WordScore());
        allScores.get(0).resetAll();

        //this interates through each left anchors
        for (int i = 0; i < anchorLeft.size(); i++) {
            String str = "";
            int j = 1;

            while (anchorLeft.get(i).getY()+j < boardSize &&Character.isLetter(board[anchorLeft.get(i).getX()][anchorLeft.get(i).getY()+j])
                    ) {
                str = str + Character.toString(board[anchorLeft.get(i).getX()][anchorLeft.get(i).getY()+j]);
                j++;
            }
            fixedString = str;        //this gets the string from the board next to the anchor
            direction = "left";
            getString(str, anchorLeft.get(i),"left");
        }


        //this iterates through each right anchor
        for (int i = 0; i < anchorRight.size(); i++) {
            String str = "";
            int j = 1;
            while (anchorRight.get(i).getY() - j >= 0 &&Character.isLetter(board[anchorRight.get(i).getX()][anchorRight.get(i).getY() - j])
                    ) {
                str = str + Character.toString(board[anchorRight.get(i).getX()][anchorRight.get(i).getY() - j]);
                j++;
            }
            fixedString = str;          //this gets the string from the board next to the anchor
            direction ="right";
            getString(str, anchorRight.get(i), "right");
        }


        //iterates through each up anchor
        for (int i = 0; i < anchorUp.size(); i++) {
            String str = "";
            int j = 1;

            while (anchorUp.get(i).getX()+j < boardSize &&Character.isLetter(board[anchorUp.get(i).getX()+j][anchorUp.get(i).getY()])) {
                str = str + Character.toString(board[anchorUp.get(i).getX()+j][anchorUp.get(i).getY()]);
                j++;
            }
            //System.out.println("this is the string from up anchor"+ str);
            fixedString = str;
            direction = "up";
            anchorUp.get(i).exchange(anchorUp.get(i).getX(),anchorUp.get(i).getY());
            //getString(str, anchorUp.get(i),"up");
        }

        //iterates through each down anchor
        for (int i = 0; i < anchorDown.size(); i++) {
            String str = "";
            int j = 1;
            while (anchorDown.get(i).getY()-j >= 0 &&Character.isLetter(board[anchorDown.get(i).getY()-j][anchorDown.get(i).getX()])
                    ) {
                str = str + Character.toString(board[anchorDown.get(i).getY()-j ][anchorDown.get(i).getX()]);
                j++;
            }
            fixedString = str;
            direction = "down";
            anchorDown.get(i).exchange(anchorDown.get(i).getX(),anchorDown.get(i).getY());
            //getString(str, anchorDown.get(i),"down");
        }

        //if computer finds the best move then we increase the computer's score
        if(computerBestPlayed!="") {
            computerScore = computerScore + allScores.get(0).getScore();
        }
        allScores.get(0).print();

        System.out.println("this is the size of all score "+allScores.size());


    }


    /**
     * this method is called to check if game ends or not.
     * @return
     */
    public boolean isWinner() {
        int storeComputerScore =computerScore;
        String storeComputerTray = computer.getComputerTray();
        if (letterScore.leftOverString.length() == 0 && (human.getHumanTray().length() == 0 || computer.getComputerTray().length() == 0)) {
            return true;
        }
        if (letterScore.leftOverString.length() == 0) {
            char[][] localBoard = new char[boardSize][boardSize];
            for (int m = 0; m < boardSize; m++) {
                for (int n = 0; n < boardSize; n++) {
                    localBoard[m][n] = board[m][n];
                }
            }
            findAnchor();
            //here we are checking if the computer has valid move
            if (!computerBestPlayed.equals("")) {

                for (int m = 0; m < boardSize; m++) {
                    for (int n = 0; n < boardSize; n++) {
                        board[m][n] = localBoard[m][n];
                    }
                }
                computerScore = storeComputerScore;
                return false;    //return false if the computerBestPlayed string is not empty
            }

            //here we are setting the human tray with computer tray to check if the human hsa valid move
            computer.setComputerTray(human.getHumanTray());
            findAnchor();

            if(!computerBestPlayed.equals("")){
                for (int m = 0; m < boardSize; m++) {
                    for (int n = 0; n < boardSize; n++) {
                        board[m][n] = localBoard[m][n];
                    }
                }
                computer.setComputerTray(storeComputerTray);
                computerScore = storeComputerScore;
                return false;     //return false if the computerBestPlayed string is not empty
            }
            computer.setComputerTray(storeComputerTray);



        } else {
            computerScore = storeComputerScore;
            return false; //return false if the letter bag has not been empty yet.
        }
        computerScore = storeComputerScore;
        return true;
    }


    /**
     * this method takes the string and checks if there is any string made out of that combination
     * this method is called from the getstring method
     * @param str
     * @returns the arraylist of strings that are words found in the dictionary
     */
    public ArrayList<String>  possibleWords(String str){

        allFoundWords.clear();
        String combined =str+tray;
        if(asterik==false) {
            allFoundWords.addAll(data.tree.findWord(combined, data.tree,str));
        } else {
            for (int i =0; i<26;i++){
                String letter =Character.toString((char)(i+'a'));
                allFoundWords.addAll(data.tree.findWord((combined+letter),data.tree,str));
            }
        }


        return allFoundWords;
    }


    /**
     * this method is called for each element in the anchor list
     * @param string is the tray and letters in the corresponding anchor
     * @param boardPosition   is the boardposition of the anchor position
     * @param str this gives the information which anchor is calling this method. Based on that we perform the search.
     */
    public void getString(String string, BoardPosition boardPosition, String str){
        transpose = false;

        if (str.equals("up")||str.equals("down")){
            transpose = true;
        }
        totalWords.clear();
        totalList.clear();
        totalWords.addAll(possibleWords(string));
        totalList.addAll(totalWords);


        for(int i =0; i<totalList.size();i++){

            for(int p =0;p<boardSize;p++){
                for(int j =0; j<boardSize;j++){
                    board[p][j]= boardStore[p][j];
                }
            }
            for(int m =0;m<boardSize;m++){
                for(int n =0; n<boardSize;n++){
                    temp[m][n]= boardStore[m][n];
                }
            }

            //if we have the transpose then we get the transposed board.
            if(transpose){
                for(int p =0;p<boardSize;p++){
                    for(int j =0; j<boardSize;j++){
                        board[p][j]= boardStore[j][p];
                    }
                }
                for(int p =0;p<boardSize;p++){
                    for(int j =0; j<boardSize;j++){
                        temp[p][j]= boardStore[j][p];
                    }
                }

            }

            if(str.equals("left")||str.equals("up")) {
                if (isLegalLeft(totalList.get(i), string, boardPosition)) {       //here we check if the move is legal
                    if (isLegal(temp)) {               //here if we check if we have the board with valid words
                        scoreLeft = 0;

                        //here we had the score made sideways
                        calculateScoreLeft(totalList.get(i), firstPositionX, firstPositionY);

                        //this calculates the scores for words that were made vertically.
                        getAdditionalScoreLeftRight();

                        //if computer uses all the letters out of tray then we add 50 points to it.
                        if(letterFromTray==7){
                            scoreLeft =scoreLeft+50;
                        }


                        if(!allScores.isEmpty()) {

                            //comparing the consecutive scores to see if computer finds the better score.
                            if (allScores.get(0).getScore() < scoreLeft &&
                                    !Character.isAlphabetic(board[allScores.get(0).row()][allScores.get(0).col()]) ) {

                                computerBestPlayed = computerPlayed;
                                allScores.get(0).setAll(new BoardPosition(firstPositionX, firstPositionY),
                                        totalList.get(i),scoreLeft,direction);
                                scoreLeft=0;
                                highestBoard =new char[boardSize][boardSize];

                                //here we set the highest board
                                for(int k =0; k<boardSize;k++){
                                    for (int m = 0; m< boardSize;m++){
                                        highestBoard[k][m] = temp[k][m];
                                    }
                                }
                            }
                        }
                    } else {
                    }
                } else {
                }
            }

            if(str.equals("right")||str.equals("down")){

                if(isLegalRight(totalList.get(i),string, boardPosition)) {
                    if (isLegal(temp)) {
                        scoreRight = 0;
                        //here we had the score made sideways
                        calculateScoreRight(totalList.get(i), firstPositionX, firstPositionY);

                        //this calculates the scores for words that were made vertically.
                        getAdditionalScoreLeftRight();

                        //if computer uses all the letters out of tray then we add 50 points to it.
                        if(letterFromTray==7){
                            scoreRight =scoreRight+50;
                        }
                        if(!allScores.isEmpty()) {

                            //comparing the consecutive scores to see if computer finds the better score.
                            if (allScores.get(0).getScore() < scoreRight) {
                                computerBestPlayed = computerPlayed;
                                allScores.get(0).setAll(new BoardPosition(firstPositionX, firstPositionY),totalList.get(i),scoreRight,direction);
                                scoreRight = 0;

                                //here we set the highest board
                                highestBoard =new char[boardSize][boardSize];
                                for(int k =0; k<boardSize;k++){
                                    for (int m = 0; m< boardSize;m++){
                                        highestBoard[k][m] = temp[k][m];
                                    }

                                }
                            }

                        }
                    }
                }
            }
        }
    }

    /**
     * this method is called to see if there are verticle words counted for scoring.
     *
     */
    public void getAdditionalScoreLeftRight() {
        boolean canAdd = false;
        String local="";

        //if direction is left or right, then we get the string on the left or right to calculate the score.
        if(!(direction.equals("up")||direction.equals("down"))) {
            for (int i = 0; i < boardSize; i++) {
                String str = "";
                for (int j = 0; j < boardSize; j++) {
                    if (Character.isLetter(temp[j][i])) {
                        str = str + Character.toString(temp[j][i]);

                        /* here we loop through the letter placement position to see if any verticle words are made out
                       touching to that placement. this is for right anchor checks.
                         */
                        for (int s = 0; s < stringPositionRight.size(); s++) {
                            if (stringPositionRight.get(s).getX() == j && stringPositionRight.get(s).getY() == i) {
                                local = local + Character.toString(temp[j][i]);
                                canAdd = true;             //this sets we can calculate the score for this string
                            }
                        }

                          /* here we loop through the letter placement position to see if any verticle words are made out
                       touching to that placement. this is for left anchor checks.
                         */
                        for (int s = 0; s < stringPositionLeft.size(); s++) {
                            if (stringPositionLeft.get(s).getX() == j && stringPositionLeft.get(s).getY() == i) {
                                local = local + Character.toString(temp[j][i]);
                                canAdd = true;       //this sets we can calculate the score for this string
                            }
                        }
                    } else {

                        /* we enter this loop after we end the word and call normal string calculator for score
                        calculations.
                         */
                        if (str.length() > 1) {
                            if (canAdd) {
                                scoreRight = scoreRight + calculateNormalString(str);
                                scoreLeft = scoreLeft + calculateNormalString(str);
                                local = "";
                            }
                        }
                        canAdd = false;
                        str = "";
                    }
                }
            }
        } else {

            //here we calculate the score sideways for up and down anchors.
            for (int j = 0; j < boardSize; j++) {
                String str = "";
                for (int i = 0; i < boardSize; i++) {
                    if (Character.isLetter(temp[j][i])) {
                        str = str + Character.toString(temp[j][i]);
                        /* here we loop through the letter placement position to see if any horizontal words are made
                        out touching to that placement. this is for down anchor checks.
                         */
                        for (int s = 0; s < stringPositionRight.size(); s++) {
                            if (stringPositionRight.get(s).getX() == j && stringPositionRight.get(s).getY() == i) {
                                local = local + Character.toString(temp[j][i]);
                                canAdd = true;
                            }
                        }

                        /* here we loop through the letter placement position to see if any horizontal words are made
                        out touching to that placement. this is for up anchor checks.
                         */
                        for (int s = 0; s < stringPositionLeft.size(); s++) {
                            if (stringPositionLeft.get(s).getX() == j && stringPositionLeft.get(s).getY() == i) {
                                local = local + Character.toString(temp[j][i]);
                                canAdd = true;
                            }
                        }
                    } else {
                        if (str.length() > 1) {
                            if (canAdd) {
                                scoreRight = scoreRight - calculateNormalString(local);
                                scoreRight = scoreRight + calculateNormalString(str);
                                scoreLeft = scoreLeft + calculateNormalString(str);
                                local = "";
                            }
                        }
                        canAdd = false;
                        str = "";
                    }
                }
            }
        }
        stringPositionLeft.clear();
        stringPositionRight.clear();
    }

    /**
     * this method takes the string and calculate the score for the string and returns the local score.
     * @param string
     * @returns
     */
    public int  calculateNormalString(String string){
        int localScore =0;
        for(int i = 0; i<string.length();i++){
            localScore = localScore+letterScore.getScore(string.charAt(i));
        }
        return localScore;
    }



    /**
     * here we calculate the score for the right anchor
     * @param str this is the string computer has found as a best word
     * @param firstPositionX  this is the position X of the first letter of word.
     * @param firstPositionY  this is the position Y of the first letter of word.
     */
    public void calculateScoreRight(String str, int firstPositionX, int firstPositionY){
        int multiplier =1;
        scoreRight = scoreRight+ calculateNormalString(fixedString);
        for(int i =0; i<str.length();i++){

           //if the original board has letters in it.
            if(!Character.isLetter(board[firstPositionX][firstPositionY+i])){

                //if the original board has number to multiply either letter or word score.
                if(Character.isDigit(board[firstPositionX][firstPositionY+i])) {

                    //if number stored in board is less than 5, then its the letter multiplier
                    if (Character.getNumericValue(board[firstPositionX][firstPositionY+i]) < 5) {
                        scoreRight = scoreRight + letterScore.getScore(str.charAt(i)) * Character.getNumericValue(board[firstPositionX][firstPositionY+i]);

                    }  //if the number stored in board is greater than 5 then its the word multiplier
                    else if (Character.getNumericValue(board[firstPositionX][firstPositionY + i]) >= 5) {
                        multiplier = Character.getNumericValue(board[firstPositionX][firstPositionY+i]) - 5;
                    }
                } else {
                    scoreRight = scoreRight + letterScore.getScore(str.charAt(i));
                }
            }
        }
        scoreRight = scoreRight*multiplier;
    }



    /**
     *
     * @param temp board is taken as parameter to see if it has the legal words in it.
     * @return true if the temp board valid words in it and otherwise, false
     */
    public boolean isLegal(char[][] temp){

        //this checks the horizontal strings in the temp
        for(int i =0; i<boardSize;i++) {
            String str = "";
            for (int j = 0; j < boardSize; j++) {
                if (Character.isLetter(temp[i][j])) {
                    str = str + Character.toString(temp[i][j]);
                }
                //if we hit blank space and string has more than one letter, then we check if the string is valid or not.
                else if (str.length() > 1) {
                    if (!data.tree.isWord(data.tree, str.toLowerCase())) {
                            return false;
                    } else {
                        str ="";
                    }
                } else {
                    str ="";
                }
            }

            //if we hit end of the board and string has more than a letter, then we check if the string is valid or not
            if (str.length() > 1) {
                if (!data.tree.isWord(data.tree,str)) {
                    return false;
                }

            }

        }

        // this checks the vertical strings in the temp
        for(int i =0; i<boardSize;i++) {
            String str = "";
            for (int j = 0; j < boardSize; j++) {
                if (Character.isLetter(temp[j][i])) {
                    str = str + Character.toString(temp[j][i]);
                }
                //if we hit blank space and string has more than one letter, then we check if the string is valid or not.
                else if (str.length() > 1) {
                    if (!data.tree.isWord(data.tree, str)) {
                        return false;
                    } else {
                        str ="";
                    }
                } else {
                    str ="";
                }
            }

            //if we hit end of the board and string has more than a letter, then we check if the string is valid or not
            if (str.length() > 1) {
                if (!data.tree.isWord(data.tree, str)) {
                    return false;
                }

            }

        }

        return true;
    }


    /**
     * this method is called to see if the found string fits in the board. if str passed fits in the board then
     * we say this is legal. This method is called for checking the right anchor.
     * @param str is the word passed to see if it fits.
     * @param fixedstr is the fixed string already on the board.
     * @param boardPosition is the board position of the anchors.
     * @returns true  if the word perfectly fits in the board.
     */
    public boolean isLegalRight(String str, String fixedstr, BoardPosition boardPosition){
        computerPlayed = "";
        stringPositionRight.clear();
        letterFromTray=0;

        int position = str.indexOf(fixedstr);
        firstPositionY = boardPosition.getY()-fixedstr.length();
        firstPositionX= boardPosition.getX();

        //this checks right left side of the right anchor
        for(int i = 0; i<position; i++) {
            if (boardPosition.getY() -1- i-fixedstr.length() >= 0) {
                if (!Character.isLetter(temp[boardPosition.getX()][boardPosition.getY()-i-1-fixedstr.length()])) {
                    firstPositionY = boardPosition.getY()-i-1-fixedstr.length();
                    stringPositionRight.add(new BoardPosition(boardPosition.getX(),boardPosition.getY()-i-1-fixedstr.length()));
                    letterFromTray++;

                    //letter from tray is placed to temp board.
                    temp[boardPosition.getX()][boardPosition.getY()-i-fixedstr.length()-1]=str.charAt(position-1-i);
                    computerPlayed = computerPlayed + str.charAt(position-1-i);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        //this checks the left side of the right anchor
        for (int i =0;i<(str.length()-position-fixedstr.length());i++) {
            if (boardPosition.getY() + i < boardSize) {
                if (!Character.isLetter(temp[boardPosition.getX()][boardPosition.getY() + i])) {
                    temp[boardPosition.getX()][boardPosition.getY() + i] = str.charAt(position + fixedstr.length() + i);
                    letterFromTray++;
                    computerPlayed = computerPlayed + str.charAt(position + fixedstr.length() + i);
                    stringPositionRight.add(new BoardPosition(boardPosition.getX(), boardPosition.getY() + i));
                } else {
                    return false;
                }
            } else return false;
        }

        //if we had the transposed the board then we set it back to its original form i.e. transform it back
        char[][] transposeB = new char[boardSize][boardSize];
        if(transpose) {
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    transposeB[i][j] = temp[i][j];
                }
            }
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    temp[i][j] = transposeB[j][i];
                }
            }
            for (int g = 0; g < boardSize; g++) {
                for (int h = 0; h < boardSize; h++) {
                    board[h][g] = boardStore[h][g];
                }
            }
        }
        //boardObject.printBoard(temp,boardSize);
        return true;
    }


    /**
     * here we calculate the score for the left anchor
     * @param str this is the string computer has found as a best word
     * @param firstPositionX  this is the position X of the first letter of word.
     * @param firstPositionY  this is the position Y of the first letter of word.
     */
    public void calculateScoreLeft(String str, int firstPositionX, int firstPositionY){
        int multiplier =1;
        scoreLeft = scoreLeft+ calculateNormalString(fixedString);
        for(int i =0; i<str.length();i++){
            if(!Character.isLetter(board[firstPositionX][firstPositionY+i])){
                if(Character.isDigit(board[firstPositionX][firstPositionY+i])) {
                    if (Character.getNumericValue(board[firstPositionX][firstPositionY+i]) < 5) {
                        scoreLeft = scoreLeft + letterScore.getScore(str.charAt(i)) * Character.getNumericValue(board[firstPositionX][firstPositionY + i]);

                    } else if (Character.getNumericValue(board[firstPositionX][firstPositionY + i]) >= 5) {

                        multiplier = Character.getNumericValue(board[firstPositionX][firstPositionY+i]) - 5;

                    }
                } else {
                    scoreLeft = scoreLeft + letterScore.getScore(str.charAt(i));

                }
            } else{
                scoreLeft = scoreLeft + letterScore.getScore(str.charAt(i));

            }
        }
        scoreLeft = scoreLeft*multiplier;

    }






    /**
     * this method is called to see if the found string fits in the board. if str passed fits in the board then
     * we say this is legal. This method is called for checking the left anchor.
     * @param str is the word passed to see if it fits.
     * @param fixedstr is the fixed string already on the board.
     * @param boardPosition is the board position of the anchors.
     * @returns true  if the word perfectly fits in the board.
     */
    public boolean isLegalLeft(String str, String fixedstr, BoardPosition boardPosition){

        computerPlayed = "";
        for(int i =0;i<boardSize;i++){
            for(int j =0; j<boardSize;j++){
                temp[i][j]= board[i][j];
            }
        }
        letterFromTray =0;
        stringPositionLeft.clear();
        int position = str.indexOf(fixedstr);
        firstPositionY = boardPosition.getY()+1;
        firstPositionX= boardPosition.getX();

        //this checks the left side of the left anchor
        for(int i = 0; i<position; i++) {
            if (boardPosition.getY() - i >= 0) {
                if (!Character.isLetter(temp[boardPosition.getX()][boardPosition.getY()-i])) {
                    firstPositionY = boardPosition.getY()-i;
                    stringPositionLeft.add(new BoardPosition(boardPosition.getX(),boardPosition.getY()-i));
                    temp[boardPosition.getX()][boardPosition.getY()-i]=str.charAt(position-1-i);
                    computerPlayed = computerPlayed + str.charAt(position-1-i);
                    letterFromTray++;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        //this checks the right side of the left anchor
        if(fixedstr.length()>0) {
            for (int i = fixedstr.length(); i < (str.length() - position); i++) {

                if (boardPosition.getY() + 1 + i < boardSize) {

                    if (!Character.isLetter(temp[boardPosition.getX()][boardPosition.getY() + 1 + i])) {
                        //System.out.println("this is char assigned 1133" + Character.toString(str.charAt(i))+"at x"+boardPosition.getX()+"and y :"+(boardPosition.getY()+1+i));
                        temp[boardPosition.getX()][boardPosition.getY() + 1 + i] = str.charAt(position + i);
                        letterFromTray++;
                        computerPlayed = computerPlayed +str.charAt(position + i);
                        stringPositionLeft.add(new BoardPosition(boardPosition.getX(), boardPosition.getY() + 1 + i));
                    } else {
                        return false;
                    }
                } else return false;
            }
        } else {

            //this checks the left side of the left anchor
            for (int i = fixedstr.length(); i < (str.length() - position); i++) {

                if (boardPosition.getY() + 1 + i < boardSize) {

                    if (!Character.isLetter(temp[boardPosition.getX()][boardPosition.getY() + i])) {
                        temp[boardPosition.getX()][boardPosition.getY() + i] = str.charAt(position + i);
                        computerPlayed = computerPlayed +str.charAt(position + i);
                        letterFromTray++;
                        stringPositionLeft.add(new BoardPosition(boardPosition.getX(), boardPosition.getY() + i));
                    } else {
                        return false;
                    }
                } else return false;
            }
        }


        //if we had the transposed the board then we set it back to its original form i.e. transform it back
        char[][] transposeB = new char[boardSize][boardSize];
        if(transpose) {
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    transposeB[i][j] = temp[i][j];
                }
            }
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    temp[i][j] = transposeB[j][i];
                }
            }
            for (int g = 0; g < boardSize; g++) {
                for (int h = 0; h < boardSize; h++) {
                    board[h][g] = boardStore[h][g];
                }
            }
        }
        //boardObject.printBoard(temp,boardSize);
        return true;
    }

    /**
     * this is the print method which is used for debugging purposes.
     * @param board and prints in the console in understandable format.
     */
    public void printBoard(char[][] board) {
        int sizeOfBoard = board[0].length;
        for (int i = 0; i < sizeOfBoard; i++) {
            for (int j = 0; j < sizeOfBoard; j++) {
                char c = board[i][j];
                String temp =""+c;
                if(Character.isDigit(c)) {
                    if (Integer.parseInt(temp) > 5) {
                        System.out.print("" + (Integer.parseInt(temp)-5) + ". ");
                    } else {
                        System.out.print("."+c+" ");
                    }
                }

                if(Character.isAlphabetic(c)){
                    System.out.print(" "+c+ " ");
                }
                if(temp.charAt(0)=='.'){
                    System.out.print(".. ");
                }
            }
            System.out.println("\n");

        }

    }
}
