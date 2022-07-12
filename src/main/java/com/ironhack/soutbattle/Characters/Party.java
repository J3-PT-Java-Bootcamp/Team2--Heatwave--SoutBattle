package com.ironhack.soutbattle.Characters;

import com.google.gson.Gson;
import com.ironhack.soutbattle.ScreenManager.TextObjects.TextObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static com.ironhack.soutbattle.ScreenManager.PrinterConstants.*;
import static com.ironhack.soutbattle.ScreenManager.TextObjects.TextObject.Scroll;

public class Party {
    //--------------------------------------------------------------------------------------------------------ATTRIBUTES
    private final ArrayList<GameCharacter> gameCharacterList;
    //    private GameManager.GameManager game;
    private final String name;
    private Boolean isPlayer;


    //-------------------------------------------------------------------------------------------------------CONSTRUCTOR
    public Party(String name, Boolean isPlayer) {
//        this.game = game;
        this.name = name;
        this.isPlayer = isPlayer;
        gameCharacterList = new ArrayList<>();
        while (!isFull()) {
            addCharacter(getRandomCharacter());

        }
    }

    public Party(String[] rawData) {

        this.name = rawData[0];
        this.isPlayer = true;
        gameCharacterList = new java.util.ArrayList<>();
        for (int i = 1; i < rawData.length; i++) {
            var data = rawData[i].split("/CHARACTER/");
            gameCharacterList.add(new Gson().fromJson(data[1], (Objects.equals(data[0], "WARRIOR") ? Warrior.class : Wizard.class)));

        }
    }

    //---------------------------------------------------------------------------------------------------GETTERSnSETTERS
    private GameCharacter getRandomCharacter() {

        Random rand = new Random();
        return (rand.nextBoolean() ? new Wizard(this, rand) : new Warrior(this, rand));
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

    public ArrayList<GameCharacter> getCharacterList() {
        return gameCharacterList;
    }

    public GameCharacter getCharacter(int i) {
        return this.gameCharacterList.get(i);

    }

    public void addCharacter(GameCharacter fighter) {
        if (!isFull()) {
            gameCharacterList.add(fighter);
        }
    }

    public boolean isFull() {
        return gameCharacterList.size() >= MAX_FIGHTERS;
    }

    //-------------------------------------------------------------------------------------------------------------PRINT
    public TextObject toTextObject() {
        var txt = new TextObject(Scroll.NO, LIMIT_X + 20, LIMIT_Y);

        ArrayList<TextObject> fighters = new ArrayList<>();
//
//        for (int i = 0; i <gameCharacterList.size() ; i++) {
//            txt.addText(gameCharacterList.get(i).toTextObject());
//        }
//
//        return txt;

        for (int i = 0; i < gameCharacterList.size(); i++) {
            fighters.add(gameCharacterList.get(i).toTextObject());
        }

        return txt.addGroupAligned(MAX_FIGHTERS, LIMIT_X, fighters.toArray(new TextObject[0]));
    }

    public boolean hasMembersAlive() {
        for (GameCharacter gameCharacter : gameCharacterList) {
            if (gameCharacter.isCharacterAlive()) return true;
        }
        return false;
    }

    public GameCharacter getRandomLiveCharacter() {
        Random rand = new Random();
        int n = 0;
        do {
            n = rand.nextInt(this.gameCharacterList.size());
        } while (!gameCharacterList.get(n).isCharacterAlive());
        return gameCharacterList.get(n);
    }


//    public String saveParty(Gson gson) {
//        var sb= new StringBuilder();
//        sb.append(name);
//        for(GameCharacter ch: gameCharacterList)sb.append("///"+ch.saveCharacter(gson));
//        return sb.toString();
//    }
}
