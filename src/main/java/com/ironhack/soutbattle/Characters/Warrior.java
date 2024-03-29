package com.ironhack.soutbattle.Characters;

import com.ironhack.soutbattle.GameManager.FightRound;
import com.ironhack.soutbattle.GameManager.GameManager;
import com.ironhack.soutbattle.ScreenManager.ColorFactory;
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
        super(name, hp, WARRIOR_IMG, isPlayer);
        this.stamina = stamina;
        this.MAX_STAMINA = stamina;
        this.strength = strength;
    }

    public Warrior(Random rand, boolean isPlayer) {
        super(GameManager.checkName(Faker.instance().gameOfThrones().character()),
                rand.nextInt(100, 200), WARRIOR_IMG, isPlayer);
        this.strength = rand.nextInt(1, 10);
        this.MAX_STAMINA = rand.nextInt(10, 50);
        this.stamina = MAX_STAMINA;
    }

    //---------------------------------------------------------------------------------------------------GETTERSnSETTERS
    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = (Math.min(strength,10));
    }

    @Override
    public TextObject getVariableAttributes() {
        return new TextObject(TextStyle.BOLD + "HP: " +
                TextStyle.RESET + (getHp() >= getMAX_HP() / 2 ? CColors.BRIGHT_GREEN : CColors.BRIGHT_RED)
                + getHp() + TextStyle.RESET + "/" + getMAX_HP() + " Stmn: " + TextStyle.RESET + (stamina >= MAX_STAMINA / 2 ? CColors.BRIGHT_GREEN : CColors.BRIGHT_RED)
                + this.stamina + TextStyle.RESET + "/" + this.MAX_STAMINA,
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
        String attackName = null;
        if (stamina >= 5) {
            if (round.report.totalRounds() + Math.random() * 10 > 25) attackName = superAttack(target);
            else attackName = heavyAttack(target);
        } else attackName = weakAttack(target);
        if (isPlayer()) round.addAttackReport(this, target, attackName);
        else round.addAttackReport(target, this, attackName);
    }
    //WARRIOR ATTACKS
    private String weakAttack(GameCharacter target) {
        /*Fighter*/
        this.stamina += (Math.random() * 10) % 4;
        /*Target*/
        damage = this.strength / 2;
        target.hp = target.getHp() - damage;
        return " attacks!";
    }
    private String heavyAttack(GameCharacter target) {
        /*Fighter*/
        this.stamina -= 5;
        /*Target*/
        damage = this.strength/*Fighter*/;
        target.hp = target.getHp() - damage;
        return " does a Heavy Attack!";
    }
    private String superAttack(GameCharacter target) {
        target.hurt(target.getHp());
        return ColorFactory.rainbowCharacters(" SMASHES IT UP!!!", 5);
    }
    @Override
    void recoverVarAttribute() {
        this.stamina = this.MAX_STAMINA;
    }
    @Override
    public boolean bonusRecovery(int playerBonus) {
        Random randomPercent = new Random();
        int random = randomPercent.nextInt(0, 10);
        if (playerBonus + random > 20) {
            this.stamina += randomPercent.nextInt(1, 10);
            return true;
        }
        return false;
    }

    @Override
    public void healPartially() {
        Random rand =new Random();
        int factor= getMAX_HP()/100;
        this.hp+=rand.nextInt(1,3) *factor;
        factor=MAX_STAMINA/100;
        this.stamina+=rand.nextInt(1,5)*factor;
    }

    @Override
    public void upgrade() {
        this.setStrength(this.getStrength()+1);
    }
}
