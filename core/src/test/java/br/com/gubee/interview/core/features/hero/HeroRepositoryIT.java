package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.hero.utils.HeroFactory;
import br.com.gubee.interview.model.Hero;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@ActiveProfiles("it")
public class HeroRepositoryIT {

    @Autowired
    private HeroRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Test
    public void testSaveAndFindHero() {
        HeroFactory heroFactory = new HeroFactory();
        Hero hero1 = heroFactory.createRandomHeroWithId();
        Hero hero2 = heroFactory.createRandomHeroWithId();
        repository.save(hero1);
        repository.save(hero2);

        assertEquals(Optional.of(hero1), repository.findById(hero1.getId()));
        assertEquals(Optional.of(hero2), repository.findById(hero2.getId()));
        assertEquals(Optional.empty(), repository.findById("3"));
    }
}
