import java.io.IOException;

public class GameManager {
    private final ConsolePrinter printer;
    private ConsolePrinter.Party playerParty;

    public GameManager() {
        printer= new ConsolePrinter();
    }
    void startGame() throws IOException {
        printer.splashScreen();
        startMenu(printer);
    }
    private void startMenu(ConsolePrinter printer) throws IOException {
        switch (printer.showMenu(false)){
            case PLAY ->
                    printer.chooseCharacter(new ConsolePrinter.Party(new String[]{"fighter1","fighter2"}));
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
//        this.playerParty=printer.chooseParty(new ConsolePrinter.Party[]{new ConsolePrinter.Party(new String[]{"a","b"})});
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
    private void gameOver() throws IOException {
        printer.printGameOver(true);
        startMenu(printer);

    }
}
