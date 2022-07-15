package com.ironhack.soutbattle.Characters;

import com.ironhack.soutbattle.GameManager.FightRound;
import com.ironhack.soutbattle.GameManager.GameManager;
import com.ironhack.soutbattle.ScreenManager.ColorFactory;
import com.ironhack.soutbattle.ScreenManager.TextObjects.TextObject;
import net.datafaker.Faker;

import java.util.Random;

import static com.ironhack.soutbattle.ScreenManager.ColorFactory.*;
import static com.ironhack.soutbattle.ScreenManager.PrinterConstants.*;

public class Wizard extends GameCharacter {
    //--------------------------------------------------------------------------------------------------------ATTRIBUTES
    private int mana;
    private int intelligence;
    private final int MAX_MANA;

    //-----------------------------------------------------------------------------------------------------------CONSTRUCTOR
    public Wizard(String name, int hp, int mana, int intelligence, boolean isPlayer) {
        super(name, hp, WARRIOR_IMG, isPlayer);
        this.mana = mana;
        this.MAX_MANA = mana;
        this.intelligence = intelligence;
    }

    public Wizard(Random rand, Boolean isPlayer) {
        super(GameManager.checkName(net.datafaker.Faker.instance().witcher().character()),
                rand.nextInt(50, 100),
                WIZARD_IMG,
                isPlayer);
        this.intelligence = rand.nextInt(5, 50);
        this.MAX_MANA = rand.nextInt(10, 50);
        this.mana = MAX_MANA;
    }

    //---------------------------------------------------------------------------------------------------GETTERSnSETTERS
    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        Math.min(this.mana + mana, MAX_MANA);
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    //-------------------------------------------------------------------------------------------------------------PRINT
    /*
     * Set of methods used by ConsolePrinter to print GameCharacter objects
     */
    @Override
    public TextObject getVariableAttributes() {
        return new TextObject(TextStyle.BOLD + "HP: " +
                TextStyle.RESET + (getHp() >= getMAX_HP() / 2 ? CColors.BRIGHT_GREEN : CColors.BRIGHT_RED)
                + getHp() + TextStyle.RESET + "/" + getMAX_HP()
                + TextStyle.BOLD + " Mana: " + TextStyle.RESET
                + (mana >= MAX_MANA / 2 ? CColors.BRIGHT_GREEN : CColors.BRIGHT_RED)
                + this.mana + TextStyle.RESET + "/" + this.MAX_MANA,

                TextObject.Scroll.NO,
                LIMIT_X / 3,
                LIMIT_Y);
    }

    @Override
    TextObject getAttributes(TextObject textObj) {
        return textObj.addText("Intllignce: " + this.intelligence)
                .addText(TextStyle.BOLD + "Mana: "
                        + TextStyle.RESET + (mana >= MAX_MANA / 2 ? CColors.BRIGHT_GREEN : CColors.BRIGHT_RED)
                        + this.mana + TextStyle.RESET + "/" + this.MAX_MANA);
    }


    @Override
    TextObject getFixAttribute(TextObject txtObj) {
        return txtObj.addText("Intelligence: " + intelligence);
    }

    //--------------------------------------------------------------------------------------------------------ATTACK_METHODS
    @Override
    public void attack(GameCharacter target, FightRound round) {
        String attackName = null;
        if (mana >= 5) {
            if (round.report.totalRounds() + Math.random() * 10 > 25) {
                attackName = superAttack(target);
            } else {
                attackName = throwFireball(target);
            }
        } else if (mana < 5) {
            attackName = throwStaffHit(target);
        }
        if (isPlayer()) round.addAttackReport(this, target, attackName);
        else round.addAttackReport(target, this, attackName);
    }

    private String superAttack(GameCharacter target) {

        target.hurt(target.getHp());
        return ColorFactory.rainbowCharacters("Raises with an INVOCATION", 5);

    }

    private String throwStaffHit(GameCharacter target) {
        /*Fighter*/
        this.mana += (Math.random() * 10) % 4;
        /*Target*/
        damage = -2;
        target.hp = target.getHp() - damage;
        return "Threw staff hit";
    }

    private String throwFireball(GameCharacter target) {
        /*Fighter*/
        this.mana -= 5;
        /*Target*/
        damage = this.intelligence;
        target.hp = target.getHp() - damage;
        return "Threw Fireball";
    }

    @Override
    void recoverVarAttribute() {
        this.mana = MAX_MANA;
    }

    @Override
    public boolean bonusRecovery(int playerBonus) {
        Random randomPercent = new Random();
        int random = randomPercent.nextInt(0, 10);
        if (playerBonus + random > 20) {
            this.setMana(randomPercent.nextInt(1, 10));
            return true;
        }
        return false;
    }
    @Override
    public void healPartially() {
        Random rand =new Random();
        int factor= getMAX_HP()/100;
        this.hp+=rand.nextInt(1,3) *factor;
        factor=MAX_MANA/100;
        this.mana+=rand.nextInt(1,5)*factor;
    }
}