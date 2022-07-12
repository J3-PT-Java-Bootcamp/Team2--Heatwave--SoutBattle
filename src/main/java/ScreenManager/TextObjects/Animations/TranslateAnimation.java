package ScreenManager.TextObjects.Animations;

import ScreenManager.TextObjects.TextObject;

import static ScreenManager.ColorFactory.BLANK_SPACE;

public class TranslateAnimation extends AnimationObject {
    @Override
    public TextObject animate(TextObject object, int frames, int max_width) {
        String text = null;
        try {
            text = String.valueOf(object.get(0));
        } catch (Exception e) {
            throw new RuntimeException(e);
            //TODO HANDLE
        }
        int distance= max_width-object.countValidCharacters(text);
        int frameDist= distance/frames;
        int rest= distance%frames;
            for (int j = 0; j < frames; j++) {
                try {
                    object.addText(BLANK_SPACE.repeat(j>=frames-rest?frameDist+1:frameDist)+object.get(j));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                    //TODO HANDLE
                }
            }
        return object.setPrintSpeed(10);
    }
}
