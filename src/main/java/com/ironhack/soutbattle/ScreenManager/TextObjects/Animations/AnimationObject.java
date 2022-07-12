package com.ironhack.soutbattle.ScreenManager.TextObjects.Animations;

import com.ironhack.soutbattle.ScreenManager.TextObjects.TextObject;

public abstract class AnimationObject {

    public abstract TextObject animate(TextObject object, int frames, int max_width);

}
