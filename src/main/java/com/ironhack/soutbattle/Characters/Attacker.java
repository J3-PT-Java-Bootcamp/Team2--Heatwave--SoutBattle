package com.ironhack.soutbattle.Characters;

import com.ironhack.soutbattle.GameManager.FightRound;

public interface Attacker {

    void attack(GameCharacter target, FightRound round);
}
