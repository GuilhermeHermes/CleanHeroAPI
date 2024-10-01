package br.com.gubee.interview.core.features.hero.utils;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.dtos.HeroDtoRequest;
import br.com.gubee.interview.model.dtos.HeroDtoResponse;
import br.com.gubee.interview.model.enums.Race;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Random;
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

    public static Hero createRandomHeroWithId() {
        Random random = new Random();
        String id = new ObjectId().toString();
        String name = "Hero" + random.nextInt(1000);
        Race race = Race.values()[random.nextInt(Race.values().length)];
        PowerStats powerStats = new PowerStats(random.nextInt(100), random.nextInt(100), random.nextInt(100), random.nextInt(100));
        boolean enabled = random.nextBoolean();

        Hero hero = new Hero();
        hero.setId(id);
        hero.setName(name);
        hero.setRace(race);
        hero.setPowerStats(powerStats);
        hero.setEnabled(enabled);
        return hero;
    }

    public static List<Hero> createListHeroes(){
        Hero hero1 = createRandomHeroWithId();
        Hero hero2 = createRandomHeroWithId();
        Hero hero3 = createRandomHeroWithId();
        return List.of(hero1, hero2, hero3);
    }

    public static List<HeroDtoRequest> createListHeroesDtoRequest(){
        HeroDtoRequest hero1 = createDefaultHeroDtoRequest();
        HeroDtoRequest hero2 = createDefaultHeroDtoRequest();
        HeroDtoRequest hero3 = createDefaultHeroDtoRequest();
        return List.of(hero1, hero2, hero3);
    }

    public static Hero createDefaultHero() {
        PowerStats defaultStats = new PowerStats(50, 50, 50, 50); // Exemplo de stats
        return createHero("Default Hero", Race.HUMAN, defaultStats, true);
    }

    public static HeroDtoResponse createDefaultHeroDtoResponse() {
        PowerStats defaultStats = new PowerStats(50, 50, 50, 50); // Exemplo de stats
        return new HeroDtoResponse("1", "Default Hero", Race.HUMAN, defaultStats);
    }

    public static HeroDtoRequest createDefaultHeroDtoRequest() {
        PowerStats defaultStats = new PowerStats(50, 50, 50, 50); // Exemplo de stats
        return new HeroDtoRequest("Default Hero", Race.HUMAN, defaultStats, true);
    }
}
