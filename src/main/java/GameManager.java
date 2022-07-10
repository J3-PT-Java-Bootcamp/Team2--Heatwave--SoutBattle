import ScreenManager.*;
import ScreenManager.ConsolePrinter.Party;
import com.google.gson.Gson;

import java.io.*;
import java.util.EmptyStackException;

public class GameManager {
    private final ConsolePrinter printer;
    private String[] graveyard;
    private Party[] parties;
    private Party playerParty, enemyParty;
    private GameData gameData;
    Gson gson;
    private String userName;

    //-----------------------------------------------------------------------------------------------------------CONSTRUCTOR
    public GameManager() {
        gson = new Gson();
        printer = new ConsolePrinter();
        printer.splashScreen();
        try {
            loadData();
        } catch (Exception e) {
            this.gameData = new GameData();
            this.userName = printer.askUserName();
        }
    }

//-------------------------------------------------------------------------------------------------------------GAME_FLOW

    void startGame() throws Exception {
        startMenu(printer);
    }

    private void startMenu(ConsolePrinter printer) throws Exception {
        switch (printer.showMenu(false)){
            case PLAY -> System.out.println("LETS PLAY");
//                    printer.chooseCharacter(new ScreenManager.ConsolePrinter.Party(new String[]{"fighter1","fighter2"}));
            case NEW_PARTY -> createNewParty();
            case ABOUT -> printer.readMe();
            case MEMORIAL -> printer.showMemorial();
            case CALIBRATE -> printer.calibrateScreen();
            case CLEAR_DATA -> clearAllData();
            case EXIT -> closeGame();
        }
        startMenu(printer);
    }

    private void closeGame() throws Exception {
        if (printer.confirmationNeeded("Do you want to close game?")) {
            saveData();
            printer.goodBye(userName);
            System.exit(0);
        } else {
            startMenu(printer);
        }
    }
    private void createNewParty() {
        printer.newPartyScreen(null);
        var brandNewParty=new Party();
        printer.newPartyScreen(brandNewParty);
    }

    private void playGame(){
//        //TODO implement all dependencies
//        this.playerParty=printer.chooseParty(new ScreenManager.ConsolePrinter.Party[]{new ScreenManager.ConsolePrinter.Party(new String[]{"a","b"})});
//        if(this.playerParty.missingCharacters()){
//            this.playerParty.recruitCharacters(printer);
//        }
//        Character playerFighter,enemyFighter;
//        FightLog fightLog = new FightLog();//Special class where to save all fight data to send it to printer
//        do {
//            playerFighter=printer.chooseCharacter(playerParty);//waits until user chooses a fighter
//            enemyFighter=enemyParty.getRandomCharacter();
//            fightLog.process(playerFighter.attack(enemyFighter),true); //returns fight results ordered
//            fightLog.process(enemyFighter.attack(playerFighter),false);//boolean isPlayerAttack
//            printer.printFight(fightLog);
//
//        }while(playerParty.hasAliveCharacters()&&enemyParty.hasAliveCharacters());
//        gameOver();
    }
    private void gameOver() throws Exception {
        printer.printGameOver(true);
        startMenu(printer);

    }

    //-------------------------------------------------------------------------------------------------------LOAD & SAVE
    private void loadData() throws Exception {
        var reader = new FileReader("gameData.json");
        gameData = gson.fromJson(reader, GameData.class);
        this.userName = gameData.getUserName();
        this.parties = gameData.getParties();
        this.graveyard = gameData.getGraveyard();
    }

    private void saveData() {
        if (this.userName != null) {
            try {
                var writer = new FileWriter("gameData.json");
                writer.write(gson.toJson(updateGameData()));
                writer.close();
            } catch (java.io.IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private GameData updateGameData() {
        gameData.setGraveyard(this.graveyard);
        gameData.setParties(this.parties);
        gameData.setUserName(this.userName);
        return gameData;
    }

    private void clearAllData() throws Exception {
        if (printer.confirmationNeeded("Are you sure to clear all saved data? ")) {
            this.userName = null;
            this.parties = null;
            this.graveyard = null;
            var file = new java.io.File("gameData.json");
            if (file.delete()) {
                System.out.println("File deleted successfully");
            } else {
                System.out.println("Failed to delete the file, trying to let it in blank");
                var writer = new FileWriter(file);
                writer.write("");
                writer.close();
            }
            throw new EmptyStackException();
        }
    }
}
