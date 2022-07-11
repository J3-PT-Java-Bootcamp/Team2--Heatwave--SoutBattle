package Characters;

import java.util.ArrayList;
import java.util.UUID;

public class Warrior extends Character {

    private int stamina;
    private int strength;


    public Warrior(String name, int hp, ArrayList<Character> partyList) {
        super(name, hp, partyList);
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
