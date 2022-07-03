import ScreenManager.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameManager {
    public final ScreenManager.ConsolePrinter printer;

    public GameManager() {
        printer= new ScreenManager.ConsolePrinter();
    }
    void startGame() throws Exception {
        printer.splashScreen();
        startMenu(printer);
    }
    private void startMenu(ScreenManager.ConsolePrinter printer) throws Exception {
        switch (printer.showMenu(false)){
            case PLAY ->
                    System.out.println("LETS PLAY");
//                    printer.chooseCharacter(new ScreenManager.ConsolePrinter.Party(new String[]{"fighter1","fighter2"}));
            case NEW_PARTY -> System.out.println("TODO - CREATE A NEW PARTY SCREEN");//TODO CREATE A NEW TEAM SCREEN
            case ABOUT -> System.out.println("This is the Read Me & instructions");//TODO CREATE A README SCREEN
            case MEMORIAL -> System.out.println("IN MEMORIAM");//TODO CREATE MEMORIAL
            case CALIBRATE -> {
                printer.calibrateScreen();
                startMenu(printer);
            }
            case CLEAR_DATA -> System.out.println("DELETE ALL");//TODO CLEAR DATA
            case EXIT -> System.exit(0);
            default -> System.out.println("FATAL ERROR!_ This should never happen");
        }
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
    private void loadData(){}
    private void saveData(){}
    private void gameOver() throws Exception {
        printer.printGameOver(true);
        startMenu(printer);

    }


    public static ArrayList<String> loadData(String fileName) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(fileName));
        ArrayList<String> tempArray = new ArrayList<String>();
        while (sc.hasNext())
        {
            tempArray.add(sc.nextLine());
        }
        sc.close();
        return tempArray;
    }
    public static void writeToFile(ArrayList<String> arr, String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);

        for(int i = 0; i<arr.size(); i++){
            writer.write(arr.get(i) + '\n');
        }
        writer.close();
    }
}
