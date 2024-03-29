package com.ironhack.soutbattle.GameManager;

import com.ironhack.soutbattle.Characters.GameCharacter;
import com.ironhack.soutbattle.ScreenManager.TextObjects.TextObject;

import java.util.ArrayList;

public class FightRound {
    private final ArrayList<TextObject> playerStates;
    private final ArrayList<TextObject> enemyStates;
    private String playerAttack,enemyAttack;
    public final FightReport report;

    public FightRound(GameCharacter player,GameCharacter enemy,FightReport report) {
        this.enemyStates=new java.util.ArrayList<>();
        this.playerStates=new java.util.ArrayList<>();
        this.report=report;
        playerStates.add(player.getVariableAttributes());
        enemyStates.add(enemy.getVariableAttributes());
    }
    private void addIndividualAttackReport(boolean isPlayer, TextObject variableAttributes){
        if (isPlayer) playerStates.add(variableAttributes);
        else enemyStates.add(variableAttributes);

    }
    private void addIndividualAttackReport(GameCharacter character){
        this.addIndividualAttackReport(character.isPlayer(),character.getVariableAttributes());
    }
    public void addAttackReport(GameCharacter player,GameCharacter enemy,String AttackName){
        addIndividualAttackReport(player);
        addIndividualAttackReport(enemy);
        if (this.playerAttack==null){
            this.playerAttack=AttackName;
        }else {
            this.enemyAttack=AttackName;
        }
    }
    public TextObject getPlayerState(int index){
        return playerStates.get(index);
    }
    public TextObject getEnemyState(int index){
        return enemyStates.get(index);
    }

    public String getPlayerAttack() {
        return playerAttack;
    }

    public String getEnemyAttack() {
        return enemyAttack;
    }

}