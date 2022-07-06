package ScreenManager.TextObjects;

public class DynamicLine extends ScreenManager.TextObjects.TextObject {

    int speed;//in frames per second
    int repeat;
    int duration;
    int currentFrame;
    ScreenManager.TextObjects.Animations.AnimationObject[] animations;

    DynamicLine(int maxWidth, int maxHeight, int speed, int repeat, int duration, ScreenManager.TextObjects.Animations.AnimationObject... animations) {
        super(maxWidth, maxHeight);
        setSpeed(speed);
        setRepeat(repeat);
        this.animations=animations;
        this.currentFrame=0;
        this.duration=duration;
    }

    protected int calculateFrames(int duration){
        return speed*duration;
    }

    protected void constructAnimation(){
        for (ScreenManager.TextObjects.Animations.AnimationObject anim : animations){
            anim.animate(this,calculateFrames(duration),MAX_WIDTH);
        }
    }


    public int getDelta() {
        return 1000/speed;
    }

    private DynamicLine setSpeed(int speed) {
        this.speed = speed;
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
}
