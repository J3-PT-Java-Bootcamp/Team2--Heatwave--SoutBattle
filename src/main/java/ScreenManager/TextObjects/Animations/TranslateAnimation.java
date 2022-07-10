package ScreenManager.TextObjects.Animations;

import ScreenManager.TextObjects.TextObject;

import static ScreenManager.ColorFactory.BLANK_SPACE;

public class TranslateAnimation extends AnimationObject {
    @Override
    public TextObject animate(TextObject object, int frames, int max_width) {
            var text = String.valueOf(object.get(0));
//            object.getText().clear();
        int distance= max_width-text.length();
        int frameDist= distance/frames;
        int rest= distance%frames;
            for (int j = 0; j < frames; j++) {
                object.addText(BLANK_SPACE.repeat(j>=frames-rest?frameDist+1:frameDist)+object.get(j));
            }
        return object.setPrintSpeed(10);
    }
}
