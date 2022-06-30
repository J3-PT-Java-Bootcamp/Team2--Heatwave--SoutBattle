import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        testPrinter();
    }

    private static void testPrinter() throws IOException {
        ConsolePrinter printer= new ConsolePrinter();
        printer.splashScreen();
        startMenu(printer);
    }

    private static void startMenu(ConsolePrinter printer) throws IOException {
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