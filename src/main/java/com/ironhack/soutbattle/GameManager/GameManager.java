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

import static com.ironhack.soutbattle.GameManager.RomanNumber.toRoman;

public class GameManager {
    private ArrayList<GameCharacter> graveyard;
    private final ConsolePrinter printer;
    Gson gson;
    private ArrayList<Party> parties;
    private Party playerParty, enemyParty;
    private GameCharacter currentPlayer, currentEnemy;
    private GameData gameData;
    private String userName;
    public static java.util.HashMap<String,Integer> usedNames;
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
            usedNames=new java.util.HashMap<>();
            this.gameData = new GameData();
            this.userName = printer.askUserName();
            printer.welcomeNewUser();
            saveData();
        }
        if (this.userName == null) {
            usedNames=new java.util.HashMap<>();
            this.gameData = new GameData();
            this.userName = printer.askUserName();
            printer.welcomeNewUser();

            saveData();
        }
    }

    public static String checkName(String name) {
        if(usedNames.containsKey(name)) {
            usedNames.put(name, usedNames.get(name) + 1);
            name+=toRoman(usedNames.get(name));
        }else {
            usedNames.put(name,1);
        }
        return name;
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
                case ABOUT ->  {
                    gameOver();            //  printer.readMe();
                }
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
        saveData();
        try {
            playGame();
        } catch (GoBackException e) {
            startMenu(printer);
        }
    }
    //Executes a play loop until one team wins, if there isn't any party, starts with party creation
    private void playGame() throws Exception {
        if(this.parties.size()==0)createNewParty();//If there is no party, first create new party,
                                                    // createNewParty() ---> callsback to playGame()
        else {
            this.playerParty = printer.chooseParty(parties);//RETURNS THE CHOSEN PARTY
            if (this.playerParty==null) throw new GoBackException();//==>GO BACK index returns a null value
            playerParty.restoreParty(graveyard);
            this.enemyParty = new Party(Faker.instance().rockBand().name(), false);//Create random enemyParty

            while (playerParty.hasMembersAlive()&&enemyParty.hasMembersAlive()){
                    currentPlayer = printer.chooseCharacter(playerParty);//RETURNS CHOSEN CHARACTER
                while (currentPlayer==null){
                    if(printer.confirmationNeeded("Do you want to exit current battle?\n Damage on "+playerParty.getName()+" party won't be undone")) {
                        this.playerParty.restoreParty(graveyard);
                        this.playerParty = printer.chooseParty(parties);//RETURNS THE CHOSEN PARTY
                        if (this.playerParty==null) throw new GoBackException();//==>GO BACK index returns a null value
                        currentPlayer = printer.chooseCharacter(playerParty);//RETURNS CHOSEN CHARACTER
                    } else printer.chooseCharacter(playerParty);
                };
                currentEnemy = enemyParty.getRandomLiveCharacter();//get random enemy alive fighter;
                var report=new FightReport(printer,this,currentPlayer,currentEnemy);
                int playerBonus=0;
                int enemyBonus=0;
                do{
                    report.newRound(currentPlayer,currentEnemy);
                    currentPlayer.attack(currentEnemy,report.getCurrentRound());
                    currentEnemy.attack(currentPlayer,report.getCurrentRound());
                    if(currentPlayer.bonusRecovery(playerBonus))playerBonus=0;

                    if(currentEnemy.bonusRecovery(enemyBonus))enemyBonus =0;
                }while (currentPlayer.isCharacterAlive()&&currentEnemy.isCharacterAlive());
                printer.printFight(report);
                if (currentPlayer.isCharacterAlive()) currentPlayer.healPartially();
                else currentEnemy.healPartially();


                currentPlayer=null;
                currentEnemy=null;
            }

            gameOver();
        }
    }
    //Executes logic before game ends, after it goues to startMenu() again
    private void gameOver() throws Exception {
        var playerWins= playerParty.hasMembersAlive();
        printer.printGameOver(playerWins);

        if (playerWins){
            playerParty.addWin();
            this.graveyard=playerParty.restoreParty(graveyard);
        }else{
            this.graveyard=this.playerParty.defeatParty(graveyard);
            this.parties.remove(playerParty);
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
        usedNames=gameData.usedNames;
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
        return gameData.serializeGraveyard(graveyard).serializeParties(this.parties).setUserName(this.userName).setUsedNames(usedNames);
    }

    private void clearAllData() throws Exception {
        if (printer.confirmationNeeded("Are you sure to clear all saved data? ")) {
            this.userName = null;
            this.parties = new ArrayList<>();
            graveyard = new ArrayList<>();
            this.gameData = new GameData();
            this.userName = printer.askUserName();
            saveData();

        }
    }

    public String getUserName() {
        return userName;
    }
}
