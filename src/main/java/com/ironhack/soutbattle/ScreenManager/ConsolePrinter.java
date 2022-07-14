package com.ironhack.soutbattle.ScreenManager;

import com.ironhack.soutbattle.Characters.GameCharacter;
import com.ironhack.soutbattle.Characters.Party;
import com.ironhack.soutbattle.GameManager.FightReport;
import com.ironhack.soutbattle.GameManager.GameManager;
import com.ironhack.soutbattle.ScreenManager.ColorFactory.BgColors;
import com.ironhack.soutbattle.ScreenManager.ColorFactory.CColors;
import com.ironhack.soutbattle.ScreenManager.TextObjects.DynamicLine;
import com.ironhack.soutbattle.ScreenManager.TextObjects.TextObject;
import com.ironhack.soutbattle.ScreenManager.TextObjects.TextObject.Scroll;
import com.ironhack.soutbattle.ScreenManager.TextObjects.WindowObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.ironhack.soutbattle.ScreenManager.ColorFactory.*;
import static com.ironhack.soutbattle.ScreenManager.PrinterConstants.*;

/**
 *
 */
public class ConsolePrinter {

    private final ArrayList<TextObject> printQueue;
    private final GameManager game;

    //---------------------------------------------------------------------------   CONSTRUCTOR
    public ConsolePrinter(GameManager game) {
        this.game = game;
        this.printQueue = new ArrayList<>();
    }

    //---------------------------------------------------------------------------   PUBLIC METHODS

    /**
     * Shows the Team Logo after calibrating console size
     */
    public void splashScreen() {
        try {
            calibrateScreen();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        clearScreen();
        sendToQueue(TEAM_LOGO
                .colorizeAllText(CColors.BRIGHT_RED, CColors.RED, CColors.RED, CColors.YELLOW, CColors.BRIGHT_YELLOW)
                .stylizeAllText(TextStyle.BOLD).setPrintSpeed(1));
        sendToQueue(GAME_LOGO.alignTextCenter().colorizeAllText().stylizeAllText(TextStyle.BOLD), 2);

        startPrint();
        waitFor(1000);
    }

    public String askUserName() {
        sendToQueue(new TextObject("Welcome to " + GAME_NAME, Scroll.TYPEWRITER, LIMIT_X, LIMIT_Y)
                .addText("Enter your name:").alignTextCenter().alignTextMiddle().setPrintSpeed(6));
        sendToQueue(new TextObject(CENTER_CARET, Scroll.LINE, LIMIT_X, LIMIT_Y));
        startPrint();
        return getNameFromInput();
    }

    /**
     * Shows Square with the screen size to allow User to resize console,
     * waits until user confirm
     */
    public void calibrateScreen() throws Exception {
//      sendToQueue(SCREEN_RECT.setAllTextBackground(BgColors.BRIGHT_WHITE));
        sendToQueue(new WindowObject(LIMIT_X, LIMIT_Y + 2, 1, 1).setBgColor(BgColors.CYAN)
                .setFrameColor(BgColors.BRIGHT_BLACK).setTxtColor(CColors.BRIGHT_WHITE)
                .addText(TextStyle.BOLD + "Adjust your console size to fit this box.")
                .addText(TextStyle.BOLD + "Press Enter when done").alignTextCenter().alignTextMiddle()
                .addText(CENTER_CARET));
        startPrint();
        var in = newInput();
        in.readLine();

    }

    public Menu showMenu(boolean showError) {
        if (showError) {
            showErrorLine();
        } else {
            clearScreen();
            var numberTextObject = new TextObject(Scroll.NO, LIMIT_X / 2
                    , LIMIT_Y - (HEADER.getTotalHeight() + 1));
            var titleTextObject = new TextObject(HEADER, Scroll.NO, LIMIT_X, HEADER.getTotalHeight() + 1);
            var nameTextObject = new TextObject(Scroll.NO, LIMIT_X / 3
                    , LIMIT_Y - (HEADER.getTotalHeight() + 1));
            titleTextObject.addText("--------------------------//  MENU  \\\\--------------------------")
                    .addText(EMPTY_LINE).alignTextCenter().colorizeAllText();
            sendToQueue(titleTextObject);
            for (int i = 0; i < Menu.values().length; i++) {
                numberTextObject.addText(BLANK_SPACE.repeat((LIMIT_X / 2) - 10) + i + " -->");
                nameTextObject.addText(Menu.values()[i].toString());
            }
            var finalTxtObj = new TextObject(Scroll.NO, LIMIT_X
                    , LIMIT_Y - (HEADER.getTotalHeight() + 2)).addGroupAligned(2,
                    LIMIT_X, new TextObject[]{numberTextObject.alignTextRight(), nameTextObject.fillAllLines()});
            sendToQueue(finalTxtObj.addText(EMPTY_LINE).colorizeAllText());
            sendToQueue(new TextObject("Enter a number to continue", Scroll.NO, LIMIT_X, 1)
                    .alignTextCenter().setPrintSpeed(6).addText(CENTER_CARET));
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

    public void showMemorial(ArrayList<GameCharacter>graveyard) {
        var finalTxtObj= new TextObject(IN_MEMORIAM, Scroll.BLOCK,LIMIT_X,LIMIT_Y);
        for (int i = 0; i < graveyard.size() ; i++) {
            finalTxtObj.addText(graveyard.get(i).toTextObject());
        }
        finalTxtObj.addText(CANDLES);
        finalTxtObj.alignTextCenter();
        finalTxtObj.setPrintSpeed(6);
        sendToQueue(finalTxtObj.getResizedText(LIMIT_X,LIMIT_Y));
        startPrint();

    }

    public void readMe() {
        sendToQueue(GAME_OVER);
        sendToQueue(FIGHT_TITLE);
        sendToQueue(YOU_WIN);
        sendToQueue(CANDLES);
        sendToQueue(CASTLE);
        sendToQueue(TOMB);
        sendToQueue(CROIX);
        sendToQueue(IN_MEMORIAM);
        sendToQueue(WARRIOR_IMG);
        sendToQueue(WIZARD_IMG);


    }

    public String newPartyScreen() {
        sendToQueue(new WindowObject(LIMIT_X, LIMIT_Y, 2, 10)
                .setBgColor(BgColors.BLACK)
                .setFrameColor(BgColors.BRIGHT_BLACK)
                .setTitleColor(CColors.BRIGHT_CYAN)
                .setTitle("NEW PARTY").setTxtColor(CColors.BRIGHT_WHITE)
                .addText("Enter a name for your new Party: ").alignTextCenter().alignTextTop());
        startPrint();
        return getNameFromInput();
    }

    /**Method that prints a list of all parties,
     *  waits until user enters a party number and return the selected party
     * @param parties ArrayList of Party with all available parties
     * @see GameManager
     * @return Party chosen;
     */
    public Party chooseParty(ArrayList<Party> parties) {
        int col = (int) Math.ceil(parties.size() / 10.0);
        int charLimit=  (int) Math.floor(col>2?LIMIT_X / col:LIMIT_X/2);
        TextObject[] txtObjs = new TextObject[col];
        for (int i = 0; i < txtObjs.length; i++) {

            txtObjs[i] = new TextObject(Scroll.BLOCK,
                 charLimit , 1);
        }
        int objIndex = 0;
        String backName=" : 0 --> Back To Parties";
        txtObjs[0].addText(backName+BLANK_SPACE.repeat(charLimit - txtObjs[0].countValidCharacters(backName)-2)+BLANK_SPACE);
        for (int i = 1; i < parties.size()+1; i++) {
            String name= parties.get(i-1).getName()+" win:"+ CColors.GREEN+parties.get(i-1).getWins()+TextStyle.RESET;
            if ( txtObjs[i/10].countValidCharacters(name)>=charLimit-14) {
                txtObjs[i / 10].addText(" :" + (i >= 10 ? i : " " + i) + " --> "
                        + name.substring(0, charLimit - 13) + ".. ");
            }else {
                txtObjs[i / 10].addText(" :" + (i >= 10 ? i : " " + i) + " --> " + name
                        + BLANK_SPACE.repeat(charLimit - txtObjs[i/10].countValidCharacters(name)-10));
            }
            txtObjs[i/10].colorizeAllText();
        }
        var finalTxtObj = new TextObject(HEADER,Scroll.BLOCK,LIMIT_X, LIMIT_Y);
        finalTxtObj.addText("------- PARTY SELECTION -------")
                .addText(BLANK_SPACE.repeat(2))
                .addText(BLANK_SPACE.repeat(2))
                .colorizeAllText();
        if (txtObjs.length > 1) finalTxtObj.addGroupAligned(txtObjs.length, LIMIT_X, txtObjs);
        else finalTxtObj.addText(txtObjs[0].alignTextRight()).alignTextCenter();
        clearScreen();
        sendToQueue(finalTxtObj.alignTextTop().alignTextCenter());
        sendToQueue(new TextObject("Select a party to play", Scroll.NO, LIMIT_X, LIMIT_Y)
                .alignTextCenter().addText(CENTER_CARET));

        startPrint();
        parties.add(null);
        int resVal=getIntFromInput(parties.toArray());
        parties.remove(parties.size()-1);
        if (resVal==0) return null;
        return parties.get(resVal-1);
    }

    public boolean confirmationNeeded(String message) {
        clearScreen();
        printQueue.add(new WindowObject(LIMIT_X, LIMIT_Y, 3, 3)
                .setBgColor(BgColors.BLACK).setFrameColor(BgColors.WHITE).setTxtColor(CColors.BRIGHT_WHITE)
                .setTitleColor(CColors.BLACK).setTitle("Confirmation Needed")
                .addText(message).addGroupAligned(2, LIMIT_X / 2,
                        new TextObject[]{
                                new TextObject(Modal.CANCEL.ordinal() + "- " + Modal.CANCEL,
                                        Scroll.BLOCK, LIMIT_X / 4, 1),
                                new TextObject(Modal.OK.ordinal() + "- " + Modal.OK,
                                        Scroll.BLOCK, LIMIT_X / 4, 1)})
                .alignTextCenter().alignTextMiddle().addText(CENTER_CARET));
        startPrint();
        return Modal.values()[getIntFromInput(Modal.values())] == Modal.OK;
    }
    /**Method that prints a list of all Characters in party,
     *  waits until user enters a valid number and return the selected party
     * @param party current Party
     * @see GameManager
     * @return GameCharacter chosen;
     */
    public GameCharacter chooseCharacter(Party party) {
        var fullTxtObj= new TextObject(HEADER, Scroll.BLOCK,LIMIT_X,LIMIT_Y)
                .addText("------ Choose Fighter ------").stylizeAllText(TextStyle.BOLD)
                .addText("---  "+party.getName()+"  ---");
        var txtObjArr = new TextObject[MAX_FIGHTERS];
        for (int i = 0; i < MAX_FIGHTERS; i++) {
            txtObjArr[i] = new TextObject(party.getCharacter(i).isAlive()?("-" +
                    (i+1) + "-"):"RIP ",
                    Scroll.BLOCK, (LIMIT_X / MAX_FIGHTERS) - 1, 1)
                    .alignTextCenter();

        }
        fullTxtObj.addGroupAligned(MAX_FIGHTERS, LIMIT_X, txtObjArr).colorizeAllText()
                .addGroupAligned(MAX_FIGHTERS,LIMIT_X,party.toTextObject())
       .addText(BLANK_SPACE).addText("Enter a Number // [0] TO CANCEL ") .alignTextCenter()
                .addText(BLANK_SPACE).addText(CENTER_CARET);
        sendToQueue(fullTxtObj);
        startPrint();
        GameCharacter[] aliveFighters = java.util.Arrays.copyOf(party.getAliveFighters().toArray(new GameCharacter[0]),MAX_FIGHTERS+1);
//        var aliveFighters= party.getAliveFighters();
//        aliveFighters.add(null);
        int resVal=getIntFromInput(aliveFighters);
        if (resVal==0) return null;
        if(!party.getCharacter(resVal-1).isAlive())return chooseCharacter(party);
        return party.getCharacter(resVal-1);
    }

    public void printFight(FightReport report) {
        try {
            clearScreen();
            sendToQueue(createFightScreenBase(report.getPlayerObject(),report.getEnemyObject()));
            sendToQueue(createFightLine(report).setPrintSpeed(10));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        startPrint();
        waitFor(1000);

    }

    private DynamicLine createFightLine(FightReport report) throws Exception {

        var charLimit=LIMIT_X/3;
        var resLine= new DynamicLine(LIMIT_X,LIMIT_Y,1,2,100);
        int auxCounter=0;
        String player= "";
        String enemy= "";
        String msg="";
        msg = "  ";
        for (int i = 0; i < report.totalRounds(); i++) {
            //START
            player= report.getRound(i).getPlayerState(0).get(0);
            enemy= report.getRound(i).getEnemyState(0).get(0);

            resLine.addText(constructResultTextFromRound(charLimit, resLine, player, enemy, msg));
            //PLAYER ATTACK ANOUNCE
            msg =  CColors.BLUE+TextStyle.BOLD.toString()+report.player.getName().split(" ")[0]
                    +" "+report.getRound(i).getPlayerAttack()+TextStyle.RESET;
            resLine.addText(constructResultTextFromRound(charLimit, resLine, player, enemy, msg));
            //Player attack varAttribute discount
            player= report.getRound(i).getPlayerState(1).get(0);
            resLine.addText(constructResultTextFromRound(charLimit, resLine, player, enemy, msg));
            //Enemy damage
            enemy= report.getRound(i).getEnemyState(1).get(0);
            resLine.addText(constructResultTextFromRound(charLimit, resLine, player, enemy, msg));
            //enemy announce
            msg = CColors.RED+TextStyle.BOLD.toString()+report.enemy.getName().split(" ")[0]
                    +" "+report.getRound(i).getEnemyAttack()+TextStyle.RESET+" ";
            resLine.addText(constructResultTextFromRound(charLimit, resLine, player, enemy, msg));
            //enemy discount
            enemy= report.getRound(i).getEnemyState(2).get(0);
            resLine.addText(constructResultTextFromRound(charLimit, resLine, player, enemy, msg));
            //player damage
            player= report.getRound(i).getPlayerState(2).get(0);
            resLine.addText(constructResultTextFromRound(charLimit, resLine, player, enemy, msg));


        }
        resLine.setPrintSpeed(4);
        resLine.constructAnimation();
        return resLine;
    }

    @org.jetbrains.annotations.NotNull
    private String constructResultTextFromRound(int charLimit, com.ironhack.soutbattle.ScreenManager.TextObjects.DynamicLine resLine, String player, String enemy, String msg) {
        int count=(charLimit - resLine.countValidCharacters(msg)) / 2;
        int rest=(charLimit - resLine.countValidCharacters(msg)) %2;
        return  BLANK_SPACE.repeat(charLimit - resLine.countValidCharacters(player))+player
                + BLANK_SPACE.repeat(count) + msg
                + BLANK_SPACE.repeat(count+rest)
                + enemy;
    }

    private TextObject createFightScreenBase(TextObject player, TextObject enemy) throws Exception {
        var charLimit=LIMIT_X/3;
        var res=new TextObject(HEADER,Scroll.BLOCK,LIMIT_X,LIMIT_Y).colorizeAllText()
                .addText(FIGHT_TITLE.colorizeAllText(
                        CColors.BLUE,
                        CColors.BRIGHT_BLUE,
                        CColors.PURPLE,
                        CColors.BRIGHT_PURPLE,
                        CColors.BRIGHT_GREEN,
                        CColors.GREEN
                ));
//        int remSize= LIMIT_Y-HEADER.getTotalHeight()-FIGHT_TITLE.getTotalHeight()-player.getTotalHeight()-1;
//        for (int i = 0; i < remSize; i++) {
//            res.addText(BLANK_SPACE.repeat(charLimit));
//        }
        for (int i = 0; i < player.getTotalHeight(); i++) {

            res.addText(BLANK_SPACE.repeat(charLimit-res.countValidCharacters(player.get(i)))+player.get(i)
            +BLANK_SPACE.repeat(charLimit)
            +enemy.get(i));

        }
        res.addText(BLANK_SPACE);
        return res;
    }
    private TextObject createFightColumns(String player,String message,TextObject enemy){
        return new TextObject(Scroll.BLOCK,LIMIT_X,1);

    }
    public void printGameOver(Boolean playerWins) {
        clearScreen();
        sendToQueue(playerWins?YOU_WIN.colorizeAllText():GAME_OVER.colorizeAllText(CColors.RED, CColors.PURPLE, CColors.BRIGHT_PURPLE));
        startPrint();
        waitFor(5000);
;    }

    public void goodBye(String userName) {
        clearScreen();
        sendToQueue(new TextObject("Thanks for Playing " + userName + ", Good Bye! ",
                Scroll.TYPEWRITER, LIMIT_X, LIMIT_Y).alignTextCenter().alignTextMiddle().setPrintSpeed(10));
        startPrint();
    }

    public void helloUser(String userName) {
        clearScreen();
        sendToQueue(new TextObject("Welcome Back " + userName, Scroll.TYPEWRITER, LIMIT_X, LIMIT_Y)
                .setPrintSpeed(10).alignTextCenter().alignTextMiddle());
        startPrint();
        waitFor(500);
    }

    //---------------------------------------------------------------------------   CONSOLE MANAGER
    public void startPrint() {
        var sb = new StringBuilder();
        while (!printQueue.isEmpty()) {
            var txtObj = pollNext();
            switch (txtObj.getScroll()) {
                case NO -> {
                    if (queueContainsScroll(Scroll.NO)) sb.append(txtObj.print()).append(NEW_LINE);
                    else System.out.print(sb.append(txtObj.print()));
                }
                case BLOCK -> {
                    System.out.print(txtObj.print());
                    waitFor(1000 / txtObj.getPrintSpeed());
                }
                case LINE -> {
                    int counter = 0;
                    while (txtObj.hasText()) {
                        System.out.print(txtObj.poll());
                        waitFor(1000 / txtObj.getPrintSpeed());
                        counter++;
                    }
                }
                case TYPEWRITER -> {
                    int counter = 0;
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
                                    if (i < line.length()) System.out.print(line.substring(j, i + 1));
                                } else {
                                    System.out.print(currentChar);
                                }
                                waitFor(1000 / txtObj.getPrintSpeed());
                            }
                            System.out.print(NEW_LINE);
                        } while (txtObj.hasText());
                    }
                }
            }
        }
    }

    private void printAnimation() {

        if (printQueue.get(0) instanceof DynamicLine) printAnimation((DynamicLine) pollNext());
    }

    private void printAnimation(DynamicLine dynLine) {
        do {
            System.out.print(DELETE_CURRENT_LINE + dynLine.poll());
//                waitFor(dynLine.getDelta());
        } while (dynLine.hasText());
    }

    public void sendToQueue(TextObject txtObj) {
        this.printQueue.add(txtObj);
    }

    public void sendToQueue(TextObject txtObj, int emptyLinesBfr) {
        for (int i = 0; i < emptyLinesBfr; i++) {
            printQueue.add(EMPTY_LINE);
        }
        sendToQueue(txtObj);
    }

    /**
     * Shorthand for Thread.sleep(miliseconds)
     *
     * @param milis time to sleep in miliseconds
     */
    public void waitFor(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends new lines to fill screen and clear last output
     */
    private void clearScreen() {
        sendToQueue(new TextObject(EMPTY_LINE, Scroll.BLOCK, LIMIT_X, LIMIT_Y * 2).alignTextTop());
    }

    private TextObject pollNext() {
        return printQueue.remove(0);
    }

    private boolean queueContainsScroll(Scroll scroll) {
        for (TextObject txtObj : printQueue) {
            if (txtObj.getScroll().equals(scroll)) return true;
        }
        return false;
    }

    //-----------------------------------------------------------------------------------------------------INPUT_METHODS
    private void showErrorLine() {
        var line = new DynamicLine(LIMIT_X, 1, 1, 0, 2);
        line.addText(CColors.BRIGHT_RED + "ERR_   Input not recognized" + TextStyle.RESET);
        line.addText(CColors.BRIGHT_GREEN + " TRY AGAIN " + TextStyle.RESET).alignTextCenter();
        line.addText(CENTER_CARET);
        sendToQueue(line);
        startPrint();
    }

    public String getNameFromInput() {
        String input = "";
        try {
            input = newInput().readLine();
        } catch (Exception e) {
            showErrorLine();
            return getNameFromInput();
        }
        if (input.trim().length() < 3 || !isValidString(input.trim())) {
            showErrorLine();
            return getNameFromInput();
        }
        clearScreen();
        return input;
    }

    public void welcomeNewUser() {
        sendToQueue(new TextObject("Nice to meet you " + game.getUserName(), Scroll.BLOCK, LIMIT_X, LIMIT_Y)
                .setPrintSpeed(1).alignTextCenter().alignTextMiddle());
        waitFor(1000);
    }

    private String getInp() {
        String input;
        var in = newInput();
        try {
            input = in.readLine();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        input = input.replace("\n", "").trim();
        return input;
    }

    private int getIntFromInput(Object[] values) {
        int inputNumber = -1;
        try {
            inputNumber = Integer.parseInt(getInp());
        } catch (Exception e) {
            showErrorLine();
            getIntFromInput(values);
        }
        if (inputNumber < values.length && inputNumber >= 0) return inputNumber;
        showErrorLine();
//        startPrint();
        return getIntFromInput(values);
    }

    private boolean isValidString(String str) {
        str=str.trim();
        if(str.trim().length()==0)return false;
        var chars = str.toCharArray();
        for (char c : chars) if (!(Character.isAlphabetic(c)||c==BLANK_SPACE_CH)) return false;
        return true;

    }

    private BufferedReader newInput() {
        return new BufferedReader(new InputStreamReader(System.in));
    }


}
