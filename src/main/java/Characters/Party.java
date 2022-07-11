package Characters;

import ScreenManager.TextObjects.TextObject;

import java.util.ArrayList;
import java.util.Random;

import static ScreenManager.PrinterConstants.*;

public class Party {
    private ArrayList<Character> characterList;


    private String name;

    public Party( String name) {
        this.name=name;
        characterList = new ArrayList<>();
        while (!isFull()) {
            addCharacter(getRandomCharacter());

        }
    }

    private Character getRandomCharacter() {

        Random rand= new Random();
        return (rand.nextBoolean()? new Wizard(this.characterList,rand): new Warrior(this.characterList,rand));
    }


    public ArrayList<Character> getCharacterList() {
        return characterList;
    }

    public void addCharacter(Character fighter) {

        if (!isFull()) {
            characterList.add(fighter);
        }

    }

    public boolean isFull() {
        return characterList.size() >= MAX_FIGHTERS;
    }

    public Character getCharacter(int i) {
        return this.characterList.get(i);

    }

    public TextObject toTextObject(){
    var txt = new TextObject(TextObject.Scroll.BLOCK,LIMIT_X, LIMIT_Y);

    ArrayList<TextObject> fighters = new ArrayList<>();

        for (int i = 0; i <characterList.size() ; i++) {
            fighters.add(characterList.get(i).toTextObject());
        }
        txt.addGroupAligned(characterList.size(),
                LIMIT_X,
                (TextObject[]) fighters.toArray()
        );

        return txt;
    }



    public String getName() {
        return this.name;
    }
}
