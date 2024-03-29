package com.ironhack.soutbattle.Characters;

import com.ironhack.soutbattle.ScreenManager.TextObjects.TextObject;

import java.util.ArrayList;
import java.util.UUID;

import static com.ironhack.soutbattle.ScreenManager.ColorFactory.CColors;
import static com.ironhack.soutbattle.ScreenManager.ColorFactory.TextStyle;
import static com.ironhack.soutbattle.ScreenManager.PrinterConstants.*;

public abstract class GameCharacter implements Attacker {
    protected int damage;
    //--------------------------------------------------------------------------------------------------------ATTRIBUTES
    private final UUID id;
    private final String name;
    public int hp;
    private final int MAX_HP;
    private final TextObject image;
    private final boolean isPlayer;

    //-------------------------------------------------------------------------------------------------------CONSTRUCTOR
    public GameCharacter(String name, int hp, TextObject image, boolean isPlayer) {
        this.isPlayer = isPlayer;
        this.id = UUID.randomUUID();
        this.name = name;
        this.hp = hp;
        this.MAX_HP = hp;
        this.image = image;
    }

    @Deprecated
    public GameCharacter(java.util.UUID id, String name, int hp, ArrayList<GameCharacter> partyList, TextObject image, boolean isPlayer) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.MAX_HP = hp;
        this.image = image;
        this.isPlayer = isPlayer;
    }

    public String getName() {
        return TextStyle.BOLD + name + TextStyle.RESET;
    }

    public int getHp() {
        return Math.max(hp, 0);
    }

    /**
     * Modifies current HP by adding value (could be negative)
     *
     * @param hp to be increased
     */
    public void modifyHp(int hp) {
        this.hp += hp;
    }

    public int getMAX_HP() {
        return MAX_HP;
    }

    /**
     * Method that returns the image to show depending on character isAlive value
     *
     * @return TextObject with the proper image in ascii
     */
    public TextObject getImage() {
        if (isAlive()) return image;
        return ((int) (Math.random() * 10) % 2 == 0 ? TOMB : CROIX).alignTextCenter();
    }

//--------------------------------------------------------------------------------------------STARTS METHODS CHARACTER

    // HEAL
    public void heal() {
        hp = MAX_HP;
    }

    //HURT
    public void hurt(int damage) {
        hp = hp - damage;
    }

    public boolean isCharacterAlive() {
        return hp > 0;
    }


    //------------------------------------------------------------------------------------------------------------PRINT
    /*
     * Set of methods used by ConsolePrinter to print GameCharacter objects
     */
    public TextObject toTextObject() {
        TextObject resVal = new TextObject(this.getImage(),
                TextObject.Scroll.BLOCK,
                (LIMIT_X - MAX_FIGHTERS) / MAX_FIGHTERS,
                LIMIT_Y / 2);
        resVal.addText(this.name)
                .addText(printCharacterType())
                .addText(TextStyle.BOLD + "HP: " +
                        TextStyle.RESET + (getHp() >= MAX_HP / 2 ? CColors.BRIGHT_GREEN : CColors.BRIGHT_RED)
                        + getHp() + TextStyle.RESET + "/" + MAX_HP).alignTextCenter()
        ;
        return getAttributes(resVal).alignTextCenter();
    }
    private String printCharacterType() {
        return TextStyle.BOLD + getCharacterType() + TextStyle.RESET;
    }
    public String getCharacterType() {
        return ((this instanceof com.ironhack.soutbattle.Characters.Warrior) ? "WARRIOR" : "WIZARD");
    }


    public TextObject toFightTxtObj() {
        TextObject resVal = new TextObject(this.getImage(),
                TextObject.Scroll.BLOCK,
                (LIMIT_X - MAX_FIGHTERS) / MAX_FIGHTERS,
                LIMIT_Y);
        resVal.addText(this.getName())
                .addText(printCharacterType())
        ;

        return getFixAttribute(resVal);

    }

    public abstract TextObject getVariableAttributes();

    abstract TextObject getAttributes(TextObject textObj);

    abstract TextObject getFixAttribute(TextObject txtObj);


    public boolean isPlayer() {
        return this.isPlayer;
    }

    abstract void recoverVarAttribute();

    public boolean isAlive() {
        return hp > 0;
    }

    public abstract boolean bonusRecovery(int playerBonus);

    public abstract void healPartially();
    public abstract void upgrade();
}



