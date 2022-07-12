package com.ironhack.soutbattle.ScreenManager.TextObjects.Animations;

import com.ironhack.soutbattle.ScreenManager.ColorFactory;
import com.ironhack.soutbattle.ScreenManager.TextObjects.TextObject;

public class RainbowAnimation extends AnimationObject {
    @Override
    public TextObject animate(TextObject object, int framesPerLine, int max_width) {
        for (int i = 0; i < object.getTotalHeight(); i++) {
            String text = null;
            try {
                text = String.valueOf(object.get(i));
            } catch (Exception e) {
                throw new RuntimeException(e);
                //TODO HANDLE
            }
            for (int j = 0; j < framesPerLine; j++) {
                object.getText().set(i, ColorFactory.rainbowCharacters(text, j));
            }
        }
        return object;
    }
}
