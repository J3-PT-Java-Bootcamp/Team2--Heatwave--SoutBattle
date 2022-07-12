package com.ironhack.soutbattle.Characters;

import com.ironhack.soutbattle.ScreenManager.TextObjects.TextObject;
import java.util.ArrayList;
import java.util.Random;
import static com.ironhack.soutbattle.ScreenManager.PrinterConstants.*;

public class Party {
    //--------------------------------------------------------------------------------------------------------ATTRIBUTES
    private ArrayList<Character> characterList;
//    private GameManager.GameManager game;
    private String name;
    private Boolean isPlayer;


    //-------------------------------------------------------------------------------------------------------CONSTRUCTOR
    public Party(String name, Boolean isPlayer) {
//        this.game = game;
        this.name=name;
        this.isPlayer = isPlayer;
        characterList = new ArrayList<>();
        while (!isFull()) {
            addCharacter(getRandomCharacter());

        }
    }

    //---------------------------------------------------------------------------------------------------GETTERSnSETTERS
    private Character getRandomCharacter() {

        Random rand= new Random();
        return (rand.nextBoolean()? new Wizard(this.characterList,rand): new Warrior(this.characterList,rand));
    }
    public String getName() {
        return this.name;
    }
    private Boolean getPlayer() {
        return isPlayer;
    }
    private Party setPlayer(Boolean player) {
        isPlayer = player;
        return this;
    }
    public ArrayList<Character> getCharacterList() {
        return characterList;
    }
    public Character getCharacter(int i) {
        return this.characterList.get(i);

    }
    public void addCharacter(Character fighter) {
        if (!isFull()) {
            characterList.add(fighter);
        }
    }
    public boolean isFull() {
        return characterList.size() >= MAX_FIGHTERS;
    }
    //-------------------------------------------------------------------------------------------------------------PRINT
    public TextObject toTextObject(){
        var txt = new TextObject(TextObject.Scroll.BLOCK,LIMIT_X+20, LIMIT_Y);

        ArrayList<TextObject> fighters = new ArrayList<>();
//
//        for (int i = 0; i <characterList.size() ; i++) {
//            txt.addText(characterList.get(i).toTextObject());
//        }
//
//        return txt;

        for (int i = 0; i <characterList.size() ; i++) {
            fighters.add(characterList.get(i).toTextObject());
        }

        return txt.addGroupAligned(MAX_FIGHTERS,LIMIT_X,fighters.toArray(new TextObject[0]));
    }


}
