import java.io.IOException;

public class GameManager {
    private final ConsolePrinter printer;

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
                    printer.printChooseCharacter(new ConsolePrinter.Party(new String[]{"fighter1","fighter2"}));
            case NEW_TEAM -> System.out.println("TODO - CREATE A NEW TEAM SCREEN");//TODO CREATE A NEW TEAM SCREEN
            case ABOUT -> System.out.println("This is the Read Me & instructions");//TODO CREATE A README SCREEN
            case MEMORIAL -> System.out.println("IN MEMORIAM");//TODO CREATE MEMORIAL
            case CALIBRATE -> printer.calibrateScreen();
            case CLEAR_DATA -> System.out.println("DELETE ALL");//TODO CLEAR DATA
            case EXIT -> System.exit(0);
            default -> System.out.println("FATAL ERROR!_ This should never happen");
        }
    }
}
