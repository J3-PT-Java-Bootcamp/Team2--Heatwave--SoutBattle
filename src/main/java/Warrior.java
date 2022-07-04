public class Warrior extends Character {

    private int stamina;
    private int strength;


    public Warrior(int id, String name, int hp, boolean isAlive) {
        super(id, name, hp, isAlive);
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
