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

        TEXT_BOLD("[001m"),
        TEXT_UNDERLINE("[004m"),
        TEXT_BLINK("[005m"),
        TEXT_REVERSED("[007m"),
        TEXT_RESET( "[000m");
        final String label;
        TextStyle(String label){
            this.label=label;
        }

        @Override
        public String toString() {
            return COLOR_CHAR+label;
        }

    }
    public enum CColors {
         TEXT_BLACK( "[030m"),

         TEXT_RED( "[031m"),

         TEXT_GREEN( "[032m"),

         TEXT_YELLOW( "[033m"),

         TEXT_BLUE( "[034m"),

         TEXT_PURPLE( "[035m"),

         TEXT_CYAN( "[036m"),

         TEXT_WHITE( "[037m"),

         TEXT_BRIGHT_BLACK( "[090m"),

         TEXT_BRIGHT_RED( "[091m"),

         TEXT_BRIGHT_GREEN( "[092m"),

         TEXT_BRIGHT_YELLOW( "[093m"),

         TEXT_BRIGHT_BLUE( "[094m"),

         TEXT_BRIGHT_PURPLE( "[095m"),

         TEXT_BRIGHT_CYAN( "[096m"),

         TEXT_BRIGHT_WHITE( "[097m"),

         TEXT_BG_BLACK( "[040m"),

         TEXT_BG_RED( "[041m"),

         TEXT_BG_GREEN( "[042m"),

         TEXT_BG_YELLOW( "[043m"),

         TEXT_BG_BLUE( "[044m"),

         TEXT_BG_PURPLE( "[045m"),

         TEXT_BG_CYAN( "[046m"),

         TEXT_BG_WHITE( "[047m"),

         TEXT_BRIGHT_BG_BLACK( "[100m"),

         TEXT_BRIGHT_BG_RED( "[101m"),

         TEXT_BRIGHT_BG_GREEN( "[102m"),

         TEXT_BRIGHT_BG_YELLOW( "[103m"),

         TEXT_BRIGHT_BG_BLUE( "[104m"),

         TEXT_BRIGHT_BG_PURPLE( "[105m"),

         TEXT_BRIGHT_BG_CYAN( "[106m"),

         TEXT_BRIGHT_BG_WHITE( "[107m");
        final String label;
            CColors(String label){
                this.label=label;
            }

        @Override
        public String toString() {
            return COLOR_CHAR+label;
        }
    }

    public static String getRandomColor(){
        int num=new Random().nextInt(ScreenManager.ColorFactory.CColors.values().length);
        return CColors.values()[num].toString();
    }

    public static String changeColors(String text,CColors...newColors){
        var charList= text.toCharArray();
        boolean isRandom = true;
        for (int i = 0; i < charList.length; i++) {
            if (i< newColors.length) isRandom=false;
            var currentChar=charList[i];
            if(charList[i]==COLOR_CHAR){
                var color = (isRandom?getRandomColor():newColors[i].toString()).toCharArray();
                System.arraycopy(color, 0, charList, i + 0, COLOR_LABEL_CHAR_SIZE - 1);
                i+=COLOR_LABEL_CHAR_SIZE-1;
            }
        }
        return new String(charList);

    }

}
