package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.hero.utils.HeroFactory;
import br.com.gubee.interview.model.Hero;
import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Testcontainers
class HeroRepositoryTest {

    @Autowired
    private HeroRepository heroRepository;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0").withExposedPorts(27017);

    @DynamicPropertySource
    static void containersProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
                                                                                                                          
    }

    @BeforeEach
    void setUp() {
        heroRepository.deleteAll();
    }

    @Test
    void shouldSaveAndRetrieveHero() {
        // Given
        HeroFactory heroFactory = new HeroFactory();
        Hero hero = heroFactory.createRandomHeroWithId();
        // When
        Hero savedHero = heroRepository.save(hero);
        // Then
        List<Hero> heroes = heroRepository.findAll();
        assertEquals(1, heroes.size());
        assertEquals(hero, heroes.get(0));
    }


}