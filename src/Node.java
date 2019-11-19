import java.util.HashSet;

/**
 * this class creates the trie tree as a data structure for representing all words in dictionary.
 */
public class Node {

    /**initiating boolean to set the true for the node if it is the end of the branch */
    private boolean endBranch = false;

    /**initiating the node root to represent the 26 alphabets we have in the dictionary */
    private Node[] nodeRoot = new Node[26];

    /**initiating the hashSet to store all possible words found from the board by the computer */
    private HashSet<String> allWords = new HashSet<>();

    /**declaring a string to hold the string to be found */
    private String findString = "";

    /**initiating the array of boolean to set only letter node be true which we have available for combination */
    private boolean[] setLetterTrue = new boolean[26];

    /**initiating the char to store the character to the tree if we have that char in specific node */
    char sendLetter;


    /**constructor for setting the boolean  array as false for a recently added node to a tree*/
    public Node() {
        for (int i = 0; i < 26; i++) {
            setLetterTrue[i] = false;
        }
    }

    /**this method takes the tree and the string to add it to the tree */
    public void addString(Node node, String string) {
        Node nodeChild = node;
        string =string.toLowerCase();
        for (int i = 0; i < string.length(); i++) {
            int key = string.charAt(i) - 'a';
            if (nodeChild.nodeRoot[key] == null) {          //a new branch is created if the letter is not found
                nodeChild.nodeRoot[key] = new Node();
            }
            nodeChild = nodeChild.nodeRoot[key];
            if (i == string.length() - 1) {
                nodeChild.endBranch = true;  //this node represents last letter of a word
            }
        }

    }

    /**
     * this method is used by the computer to find all words made out of the tray as "string" and letters from board as
     * "fixed". and the data structure is provided to find words in here.
     * @param string
     * @param node
     * @param fixed
     * @return
     */
    public HashSet<String> findWord(String string, Node node, String fixed) {

        fixed =fixed.toLowerCase();
        string = string.toLowerCase();
        allWords.clear();
        boolean[] setLetterTrue = new boolean[26];

        for (int i = 0; i < string.length(); i++) {
            setLetterTrue[string.charAt(i) - 'a'] = true;
        }
        for (int i = 0; i < 26; i++) {
            if (node.nodeRoot[i] != null && setLetterTrue[i]) {
                findString = string;
                sendLetter = (char) (i + 'a');
                //calls the first branch of the tree
                findEveryWord(node.nodeRoot[i], Character.toString((char) (i + 'a')),
                        findString.replaceFirst(Character.toString(sendLetter), ""), fixed);
            }
        }
        return allWords;
    }

    /**
     * this is a recursive method to get all possible words out of the dictionary.
     * @param node is the branch of the tree representing the dictionary
     * @param str is the string is the first part of the word. if str has last letter as true and contains the fixed str
     *            then the string is added to the possible word for computer moves.
     * @param spr contains the letters that is available to check in the specific branch.
     * @param suffix
     */
    public void findEveryWord(Node node, String str, String spr, String suffix) {
        boolean[] setLetterTrue = new boolean[26];

        for (int i = 0; i < spr.length(); i++) {
            setLetterTrue[spr.charAt(i) - 'a'] = true;
        }
        if (node.endBranch) {
            if (str.contains(suffix)) {
                allWords.add(str);
            }
        }
        for (int i = 0; i < 26; i++) {
            if (node.nodeRoot[i] != null && setLetterTrue[i]) {
                char letter = (char) (i + 'a');
                findEveryWord(node.nodeRoot[i], str + Character.toString(letter), spr.replaceFirst(Character.toString(letter), ""), suffix);
            }
        }

    }

    /**
     *
     * @param node is the tree representing the dictionary
     * @param string is the word to be checked in the given tree.
     * @returns true if the string is found in the node.
     */
    public boolean isWord(Node node, String string) {
        string = string.toLowerCase();

        for (int i = 0; i < string.length(); i++) {
            if (node.nodeRoot[string.charAt(i) - 'a'] != null) {
                node = node.nodeRoot[string.charAt(i) -'a'];
            } else{
                return false;              //returns false if the branch is not found.
            }
            if (node.endBranch&&i==string.length()-1) {     //returns true if string last char has true endbranch
                return true;
            }
        }
        return false;
    }
}












