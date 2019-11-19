/**
 * this class provides the computer tray and the features to add or remove letters from it
 */
public class ComputerPlayer {

    /**
     * initiating string computer tray to store the letters that computer has it in the tray.
     */
    private String computerTray ="";

    /**
     * this method gets letter as a parameter and adds it to the computer tray.
     * @param c
     */
    public void setComputerTray(char c){
        computerTray = computerTray+ Character.toString(c);
    }

    /**
     *
     * @return computer tray
     */
    public String getComputerTray(){
        return computerTray;
    }

    /**
     *
     * @param str is set as a computer tray by this method.
     */
    public void setComputerTray(String str){
        computerTray = str;
    }

    /**
     * this method receives the letter as a parameter to the function and removes it from the tray.
     * @param c
     */
    public void removeLetter(char c){
        if(c=='*') {
            computerTray = computerTray.replaceFirst("\\*", "");
        } else {
            computerTray = computerTray.replaceFirst(Character.toString(c), "");
        }
    }

    /**
     * this method empties the tray of the computer.
     */
    public void removeAll(){
        computerTray = "";
    }


}
