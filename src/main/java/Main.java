import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        testPrinter();

    }

    private static void testPrinter() throws Exception {
        var game = new GameManager();
        game.startGame();
    }


}