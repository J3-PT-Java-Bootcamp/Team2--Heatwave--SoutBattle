import java.awt.*;
import java.io.*;

public class ConsolePrinter {
    //---------------------------------------------------------------------------ATTRIBUTES
    private final int LIMIT_X=120,LIMIT_Y=20; //Screen size in characters
    private final int TAB_INDENT=5; //Tabulation size in characters
    private final String GAME_LOGO= """
                  |XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX|
                  |X____ _____                          _____ __                    X|
                  |X___ / ___/__  ______  ___  _____   / ___// /_____  ________     X|
                  |X__  \\__ \\/ / / / __ \\/ _ \\/ ___/   \\__ \\/ __/ __ \\/ ___/ _ \\    X|
                  |X_  ___/ / /_/ / /_/ /  __/ /      ___/ / /_/ /_/ / /  /  __/    X|
                  |X__/____/\\__,_/ .___/\\___/_/ ____ /____/\\__/\\____/_/   \\___/ ____X|
                  |X__________  /_/ ________________________________________________X|
                  |X_____________________________________COOLEST_STUFF_IN_TOWN______X|
                  |XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX|"""; //GAME NAME LOGO
    private final String TEAM_LOGO= """
   ______               __ ______
  / ____/____   ____   / //_  __/___   ____ _ ____ ___
 / /    / __ \\ / __ \\ / /  / /  / _ \\ / __ `// __ `__ \\
/ /___ / /_/ // /_/ // /  / /  /  __// /_/ // / / / / /
\\____/ \\____/ \\____//_/  /_/   \\___/ \\__,_//_/ /_/ /_/"""; //TEAM NAME LOGO
    private final PrintStream out;
    private final BufferedReader in;
    private final StringBuilder strBuilder;

    //---------------------------------------------------------------------------CONSTRUCTOR
    public ConsolePrinter() {
        this.out = System.out;
        this.in= new BufferedReader(new InputStreamReader(System.in));
        this.strBuilder= new StringBuilder();
    }

    //---------------------------------------------------------------------------PUBLIC METHODS
    /**Shows the Team Logo after calibrating console size
     */
    public void splashScreen(){
        calibrateScreen();
        out.println(TEAM_LOGO+"\n\n");
        out.println(centerText(GAME_LOGO));

    }

    //---------------------------------------------------------------------------TEXT MANIPULATION
    /**Transform a Multiline Text to be shown in the center
     * of our screen by adding spaces both sides
     * @param text String to center on Screen
     * @return Transformed String
     */
    private String centerText(String text){
        var splitText = splitText(text);
        int leftSpace,rightSpace,remainSpace;
        String resVal="";
        for (String s : splitText) {
            remainSpace = LIMIT_X - s.length();
            leftSpace = remainSpace / 2;
            rightSpace = (remainSpace % 2 == 0) ? leftSpace : leftSpace + 1;
            resVal += " ".repeat(leftSpace) + s + " ".repeat(rightSpace) + "\n";
        }
        return resVal;
    }


    /**Adds or deletes tabulation indent from a multiline text
     * @param mustAdd True for adding tabulations, False to trim each line
     * @param numTabulations    Number of tabulations to add (each tabulation = TAB_INDENT spaces)
     * @param text  Text to be tabulated
     * @return  Formated text
     */
    private String changeTab(boolean mustAdd, int numTabulations,String text){
        var auxList= splitText(text);
        text="";
        for (String s : auxList) {
            text += (mustAdd ? (" ".repeat(TAB_INDENT)).repeat(numTabulations) + s : s.trim()) + "\n";
        }
        return text;
    }

    /** Splits a multi line string into array of String lines
     * @param text multi line text
     * @return String Lines Array
     */
    private String[] splitText(String text){
        return text.split("\n");
    }

    /** Join a String Array into only one String, separated by new line
     * @param splitText String Array to join
     * @return String with all array members joined, each for line
     */
    private String joinText(String[] splitText){
        String resVal="";
        for (String s : splitText) {
            resVal += s + "\n";
        }
        return resVal;
    }

    //---------------------------------------------------------------------------CONSOLE MANAGER
    /** Shows Square with the screen size to allow User to resize console,
     *  waits until user confirm
     */
    private void calibrateScreen(){
        out.println("X".repeat(120)+"\n"+("Y"+" ".repeat(118)+"Y\n").repeat(18)+"X".repeat(120));
        out.println("Adjust your console size to fit the square above.Press Enter when done");
        try {
            in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}
