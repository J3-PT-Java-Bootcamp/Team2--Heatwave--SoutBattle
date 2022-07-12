package GameManager;

import Characters.Party;

public class GameData {
    private Party[] parties;
    private String[] graveyard;
    private String userName;

    GameData() {
    }

    protected Party[] getParties() {
        return parties;
    }

    public GameData setParties(java.util.ArrayList<Party> parties) {
        this.parties = parties.toArray(new Party[0]);
        return this;
    }

    public String[] getGraveyard() {
        return graveyard;
    }

    public GameData setGraveyard(java.util.ArrayList<String> graveyard) {
        this.graveyard = graveyard.toArray(new String[0]);
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public GameData setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}
