/**
 * this class handles the tray of the human (player One) and provides features to add or remove letters from human tray
 */
public class PlayerOne {

    /**initiating human tray to hold the letters of the human tray */
    private String humanTray = "";

    /**
     * this method takes letter as a parameter and adds it to the human tray
     * @param c
     */
    public void setHumanTray(char c){
        humanTray = humanTray + Character.toString(c);
    }


    /**
     * this method takes string as a parameter and adds it to the human tray
     * @param str
     */
    public void setHumanTray(String str){
        humanTray = humanTray + str;
    }

    /**
     *
     * @returns human tray
     */
    public String getHumanTray(){
        return humanTray;
    }

    /**
     * this method renew tray if the player confirms the exchange letters.
     * @param str
     * str's letters are removed from the human tray.
     */
    public void renewTray(String str){
        System.out.println("this is used str" + str);
        for(int i = 0; i<str.length();i++){
            if(str.charAt(i)=='*'){
                humanTray = humanTray.replaceFirst("\\*", "");
                System.out.println("asterik should have been replaced here current tray is "+humanTray);
            } else {
                humanTray = humanTray.replaceFirst(Character.toString(str.charAt(i)), "");
            }
        }
        System.out.println("this is human tray "+humanTray);
    }


    /**
     * this method empties the human tray
     */
    public void clearTray(){
        humanTray ="";
    }
}
