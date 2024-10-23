//package br.com.gubee.interview.core.features.hero;
//
//import br.com.gubee.interview.core.features.hero.utils.HeroFactory;
//import br.com.gubee.interview.domain.Hero;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.MongoDBContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataMongoTest
//@Testcontainers
//class HeroRepositoryTest {
//
//    @Autowired
//    private HeroRepository heroRepository;
//
//    @Container
//    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0").withExposedPorts(27017);
//
//    @DynamicPropertySource
//    static void containersProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
//
//    }
//
//    @BeforeEach
//    void setUp() {
//        heroRepository.deleteAll();
//    }
//
//    @Nested
//    class FindById {
//
//
//        @Test
//        public void shouldReturnEmptyWhenHeroDoesNotExist() {
//            assertEquals(Optional.empty(), heroRepository.findById("1"));
//        }
//
//        @Test
//        public void shouldReturnHeroWhenHeroExists() {
//            Hero hero = HeroFactory.createRandomHeroWithId();
//            heroRepository.save(hero);
//
//            assertEquals(Optional.of(hero), heroRepository.findById(hero.getId()));
//        }
//
//        @Test
//        public void shouldReturnEmptyWhenIdNotExists() {
//            assertEquals(Optional.empty(), heroRepository.findById("60"));
//        }
//
//        @Test
//        public void shouldReturnEmptyWhenIdIsInvalidFormat() {
//            assertEquals(Optional.empty(), heroRepository.findById("invalid-id-format"));
//        }
//
//        @Test
//        public void shouldReturnEmptyAfterHeroIsDeleted() {
//            Hero hero = HeroFactory.createRandomHeroWithId();
//            heroRepository.save(hero);
//            heroRepository.deleteById(hero.getId());
//
//            assertEquals(Optional.empty(), heroRepository.findById(hero.getId()));
//        }
//    }
//
//    @Nested
//    class FindAll {
//
//        @Test
//        public void shouldReturnEmptyListWhenNoHeroes() {
//            assertEquals(0, heroRepository.findAll().size());
//        }
//
//        @Test
//        public void shouldReturnAllHeroes() {
//            Hero hero1 = HeroFactory.createRandomHeroWithId();
//            Hero hero2 = HeroFactory.createRandomHeroWithId();
//            heroRepository.save(hero1);
//            heroRepository.save(hero2);
//
//            assertEquals(2, heroRepository.findAll().size());
//        }
//
//        @Test
//        public void shouldReturnEmptyListAfterAllHeroesAreDeleted() {
//            Hero hero1 = HeroFactory.createRandomHeroWithId();
//            Hero hero2 = HeroFactory.createRandomHeroWithId();
//            heroRepository.save(hero1);
//            heroRepository.save(hero2);
//            heroRepository.deleteAll();
//
//            assertEquals(0, heroRepository.findAll().size());
//        }
//
//        @Test
//        public void shouldReturnListHeroesWhenFindAllById(){
//            Hero hero1 = HeroFactory.createRandomHeroWithId();
//            Hero hero2 = HeroFactory.createRandomHeroWithId();
//            heroRepository.save(hero1);
//            heroRepository.save(hero2);
//            List<Hero> heroes = heroRepository.findAllByIdIn(List.of(hero1.getId(), hero2.getId()));
//            assertEquals(2, heroes.size());
//            assertEquals(hero1, heroes.get(0));
//            assertEquals(hero2, heroes.get(1));
//        }
//
//    }
//
//    @Nested
//    class save {
//
//        @Test
//        public void shouldSaveHero() {
//            Hero hero = HeroFactory.createRandomHeroWithId();
//            heroRepository.save(hero);
//
//            assertEquals(1, heroRepository.findAll().size());
//            assertEquals(hero, heroRepository.findById(hero.getId()).get());
//        }
//
//        @Test
//        public void shouldSaveMultipleHeroes() {
//            Hero hero1 = HeroFactory.createRandomHeroWithId();
//            Hero hero2 = HeroFactory.createRandomHeroWithId();
//            heroRepository.save(hero1);
//            heroRepository.save(hero2);
//
//            assertEquals(2, heroRepository.findAll().size());
//        }
//
//        @Test
//        public void shouldUpdateHero() {
//            Hero hero = HeroFactory.createRandomHeroWithId();
//            heroRepository.save(hero);
//
//            hero.setName("Updated Name");
//            heroRepository.save(hero);
//
//            assertEquals(1, heroRepository.findAll().size());
//            assertEquals("Updated Name", heroRepository.findById(hero.getId()).get().getName());
//        }
//    }
//
//    @Nested
//    class delete {
//        @Test
//        public void shouldDeleteHero() {
//            Hero hero = HeroFactory.createRandomHeroWithId();
//            heroRepository.save(hero);
//
//            heroRepository.deleteById(hero.getId());
//
//            assertEquals(0, heroRepository.findAll().size());
//        }
//
//        @Test
//        public void shouldNotThrowExceptionWhenDeletingNonExistentHero() {
//            heroRepository.deleteById("non-existent-id");
//
//            assertEquals(0, heroRepository.findAll().size());
//        }
//
//        @Test
//        public void shouldDeleteAllHeroesByName() {
//            Hero hero1 = HeroFactory.createDefaultHero();
//            Hero hero2 = HeroFactory.createDefaultHero();
//            heroRepository.save(hero1);
//            heroRepository.save(hero2);
//
//            heroRepository.deleteAllByNameIn(List.of(hero1.getName()));
//
//
//            assertEquals(0, heroRepository.findAll().size());
//        }
//
//        @Test
//        public void shouldNotThrowExceptionWhenDeletingByInvalidIdFormat() {
//            heroRepository.deleteById("invalid-id-format");
//
//            assertEquals(0, heroRepository.findAll().size());
//        }
//
//
//
//    }
//
//
//}