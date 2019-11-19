import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * this class contains the main method. It extends application.
 * this class uses the game logic part to have the game displayed.
 */
public class MainClass extends Application {

    /**initiating an integer for storing the size of the board */
    private int boardSize;

    /**initiating the pane in which every other node will be added to be displayed */
    private Pane root = new Pane();

    /**initiating the Board object which will get the board input in two-d array. */
    private Board boardObject = new Board();

    /**initiating the board which will be used to store the 2d-array.  */
    private char[][] board;

    /**stores the copy of the board */
    private char[][] boardFinal;

    /**initiating the string to store the information of the board in string. */
    private String boardString;

    /**initiating the string to store the informtion of the temporary board */
    private String tempBoardString ="";

    /**initiating the string to store the letters to be transfered from tray to the board.*/
    private String letterTransfer = "";

    /**initiating arraylist of characterposition to hold the position of character in board */
    private ArrayList<BoardPosition> characterPositions = new ArrayList<BoardPosition>();

    /**creating game object*/
    private GameManager game;

    /**initiating the confirm object to get confirmed from user move.*/
    private Button confirm = new Button("Confirm");

    /**initiating the list which holds the getTilePane object for human dominos*/
    private List<getTilePane> listOfHuman = new ArrayList<>();


    private Button undoDrop = new Button("UNDO");
    /**initiating the list which holds the getTilePane object for the board game*/
    private List<getTileBoard> listOfBoardTile = new ArrayList<>();

    /**initiating the dialogue alert which is used for displaying illegal move. */
    private Alert dialogue = new Alert(Alert.AlertType.INFORMATION);

    /**initiating the wildInput to show the TextInputDialogue for getting the input from the player. */
    TextInputDialog wildInput = new TextInputDialog("");

    /**initiating the label to show the score of the human. */
    private Label humanScore = new Label();

    /**initiating the label to show the score of the computer */
    private Label computerScore = new Label();

    /**initiating the label to show the numbers of letters left in the bag */
    private Label leftLetters = new Label();

    /**initiating the startExchange button to start the tray exchange procedure by human */
    private Button startExchange = new Button();

    /**initiating the confirmExchange button to stop the tray exchange procedure. */
    private Button confirmExchange = new Button();

    /**initiating the boolean which controls when there is legal click on the board */
    private boolean canClickOnBoard = false;

    /**initiating the string to hold the letters that were used to play on the board */
    private String usedString ="";

    /**initiating the boolean to decide when it is human's turn to play */
    private boolean humanPlay= true;

    /**initiating the boolean to decide when the exchange process is running */
    private boolean exchangeStart =false;

    /**initiating the exchangeString String to store the letters that were exchanged */
    private String exchangeString ="";

    /**initiating the turnCounter to count number of plays made.*/
    private int turnCounter =0;

    /**initiating the clickedPosition Arraylist to store the clicked position on board x and y */
    private ArrayList<BoardPosition> clickedPosition = new ArrayList<>();

    /**initiating the boolean asterik which is set on if human gets the wild card */
    private boolean asterik = false;

    /**initiating the boolean to check the first move */
    private boolean firstCheck= false;

    /**initiating the boolean the gameEnds. It is used to check of the game Ends. */
    private boolean gameEnds = false;

    /**initiating the anchor used for checking the legal move of human in anchor position*/
    private ArrayList<BoardPosition> anchor = new ArrayList<>();

    /**declaring string for storing first command line argument*/
    private static String dictionary = "";

    /**main method which calls launch method with args parameter. */
    public static void main(String args[]) {
        if(args.length==1){
            dictionary =args[0];
        }
        launch(args);
    }


    /**
     *  start method invoked from the JavaFX Application
     * @param primaryStage
     * @throws Exception
     */

    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Scrabble Game");  //setting the title of the window displayed.
        primaryStage.setScene(new Scene(GetStackPane()));
        primaryStage.show();
    }


    /**
     * GetStackPane returning parent which will have all nodes of the figure
     * @return
     */
    private Parent GetStackPane(){

        board = boardObject.getBoard();       //getting the board from board object.
        boardSize =board[0].length;           //setting the size of the board
        boardString = getString(board);       //setting the boardstring with the board string
        game = new GameManager(board,dictionary );        //getting the game object which is provided with board to set up the game.
        root.setPrefSize(1500,800);  //setting the size of the window.
        setOrginalBoard(board);               //setting the original copy of the board
        game.setUpTiles();                    //setting the human tray


        //this "for loop" gets the 7 tiles to display the human tray
        for(int i = 0; i<7; i++){
            listOfHuman.add(new getTilePane());
        }

        //setting the location of the 7 tile and adding each tile to root pane.
        for(int i = 0; i<listOfHuman.size();i++){
            getTilePane aTile = listOfHuman.get(i);
            aTile.setTranslateX(30*i);
            aTile.text.setText(Character.toString(game.human.getHumanTray().charAt(i)));
            root.getChildren().add(aTile);
        }

        //this "for loop" gets the all tiles for representing the game board.
        for (int i =0; i<boardSize*boardSize; i++){
            listOfBoardTile.add(new getTileBoard());
        }
        int rowOffset = 100;
        int col = 0;
        int row =0;

        //this sets up the location of the tiles of board
        for(int i =0; i<listOfBoardTile.size();i++){
            getTileBoard aTile = listOfBoardTile.get(i);
            aTile.setTranslateX(30*col);
            aTile.setTranslateY(rowOffset+30*row);
            if(row<boardSize-1){
                row++;
            } else {
                row = 0;
                col++;
            }
            root.getChildren().add(aTile);
        }

        //this for loop sets the text of the board tile.
        for(int i =0; i<listOfBoardTile.size(); i++) {
            char c = boardString.charAt(i);
            if (Character.isDigit(c)) {
                if (Character.getNumericValue(c) > 5) {
                    listOfBoardTile.get(i).text.setText("" + (Character.getNumericValue(c) - 5));
                    listOfBoardTile.get(i).text.setUnderline(true);
                    listOfBoardTile.get(i).text.setFill(Color.RED);
                } else {
                    listOfBoardTile.get(i).text.setText(Character.toString(c));
                }
            } else {
                listOfBoardTile.get(i).text.setText(Character.toString(c));
            }
        }


        //following lines of codes sets up the location of buttons and labels in the window.
        undoDrop.setTranslateX(1300);
        undoDrop.setTranslateY(5);
        confirm.setTranslateX(1300);
        confirm.setTranslateY(50);
        humanScore.setFont(new Font(30));
        computerScore.setFont(new Font(30));
        leftLetters.setFont(new Font(30));
        humanScore.setTranslateX(1200);
        humanScore.setTranslateY(100);
        computerScore.setTranslateX(1200);
        computerScore.setTranslateY(150);
        leftLetters.setTranslateX(1200);
        leftLetters.setTranslateY(200);
        startExchange.setTranslateX(1200);
        startExchange.setTranslateY(250);
        confirmExchange.setTranslateX(1200);
        confirmExchange.setTranslateY(300);

        //setting the text of the labels and dialogues in the following lines of codes.
        humanScore.setText("Human Score: "+ 0);
        computerScore.setText("ComputerScore: " + 0);
        leftLetters.setText("letters left :"+ game.letterScore.getStringTile().length());
        startExchange.setText("Start Exchange");
        confirmExchange.setText("Confirm Exchange");
        wildInput.setTitle("This is a wild card");
        wildInput.setHeaderText("Exchange with character");
        wildInput.setContentText("Type a Letter :");




        /**
         * this is called when start exchange button is clicked.
         * this helps to exchange the human tray
         */
        startExchange.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(letterTransfer.isEmpty()) {
                    System.out.println("this is human tray " + game.human.getHumanTray());

                    //checking if the tiles are left in the bag
                    if (game.letterScore.leftOverString.length() > 0) {
                        exchangeStart = true;
                    }
                }
            }
        });

        /**
         * this is called when undo button is clicked
         * this helps human to get all letters from the board and start placing letters to board.
         */
        undoDrop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for(int i = 0; i<game.human.getHumanTray().length();i++){
                    listOfHuman.get(i).text.setText(Character.toString(game.human.getHumanTray().charAt(i)));
                }

                //this gets old board since after undo is pressed
                for(int i = 0; i<listOfBoardTile.size();i++){
                    char c = boardString.charAt(i);
                    if (Character.isDigit(c)) {
                        if (Character.getNumericValue(c) > 5) {
                            listOfBoardTile.get(i).text.setText("" + (Character.getNumericValue(c) - 5));
                            listOfBoardTile.get(i).text.setUnderline(true);
                            listOfBoardTile.get(i).text.setFill(Color.RED);
                        } else {
                            listOfBoardTile.get(i).text.setText(Character.toString(c));
                        }
                    } else {
                        listOfBoardTile.get(i).text.setText(Character.toString(c));
                    }
                }
                letterTransfer ="";
                usedString = "";
                canClickOnBoard =false;
            }
        });


        /**
         * this method is called when picking up letters from tray to be exchanged is confirmed by clicking the confirm
         * button
         */
        confirmExchange.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(exchangeStart) {
                    game.human.clearTray();
                    String tempTray = "";

                    //this while loop helps setting the new human tray
                    while ((tempTray.length() < exchangeString.length())) {
                        String localStr = game.letterScore.getStringTile();
                        int randomInteger = game.rand.nextInt(localStr.length());
                        tempTray = tempTray + localStr.charAt(randomInteger);
                        game.letterScore.setStringTile(Character.toString(localStr.charAt(randomInteger)));
                    }

                    //this for loop puts back the letters to the bag
                    for (int i = 0; i < exchangeString.length(); i++) {
                        game.letterScore.putBackLetter(exchangeString.charAt(i));
                    }

                    //this sets the new human tray in the tiles
                    for (int i = 0; i < listOfHuman.size(); i++) {
                        game.human.setHumanTray(listOfHuman.get(i).text.getText());
                    }

                    game.human.setHumanTray(tempTray);
                    System.out.println("this is human tray " + game.human.getHumanTray());

                    //this sets the human tray in the tiles
                    for (int i = 0; i < game.human.getHumanTray().length(); i++) {
                        listOfHuman.get(i).text.setText(Character.toString(game.human.getHumanTray().charAt(i)));
                    }
                    exchangeString = "";
                    exchangeStart = false;


                    //player does not loose the turn for exchanging the trays in the first move
                    if (turnCounter != 0) {

                        //gets the board updated for the computer
                        game.updateBoard();

                        //here we get the highest board.
                        board = game.getHighestBoard();

                        //here we set the computer score in the label
                        computerScore.setText("ComputerScore: " + game.getComputerSCore());
                        leftLetters.setText("letters left :" + game.letterScore.getStringTile().length());

                        //this updates the board
                        for (int i = 0; i < boardSize; i++) {
                            for (int j = 0; j < boardSize; j++) {
                                boardFinal[i][j] = board[i][j];
                            }
                        }

                        //this gets the string representation of the board
                        boardString = getString(board);

                        //this gets the
                        setBoardText();
                        System.out.println("this is computer Tray " + game.computer.getComputerTray());
                    }
                }
            }
        });

        //this adds up the root
        root.getChildren().addAll(confirm,humanScore,computerScore, leftLetters,undoDrop,startExchange,confirmExchange);
        return root;
    }


    /**
     * this method sets the original board
     * @param board
     */
    public void setOrginalBoard(char[][] board){
        boardFinal = new char[boardSize][boardSize];
        for (int i = 0; i<boardSize; i++){
            for (int j = 0; j<boardSize; j++){
                boardFinal[i][j] = board[i][j];
            }
        }
        game.setOldBoard(boardFinal);
        game.setNewBoard(board);
    }

    /**
     * this method sets the temporary board string to check if the human makes legal or illegal move
     */
    public void getTempBoardString() {
        tempBoardString="";
        for (int i = 0; i < listOfBoardTile.size(); i++) {
            if (listOfBoardTile.get(i).text.isUnderline() &&
                    Character.isDigit(listOfBoardTile.get(i).text.getText().charAt(0))) {
                tempBoardString = tempBoardString +
                        (Character.getNumericValue(listOfBoardTile.get(i).text.getText().charAt(0) + 5));
            } else {
                tempBoardString = tempBoardString + listOfBoardTile.get(i).text.getText();
            }

        }
    }


    /**
     * this gets the board updated with human move
     * @param str
     */
    public void getTempBoard(String str ){
        int counter =0;
        for (int i =0; i< boardSize; i++){
            for (int j = 0;j<boardSize; j++){
                board[j][i] = str.charAt(counter);
                counter++;
            }
        }
    }

    /**
     * this gets the string representation of the board passed as parameter
     * @param board
     * @return
     */
    public String getString(char[][] board){
        String boardString ="";
        for(int i =0; i<boardSize; i++){
            for (int j = 0; j<boardSize; j++){
                boardString += Character.toString(board[j][i]);
            }
        }
        return boardString;

    }

    /**
     * this sets the board text
     */
    public void setBoardText(){
        for(int i =0; i<listOfBoardTile.size(); i++) {
            char c = boardString.charAt(i);
            if (Character.isDigit(c)) {
                if (Character.getNumericValue(c) > 5) {
                    listOfBoardTile.get(i).text.setText("" + (Character.getNumericValue(c)-5 ));
                    listOfBoardTile.get(i).text.setFill(Color.RED);
                } else {
                    listOfBoardTile.get(i).text.setText(Character.toString(c));
                }
            } else {
                listOfBoardTile.get(i).text.setText(Character.toString(c));
            }
        }
    }

    /**
     * this class extends stackpane to get each tile of the board
     */
    private class getTileBoard extends StackPane{
        Rectangle rec = new Rectangle(30,30);
        private Text text = new Text();

        /** the constructor to provide properties to the tiles. */
        public getTileBoard() {
            rec.setStroke(Color.BLUE);
            rec.setFill(null);
            getChildren().addAll(rec, text);
            setOnMouseClicked(this::handleTileBoard);

        }

        /**
         * this method is called when there is clicked in the board tile
         * @param E
         */
        public void handleTileBoard(MouseEvent E){
            if(canClickOnBoard && !gameEnds) {
                if (!Character.isAlphabetic(this.text.getText().charAt(0))) {
                    this.text.setText(letterTransfer);
                    if(asterik){
                        letterTransfer ="*";
                    }
                    asterik = false;
                    usedString = usedString +letterTransfer;
                    clickedPosition.add(new BoardPosition(listOfBoardTile.indexOf(this)/boardSize,
                            listOfBoardTile.indexOf(this)%boardSize));
                    canClickOnBoard =false;
                    humanPlay =true;
                }
            }

            /**
             * this method is called when there is click on confirm button
             */
            confirm.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                   if(humanPlay) {
                       testInput();
                   }
                }
            });

        }


        /**
         * this method is called at the end of the game to calculate and decide the winner
         */
        public void calculateFinalScore(){
            gameEnds =true;
            dialogue.setTitle("Game Over");
            int orginalHumanScore = game.getHumanFinalScore();
            int orginalComputerScore = game.getComputerSCore();
            boolean isHumanTrayEmpty =false;
            boolean isComputerTrayEmpty =false;


            if(game.human.getHumanTray().length()==0){
                isHumanTrayEmpty =true;
            }

            if(game.computer.getComputerTray().length()==0){
                isComputerTrayEmpty = true;
            }



            if(isHumanTrayEmpty){
                game.setFinalHumanScore();  //this sets the human score after adding up the computer tray letter score
            }
            if(isComputerTrayEmpty){
                game.setComputerFinalScore(); //this sets the computer score after adding up the human tray letter score
            }

            if(!isComputerTrayEmpty && !isHumanTrayEmpty){
                game.setComputerHumanFinalScore(); //this sets the new score of the human and computer
            }
            if(game.getHumanFinalScore()>game.getComputerSCore()){
                dialogue.setContentText("Human is winner");

            } else if (game.getHumanFinalScore()<game.getComputerSCore()) {
                dialogue.setContentText("Computer is winner");

            } else {
                if(orginalHumanScore>orginalComputerScore){

                    dialogue.setContentText("Human is winner");
                } else if(orginalHumanScore<orginalComputerScore){
                    dialogue.setContentText("Computer is winner");
                }
            }
            humanScore.setText("Human Score: "+ game.getHumanFinalScore());
            computerScore.setText("Computer Score: "+game.getComputerSCore());
            dialogue.show();   //this dialogue shows the winner name
        }


        /**
         * this method is called when human clicks confirm button
         * this method handles if the human makes legal or illegal move
         *If move is made legal, the board is updated. And,
         * if the move is illegal, then everything is set as it was before
         */
        public void testInput(){


            if (game.isWinner()) {
                calculateFinalScore(); //if we have winner, we start calculating score
            }
            humanPlay =false;
            getTempBoardString();
            getTempBoard(tempBoardString);
            if(game.isLegal(board) && isLegalClicked(board) && isLegalAnchor()){   //checks if the human makes legal move
                clickedPosition.clear();
                turnCounter++;
                leftLetters.setText("letters left :"+ game.letterScore.getStringTile().length());
                game.setNewBoard(board);
                game.printBoard(board);
                game.human.renewTray(usedString);
                game.setUpTiles();
                humanScore.setText("Human Score: "+ game.getHumanScore());

                //we replace the orginal copy of the board with the updated board
                for(int i = 0; i<boardSize; i++){
                    for (int j = 0;j<boardSize;j++){
                        boardFinal[i][j] =board[i][j];
                    }
                }

                //here the text in the human tile is updated
                for(int i =0 ; i<game.human.getHumanTray().length(); i++) {
                    listOfHuman.get(i).text.setText(Character.toString(game.human.getHumanTray().charAt(i)));
                }
                if (game.isWinner()) {
                    calculateFinalScore();
                }

                //this calls for the computer move
                game.updateBoard();
                board = game.getHighestBoard();
                computerScore.setText("ComputerScore: " + game.getComputerSCore());
                leftLetters.setText("letters left :"+ game.letterScore.getStringTile().length());
                for(int i = 0; i<boardSize; i++){
                    for (int j = 0;j<boardSize;j++){
                        boardFinal[i][j] =board[i][j];
                    }
                }
                boardString = getString(board);          //we set the new board string and set new board
                setBoardText();
                if (game.isWinner()) {
                    calculateFinalScore();
                }
                System.out.println("this is computer Tray " + game.computer.getComputerTray() );
            } else {
                firstCheck =false;
                clickedPosition.clear();
                setBoardText();
                for(int i =0 ; i<listOfHuman.size(); i++){
                    listOfHuman.get(i).text.setText(Character.toString(game.human.getHumanTray().charAt(i)));
                }
                dialogue.setTitle("Not Valid");
                dialogue.setContentText("Try Again with Valid Word");
                if(turnCounter ==0) {
                    if (firstCheck == false) {
                        dialogue.setContentText("Place the Valid Word in center");
                        firstCheck = true;
                    }
                }
                dialogue.show();
            }
        }


        /**
         * this method returns true if the human has one of the letter in anchor position
         * and returns false if no legal placement is made by human
         * @return
         */
        public boolean isLegalAnchor(){
            boolean anchorFound = false;
            if(turnCounter==0){
                anchorFound = true;
            }
            findAnchor();
            for(int i = 0; i<clickedPosition.size();i++){
                for(int j=0; j<anchor.size();j++){
                    if(anchor.get(j).getX()==clickedPosition.get(i).getX() &&anchor.get(j).getY()==clickedPosition.get(i).getY()){
                        anchorFound =true;
                    }
                }
            }
            return anchorFound;
        }


        /**
         * this method finds the anchor position of the human
         */
        public void findAnchor() {
            anchor.clear();
            characterPositions.clear();
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    if (Character.isLetter(boardFinal[i][j])) {
                        characterPositions.add(new BoardPosition(i, j));
                    }
                }
            }
            for (int i = 0; i < characterPositions.size(); i++) {
                int leftX = characterPositions.get(i).getX();
                int leftY = characterPositions.get(i).getY() - 1;

                int rightX = characterPositions.get(i).getX();
                int rightY = characterPositions.get(i).getY() + 1;

                int upX = characterPositions.get(i).getX() - 1;
                int upY = characterPositions.get(i).getY();

                int downX = characterPositions.get(i).getX() + 1;
                int downY = characterPositions.get(i).getY();

                if (leftX >= 0 && leftY >= 0 && !Character.isLetter(boardFinal[leftX][leftY])) {
                    //anchor.add(new BoardPosition(leftX, leftY));
                    anchor.add(new BoardPosition(leftY, leftX));
                }
                if (rightX < boardSize && rightY < boardSize && !Character.isLetter(boardFinal[rightX][rightY])) {
                    //anchor.add(new BoardPosition(rightX, rightY));
                    anchor.add(new BoardPosition(rightY, rightX));
                }

                if (upY >= 0 && upX >= 0 && !Character.isLetter(boardFinal[upX][upY])) {
                    //anchor.add(new BoardPosition(upX, upY));
                    anchor.add(new BoardPosition(upY, upX));
                }
                if (downY < boardSize && downX < boardSize && !Character.isLetter(boardFinal[downX][downY])) {
                    //anchor.add(new BoardPosition(downX,downY));
                    anchor.add(new BoardPosition(downY, downX));

                }
            }
            for(int i =0; i<anchor.size();i++){
                System.out.println("this is x "+ anchor.get(i).getX() + "and this is y "+ anchor.get(i).getY());
            }
        }

        /**
         * this checks if the click is legal made on the board
         * @param checkBoard
         * @return true if the position of the click made is legal
         */
        public boolean isLegalClicked(char[][] checkBoard){
            boolean legalMove = true;
            String check = "";
            firstCheck =false;

            //first move is not legal if the click was not made in the center of the board
            if(turnCounter==0){

                for(int i = 0; i<clickedPosition.size(); i++){
                    if(clickedPosition.get(i).getX()==boardSize/2 && clickedPosition.get(i).getY()==boardSize/2){
                        firstCheck = true;
                    }
                }

            } else {
                firstCheck =true;
            }

            /*if the click is made in multiple place then we check if the click is legal with no space in between
            and clicks are made in the horizontal or vertical */

            if(clickedPosition.size()>1) {
                if (clickedPosition.get(0).getX() == clickedPosition.get(1).getX()) {
                    check = "verticle";
                } else if (clickedPosition.get(0).getY() == clickedPosition.get(1).getY()) {
                    check = "horizontal";
                } else {
                    return false;
                }

                if (check.equals("verticle")) {
                    int minimum = clickedPosition.get(0).getY();
                    int maximum = clickedPosition.get(0).getY();
                    for (int i = 0; i < clickedPosition.size() - 1; i++) {
                        if (clickedPosition.get(i + 1).getY() < minimum) {
                            minimum = clickedPosition.get(i + 1).getY();
                        }
                        if (clickedPosition.get(i + 1).getY() > maximum) {
                            maximum = clickedPosition.get(i + 1).getY();
                        }

                        //all clicks has to be in same line otherwise we return false
                        if (clickedPosition.get(i).getX() != clickedPosition.get(i + 1).getX()) {
                            return false;
                        }
                    }

                    //here we return false if there is blank tile in between the click ends
                    for (int i = minimum; i <= maximum; i++) {
                        if (!Character.isAlphabetic(checkBoard[i][clickedPosition.get(0).getX()])) {
                            return false;
                        }
                    }
                }
                if (check.equals("horizontal")) {
                    int minimum = clickedPosition.get(0).getX();
                    int maximum = clickedPosition.get(0).getX();
                    for (int i = 0; i < clickedPosition.size() - 1; i++) {
                        if (clickedPosition.get(i + 1).getX() < minimum) {
                            minimum = clickedPosition.get(i + 1).getX();
                        }
                        if (clickedPosition.get(i + 1).getX() > maximum) {
                            maximum = clickedPosition.get(i + 1).getX();
                        }

                        //all clicks has to be in same line otherwise we return false
                        if (clickedPosition.get(i).getY() != clickedPosition.get(i + 1).getY()) {
                            return false;
                        }
                    }

                    //here we return false if there is blank tile in between the click ends
                    for (int i = minimum; i <= maximum; i++) {
                        if (!Character.isAlphabetic(checkBoard[clickedPosition.get(0).getY()][i])) {
                            return false;
                        }
                    }
                }
            }

            return legalMove &&firstCheck;
        }

    }


    /**
     * inner class which extends StackPane and is responsible to create human tile and provide the properties to the tile
     */
    private class getTilePane extends StackPane{
        private Rectangle rec = new Rectangle(30,30);
        private Text text = new Text();

        /** the constructor to provide properties to the tiles. */
        public getTilePane() {
            rec.setStroke(Color.BLUE);
            rec.setFill(null);
            getChildren().addAll(rec, text);
            setOnMouseClicked(this::handleTile);

        }

        /**
         * this method is called when there is a click in the human tile
         * @param mouseEvent
         */
        public void handleTile(MouseEvent E){

            /*this if condition satisfies if there is no exchange process and has not already picked a letter and
            game has not ended. */
            if(!exchangeStart &&!canClickOnBoard &&!gameEnds &&!this.text.getText().isEmpty()) {
                boolean test =false;
                String storeText = this.text.getText();


                //if human clicks on the wild tile we ask for a letter input
                if(this.text.getText().charAt(0)=='*'){
                   do {
                       this.text.setText("");
                       Optional<String> letter = wildInput.showAndWait();
                       asterik = true;
                       if(letter.isPresent()) {
                           if(letter.get().equals("")){
                               test = false;
                           } else {
                               test = true;
                           }
                           this.text.setText(letter.get());

                       } else {
                            test =false;
                       }
                   } while (this.text.getText().length()>1);
                }
                letterTransfer = this.text.getText();
                if(asterik && test==false) {
                   this.text.setText(storeText);
                } else {
                    canClickOnBoard = true;
                    this.text.setText("");
                }

            } else if(exchangeStart){     //click on human tray is allowed if exchange process has been started.
                if(exchangeString.length()<=game.letterScore.leftOverString.length()) {
                    exchangeString = exchangeString + this.text.getText();
                    this.text.setText("");
                }
            }
        }
    }
}