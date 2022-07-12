package com.ironhack.soutbattle;

import com.ironhack.soutbattle.GameManager.GameManager;

import java.util.EmptyStackException;

public class Main {
    public static void main(String[] args) {
        var game = new GameManager();
        reStart:
        try {
            game.startGame();
        } catch (EmptyStackException e) {
            game=new GameManager();
            break reStart;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


}