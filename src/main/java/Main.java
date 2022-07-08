import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        testPrinter();

    }

    private static void testPrinter() {
        var game = new GameManager();
        try {
            game.startGame();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        testPrinter();
    }


}