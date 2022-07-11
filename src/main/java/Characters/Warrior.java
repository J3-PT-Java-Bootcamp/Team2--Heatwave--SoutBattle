package Characters;

import ScreenManager.TextObjects.TextObject;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import static ScreenManager.PrinterConstants.WARRIOR_IMG;

public class Warrior extends Character {

    private int stamina;
    private int strength;
    private final int MAX_STAMINA;

    public Warrior(String name, int hp, ArrayList<Character> partyList, int stamina, int strength ) {
        super(name, hp, partyList, WARRIOR_IMG);
        this.stamina=stamina;
        this.MAX_STAMINA= stamina;
        this.strength=strength;
    }
    public Warrior(ArrayList<Character> partyList, Random rand) {
        super(Faker.instance().gameOfThrones().character(),rand.nextInt(100,200),partyList, WARRIOR_IMG);
        this.strength= rand.nextInt(1,10);
        this.MAX_STAMINA= rand.nextInt(10,50);
        this.stamina= MAX_STAMINA;



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

    @Override
    TextObject getAttributes(TextObject textObj) {
        return textObj.addText("Strength: "+this.strength).addText("HP: "+this.getHp()+"/"+getMAX_HP()+" "+"Stmna:"+this.stamina+"/"+this.MAX_STAMINA);
    }
}
