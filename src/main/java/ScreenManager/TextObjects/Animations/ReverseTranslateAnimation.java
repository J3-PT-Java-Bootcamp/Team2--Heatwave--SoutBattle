package ScreenManager.TextObjects.Animations;

import static ScreenManager.ColorFactory.BLANK_SPACE;

public class ReverseTranslateAnimation extends AnimationObject {
    @Override
    public ScreenManager.TextObjects.TextObject animate(ScreenManager.TextObjects.TextObject object, int frames, int max_width) {
            var text = String.valueOf(object.get(0));
//            object.getText().clear();
        int distance= max_width-text.length();
        object.getText().set(0,BLANK_SPACE.repeat(distance)+text);
        int frameDist= distance/frames;
        int rest= distance%frames;
            for (int j = 1; j <= frames; j++) {
                object.addText(BLANK_SPACE.repeat(distance-frameDist*j)+text);
            }
            object.addText(text);
        return object.setPrintSpeed(10);
    }
}
