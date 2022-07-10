package GameManager;

public class GameData {
    private ScreenManager.ConsolePrinter.Party[] parties;
    private String[] graveyard;
    private String userName;

    GameData() {
    }

    protected ScreenManager.ConsolePrinter.Party[] getParties() {
        return parties;
    }

    public GameData setParties(ScreenManager.ConsolePrinter.Party[] parties) {
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
