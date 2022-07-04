public class Wizard extends Character {

    private int mana;
    private int intelligence;

    public Wizard(int id, String name, int hp, boolean isAlive) {
        super(id, name, hp, isAlive);
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }
}
