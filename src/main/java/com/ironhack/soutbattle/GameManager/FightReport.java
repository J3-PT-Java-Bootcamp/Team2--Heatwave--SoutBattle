package com.ironhack.soutbattle.GameManager;

import com.ironhack.soutbattle.Characters.GameCharacter;
import com.ironhack.soutbattle.ScreenManager.ConsolePrinter;
import com.ironhack.soutbattle.ScreenManager.TextObjects.TextObject;

import java.util.ArrayList;

public class FightReport {
    private TextObject imagePlayer,imageEnemy;
    private String namePlayer, nameEnemy;
    private final TextObject playerObject,enemyObject;
    private ArrayList<FightRound> roundsList;
    private ConsolePrinter printer;

    GameManager game;
    public FightReport(ConsolePrinter printer, GameManager game, GameCharacter player, GameCharacter enemy) {
        setImage(player.getImage(),enemy.getImage());
        setName(player.getName(),enemy.getName());

        roundsList = new ArrayList<>();
        this.printer = printer;
        this.game = game;

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
    public FightReport newRound(GameCharacter player,GameCharacter enemy){
        roundsList.add(new FightRound(player,enemy));
        return this;
    }

    public void addAttackReport(GameCharacter player,GameCharacter enemy, String attackName){
        this.roundsList.get(roundsList.size()-1).addAttackReport(player,enemy,attackName);
    }
    private FightRound poll(){
       return roundsList.remove(0);
    }

  public TextObject[] startPrint(){
        printer.printFight(this);
        imagePlayer.addText(namePlayer);
        imageEnemy.addText(nameEnemy);
        return new TextObject[] {imagePlayer, imageEnemy};
  }
  public FightRound printNext() throws Exception {
        if(roundsList.isEmpty()) throw new Exception();
        return  poll();
  }
}
