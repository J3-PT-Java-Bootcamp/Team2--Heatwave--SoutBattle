package com.ironhack.soutbattle.GameManager;

import com.ironhack.soutbattle.ScreenManager.ConsolePrinter;
import com.ironhack.soutbattle.ScreenManager.TextObjects.TextObject;

import java.util.ArrayList;

public class FightReport {
    TextObject imagePlayer,imageEnemy;
    String namePlayer, nameEnemy;
    ArrayList<FightRound> roundsList;
    ConsolePrinter printerp;

    GameManager game;
    public FightReport(ConsolePrinter printerp, GameManager game) {

        roundsList = new ArrayList<>();
        this.printerp = printerp;
        this.game = game;

    }

    public void setImage(TextObject imagePlayer, TextObject imageEnemy){
        this.imageEnemy = imageEnemy;
        this.imagePlayer = imagePlayer;
    }

    public void setName(String namePlayer, String nameEnemy){
        this.nameEnemy = nameEnemy;
        this.namePlayer = namePlayer;
    }
    public FightReport newRound(){
        roundsList.add(new FightRound());
        return this;
    }
    private FightRound poll(){
       return roundsList.remove(0);
    }

  public TextObject[] startPrint(){
        printerp.printFight(this);
        imagePlayer.addText(namePlayer);
        imageEnemy.addText(nameEnemy);
        return new TextObject[] {imagePlayer, imageEnemy};
  }
  public FightRound printNext() throws Exception {
        if(roundsList.isEmpty()) throw new Exception();
        return  poll();
  }
}
