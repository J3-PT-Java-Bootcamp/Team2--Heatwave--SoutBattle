import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        testPrinter();
        ArrayList<String> muertosArray = testLoadData();
        testWriteToFile(muertosArray);
    }

    private static void testPrinter() throws Exception {
        var game = new GameManager();
        game.startGame();
    }

    public static ArrayList<String> testLoadData() throws IOException {
        String filename = "graveyard.csv";
        ArrayList<String> muertosArray = GameManager.loadData(filename);
//        for(int i = 0; i < muertosArray.size(); i++){
//            System.out.println(muertosArray.get(i));
//        }
        return muertosArray;
    }
    private static void testWriteToFile(ArrayList<String> arr) throws IOException {
        String filename = "graveyard.csv";
        GameManager.writeToFile(arr, filename);
       }

}