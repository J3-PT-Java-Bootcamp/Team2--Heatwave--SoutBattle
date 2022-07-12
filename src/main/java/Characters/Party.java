package Characters;

import ScreenManager.TextObjects.TextObject;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;
import static ScreenManager.PrinterConstants.*;

public class Party {
    //--------------------------------------------------------------------------------------------------------ATTRIBUTES
    private ArrayList<Characters.Character> characterList;
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
    public Party(String[] rawData){

        this.name=rawData[0];
        this.isPlayer=true;
        characterList=new java.util.ArrayList<>();
        for (int i = 1; i < rawData.length; i++) {
            var data= rawData[i].split("/CHARACTER/");
            characterList.add(new Gson().fromJson(data[1], (java.util.Objects.equals(data[0], "WARRIOR") ?Warrior.class:Wizard.class)));

        }
    }
    //---------------------------------------------------------------------------------------------------GETTERSnSETTERS
    private Character getRandomCharacter() {

        Random rand= new Random();
        return (rand.nextBoolean()? new Wizard(this,rand): new Warrior(this,rand));
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
    public java.util.ArrayList<Character> getCharacterList() {
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
        var txt = new TextObject(TextObject.Scroll.NO,LIMIT_X+20, LIMIT_Y);

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

    public boolean hasMembersAlive() {
        for (Character character:characterList){
            if(character.isCharacterAlive())return true;
        }
        return false;
    }

    public Characters.Character getRandomLiveCharacter() {
        Random rand= new Random();
        int n=0;
        do {
            n=rand.nextInt(this.characterList.size());
        }while(!characterList.get(n).isCharacterAlive());
        return characterList.get(n);
    }


//    public String saveParty(Gson gson) {
//        var sb= new StringBuilder();
//        sb.append(name);
//        for(Character ch: characterList)sb.append("///"+ch.saveCharacter(gson));
//        return sb.toString();
//    }
}
