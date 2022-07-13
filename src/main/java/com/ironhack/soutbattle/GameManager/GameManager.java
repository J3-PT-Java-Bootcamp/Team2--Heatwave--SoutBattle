package com.ironhack.soutbattle.GameManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ironhack.soutbattle.Characters.GameCharacter;
import com.ironhack.soutbattle.Characters.Party;
import com.ironhack.soutbattle.ScreenManager.ConsolePrinter;
import com.ironhack.soutbattle.ScreenManager.GoBackException;
import net.datafaker.Faker;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.EmptyStackException;

public class GameManager {
    private static ArrayList<GameCharacter> graveyard;
    private final ConsolePrinter printer;
    Gson gson;
    private ArrayList<Party> parties;
    private Party playerParty, enemyParty;
    private GameCharacter currentPlayer, currentEnemy;
    private GameData gameData;
    private String userName;

    //-----------------------------------------------------------------------------------------------------------CONSTRUCTOR
    public GameManager() {
        gson = new GsonBuilder().create();
        this.parties = new ArrayList<>();
        graveyard = new ArrayList<>();
        printer = new ConsolePrinter(this);
        printer.splashScreen();
        try {
            loadData();
        } catch (Exception e) {
            this.gameData = new GameData();
            this.userName = printer.askUserName();
            saveData();
        }
        if (this.userName == null) {
            this.gameData = new GameData();
            this.userName = printer.askUserName();
            printer.welcomeNewUser();

            saveData();
        } else {
            printer.helloUser(userName);
        }
    }

    public static void addToGraveyard(GameCharacter gameCharacter) {
        graveyard.add(gameCharacter);
    }

//-------------------------------------------------------------------------------------------------------------GAME_FLOW

    public void startGame() throws Exception {
        startMenu(printer);
    }

    private void startMenu(ConsolePrinter printer)  {
        saveData();
        try {
            switch (printer.showMenu(false)) {
                case PLAY -> playGame();
                case NEW_PARTY -> createNewParty();
                case ABOUT -> printer.readMe();
                case MEMORIAL -> printer.showMemorial(graveyard);
                case CALIBRATE -> printer.calibrateScreen();
                case CLEAR_DATA -> clearAllData();
                case EXIT -> closeGame();
            }
            startMenu(printer);
        }catch (Exception e){
            startMenu(printer);
        }
    }

    private void closeGame() throws Exception {
        if (printer.confirmationNeeded("Do you want to close game?")) {
            printer.goodBye(userName);
            System.exit(0);
        } else {
            startMenu(printer);
        }
    }

    public void createNewParty() throws Exception {
        this.parties.add(new Party(printer.newPartyScreen(), true));
        try {
            playGame();
        } catch (GoBackException e) {
            startMenu(printer);
        }
    }

    private void playGame() throws Exception {
        if(this.parties.size()==0)createNewParty();
        else {
            START:
            this.playerParty = printer.chooseParty(parties);
            if (this.playerParty==null) throw new GoBackException();
            this.enemyParty = new Party(Faker.instance().rockBand().name(), false);
//        while (playerParty.hasMembersAlive()&&enemyParty.hasMembersAlive()){
            currentPlayer = printer.chooseCharacter(playerParty);
            if(currentPlayer==null) throw new GoBackException();
            currentEnemy = enemyParty.getRandomLiveCharacter();
            System.out.println(currentPlayer.toFightTxtObj());
            printer.waitFor(1000);
//        }


//        if(this.playerParty.missingCharacters()){
//            this.playerParty.recruitCharacters(printer);
//        }
//        GameCharacter playerFighter,enemyFighter;
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
    }

    private void gameOver() throws Exception {
        printer.printGameOver(true);
        startMenu(printer);

    }

    //-------------------------------------------------------------------------------------------------------LOAD & SAVE
    private void loadData() throws Exception {
        var reader = new FileReader("gameData.txt");
        this.gameData = gson.fromJson(reader, GameData.class);
        this.userName = gameData.userName;
        this.parties = gameData.deserializeParties();
        graveyard = gameData.deserializeGraveyard();


    }

    public void saveData() {

        try {
            var writer = new FileWriter("gameData.txt");
            updateGameData();
            writer.write(gson.toJson(gameData));

            writer.close();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }

    }

    private GameData updateGameData() {
        return gameData.serializeGraveyard(graveyard).serializeParties(this.parties).setUserName(this.userName);
    }

    private void clearAllData() throws Exception {
        if (printer.confirmationNeeded("Are you sure to clear all saved data? ")) {
            this.userName = null;
            this.parties = new ArrayList<>();
            graveyard = new ArrayList<>();
            this.gameData = new GameData();
            var file = new java.io.File("gameData.txt");
            if (file.delete()) {
                System.out.println("File deleted successfully");
            } else {
                System.out.println("Failed to delete the file, trying to let it in blank");
                saveData();
            }
            throw new EmptyStackException();
        }
    }

    public String getUserName() {
        return userName;
    }
}
