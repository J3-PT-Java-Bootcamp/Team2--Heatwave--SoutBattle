package ScreenManager.TextObjects;
import ScreenManager.TextObjects.Animations.*;

import static ScreenManager.ColorFactory.*;

public class DynamicLine extends ScreenManager.TextObjects.TextObject {

    int delay;//milliseconds
    int repeat;
    int duration;
    int currentFrame;
    AnimationObject[] animations;

    public DynamicLine(int maxWidth, int maxHeight, int delay, int repeat, int duration, AnimationObject... animations) {
        super(Scroll.LINE, maxWidth, maxHeight);
        setDelay(delay);
        setRepeat(repeat);
        this.animations=animations;
        this.currentFrame=0;
        this.duration=duration;
    }

    protected int calculateFrames(int duration){
        return delay*duration;
    }

    protected void constructAnimation(){
        for (AnimationObject anim : animations){
            anim.animate(this,calculateFrames(duration),MAX_WIDTH);
        }
    }



    private DynamicLine setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    private int getRepeat() {
        return repeat;
    }

    private DynamicLine setRepeat(int repeat) {
        this.repeat = repeat;
        return this;
    }

    private int getCurrentFrame() {
        return currentFrame;
    }

    @Override
    public String poll() {
        currentFrame++;
        return super.poll();
    }
    @Override
    public String print(){
        return DELETE_CURRENT_LINE + poll();
    }
}
