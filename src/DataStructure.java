import java.io.*;

/**
 * this class performs the reading of the dictionary and used tree object to add the string to the tree.
 */
public class DataStructure {

    /**declaring string for storing first command line argument*/
    private String dictionary;

    /**initiating the node object */
    Node tree = new Node();

    public DataStructure(String str){
        dictionary =str;
    }

    /**
     * this method reads the dictionary and adds each word to the Trie tree.
     */
    public  void getStructure()  {
        BufferedReader reader =null;

        try{
            reader = new BufferedReader(new FileReader(new File(dictionary)));
            String line = reader.readLine();
            while (line!=null){
                tree.addString(tree,line.toLowerCase());
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
