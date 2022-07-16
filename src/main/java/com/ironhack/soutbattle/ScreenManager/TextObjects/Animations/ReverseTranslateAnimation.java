package com.ironhack.soutbattle.ScreenManager.TextObjects.Animations;

import com.ironhack.soutbattle.ScreenManager.ColorFactory;

public class ReverseTranslateAnimation extends AnimationObject {
    @Override
    public void animate(com.ironhack.soutbattle.ScreenManager.TextObjects.TextObject object, int frames, int max_width) {
        String text = null;
        try {
            text = String.valueOf(object.get(0));
        } catch (Exception e) {
            throw new RuntimeException(e);
            //TODO HANDLE
        }
        int distance = max_width - object.countValidCharacters(text);
        object.getText().set(0, ColorFactory.BLANK_SPACE.repeat(distance) + text);
        int frameDist = distance / frames;
        int rest = distance % frames;
        for (int j = 1; j <= frames; j++) {
            object.addText(ColorFactory.BLANK_SPACE.repeat(distance - frameDist * j) + text);
        }
        object.addText(text);
        object.setPrintSpeed(10);
    }
}
