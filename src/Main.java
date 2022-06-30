import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        testPrinter();
    }

    private static void testPrinter() throws IOException {
        var game = new GameManager();
        game.startGame();
    }


}