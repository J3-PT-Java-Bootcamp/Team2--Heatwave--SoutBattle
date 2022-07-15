package com.ironhack.soutbattle.Characters;

import com.google.gson.Gson;
import com.ironhack.soutbattle.GameManager.GameManager;
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
    private int wins;


    //-------------------------------------------------------------------------------------------------------CONSTRUCTOR
    public Party(String name, Boolean isPlayer, ArrayList <String> nameList) {
//        this.game = game;
        this.name = name;
        this.isPlayer = isPlayer;
        gameCharacterList = new ArrayList<>();
        while (!isFull()) {
            addCharacter(createRandomCharacter());
            gameCharacterList.get(gameCharacterList.size() -1);
        }
        this.wins=0;
    }

    /**Constructs a Party from a Json raw String[] and its Characters
     * @see com.ironhack.soutbattle.GameManager.GameData
     * @param rawData array of Character Json Strings(rawData[0] corresponds to Party.name
     */
    public Party(String[] rawData) {
        var aux= rawData[0].split("//");
        this.name = aux[0];
        this.wins= Integer.parseInt(aux[1]);
        this.isPlayer = true;
        gameCharacterList = new java.util.ArrayList<>();
        for (int i = 1; i < rawData.length; i++) {
            var data = rawData[i].split("/CHARACTER/");
            gameCharacterList.add(new Gson().fromJson(data[1], (Objects.equals(data[0], "WARRIOR") ? Warrior.class : Wizard.class)));

        }
    }
    //---------------------------------------------------------------------------------------------------GETTERSnSETTERS

    public int getWins() {
        return wins;
    }

    public Party addWin() {
        this.wins++;
        return this;
    }

    public String getName() {
        return this.name;
    }

    private Boolean isPlayer() {
        return isPlayer;
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


    /**Method that returns a random ALIVE character from list
     * @return random ALIVE character
     */
    public GameCharacter getRandomLiveCharacter() {
        assert (this.hasMembersAlive()); //this method should ony be called if it still has alive members
        Random rand = new Random();
        int n = 0;
        do {
            n = rand.nextInt(this.gameCharacterList.size());
        } while (!gameCharacterList.get(n).isCharacterAlive());
        return gameCharacterList.get(n);
    }

    //-------------------------------------------------------------------------------------------------------------PRINT
    /**Method that returns all the attributes from its fighters aligned
     * @return TextObject with all characters from party aligned in columns to be printed
     */
    public TextObject[] toTextObject() {
        var txt = new TextObject(Scroll.NO, LIMIT_X + 20, LIMIT_Y/2);

        ArrayList<TextObject> fighters = new ArrayList<>();
        for (int i = 0; i < gameCharacterList.size(); i++) {
            fighters.add(gameCharacterList.get(i).toTextObject());
        }

        return fighters.toArray(new TextObject[0]);
    }

    //----------------------------------------------------------------------------------------------------PUBLIC_METHODS
    /**Creates a new Character Randomly
     * @return new Character
     */
    private GameCharacter createRandomCharacter() {
        Random rand = new Random();
        return (rand.nextBoolean() ? new Wizard(rand,this.isPlayer) : new Warrior(rand,this.isPlayer));
    }
    /**Method to check if it remains any character alive
     * @return boolean
     */
    public boolean hasMembersAlive() {
        for (GameCharacter gameCharacter : gameCharacterList) {
            if (gameCharacter.isCharacterAlive()) return true;
        }
        return false;
    }

    /**
     * Method to call from GameManager after each game, checks all party fighters (Characters) isAlive value
     * if dead: sends it to graveyard and creates a new random one on its place
     * otherwise: heals its HP and stamina/mana values
     *
     * @param graveyard the graveyard
     *
     * @return
     */
    public ArrayList<GameCharacter> restoreParty(ArrayList<GameCharacter> graveyard, ArrayList<String> namesList){
//        var resArr = new ArrayList<GameCharacter>();
        GameCharacter currentChar;
        for(int i = 0; i > gameCharacterList.size(); i++){
            currentChar = this.gameCharacterList.get(i);
            if(currentChar.isAlive()){
                currentChar.heal();
                currentChar.recoverVarAttribute();
            }
            else{
                graveyard.add(currentChar);
                this.gameCharacterList.remove(currentChar);
                GameCharacter aux = createRandomCharacter();

                gameCharacterList.add(aux);
                GameManager.checkName(aux);
            }
        }
//        graveyard.addAll(resArr); //<---- characters added to "resArr" will go to graveyard.
        return graveyard;
    }

    /**
     * Method to be called from GameManager when all members from a Party are dead (so when player lose)
     * it adds all characters to graveyard
     *
     * @param graveyard ArrayList<GameCharacters> where to save the dead fighters
     *
     * @return Party itself to permit using return value as parameter
     * to the ArrayList.remove() method on GameManager.parties
     */
    public ArrayList<GameCharacter> defeatParty(ArrayList<GameCharacter> graveyard){
        graveyard.addAll(this.gameCharacterList);//MUST ADD all characters to send them to graveyard
        return graveyard;
    }

    public ArrayList<GameCharacter> getAliveFighters() {
        var resVal= new ArrayList<GameCharacter>();
        for (int i = 0; i < MAX_FIGHTERS; i++) {
            if(getCharacter(i).isCharacterAlive())resVal.add(getCharacter(i));
        }
        resVal.add(null);
        return resVal;
    }
}
