package Characters;

import ScreenManager.TextObjects.TextObject;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.Random;

import static ScreenManager.PrinterConstants.WARRIOR_IMG;
import static ScreenManager.PrinterConstants.WIZARD_IMG;

public class Wizard extends Character {

    private int mana;

    private int intelligence;
    private final int MAX_MANA;

    public Wizard(String name, int hp, ArrayList<Character> partyList, int mana, int intelligence ) {
        super(name, hp, partyList, WARRIOR_IMG);
        this.mana=mana;
        this.MAX_MANA= mana;
        this.intelligence=intelligence;
    }

    public Wizard(ArrayList<Character> partyList, Random rand) {
        super(Faker.instance().gameOfThrones().character(),rand.nextInt(50,100),partyList, WARRIOR_IMG);
        this.intelligence= rand.nextInt(1,50);
        this.MAX_MANA= rand.nextInt(10,50);
        this.mana= MAX_MANA;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    @Override
    TextObject getAttributes(TextObject textObj) {
          return textObj.addText("Mana:"+this.mana+"/"+this.MAX_MANA).addText("Intelligence"+this.intelligence);
        
    }
}
