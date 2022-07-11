package Characters;
import java.util.ArrayList;

public class Party{
    private ArrayList<Character> characterList;
    private final int MAX_FIGHTERS = 5 ;

    public Party() {
        characterList= new ArrayList<>();
        while(!isFull()){
            addCharacter(getRandomCharacter());

        }
    }

    private Character getRandomCharacter() {

        return null; //TODO
        //CREAR WARRIOR O WIZARD , RANDOM
    }


    public ArrayList<Character> getCharacterList() {
        return characterList;
    }

    public void addCharacter (Character fighter) {
       
        if (isFull())
        {
            characterList.add(fighter);
        }
        
    }

    public boolean isFull() {
        return characterList.size() < MAX_FIGHTERS;
    }
}
