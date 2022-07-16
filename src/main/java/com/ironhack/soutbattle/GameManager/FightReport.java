package com.ironhack.soutbattle.GameManager;

import com.ironhack.soutbattle.Characters.GameCharacter;
import com.ironhack.soutbattle.ScreenManager.TextObjects.TextObject;

import java.util.ArrayList;

public class FightReport {
    public final GameCharacter player;
    public final GameCharacter enemy;
    private TextObject imagePlayer,imageEnemy;
    private String namePlayer, nameEnemy;
    private final TextObject playerObject,enemyObject;
    private final ArrayList<FightRound> roundsList;
    public FightReport(GameCharacter player, GameCharacter enemy) {
        this.player=player;
        this.enemy=enemy;
        setImage(player.getImage(),enemy.getImage());
        setName(player.getName(),enemy.getName());

        roundsList = new ArrayList<>();

        playerObject = player.toFightTxtObj();
        enemyObject=enemy.toFightTxtObj();
    }

    public void setImage(TextObject imagePlayer, TextObject imageEnemy){
        this.imageEnemy = imageEnemy;
        this.imagePlayer = imagePlayer;
    }

    public void setName(String namePlayer, String nameEnemy){
        this.nameEnemy = nameEnemy;
        this.namePlayer = namePlayer;
    }
    public void newRound(com.ironhack.soutbattle.Characters.GameCharacter player, com.ironhack.soutbattle.Characters.GameCharacter enemy){
        roundsList.add(new FightRound(player,enemy,this));
    }
    public FightRound getCurrentRound(){
        return this.roundsList.get(roundsList.size()-1);
    }

    private FightRound poll(){
       return roundsList.remove(0);
    }

  public TextObject[] startPrint(){
//        printer.printFight(this);
        imagePlayer.addText(namePlayer);
        imageEnemy.addText(nameEnemy);
        return new TextObject[] {imagePlayer, imageEnemy};
  }
  public FightRound printNext() throws Exception {
        if(roundsList.isEmpty()) throw new Exception();
        return  poll();
  }

    public boolean hasNext() {
        return roundsList.size()>0;
    }
    public int totalRounds(){
        return this.roundsList.size();
    }

    public TextObject getPlayerObject() {
        return playerObject;
    }
    public TextObject getEnemyObject() {
        return enemyObject;
    }

    public FightRound getRound(int i) {
        return this.roundsList.get(i);
    }
}
