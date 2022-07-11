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

    public GameData setParties(Party[] parties) {
        this.parties = parties;
        return this;
    }

    public String[] getGraveyard() {
        return graveyard;
    }

    public GameData setGraveyard(String[] graveyard) {
        this.graveyard = graveyard;
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
