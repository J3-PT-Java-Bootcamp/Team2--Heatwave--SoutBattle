package com.ironhack.soutbattle.Characters;

import com.ironhack.soutbattle.GameManager.FightRound;
import com.ironhack.soutbattle.ScreenManager.TextObjects.*;
import com.ironhack.soutbattle.ScreenManager.TextObjects.TextObject.*;
import net.datafaker.Faker;
import java.util.Random;
import static com.ironhack.soutbattle.ScreenManager.ColorFactory.*;
import static com.ironhack.soutbattle.ScreenManager.PrinterConstants.*;

public class Warrior extends GameCharacter {
    //--------------------------------------------------------------------------------------------------------ATTRIBUTES

    private int stamina;
    private int strength;
    private final int MAX_STAMINA;

    //-------------------------------------------------------------------------------------------------------CONSTRUCTOR

    public Warrior(String name, int hp, int stamina, int strength, boolean isPlayer) {
        super(name, hp,  WARRIOR_IMG,isPlayer);
        this.stamina = stamina;
        this.MAX_STAMINA = stamina;
        this.strength = strength;
    }

    public Warrior(Random rand, boolean isPlayer) {
        super(Faker.instance().gameOfThrones().character(), rand.nextInt(100, 200),  WARRIOR_IMG,isPlayer);
        this.strength = rand.nextInt(1, 10);
        this.MAX_STAMINA = rand.nextInt(10, 50);
        this.stamina = MAX_STAMINA;
    }

    //---------------------------------------------------------------------------------------------------GETTERSnSETTERS
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

    //------------------------------------------------------------------------------------------------------------PRINT    /*
    //     * Set of methods used by ConsolePrinter to print GameCharacter objects
    //     */

    @Override
    public TextObject toFightTxtObj() {

        return super.toFightTxtObj();
    }

    @Override
    public TextObject getVariableAttributes() {
        return new TextObject("HP:" + getHp() + "/" + getMAX_HP() + "Stmn:" + getStamina() + MAX_STAMINA,
                Scroll.NO,
                LIMIT_X / 3,
                LIMIT_Y);
    }

    @Override
    TextObject getAttributes(TextObject textObj) {
        return textObj.addText("Strength: " + this.strength)
                .addText(TextStyle.BOLD + "Stamina:"
                        + TextStyle.RESET + (stamina >= MAX_STAMINA / 2 ? CColors.BRIGHT_GREEN : CColors.BRIGHT_RED)
                        + this.stamina + TextStyle.RESET + "/" + this.MAX_STAMINA);
    }

    @Override
    TextObject getFixAttribute(TextObject txtObj) {
        return txtObj.addText("Strength: " + strength);
    }

    //----------------------------------------------------------------------------------------------------ATTACK_METHODS
    @Override
    public void attack(GameCharacter target, FightRound round) {
        String attackName = null;//TODO Make heavyAttack() y weakAttack() return the attack name string
        if (stamina >= 5) {
            attackName=heavyAttack(target);
        } else if (stamina < 5) {

            attackName=weakAttack();
        }
        if(isPlayer()) round.addAttackReport(this,target,attackName);
        else round.addAttackReport(target,this,attackName);

    }

    //WARRIOR ATTACKS
    private String weakAttack() {
        //TODO
        /*Fighter*/
        stamina += 1;
        /*Target*/
        damage = strength / 2;

        return null;
    }

    private String heavyAttack(com.ironhack.soutbattle.Characters.GameCharacter target) {
        //TODO
        /*Fighter*/
        stamina -= 5;
        /*Target*/
        damage = strength/*Fighter*/;

        return null;
    }
}
