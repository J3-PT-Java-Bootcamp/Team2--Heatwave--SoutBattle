package ScreenManager.TextObjects;

import ScreenManager.ColorFactory.*;
import org.jetbrains.annotations.NotNull;

import java.awt.Point;

import static ScreenManager.ColorFactory.BLANK_SPACE;

public class WindowObject extends TextObject{
    Point padding, borderSize,margin;
    String[] pattern;
    String title;
    BgColors bgColor;
    String[] horizontalPattern;
    String[] verticalPattern;
    WindowObject(int maxWidth, int maxHeight, Point padding, Point borderSize, Point margin, String ... pattern){
        super(maxWidth-(padding.x+borderSize.x+margin.x),
                maxHeight-(padding.y+borderSize.y+margin.y));
        this.pattern=pattern;
        this.padding=padding;
        this.borderSize=borderSize;
        this.margin=margin;
        //generateBorder(pattern);
    }

    WindowObject(int maxWidth, int maxHeight, int padding, int borderSize, int margin, String ... pattern){
        super(maxWidth-(padding+borderSize+margin),
                maxHeight-(padding+borderSize+margin));
        this.pattern=pattern;
        this.padding=new Point(padding,padding);
        this.borderSize=new Point(borderSize,borderSize);
        this.margin=new Point(margin,margin);
        //generateBorder(pattern);
    }

    private String generateBorder(boolean isVertical, boolean starts) {
        var sb = new StringBuilder();
        if(isVertical) {
            if(starts?margin.x>0:padding.x>0)sb.append(BLANK_SPACE.repeat(margin.x));
            if(borderSize.x>0&&pattern.length>0){
                for (int i = 0; i < borderSize.x; i++) {
                    sb.append(pattern[starts?i:borderSize.x-1]);
                }
            }
            if(starts?margin.x>0:padding.x>0)sb.append(BLANK_SPACE.repeat(margin.x));
            sb.append(TextStyle.RESET).append(bgColor);
            return sb.toString();
        }
        //TODO horizontal case
        return "";
    }

    public WindowObject setTitle(String title) {
        this.title = title;
        return this;
    }

    public WindowObject setBgColor(BgColors bgColor) {
        this.bgColor = bgColor;
        return this;
    }
}
