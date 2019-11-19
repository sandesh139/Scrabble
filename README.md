# Scrabble Game
Author -Sandesh Timilsina

# Introduction
The project is to solve the input text file and give the best move for the computer and also make a 
playable GUI version of game. This repository consists for source code for gui version inside src
directory, jar file Console.jar for solving the text input, docs folder containing the designs of
the game and .gitignore file.
 
# How to run the Console.jar
  - Console.jar can be run from the command line with command "java -jar Console.jar sowpods.txt"
where sowpods.txt is the dictionary file to be read by the program and should be in the same 
directory as the jar file is in while running the jar. After the program runs, one should copy
and paste the input board which sample is in inside the resources directory as example_input.txt.
After the input is pasted then user should press Ctrl+Z and enter (if program is run through cmd).
Ctrl + Z will terminate the System.in reading. For bugs.... in solving input, see the bug section at 
the end of this README. Console.jar is slow because of unnecessary computation made, briefly
described in the bug section.

# How to run the Scrabble.jar
  - Scrabble.jar is the Gui version of the project. Before running Scrabble.jar make sure you have
scrabble_board.txt and sowpods.txt. These text file can be found in the resource directory. The 
Scrabble.jar can be run from the command line with command "java -jar Scrabble.jar sowpods.txt".
The game also can be run using the source code from src directory. Make sure you have same above 
text file in src folder.



# How to play the game

  - Understand Board: Tiles having dots and numbers are blank places. Red number underlined
represents that the word score will get multiplied with that number and normal number represents
that the letter score is multiplied by that number.

  - First move: The first play is always given to the human. In only first play of the game, player
do not loose the turn for changing the tray. But, from next play, human will loose turn for changing 
tray. The first play is only started from the middle of the board. Exactly one of the letters
should touch the center of the board in the first play. If human tries to place the word other
than in center, he gets information displayed telling him to make first play in the center. If
human tries to make illegal move, board gets reset as well as human tray, however human does not
loose the turn for trying any number of illegal moves.

  - Valid Move: Any move should be placed in one horizontal row or verticle column. And, placed
letters cannot have any spaces in between them. Also, other than in first move, at least one
letter should touch the boundry of another letter already present in the board. When human places
the letter in board if any human letter touches the board letters, that should form the word from
top to bottom or from left to right in reading. Player do not loose turn for trying the illegal
move.

   - Place letters on board : Human has to click his tray and then click on board to transfer the
letter from tray to board. Once the player is done, he should click on "confirm" button to confirm
the end of the letter placement in the board.

   - Exchange letters: Human can exchange any number of letters from his tray. For exchanging he
should first click on the "start exchange" button and start clicking on the letters in his tray.
Once the start exchange begins, it cannot be cancelled. After player is done choosing the letters
to be exchanged, he should confirm exchange. 

   - Undo: Human can undo the placement of his letters from tray to board by clicking in undo button.
In undo process, player do not loose turn.     

   - Computer Move Time: Computer moving time usually takes few seconds. You can usually notice the
total score on the board for computer's increasing if it makes the move. If it does not increase, 
you should know that computer exchanged the tray and lost his turn. Computer only plays horizontally.
See the bug section ..... to know why it only play in horizontal.
 

# Score
Total Score of the game is updated after each play and shown in the game. Winner is decided based
on the whoever player has higher score wins the game. Asterik score has bug in it described in 
the bug section.


# Game Logic
Game Manager handles the most logic part of the game. MainClass provides the information from
human to the Game manager part and then the Game manager analyises if the input is legal or not
and if it is legal, then calculates the score. GameManager updates the left over letters in the
bag. After the Human play is done, then game managers analyze the board and makes result ready 
to be displayed by the MainClass using Javafx. Game ending is handled at its best but has some
bugs described in the last setion.


# Algorithm
The heavy lifting of the work is performed by the Game Manager Class and the Node class. For finding
the best move, Node class finds all posible words that are formed using the  computer tray and anchor
neigbors and provide those words to the GameManager for finding the legal and the best word. I use the
same method from sideways best word for verticle sideways after transposing the board. But, I couldnot
find the bug in transposing the board while working with the vertical best words.

# Bugs in the program
There are some bugs in the game. Bugs are described in the sections given below.
          Console.jar: console.jar only handles the one wild card. The main reason for not handling
more than one wild card is unnecessary computation I did takes forever for handling more than one
wild Card. "Unnecessay Computation" means I mix the board letters and computer tray together to get
all possible words and later i filter the string which has substring from the board. And, Another 
bug is score for the Wild card. I set the method for score calculation in such a way that I couldnot
later change the code for fixing the score of the asterik. SO, whatever letter wildcard is assigned to
gets that score added to the total score. And, also, since I used the .tolowercase method for setting
the board, to avoid error for finding the best word, I just showed the wildcard by its lowercase letter
after it is placed in the board. Also, I could not use the multiplier more than one time for letter or
word.
		The main bug I have in the best word finder is transpose part. I think I miss the board
property somewhere while transposing. So, it does not solve some board properly. 

          Scrabble.jar: Gui part has fewer bugs than the word solver. 
Because my word solver has problem in checking vertical direction for best words, I only used the horizontal
placement of word by computer in GUI. This helps making the game look very legal and working.
Some bugs in GUI are   
  - computer only plays horizontal.
  - So, computer only find few legal words and do exhange tray when cannot.
  - Sometime, computer finds the best word as the last one but doesnot affect scoring.
 Scoring is maintained good.
  - Game gets error when player tries to change trays more than we have left letters
in the bag, in the end of game. While in few trials, array goes out of bound while checking
if the game ends or not.
  - Sometimes multiplier doesnot work for human while calculating score.
			