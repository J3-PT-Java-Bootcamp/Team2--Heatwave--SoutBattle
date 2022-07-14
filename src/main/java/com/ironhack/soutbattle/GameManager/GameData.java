package com.ironhack.soutbattle.GameManager;

import com.google.gson.Gson;
import com.ironhack.soutbattle.Characters.*;

import java.util.ArrayList;
import java.util.Objects;

import static com.ironhack.soutbattle.ScreenManager.PrinterConstants.MAX_FIGHTERS;

public class GameData {
    public String[][] parties;
    public String[] graveyard;
    public String userName;
//    public final com.google.gson.Gson gson;
    public  ArrayList <String> namesList;

    GameData() {
        this.namesList=new ArrayList<>();
    }

    public GameData serializeParties(ArrayList<Party> parties) {
        this.parties = new String[parties.size()][MAX_FIGHTERS + 1];
        for (int i = 0; i < parties.size(); i++) {
            this.parties[i][0] = parties.get(i).getName()+"//"+parties.get(i).getWins();
            for (int j = 1; j <= MAX_FIGHTERS; j++) {
                this.parties[i][j] = (parties.get(i).getCharacter(j - 1).getCharacterType())
                        + "/CHARACTER/" + (new Gson().toJson(parties.get(i).getCharacter(j - 1)));
            }
        }
        return this;
    }

    public ArrayList<Party> deserializeParties() {
        var resArr = new ArrayList<Party>();
        for (int i = 0; i < this.parties.length; i++) {
            resArr.add(new Party(parties[i]));
        }
        return resArr;
    }

    public ArrayList<GameCharacter> deserializeGraveyard() {
        var resArr = new ArrayList<GameCharacter>();
        for (int i = 0; i < this.graveyard.length; i++) {
            var jsonArr = this.graveyard[i].split("/CHARACTER/");
            resArr.add(new Gson().fromJson(jsonArr[1],
                    Objects.equals(jsonArr[0], "WARRIOR") ? Warrior.class : Wizard.class));
        }
        return resArr;
    }

    public GameData serializeGraveyard(ArrayList<GameCharacter> graveyard) {
        this.graveyard = new String[graveyard.size()];
        for (int i = 0; i < graveyard.size(); i++) {
            this.graveyard[i] = graveyard.get(i).getCharacterType()
                    + "/CHARACTER/" + new Gson().toJson(graveyard.get(i));
        }
        return this;
    }

    private String getUserName() {
        return userName;
    }

    public GameData setUserName(String userName) {
        this.userName = userName;
        return this;
    }








}
