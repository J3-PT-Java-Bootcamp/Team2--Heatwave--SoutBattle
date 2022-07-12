package com.ironhack.soutbattle.Characters;

import com.ironhack.soutbattle.GameManager.FightRound;

public interface Attacker {

    void attack(Character target, FightRound round);
}
