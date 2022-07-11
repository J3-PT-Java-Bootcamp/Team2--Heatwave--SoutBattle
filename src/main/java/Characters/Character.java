package Characters;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Character {

    private UUID id;
    private String name;
    private int hp;
    private ArrayList<Character> partyList;
    private final int MAX_HP;


    public Character(String name, int hp, ArrayList<Character> partyList) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.hp = hp;
        this.partyList = partyList;
        this.MAX_HP = hp;
    }

    public Character(UUID id, String name, int hp, ArrayList<Character> partyList) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.partyList = partyList;
        this.MAX_HP = hp;

    }

    public UUID getId() {
        return id;
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


    //STARTS METHODS CHARACTER

    // DIE
    public void die() {
        partyList.remove(this);

        sentToGraveyard();

        System.out.println("is death?");
    }

    private void sentToGraveyard() {
        //TODO

    }

    // HEAL
    public void heal() {

        hp = MAX_HP;

    }

    //HURT
    public void hurt(int damage) {
        hp = hp - damage;
        if (!isalive()) {
            die();
        }
        System.out.println("Are you hurt??");
    }

    //DELETE FROM PARTY
    public void deleteFromParty() {
        System.out.println("Go home!");
    }

    // IS ALIVE

    public boolean isalive() {

       /* if (hp<=0) return false;
        return true;*/
        // checks if number is less than 0
        return hp > 0;

    }
}



