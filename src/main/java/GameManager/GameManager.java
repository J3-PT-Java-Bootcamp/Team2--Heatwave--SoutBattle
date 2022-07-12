package GameManager;

import Characters.Party;
import ScreenManager.ConsolePrinter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.EmptyStackException;

public class GameManager {
    private final ConsolePrinter printer;
    private static ArrayList<Characters.Character> graveyard;
    private ArrayList<Characters.Party> parties;
    private Party playerParty, enemyParty;
    private Characters.Character currentPlayer,currentEnemy;
    private GameData gameData;
    Gson gson;
    private String userName;

    //-----------------------------------------------------------------------------------------------------------CONSTRUCTOR
    public GameManager() {
        gson = new GsonBuilder().create();
        this.parties=new ArrayList<>();
        graveyard=new java.util.ArrayList<>();
        printer = new ConsolePrinter(this);
        printer.splashScreen();
        try {
            loadData();
        } catch (Exception e) {
            this.gameData = new GameData();
            this.userName = printer.askUserName();
            saveData();
        }
        if(this.userName==null){
            this.gameData = new GameData();
            this.userName = printer.askUserName();
            printer.welcomeNewUser();

            saveData();
        }else {
            printer.helloUser(userName);
        }
    }

    public static void addToGraveyard(Characters.Character character) {
        graveyard.add(character);
    }

//-------------------------------------------------------------------------------------------------------------GAME_FLOW

    public void startGame() throws Exception {
        startMenu(printer);
    }

    private void startMenu(ConsolePrinter printer) throws Exception {
        saveData();
        switch (printer.showMenu(false)){
            case PLAY ->playGame();
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
            printer.goodBye(userName);
            System.exit(0);
        } else {
            startMenu(printer);
        }
    }
    public void createNewParty() {
        this.parties.add(new Party(printer.newPartyScreen(), true));
        playGame();
    }

    private void playGame(){
        this.playerParty=printer.chooseParty(parties);
        this.enemyParty=new Party(net.datafaker.Faker.instance().rockBand().name(),false);
//        while (playerParty.hasMembersAlive()&&enemyParty.hasMembersAlive()){
            currentPlayer=printer.chooseCharacter(playerParty);
             currentEnemy=enemyParty.getRandomLiveCharacter();
//        }


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
        var reader = new FileReader("gameData.txt");
        this.gameData=gson.fromJson(reader,GameData.class);
        this.userName=gameData.userName;
        this.parties=gameData.deserializeParties();
        this.graveyard=gameData.deserializeGraveyard();


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
        return gameData.serializeGraveyard(this.graveyard).serializeParties(this.parties).setUserName(this.userName);
    }

    private void clearAllData() throws Exception {
        if (printer.confirmationNeeded("Are you sure to clear all saved data? ")) {
            this.userName = null;
            this.parties = new ArrayList<>();
            graveyard = new java.util.ArrayList<>();
            this.gameData=new GameData();
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
