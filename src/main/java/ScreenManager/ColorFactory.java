package ScreenManager;

import java.util.Random;

public class ColorFactory {

    public static final char DELETE_CURRENT_LINE='\r';
    public static final char NEW_LINE_CH = '\n';
    public static final String NEW_LINE = "\n";
    public static final char BLANK_SPACE_CH = ' ';
    public static final String BLANK_SPACE = " ";
    public static final String TAB_SPACE = "    ";

    public static final int COLOR_LABEL_CHAR_SIZE=6;
    public static final char COLOR_CHAR='\u001B';
    public enum TextStyle{

        BOLD("[001m"),
        UNDERLINE("[004m"),
        BLINK("[005m"),
        REVERSED("[007m"),
        RESET( "[000m");
        final String label;
        TextStyle(String label){
            this.label=label;
        }

        @Override
        public String toString() {
            return COLOR_CHAR+label;
        }

    }
    public enum BgColors{
        BLACK( "[040m"),

        RED( "[041m"),

        GREEN( "[042m"),

        YELLOW( "[043m"),

        BLUE( "[044m"),

        PURPLE( "[045m"),

        CYAN( "[046m"),

        WHITE( "[047m"),

        BRIGHT_BLACK( "[100m"),

        BRIGHT_RED( "[101m"),

        BRIGHT_GREEN( "[102m"),

        BRIGHT_YELLOW( "[103m"),

        BRIGHT_BLUE( "[104m"),

        BRIGHT_PURPLE( "[105m"),

        BRIGHT_CYAN( "[106m"),

        BRIGHT_WHITE( "[107m");
        final String label;
        BgColors(String label){
            this.label=label;
        }

        @Override
        public String toString() {
            return COLOR_CHAR+label;
        }

    }
    public enum CColors {
         BLACK( "[030m"),

         RED( "[031m"),

         GREEN( "[032m"),

         YELLOW( "[033m"),

         BLUE( "[034m"),

         PURPLE( "[035m"),

         CYAN( "[036m"),

         WHITE( "[037m"),

         BRIGHT_BLACK( "[090m"),

         BRIGHT_RED( "[091m"),

         BRIGHT_GREEN( "[092m"),

         BRIGHT_YELLOW( "[093m"),

         BRIGHT_BLUE( "[094m"),

         BRIGHT_PURPLE( "[095m"),

         BRIGHT_CYAN( "[096m"),

         BRIGHT_WHITE( "[097m");

         
        final String label;
            CColors(String label){
                this.label=label;
            }

        @Override
        public String toString() {
            return COLOR_CHAR+label;
        }
    }

    public static CColors getRandomColor(){
        int num;
        boolean noColor;
        do {
            num = new java.util.Random().nextInt(CColors.values().length);
            noColor = num == ScreenManager.ColorFactory.CColors.BLACK.ordinal()
                    || num == ScreenManager.ColorFactory.CColors.BRIGHT_BLACK.ordinal()
                    || num == ScreenManager.ColorFactory.CColors.BRIGHT_WHITE.ordinal()
                    || num == ScreenManager.ColorFactory.CColors.WHITE.ordinal();
        } while (noColor);
        return CColors.values()[num];
    }

    public static String changeColors(String text,CColors...newColors){
        var charList= text.toCharArray();
        boolean isRandom = true;
        for (int i = 0; i < charList.length; i++) {
            if (i< newColors.length) isRandom=false;
            var currentChar=charList[i];
            if(charList[i]==COLOR_CHAR){
                var color = (isRandom?getRandomColor().toString():newColors[i].toString()).toCharArray();
                System.arraycopy(color, 0, charList, i, COLOR_LABEL_CHAR_SIZE - 1);
                i+=COLOR_LABEL_CHAR_SIZE-1;
            }
        }
        return new String(charList);

    }

}
