package com.ironhack.soutbattle.ScreenManager.TextObjects;

import com.ironhack.soutbattle.ScreenManager.ColorFactory.BgColors;
import com.ironhack.soutbattle.ScreenManager.ColorFactory.CColors;
import com.ironhack.soutbattle.ScreenManager.ColorFactory.TextStyle;

import java.awt.*;

import static com.ironhack.soutbattle.ScreenManager.ColorFactory.BLANK_SPACE;
import static com.ironhack.soutbattle.ScreenManager.ColorFactory.NEW_LINE;

public class WindowObject extends TextObject{
    final Point borderSize;
    final Point margin;
    String[] pattern;
    String title;
    final int windowWidth;
    final int windowHeight;
    private BgColors frameColor,bgColor;
    private CColors txtColor,titleColor;

    WindowObject(int maxWidth, int maxHeight, Point padding, Point borderSize, Point margin, String ... pattern){
        super(Scroll.BLOCK, maxWidth-((padding.x+borderSize.x+margin.x)*2),
                maxHeight-(padding.y+borderSize.y+margin.y));
        this.pattern=pattern;
        this.borderSize=borderSize;
        this.margin=margin;
        this.windowHeight=maxHeight;
        this.windowWidth=maxWidth;
        //generateBorder(pattern);
    }

    public WindowObject(int maxWidth, int maxHeight, int borderSize, int margin, String... pattern){
        super(Scroll.BLOCK, maxWidth-((borderSize+margin)*2),
                maxHeight-(borderSize+margin));
        this.pattern=pattern;
        this.borderSize=new Point(borderSize,borderSize);
        this.margin=new Point(margin,margin);
        this.windowHeight=maxHeight;
        this.windowWidth=maxWidth;
        //generateBorder(pattern);
    }

    private String generateBorder(boolean isVertical, boolean starts) {
        adjustPattern();
        var sb = new StringBuilder();
        if(isVertical) {
            if(starts&&margin.x>0)sb.append(BLANK_SPACE.repeat(margin.x));
            if (!starts)sb.append(TextStyle.RESET);
            if(frameColor!=null)sb.append(frameColor);
            if(borderSize.x>0&&pattern.length>0){
                for (int i = 0; i < borderSize.x; i++) {
                    sb.append(pattern[starts?i:borderSize.x-i-1]);
                }
                sb.append(TextStyle.RESET);
                if(starts && bgColor!=null)sb.append(bgColor);
                if(starts&&txtColor!=null)sb.append(txtColor);
            }
            if(!starts&&margin.x>0)sb.append(BLANK_SPACE.repeat(margin.x));
            if(!starts)sb.append(TextStyle.RESET).append(NEW_LINE);
            return sb.toString();
        }
        if(starts){
            if(margin.y>0)sb.append((BLANK_SPACE.repeat(windowWidth-(margin.x*2))+NEW_LINE).repeat(margin.y));
        }
        if(borderSize.y>0&&pattern.length>0){
            for (int i = 0; i < borderSize.y; i++) {
                sb.append(BLANK_SPACE.repeat(margin.x));
                if(frameColor!=null)sb.append(frameColor);
                if (title!=null&&(title.length() > 0) && (borderSize.y > 1) && starts && (i == 0)){
                    int fillLeft= windowWidth-(margin.x*2)-title.length();
                    int fillRight= ((fillLeft % 2) == 0) ? (fillLeft / 2) : ((fillLeft / 2) + 1);
                    fillLeft/=2;
                    sb.append(pattern[i].repeat(fillLeft)).append(titleColor!=null?titleColor:"").append(TextStyle.BOLD).append(title).append(TextStyle.RESET)
                            .append(frameColor!=null?frameColor:"").append(pattern[i].repeat(fillRight));
                }else {
                    sb.append(pattern[starts ? i : borderSize.y - i-1].repeat(windowWidth-(margin.x*2)));
                }
                sb.append(TextStyle.RESET).append(BLANK_SPACE.repeat(margin.x)).append(NEW_LINE);
            }
        }
        return sb.toString();
    }

    private void adjustPattern() {
        int size= Math.max(borderSize.x, borderSize.y);
        int aux= pattern.length-size;
        if(aux<0){
            var newPattern= new String[size];
            for (int i = 0; i < size; i++) {
                newPattern[i] = (i < pattern.length) ? (pattern[i].equals("") ? BLANK_SPACE : pattern[i]) : " ";
            }
            pattern=newPattern.clone();
        }else{
            for (int i = 0; i < pattern.length; i++) {
                if (pattern[i].equals("")) pattern[i] += BLANK_SPACE;
            }
        }
    }

    public WindowObject setTitle(String title) {
        this.title = title;
        return this;
    }

    public WindowObject setBgColor(BgColors bgColor) {
        this.bgColor = bgColor;
        return this;
    }
    public WindowObject setTxtColor(CColors color){
        this.txtColor=color;
        return this;
    }
    public WindowObject setTitleColor(CColors color){
        this.titleColor=color;
        return this;
    }

    public WindowObject setFrameColor(BgColors frameColor) {
        this.frameColor = frameColor;
        return this;
    }
    @Override
    public String print(){
        var sb= new StringBuilder();
        sb.append(generateBorder(false,true));
        while (hasText()) {
            sb.append(generateBorder(true,true))
                    .append(fillLine(poll())).append(generateBorder(true,false));
        }
        sb.append(generateBorder(false,false));
        sb.append(BLANK_SPACE.repeat(windowWidth / 2));
        return sb.toString();
    }

}
