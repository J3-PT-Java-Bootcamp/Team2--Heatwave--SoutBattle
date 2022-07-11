package ScreenManager.TextObjects.Animations;

import ScreenManager.ColorFactory;
import ScreenManager.TextObjects.TextObject;

public class RainbowAnimation extends AnimationObject{
    @Override
    public TextObject animate(TextObject object, int framesPerLine, int max_width) {
        for (int i = 0; i < object.getTotalHeight(); i++) {
            var text = String.valueOf(object.get(i));
            for (int j = 0; j < framesPerLine; j++) {
                object.getText().set(i, ColorFactory.rainbowCharacters(text, j));
            }
        }
        return object;
    }
}
