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
    private ArrayList<GameCharacter> graveyard;
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
            printer.helloUser(userName);
        } catch (Exception e) {
            this.gameData = new GameData();
            this.userName = printer.askUserName();
            printer.welcomeNewUser();
            saveData();
        }
        if (this.userName == null) {
            this.gameData = new GameData();
            this.userName = printer.askUserName();
            printer.welcomeNewUser();

            saveData();
        }
    }

    public void addToGraveyard(GameCharacter gameCharacter) {
        graveyard.add(gameCharacter);
    }

//-------------------------------------------------------------------------------------------------------------GAME_FLOW

    public void startGame() throws Exception {
        startMenu(printer);
    }
    //START MENU LOOP, ANY EXCEPTION WILL RERUN THIS FUNCTION
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

    //CREATES A NEWPARTY AND GOES TO playGame()
    public void createNewParty() throws Exception {
        this.parties.add(new Party(printer.newPartyScreen(), true));
        try {
            playGame();
        } catch (GoBackException e) {
            startMenu(printer);
        }
    }
    //Executes a play loop until one team wins, if there isn't any party, starts with party creation
    private void playGame() throws Exception {
        int securityExit=0;
        if(this.parties.size()==0)createNewParty();//If there is no party, first create new party,
                                                    // createNewParty() ---> callsback to playGame()
        else {
            this.playerParty = printer.chooseParty(parties);//RETURNS THE CHOSEN PARTY
            if (this.playerParty==null) throw new GoBackException();//==>GO BACK index returns a null value
            this.enemyParty = new Party(Faker.instance().rockBand().name(), false);//Create random enemyParty
            while (playerParty.hasMembersAlive()&&enemyParty.hasMembersAlive()){
                currentPlayer = printer.chooseCharacter(playerParty);//RETURNS CHOSEN CHARACTER
                if(currentPlayer==null) throw new GoBackException();//==>GO BACK index returns a null value
                currentEnemy = enemyParty.getRandomLiveCharacter();//get random enemy alive fighter;
                var report=new FightReport(printer,this,currentPlayer,currentEnemy);
                do{
                    report.newRound(currentPlayer,currentEnemy);
                    currentPlayer.attack(currentEnemy,report.getCurrentRound());
                    currentEnemy.attack(currentPlayer,report.getCurrentRound());
                    if (securityExit>10000)break;
                    securityExit++;
                }while (currentPlayer.isCharacterAlive()&&currentEnemy.isCharacterAlive());
                printer.printFight(report);

                if (securityExit>10000)break;
                securityExit++;
            }
            if (securityExit>10000) {
                System.err.println("ERR_FIGHT ROUND COUNTER IS BIGGER THAN 9999\n WE CANNOT MANAGE ALMOST ETERNAL FIGHTS\nABORTING FIGHT...");
                printer.waitFor(1000);
                throw new Exception();
            }
            securityExit++;
            gameOver();
        }
    }
    //Executes logic before game ends, after it goues to startMenu() again
    private void gameOver() throws Exception {
        var playerWins= playerParty.hasMembersAlive();
        printer.printGameOver(playerWins);
        //TODO Send dead characters to graveyard
        this.enemyParty=null;
        if (playerWins){
            playerParty.restoreParty(graveyard);
            //TODO heal survivors and recruit new characters
        }else{
            this.parties.remove(playerParty);
            this.playerParty=null;
            //Delete party from parties
        }
        saveData();
        startMenu(printer);

    }

    private void closeGame() throws Exception {
        if (printer.confirmationNeeded("Do you want to close game?")) {
            printer.goodBye(userName);
            System.exit(0);
        } else {
            startMenu(printer);
        }
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
