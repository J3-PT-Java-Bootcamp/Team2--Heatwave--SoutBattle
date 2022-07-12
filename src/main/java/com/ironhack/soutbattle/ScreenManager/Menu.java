package com.ironhack.soutbattle.ScreenManager;
public enum Menu{
    PLAY("Play Game"),
    NEW_PARTY("Create New Party"),
    ABOUT("Read Me"),
    MEMORIAL("See Graveyard"),
    CALIBRATE("Calibrate Screen"),
    CLEAR_DATA("Clear All Data"),
    EXIT("Exit Game");
    private final String label;

    Menu(String label){
        this.label=label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
