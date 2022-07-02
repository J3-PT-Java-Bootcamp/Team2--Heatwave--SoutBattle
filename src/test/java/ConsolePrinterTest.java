import static org.junit.jupiter.api.Assertions.*;

class ConsolePrinterTest {

    @org.junit.jupiter.api.Test
    void test_sendToPrint_exception() throws ScreenManager.TextOutOfScreenException {
        assertThrows(ScreenManager.TextOutOfScreenException.class, () -> {
            new ScreenManager.ConsolePrinter().sendToPrint("x".repeat(200));
        });

    }
}
