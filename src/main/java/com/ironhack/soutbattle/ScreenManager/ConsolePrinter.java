package com.ironhack.soutbattle.ScreenManager;

import com.ironhack.soutbattle.Characters.GameCharacter;
import com.ironhack.soutbattle.Characters.Party;
import com.ironhack.soutbattle.GameManager.FightReport;
import com.ironhack.soutbattle.GameManager.GameManager;
import com.ironhack.soutbattle.ScreenManager.ColorFactory.CColors;
import com.ironhack.soutbattle.ScreenManager.TextObjects.DynamicLine;
import com.ironhack.soutbattle.ScreenManager.TextObjects.TextObject;
import com.ironhack.soutbattle.ScreenManager.TextObjects.WindowObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.ironhack.soutbattle.ScreenManager.ColorFactory.*;
import static com.ironhack.soutbattle.ScreenManager.PrinterConstants.*;
import static com.ironhack.soutbattle.ScreenManager.TextObjects.TextObject.Scroll;

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
//            numberTextObject.alignTextRight();
//            nameTextObject.fillAllLines();
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

    public void showMemorial() {

//       Warrior trufa = new Warrior("Trufa",123, null,30, 10,false);
////        sendToQueue(trufa.toTextObject());
//
//        Party team1 = new Party("Equipo1", true);
//        sendToQueue(team1.toTextObject());

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

    public Party chooseParty(ArrayList<Party> parties) {
        int col = (int) Math.ceil(parties.size() / 10.0);
        int charLimit=  (int) Math.floor(col>2?LIMIT_X / col:LIMIT_X/2);
        TextObject[] txtObjs = new TextObject[col];
        for (int i = 0; i < txtObjs.length; i++) {

            txtObjs[i] = new TextObject(Scroll.BLOCK,
                 charLimit , 1);
        }
        int objIndex = 0;
        String backName=" : 0 --> Back To Menu";
        txtObjs[0].addText(backName+BLANK_SPACE.repeat(charLimit - txtObjs[0].countValidCharacters(backName)-2)+BLANK_SPACE);
        for (int i = 1; i < parties.size()+1; i++) {
            String name= parties.get(i-1).getName();
            if ( txtObjs[i/10].countValidCharacters(name)>=charLimit-14) {
                txtObjs[i / 10].addText(" :" + (i >= 10 ? i : " " + i) + " --> "
                        + name.substring(0, charLimit - 13) + ".. ");
            }else {
                txtObjs[i / 10].addText(" :" + (i >= 10 ? i : " " + i) + " --> " + name
                        + BLANK_SPACE.repeat(charLimit - txtObjs[i/10].countValidCharacters(name)-10));
            }
        }
        var finalTxtObj = new TextObject(HEADER,Scroll.BLOCK,LIMIT_X, LIMIT_Y);
        finalTxtObj.addText("------- PARTY SELECTION -------")
                .addText(BLANK_SPACE.repeat(2))
                .addText(BLANK_SPACE.repeat(2));
        if (txtObjs.length > 1) finalTxtObj.addGroupAligned(txtObjs.length, LIMIT_X, txtObjs);
        else finalTxtObj.addText(txtObjs[0].alignTextRight()).alignTextCenter();
        clearScreen();
        sendToQueue(finalTxtObj.alignTextTop().colorizeAllText().alignTextCenter());
        sendToQueue(new TextObject("Select a party to play", Scroll.NO, LIMIT_X, LIMIT_Y)
                .alignTextCenter().addText(CENTER_CARET));

        startPrint();
        parties.add(null);
        int resVal=getIntFromInput(parties.toArray());
        parties.remove(parties.size()-1);
        if (resVal==0) return null;
        return parties.get(resVal-1);//TODO CHANGE THIS METHOD TO ALLOW CANCEL AND NEW PARTY OPTION
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

    public GameCharacter chooseCharacter(Party party) {
        var fullTxtObj= new TextObject(HEADER,TextObject.Scroll.NO,LIMIT_X,LIMIT_Y)
                .addText("------ Choose Fighter ------").stylizeAllText(TextStyle.BOLD)
                .addText("Enter 0 to Go Back")
                .alignTextCenter()
                .colorizeAllText()
                .addGroupAligned(MAX_FIGHTERS,LIMIT_X,party.toTextObject());
        var txtObjArr = new TextObject[MAX_FIGHTERS];
        int j=1;
        for (int i = 0; i < MAX_FIGHTERS; i++) {
            txtObjArr[i] = new TextObject(party.getCharacter(i).isCharacterAlive()?"-" + j + "-":"RIP ",
                    Scroll.NO, (LIMIT_X / MAX_FIGHTERS) - 1, 1)
                    .alignTextCenter();
            j++;

        }
        fullTxtObj.addText(new TextObject(Scroll.NO, LIMIT_X, 1)
                .addGroupAligned(MAX_FIGHTERS, LIMIT_X, txtObjArr).alignTextTop().alignTextCenter().addText(CENTER_CARET));
        sendToQueue(fullTxtObj);
        startPrint();
        var parties = java.util.Arrays.copyOf(party.getCharacterList().toArray(new GameCharacter[0]),MAX_FIGHTERS+1);
        int resVal=getIntFromInput(parties);
        if (resVal==0) return null;
        return party.getCharacter(resVal-1);
    }

    public void printFight(FightReport report) {

    }

    public void printGameOver(Boolean playerWins) {
        //TODO print game over screen (model depends on player winning or not)
    }

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
        //TODO
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
    private void waitFor(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);//TODO report to log
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
