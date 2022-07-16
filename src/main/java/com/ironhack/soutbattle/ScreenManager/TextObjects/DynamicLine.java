package com.ironhack.soutbattle.ScreenManager.TextObjects;


import com.ironhack.soutbattle.ScreenManager.TextObjects.Animations.AnimationObject;

import static com.ironhack.soutbattle.ScreenManager.ColorFactory.DELETE_CURRENT_LINE;

public class DynamicLine extends TextObject {

    int delay;//milliseconds
    int currentFrame;
    final AnimationObject[] animations;

    public DynamicLine(int maxWidth, int maxHeight, int delay, AnimationObject... animations) {
        super(Scroll.LINE, maxWidth, maxHeight);
        setDelay(delay);
        this.animations = animations;
        this.currentFrame = 0;
    }

    protected int calculateFrames(int duration) {
        return delay * duration;
    }

    public void constructAnimation() {
        for (AnimationObject anim : animations) {
            anim.animate(this, 20, MAX_WIDTH);
        }
    }


    private void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public String poll() {
        currentFrame++;
        return DELETE_CURRENT_LINE + text.remove(0);
    }

    @Override
    public String print() {
        return DELETE_CURRENT_LINE + poll();
    }
}
