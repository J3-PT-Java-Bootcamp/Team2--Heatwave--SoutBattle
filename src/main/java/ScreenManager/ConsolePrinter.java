package ScreenManager;

import java.io.*;
import java.util.ArrayList;
import static ScreenManager.ColorFactory.*;

/**
 *
 */
public class ConsolePrinter {


    //---------------------------------------------------------------------------------------------------------CONSTANTS
    private enum Scroll{NO,BLOCK,LINE}
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
            +"-".repeat(LIMIT_X-GAME_NAME.length())+GAME_NAME+"\n"+"=".repeat(LIMIT_X),LIMIT_X,LIMIT_Y/2);

    private final TextObject GAME_LOGO= new ScreenManager.TextObject("""
                                                                            ,,
    .M""\"bgd                      mm       `7MM""\"Yp,          mm     mm   `7MM
  ,MI    "Y                      MM         MM    Yb          MM     MM     MM
           `MMb.      ,pW"Wq.`7MM  `7MM mmMMmm       MM    dP  ,6"Yb.mmMMmm mmMMmm   MM  .gP"Ya
              `YMMNq. 6W'   `Wb MM    MM   MM         MM""\"bg. 8)   MM  MM     MM     MM ,M'   Yb
            .     `MM 8M     M8 MM    MM   MM         MM    `Y  ,pm9MM  MM     MM     MM 8M""\"""\"
            Mb     dM YA.   ,A9 MM    MM   MM    d8b  MM    ,9 8M   MM  MM     MM     MM YM.    ,
            P"Ybmmd"   `Ybmd9'  `Mbod"YML. `Mbmo Y8P.JMMmmmd9  `Moo9^Yo.`Mbmo  `Mbmo.JMML.`Mbmmd'""",LIMIT_X, LIMIT_Y),
            TEAM_LOGO= new ScreenManager.TextObject( """
                __  __           __ _       __
               / / / /__  ____ _/ /| |     / /___ __   _____
              / /_/ / _ \\/ __ `/ __/ | /| / / __ `/ | / / _ \\
             / __  /  __/ /_/ / /_ | |/ |/ / /_/ /| |/ /  __/
            /_/ /_/\\___/\\__,_/\\__/ |__/|__/\\__,_/ |___/\\___/ 		 _  __   __  _ _/_ _
            														/_///_'_\\/_'/ // _\\  ...
            													   /
            														""",LIMIT_X,LIMIT_Y),
            SCREEN_RECT= new ScreenManager.TextObject("X".repeat(LIMIT_X) + "\n"
                    + ("Y" + " ".repeat(LIMIT_X-2) + "Y\n").repeat(LIMIT_Y-4)
                    + "X".repeat(LIMIT_X),LIMIT_X,LIMIT_Y),
            EMPTY_LINE=new TextObject(BLANK_SPACE.repeat(LIMIT_X-1),LIMIT_X,1);
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
                .stylizeAllText(TextStyle.BOLD).alignTextTop(),2);
        sendToQueue(GAME_LOGO.alignTextCenter().colorizeAllText().stylizeAllText(TextStyle.BOLD).alignTextMiddle(),2);
        this.printSpeed =2;
        startPrint(Scroll.BLOCK);
        waitFor(1000);
    }
    /** Shows Square with the screen size to allow User to resize console,
     *  waits until user confirm
     */
    public void calibrateScreen() throws Exception {
      sendToQueue(SCREEN_RECT.setAllTextBackground(BgColors.BRIGHT_WHITE));
      sendToQueue((new TextObject("Adjust your console size to fit the rectangle above.", LIMIT_X,2)
              .addText(CColors.CYAN +"Press Enter TWICE when done"+TextStyle.RESET).alignTextCenter()));
      startPrint(Scroll.BLOCK);
      in.skip(2);

    }

    public Menu showMenu(boolean showError) {
//        clearScreen();
//        var strBuilder= new StringBuilder();
//        var strBuilderAux= new StringBuilder();
//        StringBuilder auxString= new StringBuilder();
//
//        strBuilderAux.append(HEADER).append("\n").append("--------------------------//  MENU  \\\\--------------------------\n");
//        for (int i = 0; i < Menu.values().length; i++) {
//            strBuilder.append("\n").append(" ".repeat(TAB_INDENT*3)).append(i).append(" -->");
//            auxString.append("\n").append(Menu.values()[i].toString());
//        }
//        String outputText=textToTable(2,
//                LIMIT_X/2,
//                new String[]{strBuilder.toString(), auxString.toString()})+"\n";
//        if(showError) outputText+=("\n\n \u001B[31m        ERR_   Input not recognized \u001B[0m");
//        outputText+="\n Enter a number to continue";
//       sendToPrint(textToTop(centerText(strBuilderAux.append(outputText).toString(),LIMIT_X),LIMIT_Y));
//        var input= in.readLine();
//        input=input.replace("\n","").trim();
//        int inputNumber = -1;
//        try {
//            inputNumber = Integer.parseInt(input);
//        }catch (Exception e){
//            return showMenu(true);
//        }
//        if (inputNumber<Menu.values().length&&inputNumber>=0) {
//            return Menu.values()[inputNumber];
//        }
//        return showMenu(true);
        return ScreenManager.ConsolePrinter.Menu.EXIT;
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
    public void startPrint(Scroll scroll){
        switch (scroll) {
            case NO -> System.out.print(mergeQueue());
            case BLOCK -> {
                while (!printQueue.isEmpty()){
                    System.out.print(pollNext());
                    waitFor(1000/ printSpeed);
                }
            }
            case LINE -> {
                lineSplitQueue();
                while (!printQueue.isEmpty()){
                    System.out.print(pollNext());
                    waitFor(1000/ printSpeed);
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + scroll);
        }
    }

    private void lineSplitQueue() {
        var newQueue= new ArrayList<TextObject>();
        TextObject currentTxtObj;
        while (!printQueue.isEmpty()) {
            currentTxtObj = pollNext();
            for (int j = 0; j < currentTxtObj.getTotalHeight() ; j++) {
                newQueue.add(new TextObject(currentTxtObj.get(j),LIMIT_X,1));
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
    public void sendToQueue(TextObject txtObj,int emptyLinesBfr){
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
        sendToQueue(new TextObject((EMPTY_LINE+NEW_LINE).repeat(LIMIT_Y),LIMIT_X,LIMIT_Y));
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

    static class Party {//TODO as separated class, only here while not implemented
        String[] fighters;

        public Party(String[] fighters) {
            this.fighters = fighters;
        }
    }
}
