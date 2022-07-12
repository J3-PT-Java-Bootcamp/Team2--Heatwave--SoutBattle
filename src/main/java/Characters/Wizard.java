package Characters;

import ScreenManager.TextObjects.TextObject;
import com.google.gson.Gson;
import net.datafaker.Faker;

import java.util.Random;

import static ScreenManager.ColorFactory.*;
import static ScreenManager.PrinterConstants.*;
import static ScreenManager.PrinterConstants.LIMIT_Y;

public class Wizard extends Character {
    //--------------------------------------------------------------------------------------------------------ATTRIBUTES
    private int mana;
    private int intelligence;
    private final int MAX_MANA;
//-----------------------------------------------------------------------------------------------------------CONSTRUCTOR
    public Wizard(){
        super();
        MAX_MANA=0;
    }
    public Wizard(String name, int hp, Characters.Party partyList, int mana, int intelligence ) {
        super(name, hp, partyList, WARRIOR_IMG);
        this.mana=mana;
        this.MAX_MANA= mana;
        this.intelligence=intelligence;
    }
    public Wizard(Characters.Party partyList, Random rand) {
        super(Faker.instance().witcher().character(),rand.nextInt(50,100),partyList, WIZARD_IMG);
        this.intelligence= rand.nextInt(1,50);
        this.MAX_MANA= rand.nextInt(10,50);
        this.mana= MAX_MANA;
    }

    //---------------------------------------------------------------------------------------------------GETTERSnSETTERS
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
    //-------------------------------------------------------------------------------------------------------------PRINT
    @Override
    ScreenManager.TextObjects.TextObject getVariableAttributes() {
        return new TextObject(TextStyle.BOLD+"HP: "+
                TextStyle.RESET+ (getHp() >= getMAX_HP() / 2 ? CColors.BRIGHT_GREEN : CColors.BRIGHT_RED)
                + getHp()+TextStyle.RESET+"/"+getMAX_HP()
                +TextStyle.BOLD+"Mana: " + TextStyle.RESET
                +(mana >= MAX_MANA / 2 ? CColors.BRIGHT_GREEN : CColors.BRIGHT_RED)
                +this.mana+ TextStyle.RESET+"/"+this.MAX_MANA,

                TextObject.Scroll.NO,
                LIMIT_X/3,
                LIMIT_Y);
    }
    @Override
    TextObject getAttributes(TextObject textObj) {
        return textObj.addText("Intllignce: "+this.intelligence)
                .addText( TextStyle.BOLD+"Mana: "
                        + TextStyle.RESET+ (mana >= MAX_MANA / 2 ? CColors.BRIGHT_GREEN : CColors.BRIGHT_RED)
                        +this.mana+ TextStyle.RESET+"/"+this.MAX_MANA);
    }
    @Override
    ScreenManager.TextObjects.TextObject getFixAttribute(ScreenManager.TextObjects.TextObject txtObj) {
        return txtObj.addText("Intelligence: "+intelligence);
    }

//    @Override
//    String saveCharacter(com.google.gson.Gson gson) {
//        return getCharacterType()+"/#/"+gson.toJson(this);
//    }
}
