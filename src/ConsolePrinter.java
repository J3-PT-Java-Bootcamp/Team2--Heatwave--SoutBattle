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


    //---------------------------------------------------------------------------   CONSTRUCTOR
    public ConsolePrinter() {
        this.out = System.out;
        this.in= new BufferedReader(new InputStreamReader(System.in));
    }

    //---------------------------------------------------------------------------   PUBLIC METHODS
    /**Shows the Team Logo after calibrating console size
     */
    public void splashScreen(){
        calibrateScreen();
        out.println(TEAM_LOGO+"\n\n");
        out.println(centerText(GAME_LOGO));

    }
    public void printError(String message){
        //TODO prints a error message
    }
    public void printChooseCharacter(Party party){
        //TODO prints all characters of a party and all the stats and waits until user choose one
        out.println(partyToString(party));
    }
    public void printWinnerCharacter(Character fighter){
        //TODO print message to announce current fight winner
    }
    public void printGameOver(Boolean playerWins){
        //TODO print game over screen (model depends on player winning or not)
    }
    public void printMemorial(String[] graveyard){
        //TODO prints memorial screen with all dead fighters
    }

    //---------------------------------------------------------------------------   TEXT MANIPULATION
    /**Transform a Multiline Text to be shown in the center
     * of our screen by adding spaces both sides
     * @param text String to center on Screen
     * @return Transformed String
     */
    private String centerText(String text){
        var splitText = splitText(text);
        int leftSpace,rightSpace,remainSpace;
        StringBuilder resVal= new StringBuilder();
        for (String s : splitText) {
            remainSpace = LIMIT_X - s.length();
            leftSpace = remainSpace / 2;
            rightSpace = (remainSpace % 2 == 0) ? leftSpace : leftSpace + 1;
            resVal.append(" ".repeat(leftSpace)).append(s).append(" ".repeat(rightSpace)).append("\n");
        }
        return resVal.toString();
    }


    /**Adds or deletes tabulation indent from a multiline text
     * @param mustAdd True for adding tabulations, False to trim each line
     * @param numTabulations    Number of tabulations to add (each tabulation = TAB_INDENT spaces)
     * @param text  Text to be tabulated
     * @return  Formatted text
     */
    private String changeTab(boolean mustAdd, int numTabulations,String text){
        var auxList= splitText(text);
        StringBuilder textBuilder = new StringBuilder();
        for (String s : auxList) {
            textBuilder.append(mustAdd ? (" ".repeat(TAB_INDENT)).repeat(numTabulations) + s : s.trim()).append("\n");
        }
        return textBuilder.toString();
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
        StringBuilder resVal= new StringBuilder();
        for (String s : splitText) {
            resVal.append(s).append("\n");
        }
        return resVal.toString();
    }

    /**Adds new lines to show specified text at console Top (aprox)
     * @param text to show on top
     * @return formatted text
     */
    private String textToTop(String text){
        return  text+="\n".repeat(LIMIT_Y-text.split("\n").length);
    }
    private String partyToString(Party party){
        return "";
    }


    //---------------------------------------------------------------------------   CONSOLE MANAGER
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

    /** Sends new lines to fill screen and clear last output
     */
    private void clearScreen(){
        out.println("\n".repeat(LIMIT_Y));
    }


    private class Party {//TODO as separated class, only here while not implemented
    }
}
