package GameManager;

import Characters.*;

import java.lang.Character;
import java.util.ArrayList;

import static ScreenManager.PrinterConstants.MAX_FIGHTERS;

public class GameData {
    public String[][] parties;
    public String[] graveyard;
    public String userName;
//    public final com.google.gson.Gson gson;


    GameData() {
    }

    public GameData serializeParties(java.util.ArrayList<Party> parties) {
        this.parties=new String[parties.size()][MAX_FIGHTERS+1];
        for (int i = 0; i < parties.size(); i++) {
            this.parties[i][0]=parties.get(i).getName();
            for (int j =1; j <= MAX_FIGHTERS; j++) {
               this.parties[i][j]=(parties.get(i).getCharacter(j-1).getCharacterType())
                       +"/CHARACTER/"+(new com.google.gson.Gson().toJson(parties.get(i).getCharacter(j-1)));
            }
        }
        return this;
    }
    public ArrayList<Party> deserializeParties(){
        var resArr=new ArrayList<Party>();
        for (int i = 0; i < this.parties.length; i++) {
            resArr.add(new Party(parties[i]));
        }
        return resArr;
    }

    public ArrayList<Characters.Character> deserializeGraveyard() {
        var resArr = new ArrayList<Characters.Character>();
        for (int i = 0; i < this.graveyard.length; i++) {
            var jsonArr=this.graveyard[i].split("/CHARACTER/");
            resArr.add(new com.google.gson.Gson().fromJson(jsonArr[1], java.util.Objects.equals(jsonArr[0], "WARRIOR") ?Warrior.class:Wizard.class));
        }
        return resArr;
    }

    public GameData serializeGraveyard(java.util.ArrayList<Characters.Character> graveyard) {
        this.graveyard= new String[graveyard.size()];
        for (int i = 0; i < graveyard.size(); i++) {
            this.graveyard[i]=graveyard.get(i).getCharacterType()
                    +"/CHARACTER/"+new com.google.gson.Gson().toJson(graveyard.get(i));
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
