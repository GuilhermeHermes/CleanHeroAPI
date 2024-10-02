package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.hero.utils.HeroFactory;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.dtos.HeroDtoRequest;
import br.com.gubee.interview.model.dtos.HeroDtoResponse;
import br.com.gubee.interview.model.enums.Race;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("it")
class HeroServiceIT {

    @Autowired
    private HeroService heroService;

    @Autowired
    private HeroRepository heroRepository;

    @BeforeEach
    void setUp() {
        heroRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        heroRepository.deleteAll();
    }

    @Test
    void testSaveAndFindById() {
        HeroDtoRequest request = HeroFactory.createDefaultHeroDtoRequest();
        HeroDtoResponse savedHero = heroService.save(request);

        assertNotNull(savedHero.getId());
        assertEquals("Default Hero", savedHero.getName());

        HeroDtoResponse foundHero = heroService.findById(savedHero.getId());
        assertEquals(savedHero.getId(), foundHero.getId());
        assertEquals("Default Hero", foundHero.getName());
        assertEquals(Race.HUMAN, foundHero.getRace());
    }



    @Test
    void testSaveAllAndFindAll() {
        List<HeroDtoRequest> requests = HeroFactory.createListHeroesDtoRequest();

        List<HeroDtoResponse> savedHeroes = heroService.saveAll(requests);
        assertEquals(3, savedHeroes.size());

        List<HeroDtoResponse> allHeroes = heroService.findAll();
        assertEquals(3, allHeroes.size());
        assertTrue(allHeroes.stream().anyMatch(h -> h.getName().equals("Iron Man")));
        assertTrue(allHeroes.stream().anyMatch(h -> h.getName().equals("Spider-Man")));
        assertTrue(allHeroes.stream().anyMatch(h -> h.getName().equals("Thor")));
    }

    @Test
    void testFindAllByIds() {
        List<HeroDtoRequest> requests = HeroFactory.createListHeroesDtoRequest();
        List<HeroDtoResponse> response = heroService.saveAll(requests);
        List<String> ids = Arrays.asList(response.get(0).getId(), response.get(2).getId());

        List<HeroDtoResponse> foundHeroes = heroService.findAll(ids);
        assertEquals(2, foundHeroes.size());
        assertTrue(foundHeroes.stream().anyMatch(h -> h.getName().equals("Iron Man")));
        assertTrue(foundHeroes.stream().anyMatch(h -> h.getName().equals("Thor")));
    }

    @Test
    void testCount() {
        heroService.saveAll(HeroFactory.createListHeroesDtoRequest());


        long count = heroService.count();
        assertEquals(3, count);
    }

    @Test
    void testDeleteById() {

        HeroDtoResponse savedHero = heroService.save(HeroFactory.createDefaultHeroDtoRequest());
        assertNotNull(heroService.findById(savedHero.getId()));

        heroService.delete(savedHero.getId());
        assertThrows(RuntimeException.class, () -> heroService.findById(savedHero.getId()));
    }

    @Test
    void testDeleteByNames() {
        heroService.saveAll(HeroFactory.createListHeroesDtoRequest());


        heroService.delete(Arrays.asList("Iron Man", "Thor"));

        List<HeroDtoResponse> remainingHeroes = heroService.findAll();
        assertEquals(1, remainingHeroes.size());
        assertEquals("Spider-Man", remainingHeroes.get(0).getName());
    }

    @Test
    void testDeleteAll() {
        heroService.saveAll(HeroFactory.createListHeroesDtoRequest());


        heroService.deleteAll();

        List<HeroDtoResponse> allHeroes = heroService.findAll();
        assertTrue(allHeroes.isEmpty());
    }

    @Test
    void testUpdate() {

        HeroDtoResponse savedHero = heroService.save(HeroFactory.createDefaultHeroDtoRequest());
        PowerStats updatedPowerStats = new PowerStats(0,10,10,0);
        HeroDtoRequest updateRequest = new HeroDtoRequest( "Superman", Race.ALIEN, savedHero.getPowerStats(), true);
        updateRequest.setId(savedHero.getId());

        HeroDtoResponse updatedHero = heroService.update(updateRequest);

        assertEquals(savedHero.getId(), updatedHero.getId());
        assertEquals("Superman", updatedHero.getName());
        assertEquals("ALIEN", updatedHero.getRace());
        assertEquals(0, updatedHero.getPowerStats().getStrength());
        assertEquals(10, updatedHero.getPowerStats().getAgility());
        assertEquals(10, updatedHero.getPowerStats().getDexterity());
        assertEquals(0, updatedHero.getPowerStats().getIntelligence());
    }

    @Test
    void testUpdateList() {
        List<HeroDtoRequest> heroes = HeroFactory.createListHeroesDtoRequest();
        heroService.saveAll(heroes);

        PowerStats updatedPowerStats1 = new PowerStats(0,10,10,0);
        PowerStats updatedPowerStats2 = new PowerStats(10,0,10,0);


        List<HeroDtoRequest> updateRequests = Arrays.asList(
                new HeroDtoRequest( "Superman", Race.ALIEN, updatedPowerStats1, true),
                new HeroDtoRequest( "Batman", Race.HUMAN, updatedPowerStats2, true)
        );

        updateRequests.get(0).setId(heroes.get(0).getId());
        updateRequests.get(1).setId(heroes.get(1).getId());

        List<Hero> updatedHeroes = heroService.update(updateRequests);

        assertEquals(2, updatedHeroes.size());
        assertTrue(updatedHeroes.stream().anyMatch(h -> h.getRace().equals(Race.ALIEN)));
        assertTrue(updatedHeroes.stream().anyMatch(h -> h.getRace().equals(Race.HUMAN)));
        assertEquals(0, updatedHeroes.get(0).getPowerStats().getStrength());
        assertEquals(10, updatedHeroes.get(0).getPowerStats().getAgility());
        assertEquals(10, updatedHeroes.get(0).getPowerStats().getDexterity());
        assertEquals(0, updatedHeroes.get(0).getPowerStats().getIntelligence());
    }

    @Test
    void testCompare() {
        List<HeroDtoRequest> heroes = HeroFactory.createListHeroesDtoRequest();
        List<HeroDtoResponse> response = heroService.saveAll(heroes);
        HeroDtoResponse hero1 = heroService.findById(response.get(0).getId());
        HeroDtoResponse hero2 = heroService.findById(response.get(1).getId());

        Map<String, Object> comparison = heroService.compare(hero1.getId(), hero2.getId());

        //        HeroDtoRequest hero1 = new HeroDtoRequest("Iron Man", Race.HUMAN, new PowerStats(85, 90, 95, 80), true);
        //        HeroDtoRequest hero2 = new HeroDtoRequest("Spider-Man", Race.HUMAN, new PowerStats(75, 80, 85, 70), true);

        assertNotNull(comparison);
        assertEquals(hero1.getId(), comparison.get("id1"));
        assertEquals(hero2.getId(), comparison.get("id2"));
        assertEquals(10, comparison.get("strengthDiff"));
        assertEquals(10, comparison.get("agilityDiff"));
        assertEquals(10, comparison.get("dexterityDiff"));
        assertEquals(10, comparison.get("intelligenceDiff"));


    }

    @Test
    void testFindByName() {
        List<HeroDtoRequest> heroes = HeroFactory.createListHeroesDtoRequest();
        heroService.saveAll(heroes);

        List<HeroDtoResponse> foundHeroes = heroService.findByName("Man");

        assertEquals(2, foundHeroes.size());
        assertTrue(foundHeroes.stream().anyMatch(h -> h.getName().equals("Spider-Man")));
        assertTrue(foundHeroes.stream().anyMatch(h -> h.getName().equals("Iron Man")));
    }
}
