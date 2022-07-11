package Characters;

import java.util.ArrayList;

public class Wizard extends Character {

    private int mana;
    private int intelligence;



    public Wizard(String name, int hp, ArrayList<Character> partyList) {
        super(name, hp, partyList);
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
}
