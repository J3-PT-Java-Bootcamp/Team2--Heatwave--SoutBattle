package ScreenManager.TextObjects.Animations;

import ScreenManager.TextObjects.TextObject;

public abstract class AnimationObject {

    public abstract TextObject animate(TextObject object, int frames, int max_width);

}
