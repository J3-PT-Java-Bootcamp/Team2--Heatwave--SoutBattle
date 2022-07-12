package com.ironhack.soutbattle.Characters;

import com.ironhack.soutbattle.ScreenManager.TextObjects.TextObject;

import java.util.ArrayList;
import java.util.UUID;

import static com.ironhack.soutbattle.ScreenManager.ColorFactory.CColors;
import static com.ironhack.soutbattle.ScreenManager.ColorFactory.TextStyle;
import static com.ironhack.soutbattle.ScreenManager.PrinterConstants.*;

public abstract class GameCharacter implements Attacker  {
    protected int damage;
    //--------------------------------------------------------------------------------------------------------ATTRIBUTES
    private final UUID id;
    private String name;
    private int hp;
    private final int MAX_HP;
    private boolean isAlive;
    private final TextObject image;

    //-------------------------------------------------------------------------------------------------------CONSTRUCTOR
    public GameCharacter(String name, int hp, com.ironhack.soutbattle.Characters.Party partyList, com.ironhack.soutbattle.ScreenManager.TextObjects.TextObject image) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.hp = hp;
        this.MAX_HP = hp;
        this.image=image;
        this.isAlive=true;
    }

    public GameCharacter(UUID id, String name, int hp, ArrayList<GameCharacter> partyList, TextObject image) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.MAX_HP = hp;
        this.image=image;
        this.isAlive=isAlive;


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
    public TextObject getImage(){
        if(isAlive) return image;
        return (int)(Math.random()*10)%2==0?TOMB:CROIX;
    }

//--------------------------------------------------------------------------------------------STARTS METHODS CHARACTER

    // DIE
    public void die() {
        this.isAlive=false;
        sendToGraveyard();

        System.out.println("is death?");
    }

    private void sendToGraveyard() {
        com.ironhack.soutbattle.GameManager.GameManager.addToGraveyard(this);
    }

    // HEAL
    public void heal() {
        hp = MAX_HP;
    }

    //HURT
    public void hurt(int damage) {
        hp = hp - damage;
        if (!isCharacterAlive()) {
            die();
        }
    }

    public void deleteFromParty() {
        System.out.println("Go home!");
    }
    public boolean isCharacterAlive() {

       /* if (hp<=0) return false;
        return true;*/
        // checks if number is less than 0
        return hp > 0;
    }



    //------------------------------------------------------------------------------------------------------------PRINT
    public TextObject toTextObject() {
      TextObject resVal= new TextObject(this.getImage(),
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
        return TextStyle.BOLD + getCharacterType() + TextStyle.RESET;
    }

    public String getCharacterType() {
        return ((this instanceof com.ironhack.soutbattle.Characters.Warrior )? "WARRIOR" : "WIZARD");
    }


    public TextObject toFightTxtObj(){
        TextObject resVal= new TextObject(this.getImage(),
                TextObject.Scroll.BLOCK,
                (LIMIT_X-MAX_FIGHTERS) / MAX_FIGHTERS,
                LIMIT_Y);
        resVal  .addText(this.getName())
                .addText(printCharacterType())
        ;

        return getFixAttribute(resVal);

    }

    abstract TextObject getVariableAttributes();

    abstract TextObject getAttributes(TextObject textObj);
    abstract TextObject getFixAttribute(TextObject txtObj);




}



