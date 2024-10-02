package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.hero.utils.HeroFactory;
import br.com.gubee.interview.model.Hero;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@ActiveProfiles("it")
class HeroRepositoryIT {

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private MongoTemplate mongoTemplate;



    @AfterEach
    public void cleanup() {
        heroRepository.deleteAll();
    }

    @Test
    void shouldPerformCRUDOperations() {
        // Create
        Hero hero = HeroFactory.createRandomHeroWithId();
        Hero savedHero = heroRepository.save(hero);
        assertThat(savedHero.getId()).isNotNull();

        // Read
        Hero foundHero = heroRepository.findById(savedHero.getId()).orElse(null);
        assertThat(foundHero).isNotNull();
        assertThat(foundHero.getName()).isEqualTo(hero.getName());

        // Update
        foundHero.setName("Diana of Themyscira");
        Hero updatedHero = heroRepository.save(foundHero);
        assertThat(updatedHero.getName()).isEqualTo("Diana of Themyscira");

        // Delete
        heroRepository.delete(updatedHero);
        assertThat(heroRepository.findById(updatedHero.getId())).isEmpty();
    }

    @Test
    void shouldFindAllHeroes() {
        Hero hero1 = HeroFactory.createRandomHeroWithId();
        Hero hero2 = HeroFactory.createRandomHeroWithId();
        heroRepository.saveAll(List.of(hero1, hero2));

        List<Hero> allHeroes = heroRepository.findAll();

        assertThat(allHeroes).hasSize(2);
        assertThat(allHeroes).extracting(Hero::getName).containsExactlyInAnyOrder("Iron Man", "Black Widow");
    }

    @Test
    void shouldUseMongoTemplateForComplexQueries() {
        Hero hero = HeroFactory.createDefaultHero();
        mongoTemplate.save(hero);

        Hero foundHero = mongoTemplate.findOne(
                Query.query(Criteria.where("race").regex("^HUMAN")),
                Hero.class
        );

        assertThat(foundHero).isNotNull();
        assertThat(foundHero.getName()).isEqualTo("Default Hero");
    }

}
