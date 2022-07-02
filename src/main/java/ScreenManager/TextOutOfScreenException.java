package ScreenManager;

public class TextOutOfScreenException extends Exception{
    String originalText;
    int line;

    public TextOutOfScreenException(String message, String originalText, int line) {
        super(message);
        this.originalText = originalText;
        this.line= line;
    }

    private String getOriginalText() {
        return originalText;
    }
    private int getLine() {
        return line;
    }
}
