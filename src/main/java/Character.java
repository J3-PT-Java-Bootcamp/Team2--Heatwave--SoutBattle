public abstract class Character {

    private int id;
    private String name;
    private int hp;
    private boolean isAlive;

    public Character(int id, String name, int hp, boolean isAlive) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.isAlive = isAlive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    //STARTS METHODS CHARACTER

    // DIE
    public static void die() {
        System.out.println("is death?");
    }

    // HEAL
    public static void heal() {
        System.out.println("Healing?");
    }

    //HURT
    public static void hurt() {
        System.out.println("Are you hurt??");
    }

    //DELETE FROM PARTY
    public static void deleteFromParty() {
        System.out.println("Go home!");
    }

    // IS ALIVE

    public static void isalive() {
//TESTING IF / ELSE
        int hp1 = 180;
        int hp2 = 180;
        int hp3 = -12;

        // checks if number is less than 0
        if (hp3 > 0) {
            System.out.println("You're still alive");
        }else{

        System.out.println("You're death!!! Go to memorial");
    }
}
}


