package ScreenManager;

import java.util.ArrayList;

import static ScreenManager.ColorFactory.*;
import static java.lang.String.valueOf;

public class TextObject{
    ArrayList<String> text;
    private final int MAX_WIDTH,MAX_HEIGHT;
    private int totalWidth,totalHeight;
    //-------------------------------------------------------------------------------------------------------CONSTRUCTOR
    public TextObject(String text, int maxWidth,int maxHeight){
        MAX_WIDTH=maxWidth;
        MAX_HEIGHT=maxHeight;
        this.text=new java.util.ArrayList<>();
//        setTotalWidth(0);
//        addText(text);
    }
    public TextObject(int maxWidth,int maxHeight){
        MAX_WIDTH=maxWidth;
        MAX_HEIGHT=maxHeight;
        this.text=new java.util.ArrayList<>();
    }
    public TextObject(String[] textLines, int maxWidth,int maxHeight) {
        MAX_WIDTH=maxWidth;
        MAX_HEIGHT=maxHeight;
        this.text= new ArrayList<>(java.util.List.of(textLines));
    }
    public TextObject(TextObject txtObject, int maxWidth,int maxHeight) {
        MAX_WIDTH=maxWidth;
        MAX_HEIGHT=maxHeight;
        this.text=new java.util.ArrayList<>();
        addText(txtObject);
    }

    //---------------------------------------------------------------------------------------------------Getters&Setters
    public ArrayList<String> getText() {
        return text;
    }
    public int getTotalWidth() {
        return totalWidth;
    }
    private TextObject setTotalWidth(int totalWidth) {
        this.totalWidth = Math.max(totalWidth,getTotalWidth());
        return this;
    }
    public int getTotalHeight() {
        return totalHeight;
    }
    private TextObject setTotalHeight() {
        this.totalHeight = getText().size();
        return this;
    }
    public boolean hasText(){
        return getTotalHeight()>0;
    }
    public String get(int index){
        return index<getTotalHeight()?text.get(index):"NO SUCH INDEX";
    }
    public String poll(){
        return text.remove(0);
    }
    //------------------------------------------------------------------------------------------------------------ADDERS

    public TextObject addText(String text){
        return addText(splitTextInLines(text));
    }
    public TextObject addText(String[] lines){
        for (String line : lines) {
            addSimpleLine(line);
        }
        return this;
    }
    public TextObject addText(TextObject txtObject){
        for (int i = 0; i < txtObject.getTotalHeight(); i++) {
            addSimpleLine(txtObject.get(i));
        }
        return this;
    }
    public TextObject addGroupMerged(TextObject[] txtCollection){
        return this;
    }
    public TextObject addGroupAligned(int numberOfColumns, int totalSize, TextObject [] columnsContent) {
        int charLimit = (numberOfColumns > 1 ? (totalSize / numberOfColumns) : totalSize) - numberOfColumns;
        int totalLines = 0;
        for (TextObject textColumn : columnsContent) {
            if(textColumn.getTotalWidth()>charLimit)textColumn=textColumn.getResizedText(charLimit,MAX_HEIGHT);
            totalLines = Math.max(totalLines, textColumn.getTotalHeight());
        }
        for (int j = 0; j < totalLines; j++) {
            var strBuilder = new StringBuilder();
            for (int i = 0; i < numberOfColumns; i++) {
                String currentVal;
                try {
                    currentVal = columnsContent[i].get(j);
                } catch (Exception e) {
                    currentVal = BLANK_SPACE.repeat(charLimit);
                }
                strBuilder.append(BLANK_SPACE).append(currentVal);
//                if (countValidCharacters(currentVal) < charLimit) {
//                    strBuilder.append(" ".repeat(charLimit - countValidCharacters(columnsContent[i].get(j))));
//                }
            }
            addText(strBuilder.toString());
        }
        return this;
    }



    //-----------------------------------------------------------------------------------------------------INNER_METHODS

    //===================   LINE_MANIPULATION   ===================\\
    //REAL ADD_LINE METHOD, IT WRAPS IN 2 LINES IF IT EXCEEDS MAX_WIDTH
    private void addSimpleLine(String line) {
        int sizeCounter;
        sizeCounter=countValidCharacters(line);
        if (sizeCounter>MAX_WIDTH) {
            var group= wrapLine(line,MAX_WIDTH);
            for(String wrapLine:group)addSafe(wrapLine);
        }
        else {
            setTotalWidth(sizeCounter);
            addSafe(line);
        }
    }
    //Only method that can add a new element to text attribute
    private void addSafe(String line){
        this.text.add(fillLine(line.replaceAll(NEW_LINE,"")));
        setTotalHeight();
    }
    private void addSafe(int index,String line){
        this.text.add(index,fillLine(line.replaceAll(NEW_LINE,"")));
        setTotalHeight();
    }
    private int countValidCharacters(String line){
        int colourCount=0;
        int charCount=0;
        var chArray=line.toCharArray();
        for (char ch:chArray){
            charCount++;
            if (ch==COLOR_CHAR)colourCount++;
            if(ch== NEW_LINE_CH)charCount--;
        }
        return charCount-(colourCount*6);
    }
    private String[] wrapLine(String line,int limit) {

        var wordList = line.replace(BLANK_SPACE+BLANK_SPACE,"__").split(BLANK_SPACE);
        StringBuilder line1=new StringBuilder();
        StringBuilder line2=new StringBuilder();
        int spaceCounter=0;
        int charCounter= 0;
        for (String word : wordList) {
            if (java.util.Objects.equals(word, "__")) spaceCounter++;
            charCounter += countValidCharacters(word) + (java.util.Objects.equals(word, "__") ? 0 : 1);
            if (charCounter <= limit) line1.append(" ").append(word.replace("__", "  "));
            else line2.append(" ").append(word.replace("__", "  "));
        }
        if(charCounter>limit*2){
            var auxList= wrapLine(line2.toString(),limit);
            var resVal= new String[auxList.length+1];
            resVal[0]=line1.toString();
            System.arraycopy(auxList, 0, resVal, 1, auxList.length);
            return resVal;
        }else return new String[]{line1.toString(),line2.toString()};
    }
    private String fillLine(String line){
        return fillLine(line,MAX_WIDTH);
    }
    private String fillLine(String line,int width){
        return line+(BLANK_SPACE.repeat(Math.max(width-countValidCharacters(line), 0)));
    }
    private String lineToRight(String line) {
        return lineToRight(line,MAX_WIDTH);
    }
    private String lineToRight(String line,int width) {
        int count=width-countValidCharacters(line);
        return (count>0?BLANK_SPACE.repeat(count):"" )+line;
    }
    private String centerLine(String line){
        return centerLine(line,MAX_WIDTH);
    }
    private String centerLine(String line,int width){
        int leftSpace,rightSpace,remainSpace;
        remainSpace = width - countValidCharacters(line);
        leftSpace = remainSpace / 2;
        rightSpace = (remainSpace % 2 == 0) ? leftSpace : leftSpace + 1;
        return " ".repeat(leftSpace)+line+" ".repeat(rightSpace);
    }

    //===================   TEXT_MANIPULATION   ===================

    /** Splits a multi line string into array of String lines
     * @param text multi line text
     * @return String Lines Array
     */
    private String[] splitTextInLines(String text){
        return text.split(NEW_LINE);
    }



    //----------------------------------------------------------------------------------------------------PUBLIC_METHODS
    public TextObject getResizedText(int newWidth,int newHeight){
            return new TextObject(this,newWidth,newHeight);
    }
    public TextObject alignTextMiddle(){
        int remainingLines= MAX_HEIGHT - getTotalHeight();
        int num;
        if (remainingLines>1){
            num=Math.floorDiv(remainingLines, 2);
            for (int i = 0; i < num; i++) {
                addSafe(0, BLANK_SPACE);
                addSafe(BLANK_SPACE);
            }
        }
        return this;
    }
    public TextObject alignTextTop(){
        if(getTotalHeight()<MAX_HEIGHT){
            int missingLines=MAX_HEIGHT-getTotalHeight();
            for (int i = 0; i < missingLines; i++) {
                addSafe(BLANK_SPACE);
            }
        }
        return this;
    }
    public TextObject alignTextRight(){
        for (int i = 0; i < totalHeight; i++) {
            text.set(i,lineToRight(text.get(i)));
        }
        return this;
    }
    public TextObject alignTextCenter(){
        for (int i = 0; i < totalHeight; i++) {
            text.set(i,centerLine(text.get(i)));
        }
        return this;
    }


}
