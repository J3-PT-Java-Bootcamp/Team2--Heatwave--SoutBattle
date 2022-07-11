package Characters;

import ScreenManager.TextObjects.TextObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.UUID;

import static ScreenManager.PrinterConstants.*;
import static ScreenManager.PrinterConstants.LIMIT_Y;

public abstract class Character {

    private UUID id;
    private String name;
    private int hp;
    private ArrayList<Character> partyList;
    private final int MAX_HP;

    private TextObject image;


    public Character(String name, int hp, ArrayList<Character> partyList, TextObject image) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.hp = hp;
        this.partyList = partyList;
        this.MAX_HP = hp;
        this.image=image;
    }

    public Character(UUID id, String name, int hp, ArrayList<Character> partyList, TextObject image) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.partyList = partyList;
        this.MAX_HP = hp;
        this.image=image;

    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMAX_HP() {
        return MAX_HP;
    }

//STARTS METHODS CHARACTER

    // DIE
    public void die() {
        partyList.remove(this);

        sentToGraveyard();

        System.out.println("is death?");
    }

    private void sentToGraveyard() {
        //TODO

    }

    // HEAL
    public void heal() {

        hp = MAX_HP;

    }

    //HURT
    public void hurt(int damage) {
        hp = hp - damage;
        if (!isalive()) {
            die();
        }
        System.out.println("Are you hurt??");
    }

    //DELETE FROM PARTY
    public void deleteFromParty() {
        System.out.println("Go home!");
    }

    // IS ALIVE

    public boolean isalive() {

       /* if (hp<=0) return false;
        return true;*/
        // checks if number is less than 0
        return hp > 0;

    }



    public TextObject toTextObject() {
      TextObject textObj= new TextObject(this.image, TextObject.Scroll.BLOCK,LIMIT_X / MAX_FIGHTERS, LIMIT_Y);
textObj.addText(this instanceof Warrior? "WARRIOR" : "WIZARD").addText(this.name);

        return getAttributes(textObj);

    }

    abstract TextObject getAttributes(TextObject textObj);





}



