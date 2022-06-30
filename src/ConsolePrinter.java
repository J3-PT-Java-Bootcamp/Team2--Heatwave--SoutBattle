import java.io.*;
import java.util.ArrayList;

/**
 *
 */
public class ConsolePrinter {
    //---------------------------------------------------------------------------ATTRIBUTES
    enum Menu{
        PLAY("Play Game","P"),
        NEW_TEAM("Create New Team","N"),
        ABOUT("Read Me","R"),
        MEMORIAL("See Graveyard","G"),
        CALIBRATE("Calibrate Screen","S"),
        CLEAR_DATA("Clear All Data","C"),
        EXIT("Exit Game","E");
        private final String label;
        private final String keyChar;
        Menu(String label,String keyChar){
            this.label=label;
            this.keyChar=keyChar;
        }

        @Override
        public String toString() {
            return this.label;
        }
    }
    private final int LIMIT_X=120,LIMIT_Y=20,TAB_INDENT=5; //Screen sizes in characters
    private final String GAME_NAME="AmazingFighter";
    private final String HEADER="=".repeat(LIMIT_X)+"\n"
            +"-".repeat(LIMIT_X-GAME_NAME.length())+GAME_NAME+"\n"+"=".repeat(LIMIT_X);
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
    public void splashScreen() throws IOException {
        calibrateScreen();
        clearScreen();
        out.println(TEAM_LOGO+"\n\n");
        out.println(centerText(GAME_LOGO,LIMIT_X));
        waitFor(2000);
    }
    /** Shows Square with the screen size to allow User to resize console,
     *  waits until user confirm
     */

    public void calibrateScreen() throws IOException {
        System.out.println("X".repeat(120)
                + "\n" + ("Y" + " ".repeat(118) + "Y\n").repeat(18) + "X".repeat(120) +
                centerText("\n\nAdjust your console size to fit the square above.Press Enter TWICE when done"
                        , LIMIT_X));

        in.skip(2);

    }

    public Menu showMenu(boolean showError) throws IOException {
        clearScreen();
        var strBuilder= new StringBuilder();
        var strBuilderAux= new StringBuilder();
        StringBuilder auxString= new StringBuilder();

        strBuilderAux.append(HEADER).append("\n").append("--------------------------//  MENU  \\\\--------------------------\n");
        for (int i = 0; i < Menu.values().length; i++) {
            strBuilder.append("\n").append(" ".repeat(TAB_INDENT*3)).append(i).append(" -->");
            auxString.append("\n").append(Menu.values()[i].toString());
        }
        String outputText=textToTable(2,
                LIMIT_X/2,
                new String[]{strBuilder.toString(), auxString.toString()})+"\n";
        if(showError) outputText+=("\n\n \u001B[31m        ERR_   Input not recognized \u001B[0m");
        System.out.println(textToTop(centerText(strBuilderAux.append(outputText).toString(),LIMIT_X),LIMIT_Y));
        var input= in.readLine();
        input=input.replace("\n","").trim();
        int inputNumber = -1;
        try {
            inputNumber = Integer.parseInt(input);
        }catch (Exception e){
            return showMenu(true);
        }
        if (inputNumber<Menu.values().length&&inputNumber>=0) {
            return Menu.values()[inputNumber];
        }
        return showMenu(true);
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
    private String centerText(String text, int width){
        var splitText = splitText(text);
        int leftSpace,rightSpace,remainSpace;
        StringBuilder resVal= new StringBuilder();
        for (String s : splitText) {
            remainSpace = width - s.length();
//            if(remainSpace<0)break;
            leftSpace = remainSpace / 2;
            rightSpace = (remainSpace % 2 == 0) ? leftSpace : leftSpace + 1;
            resVal.append(" ".repeat(leftSpace)).append(s).append(" ".repeat(rightSpace)).append("\n");
        }
        return resVal.toString();
    }
    private String justifyText(String text,int width){
        var strBuilder = new StringBuilder();
        for (String str:text.split("\n")) {
            if (str.length()>width){
                var auxList=str.split(" ");
                if(auxList.length>1){
                    int charCount=0;
                    for (String s : auxList) {
                        charCount += s.length();
                        strBuilder.append(charCount > width ? "\n" : "").append(s);
                    }
                }else {
                    String auxStr;
                    do {
                        strBuilder.append(str, 0, width - 1).append("-\n");
                        str=str.substring(width);
                        auxStr = str.substring(width);
                    }while (auxStr.length()>width);
                }
            }else {
                strBuilder.append(str).append("\n");
            }

        }
        return strBuilder.toString();
    }
    private String centerTextVertically(String text,int height){
        int remainingLines= height - text.split("\n").length;
        if (remainingLines>1){
            return "\n".repeat(Math.floorDiv(remainingLines, 2)) +
                    text + "\n".repeat(Math.floorDiv(remainingLines, 2));
        }
        return text;
    }
    private String textToTable(int numberOfColumns, int totalSize, String [] columnsContent){
        int charLimit = (numberOfColumns>1?(totalSize/numberOfColumns):totalSize)-numberOfColumns;
        int totalLines=0;
        var strBuilder= new StringBuilder();
        var content=new ArrayList<String[]>();
        for (String column:columnsContent) {
            totalLines=Math.max(totalLines,countLines(column));
//            content.add(splitText(centerTextVertically(justifyText(column,charLimit),totalLines)));
            content.add(splitText(justifyText(column,charLimit)));
        }
        String[] auxList;

        for (int j = 0; j < totalLines; j++) {
            for (int i = 0; i < numberOfColumns; i++) {
                String currentVal;
                try{
                    currentVal = content.get(i)[j];
                }catch (Exception e){
                    currentVal=" ".repeat(charLimit);
                }
                strBuilder.append(" ").append(currentVal);
                if(currentVal.length()<charLimit){
                            strBuilder.append(" ".repeat(charLimit-content.get(i)[j].length()));
                }
                if(i==numberOfColumns-1)strBuilder.append("\n");
            }
        }
//        int calcParts=(int) Math.ceil((columnsContent.length())/ charLimit);
//        String[] resVal= new String[calcParts];
//        for (int i=0;i<calcParts;i++) {
//            resVal[i]= " "+columnsContent.subSequence(i*charLimit,(i*charLimit+charLimit)).toString();
//        }
        return strBuilder.toString();
    }
    private int countLines(String text){
        return text.split("\n").length;
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

    /**Adds new lines to show specified text at console Absolute Top (aprox)
     * @param text to show on top
     * @return formatted text
     */
    private String textToTop(String text,int height){
        var count=height-countLines(text);
        return  count>0?text+"\n".repeat(count):text;
    }
    private String partyToString(Party party){
        return "";
    }


    //---------------------------------------------------------------------------   CONSOLE MANAGER

    /**Shorthand for Thread.sleep(miliseconds)
     * @param milis time to sleep in miliseconds
     */
    private void waitFor(int milis){
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);//TODO report to log
        }
    }
    /** Sends new lines to fill screen and clear last output
     */
    private void clearScreen(){
        out.println("\n".repeat(LIMIT_Y));
    }

    /**Prints the text line per line with specified delay
     * @param text text to print
     * @param linesPerSecond number of lines to be printed in 1000ms
     */
    private void scrollText(String text,int linesPerSecond){
        scrollText(splitText(text),linesPerSecond);
    }
    /**Prints the text grouped in blocks with specified delay
     * @param splitText text array to print, split by blocks
     * @param blocksPerSecond number of blocks to be printed in 1000ms
     */
    private void scrollText(String[] splitText,int blocksPerSecond){
        for (String s : splitText) {
            waitFor(1000 / blocksPerSecond);
            System.out.println(s);
        }
    }

    static class Party {//TODO as separated class, only here while not implemented
        String[] fighters;

        public Party(String[] fighters) {
            this.fighters = fighters;
        }
    }
}
