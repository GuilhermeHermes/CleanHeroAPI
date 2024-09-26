package br.com.gubee.interview.core.features.hero.utils;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.enums.Race;
import org.bson.types.ObjectId;

import java.util.UUID;

public class HeroFactory {

    public static Hero createHero(String name, Race race, PowerStats powerStats, boolean enabled) {
        Hero hero = new Hero();
        hero.setName(name);
        hero.setRace(race);
        hero.setPowerStats(powerStats);
        hero.setEnabled(enabled);
        return hero;
    }

    public static Hero createHeroWithId(String id, String name, Race race, PowerStats powerStats, boolean enabled) {
        Hero hero = new Hero();
        hero.setId(id);
        hero.setName(name);
        hero.setRace(race);
        hero.setPowerStats(powerStats);
        hero.setEnabled(enabled);
        return hero;
    }

    public static Hero createDefaultHero() {
        PowerStats defaultStats = new PowerStats(50, 50, 50, 50); // Exemplo de stats
        return createHero("Default Hero", Race.HUMAN, defaultStats, true);
    }
}

