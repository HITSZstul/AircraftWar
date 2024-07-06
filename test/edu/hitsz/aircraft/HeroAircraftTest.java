package edu.hitsz.aircraft;

import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("**--- Executed once before all test methods in this class ---**");
    }
    @org.junit.jupiter.api.Test
    void getHero() {
        HeroAircraft hero1 = HeroAircraft.getHero();
        HeroAircraft hero2 = HeroAircraft.getHero();
        assertEquals(hero1,hero2);
    }

    @org.junit.jupiter.api.Test
    void update() {
        }


}