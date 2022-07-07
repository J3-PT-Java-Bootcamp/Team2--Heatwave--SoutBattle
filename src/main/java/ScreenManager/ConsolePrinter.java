package ScreenManager;

import ScreenManager.ColorFactory.BgColors;
import ScreenManager.TextObjects.*;
import ScreenManager.TextObjects.TextObject.Scroll;

import java.io.*;
import java.util.ArrayList;
import static ScreenManager.ColorFactory.*;

/**
 *
 */
public class ConsolePrinter {


    public void test() {
        this.printSpeed=6;
        printQueue.add(new ScreenManager.TextObjects.TextObject("TESTING A TYPE WRITER SCROLL ", ScreenManager.TextObjects.TextObject.Scroll.TYPEWRITER,LIMIT_X,LIMIT_Y).alignTextCenter());
        printQueue.add(new WindowObject(LIMIT_X,LIMIT_Y,2,2)
                .setTitle("_TEST_").setFrameColor(ScreenManager.ColorFactory.BgColors.BLUE).setBgColor(BgColors.RED)
                .setTitleColor(CColors.BRIGHT_WHITE).setTxtColor(CColors.BLACK)
                .addText("THIS IS A MAIN WINDOW TEST\n\n\n With a multiline long long long text to be displayed in the center of the window, let's see if it wraps lines CORRECTLY")
                .alignTextCenter().alignTextMiddle());
        startPrint();
        printSpeed=1;
        printQueue.add(new DynamicLine(LIMIT_X,LIMIT_Y,500,2,2)
                .addText("This").addText("is").addText("a").addText("dynamic").addText("line").alignTextCenter());
        startPrint();
    }

    //---------------------------------------------------------------------------------------------------------CONSTANTS
    public enum Menu{
        PLAY("Play Game"),
        NEW_PARTY("Create New Party"),
        ABOUT("Read Me"),
        MEMORIAL("See Graveyard"),
        CALIBRATE("Calibrate Screen"),
        CLEAR_DATA("Clear All Data"),
        EXIT("Exit Game");
        private final String label;

        Menu(String label){
            this.label=label;
        }

        @Override
        public String toString() {
            return this.label;
        }
    }
    enum Modal{OK("Confirm"),
        CANCEL("Cancel");
        private final String label;

        Modal(String label){
            this.label=label;
        }

        @Override
        public String toString() {
            return this.label;
        }}
    private final int LIMIT_X=120,LIMIT_Y=20,TAB_INDENT=5; //Screen sizes in characters
    private final String GAME_NAME="S.OUT.Battle";
    private final TextObject HEADER=new TextObject("=".repeat(LIMIT_X)+"\n"
            +"-".repeat(LIMIT_X-GAME_NAME.length())+GAME_NAME+"\n"+"=".repeat(LIMIT_X), Scroll.NO, LIMIT_X,LIMIT_Y/2);

    public final TextObject GAME_LOGO= new TextObject("""
                                                                            ,,
    .M""\"bgd                      mm       `7MM""\"Yp,          mm     mm   `7MM
  ,MI    "Y                      MM         MM    Yb          MM     MM     MM
           `MMb.      ,pW"Wq.`7MM  `7MM mmMMmm       MM    dP  ,6"Yb.mmMMmm mmMMmm   MM  .gP"Ya
              `YMMNq. 6W'   `Wb MM    MM   MM         MM""\"bg. 8)   MM  MM     MM     MM ,M'   Yb
            .     `MM 8M     M8 MM    MM   MM         MM    `Y  ,pm9MM  MM     MM     MM 8M""\"""\"
            Mb     dM YA.   ,A9 MM    MM   MM    d8b  MM    ,9 8M   MM  MM     MM     MM YM.    ,
            P"Ybmmd"   `Ybmd9'  `Mbod"YML. `Mbmo Y8P.JMMmmmd9  `Moo9^Yo.`Mbmo  `Mbmo.JMML.`Mbmmd'""", Scroll.BLOCK, LIMIT_X, LIMIT_Y);
    public final TextObject TEAM_LOGO= new TextObject( """
                __  __           __ _       __
               / / / /__  ____ _/ /| |     / /___ __   _____
              / /_/ / _ \\/ __ `/ __/ | /| / / __ `/ | / / _ \\
             / __  /  __/ /_/ / /_ | |/ |/ / /_/ /| |/ /  __/
            /_/ /_/\\___/\\__,_/\\__/ |__/|__/\\__,_/ |___/\\___/ 		 _  __   __  _ _/_ _
            														/_///_'_\\/_'/ // _\\  ...
            													   /
            														""", Scroll.BLOCK,LIMIT_X,LIMIT_Y);
    public final TextObject SCREEN_RECT= new TextObject("X".repeat(LIMIT_X) + "\n"
                    + ("Y" + " ".repeat(LIMIT_X-2) + "Y\n").repeat(LIMIT_Y)
                    + "X".repeat(LIMIT_X), Scroll.BLOCK, LIMIT_X,LIMIT_Y);
    public final TextObject EMPTY_LINE=new TextObject(BLANK_SPACE.repeat(LIMIT_X-1), Scroll.NO, LIMIT_X,1);
    private final BufferedReader in;
    private final FileWriter logWriter;
    private ArrayList<TextObject> printQueue;
    private int printSpeed;

    //---------------------------------------------------------------------------   CONSTRUCTOR
    public ConsolePrinter() {
        this.in= new BufferedReader(new InputStreamReader(System.in));
        this.printSpeed =1;
        this.printQueue=new java.util.ArrayList<>();
        try {
            this.logWriter= new java.io.FileWriter("data/consolePrinter_log.txt");
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    //---------------------------------------------------------------------------   PUBLIC METHODS
    /**Shows the Team Logo after calibrating console size
     */
    public void splashScreen() throws Exception {
        calibrateScreen();
        clearScreen();
        sendToQueue(TEAM_LOGO
                .colorizeAllText(CColors.BRIGHT_RED,CColors.RED,CColors.RED,  CColors.YELLOW, CColors.BRIGHT_YELLOW)
                .stylizeAllText(TextStyle.BOLD));
        sendToQueue(GAME_LOGO.alignTextCenter().colorizeAllText().stylizeAllText(TextStyle.BOLD).alignTextMiddle(),2);
        this.printSpeed =2;
        startPrint();
        waitFor(1000);
    }
    /** Shows Square with the screen size to allow User to resize console,
     *  waits until user confirm
     */
    public void calibrateScreen() throws Exception {
      sendToQueue(SCREEN_RECT.setAllTextBackground(BgColors.BRIGHT_WHITE));
      sendToQueue(new TextObject(rainbowCharacters("Adjust your console size to fit the rectangle above.",4), TextObject.Scroll.NO, LIMIT_X,2)
              .addText(ScreenManager.ColorFactory.CColors.CYAN +"Press Enter TWICE when done"+ ScreenManager.ColorFactory.TextStyle.RESET).alignTextCenter());
      startPrint();
      in.skip(2);

    }

    public Menu showMenu(boolean showError) {
        if(showError) {
            var str= CColors.BRIGHT_RED + "ERR_   Input not recognized" + TextStyle.RESET;
            int availableSpace=((LIMIT_X-(str.length()-(3*COLOR_LABEL_CHAR_SIZE)))/2);
            str= BLANK_SPACE.repeat(availableSpace)+str+BLANK_SPACE.repeat(availableSpace);
            System.out.print(str);
            waitFor(600);
            System.out.print(DELETE_CURRENT_LINE + CColors.BRIGHT_GREEN.toString()+(BLANK_SPACE.repeat(availableSpace))
                    +" TRY AGAIN "+TextStyle.RESET );
            waitFor(500);
            System.out.print(DELETE_CURRENT_LINE + BLANK_SPACE.repeat(availableSpace) );

        }else {

            clearScreen();
            var numberTextObject = new TextObject(Scroll.NO, LIMIT_X / 2
                    ,LIMIT_Y - (HEADER.getTotalHeight() + 1));

            var titleTextObject = new TextObject(HEADER, Scroll.NO, LIMIT_X, HEADER.getTotalHeight() + 1);
            var nameTextObject = new TextObject(Scroll.NO, LIMIT_X / 2
                    ,LIMIT_Y - (HEADER.getTotalHeight() + 1));

            titleTextObject.addText("--------------------------//  MENU  \\\\--------------------------")
                    .addText(EMPTY_LINE).alignTextCenter();
            sendToQueue(titleTextObject);
            for (int i = 0; i < Menu.values().length; i++) {
                numberTextObject.addText(i + " -->");
                nameTextObject.addText(Menu.values()[i].toString());
            }
            numberTextObject.alignTextRight();
            nameTextObject.fillAllLines();
            var finalTxtObj = new TextObject(Scroll.NO, LIMIT_X
                    , LIMIT_Y - (HEADER.getTotalHeight() + 1)).addGroupAligned(2,
                    LIMIT_X / 2, new TextObject[]{numberTextObject, nameTextObject});
            sendToQueue(finalTxtObj.addText(EMPTY_LINE).alignTextMiddle().colorizeAllText());
            sendToQueue(new TextObject("Enter a number to continue", ScreenManager.TextObjects.TextObject.Scroll.TYPEWRITER, LIMIT_X, 1).alignTextCenter());
            startPrint();

        }
        int inputNumber;
        try {
            inputNumber = Integer.parseInt(getInp());
        } catch (Exception e) {
            return showMenu(true);
        }
        if (inputNumber < Menu.values().length && inputNumber >= 0) {
            return Menu.values()[inputNumber];
        }
        return showMenu(true);
    }

    private String getInp() {
        String input;
        try {
            input = in.readLine();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        input = input.replace("\n", "").trim();
        return input;
    }

    public Party chooseParty(Party[] parties){
        return parties[0];
    }
    public Character chooseCharacter(Party party){
        //TODO prints all characters of a party and all the stats and waits until user choose one
//        (partyToString(party));
        return 'a';
    }
    public void printFight(){

    }
    public void printGameOver(Boolean playerWins){
        //TODO print game over screen (model depends on player winning or not)
    }
    public void printMemorial(String[] graveyard){
        //TODO prints memorial screen with all dead fighters
    }

    public Modal showModal(String message, String captureInput){
        return ConsolePrinter.Modal.OK;
    }

    //---------------------------------------------------------------------------   CONSOLE MANAGER
    public void startPrint(){
        var sb=new StringBuilder();
        while(!printQueue.isEmpty()) {
            var txtObj=pollNext();
            switch (txtObj.getScroll()) {
                case NO -> {
                    if(queueContainsScroll(Scroll.NO)) sb.append(txtObj.print()).append(NEW_LINE);
                    else System.out.print(sb.append(txtObj.print()));
                }
                case BLOCK -> {
                    System.out.print(txtObj.print());
                    waitFor(1000 / printSpeed);
                }
                case LINE -> {
                    int counter=0;
                    while (txtObj.hasText()) {
                        System.out.print(txtObj.print());
                        waitFor(1000 / printSpeed);
                        counter++;
                    }
                }
                case TYPEWRITER -> {
                    int counter=0;
                    if (txtObj.hasText()) {
                        do {
                            String line = txtObj.printLine(counter);
                            for (int i = 0; i < line.length(); i++) {
                                var currentChar = line.charAt(i);
                                if (currentChar == COLOR_CHAR) {
                                    int j = i;
                                    i += COLOR_LABEL_CHAR_SIZE - 1;
                                    var format = line.substring(j, i);
                                    System.out.print(format);
                                } else if (isASpecialCharacter(currentChar) || currentChar == BLANK_SPACE_CH) {
                                    int j = i;
                                    while (i < line.length() - 1 && (isASpecialCharacter(line.charAt(i + 1)) || line.charAt(i + 1) == BLANK_SPACE_CH)) {
                                        i++;
                                    }
                                    i++;
                                    if(i<line.length())System.out.print(line.substring(j, i+1));
                                }else{
                                    System.out.print(currentChar);
                                }
                                waitFor(1000/printSpeed);
                            }
                            System.out.print(NEW_LINE);
                        } while (txtObj.hasText());
                    }
                }
            }
        }
    }
    private void printAnimation(){
        if(printQueue.get(0)instanceof DynamicLine)printAnimation((DynamicLine) pollNext());
    }
    private void printAnimation(DynamicLine dynLine){
            do {
                System.out.print(DELETE_CURRENT_LINE+dynLine.poll());
//                waitFor(dynLine.getDelta());
            } while (dynLine.hasText());
    }
    private void lineSplitQueue() {
        var newQueue= new ArrayList<TextObject>();
        TextObject currentTxtObj;
        while (!printQueue.isEmpty()) {
            currentTxtObj = pollNext();
            for (int j = 0; j < currentTxtObj.getTotalHeight() ; j++) {
                newQueue.add(new TextObject(currentTxtObj.get(j), Scroll.LINE, LIMIT_X,1));
            }
        }
        printQueue=newQueue;
    }

    private String mergeQueue() {
        var sb= new StringBuilder();
        while (!printQueue.isEmpty()) sb.append(pollNext());
        return sb.toString();
    }

    public void sendToQueue(TextObject txtObj){
        this.printQueue.add(txtObj);
    }
    public void sendToQueue(TextObject txtObj, int emptyLinesBfr){
        for (int i = 0; i < emptyLinesBfr; i++) {
            printQueue.add(EMPTY_LINE);
        }
        sendToQueue(txtObj);
    }
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
    private void clearScreen() {
        sendToQueue(new TextObject(EMPTY_LINE, Scroll.BLOCK, LIMIT_X,LIMIT_Y).alignTextTop());
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

    private TextObject pollNext(){
        return printQueue.remove(0);
    }

    private boolean queueContainsScroll(Scroll scroll){
        for(TextObject txtObj: printQueue){
            if(txtObj.getScroll().equals(scroll))return true;
        }
        return false;
    }

    static class Party {//TODO as separated class, only here while not implemented
        String[] fighters;

        public Party(String[] fighters) {
            this.fighters = fighters;
        }
    }
}
