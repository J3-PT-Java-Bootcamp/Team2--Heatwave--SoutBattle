package Characters;

import ScreenManager.ColorFactory.TextStyle;
import ScreenManager.TextObjects.DynamicLine;
import ScreenManager.TextObjects.TextObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.UUID;

import static ScreenManager.ColorFactory.*;
import static ScreenManager.PrinterConstants.*;
import static ScreenManager.PrinterConstants.LIMIT_Y;

public abstract class Character {
    //--------------------------------------------------------------------------------------------------------ATTRIBUTES
    private UUID id;
    private String name;
    private int hp;
    private ArrayList<Character> partyList;
    private final int MAX_HP;

    private TextObject image;

    //-------------------------------------------------------------------------------------------------------CONSTRUCTOR
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
    //---------------------------------------------------------------------------------------------------GETTERSnSETTERS
    public UUID getId() {
        return id;
    }

    public String getName() {
        return TextStyle.BOLD + name + TextStyle.RESET;
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

//--------------------------------------------------------------------------------------------STARTS METHODS CHARACTER

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

    public void deleteFromParty() {
        System.out.println("Go home!");
    }
    public boolean isalive() {

       /* if (hp<=0) return false;
        return true;*/
        // checks if number is less than 0
        return hp > 0;
    }
    //------------------------------------------------------------------------------------------------------------PRINT
    public TextObject toTextObject() {
      TextObject resVal= new TextObject(this.image,
              TextObject.Scroll.BLOCK,
              (LIMIT_X-MAX_FIGHTERS) / MAX_FIGHTERS,
              LIMIT_Y);
      resVal  .addText(this.name)
              .addText(printCharacterType())
              .addText(TextStyle.BOLD+"HP: "+
                      TextStyle.RESET+ (getHp() >= MAX_HP / 2 ? CColors.BRIGHT_GREEN : CColors.BRIGHT_RED)
                      + getHp()+TextStyle.RESET+"/"+MAX_HP)
      ;

        return getAttributes(resVal).alignTextCenter();

    }
    private String printCharacterType() {
        return TextStyle.BOLD + (this instanceof Characters.Warrior ? "WARRIOR" : "WIZARD") + TextStyle.RESET;
    }

    public TextObject toFightTxtObj(){
        TextObject resVal= new TextObject(this.image,
                TextObject.Scroll.BLOCK,
                (LIMIT_X-MAX_FIGHTERS) / MAX_FIGHTERS,
                LIMIT_Y);
        resVal  .addText(this.getName())
                .addText(printCharacterType())
        ;

        return getFixAttribute(resVal);

    };
    abstract TextObject getVariableAttributes();

    abstract TextObject getAttributes(TextObject textObj);
    abstract TextObject getFixAttribute(TextObject txtObj);




}



