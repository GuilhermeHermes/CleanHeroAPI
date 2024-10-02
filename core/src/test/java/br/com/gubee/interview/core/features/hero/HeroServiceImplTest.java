package br.com.gubee.interview.core.features.hero;


import br.com.gubee.interview.core.features.hero.exception.ObjectNotFoundException;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.dtos.HeroDtoRequest;
import br.com.gubee.interview.model.dtos.HeroDtoResponse;
import br.com.gubee.interview.model.enums.Race;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class HeroServiceImplTest {

    @Mock
    private HeroRepository heroRepository;

    @Mock
    private HeroMapper heroMapper;


    private HeroServiceImpl heroService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
         heroService = new HeroServiceImpl(heroRepository, heroMapper);
    }

    @Test
    public void testFindAll() {
        List<Hero> heroes = createListHeroes();

        when(heroRepository.findAll()).thenReturn(heroes);
        when(heroMapper.mapToDtoResponseList(heroes)).thenReturn(Arrays.asList(
                new HeroDtoResponse(heroes.get(0).getId(), heroes.get(0).getName(), heroes.get(0).getRace(), heroes.get(0).getPowerStats()),
                new HeroDtoResponse(heroes.get(1).getId(), heroes.get(1).getName(), heroes.get(1).getRace(), heroes.get(1).getPowerStats())
        ));

        List<HeroDtoResponse> heroesResponse = heroService.findAll();

        assertEquals(2, heroesResponse.size());
        assertEquals(heroes.get(0).getId(), heroesResponse.get(0).getId());
        assertEquals(heroes.get(1).getId(), heroesResponse.get(1).getId());
    }

    @Test
    public void testFindAllByIds(){
        List<Hero> heroes = createListHeroes();
        List<String> ids = Arrays.asList("1", "2");

        when(heroRepository.findAllByIdIn(ids)).thenReturn(heroes);
        when(heroMapper.mapToDtoResponseList(heroes)).thenReturn(Arrays.asList(
                new HeroDtoResponse(heroes.get(0).getId(), heroes.get(0).getName(), heroes.get(0).getRace(), heroes.get(0).getPowerStats()),
                new HeroDtoResponse(heroes.get(1).getId(), heroes.get(1).getName(), heroes.get(1).getRace(), heroes.get(1).getPowerStats())));

        List<HeroDtoResponse> heroesResponse = heroService.findAll(ids);

        assertEquals(2, heroesResponse.size());
        assertEquals(heroes.get(0).getId(), heroesResponse.get(0).getId());
        assertEquals(heroes.get(1).getId(), heroesResponse.get(1).getId());

    }


    @Test
    public void shouldReturnHeroDtoResponseWhenFindById() {
        List<Hero> heroes = createListHeroes();

        when(heroRepository.findById("1")).thenReturn(Optional.ofNullable(heroes.get(0)));
        when(heroMapper.mapToDtoResponse(heroes.get(0))).thenReturn(new HeroDtoResponse(heroes.get(0).getId().toString(), heroes.get(0).getName(), heroes.get(0).getRace(), heroes.get(0).getPowerStats()));

        HeroDtoResponse hero = heroService.findById("1");


        assertEquals("1", hero.getId());
        assertEquals("Superman", hero.getName());
        assertEquals(Race.ALIEN, hero.getRace());
        assertEquals(100, hero.getPowerStats().getStrength());
        assertEquals(100, hero.getPowerStats().getAgility());
        assertEquals(50, hero.getPowerStats().getDexterity());
        assertEquals(50, hero.getPowerStats().getIntelligence());
    }

    @Test
    public void shouldReturnObjectNotFoundWhenFindByIdDontMatch() {
        when(heroRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> heroService.findById("1"));
    }


    @Test
    public void shouldReturnHeroDtoResponseWhenFindByName() {
        List<Hero> heroes = createListHeroes();

        when(heroRepository.findByNameContainingIgnoreCase("Superman")).thenReturn(heroes);
        when(heroMapper.mapToDtoResponseList(heroes)).thenReturn(Arrays.asList(new HeroDtoResponse(), new HeroDtoResponse()));

        List<HeroDtoResponse> heroesResponse = heroService.findByName("Superman");

        assertEquals(2, heroesResponse.size());
    }

    @Test
    public void shouldReturnVoidBodyWhenFindByNameDontMatch(){
        List<Hero> heroes = new ArrayList<>();

        when(heroRepository.findByNameContainingIgnoreCase("test")).thenReturn(heroes);
        when(heroMapper.mapToDtoResponseList(heroes)).thenReturn(new ArrayList<>());

        List<HeroDtoResponse> heroesResponse = heroService.findByName("Superman");
        assertEquals(heroesResponse.size(), 0);
    }

    @Test
    public void shouldReturnHeroDtoResponseWhenInsert() {
        Hero hero = createHero();
        HeroDtoRequest heroDtoRequest = heroMapper.mapToDtoRequest(hero);
        HeroDtoResponse expectedResponse = createHeroDtoResponse();

        when(heroMapper.mapToHero(heroDtoRequest)).thenReturn(hero);
        when(heroRepository.save(hero)).thenReturn(hero);
        when(heroMapper.mapToDtoResponse(hero)).thenReturn(expectedResponse);

        HeroDtoResponse heroResponse = heroService.save(heroDtoRequest);

        assertEquals(expectedResponse.getId(), heroResponse.getId());
        assertEquals(expectedResponse.getName(), heroResponse.getName());
        assertEquals(expectedResponse.getRace(), heroResponse.getRace());
        assertEquals(expectedResponse.getPowerStats(), heroResponse.getPowerStats());
    }

    @Test
    public void testCompareHeroes() {
        List<Hero> heroes = createListHeroes();

        when(heroRepository.findById("1")).thenReturn(Optional.ofNullable(heroes.get(0)));
        when(heroRepository.findById("2")).thenReturn(Optional.ofNullable(heroes.get(1)));
        when(heroMapper.mapToDtoResponse(heroes.get(0))).thenReturn(new HeroDtoResponse(heroes.get(0).getId().toString(), heroes.get(0).getName(), heroes.get(0).getRace(), heroes.get(0).getPowerStats()));
        when(heroMapper.mapToDtoResponse(heroes.get(1))).thenReturn(new HeroDtoResponse(heroes.get(1).getId().toString(), heroes.get(1).getName(), heroes.get(1).getRace(), heroes.get(1).getPowerStats()));


        Map<String, Object> result = heroService.compare("1", "2");
        assertEquals("1", result.get("id1"));
        assertEquals("2", result.get("id2"));
        assertEquals(50, result.get("strengthDiff"));
        assertEquals(50, result.get("agilityDiff"));
        assertEquals(-50, result.get("dexterityDiff"));
        assertEquals(-50, result.get("intelligenceDiff"));
    }

    @Test
    public void shouldReturnSuccessWhenNegativeParams(){
        List<Hero> heroes = createListHeroes();
        heroes.getFirst().getPowerStats().setAgility(-100);
        heroes.getFirst().getPowerStats().setDexterity(-100);
        heroes.getFirst().getPowerStats().setIntelligence(-100);
        heroes.getFirst().getPowerStats().setStrength(-100);
        heroes.get(1).getPowerStats().setAgility(-50);
        heroes.get(1).getPowerStats().setDexterity(-50);
        heroes.get(1).getPowerStats().setIntelligence(-50);
        heroes.get(1).getPowerStats().setStrength(-50);


        when(heroRepository.findById("1")).thenReturn(Optional.ofNullable(heroes.get(0)));
        when(heroRepository.findById("2")).thenReturn(Optional.ofNullable(heroes.get(1)));
        when(heroMapper.mapToDtoResponse(heroes.get(0))).thenReturn(new HeroDtoResponse(heroes.get(0).getId().toString(), heroes.get(0).getName(), heroes.get(0).getRace(), heroes.get(0).getPowerStats()));
        when(heroMapper.mapToDtoResponse(heroes.get(1))).thenReturn(new HeroDtoResponse(heroes.get(1).getId().toString(), heroes.get(1).getName(), heroes.get(1).getRace(), heroes.get(1).getPowerStats()));


        Map<String, Object> result = heroService.compare("1", "2");
        assertEquals("1", result.get("id1"));
        assertEquals("2", result.get("id2"));
        assertEquals(-50, result.get("strengthDiff"));
        assertEquals(-50, result.get("agilityDiff"));
        assertEquals(-50, result.get("dexterityDiff"));
        assertEquals(-50, result.get("intelligenceDiff"));
    }

    @Test
    public void shouldReturnObjectNotFoundWhenCompareHeroesDontMatch() {
        when(heroRepository.findById("1")).thenReturn(Optional.empty());
        when(heroRepository.findById("2")).thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> heroService.compare("1", "2"));
    }




    private List<Hero> createListHeroes(){

        PowerStats powerStats1 = new PowerStats(100, 100, 50, 50);
        PowerStats powerStats2 = new PowerStats(50, 50, 100, 100);

        Hero hero1 = new Hero("1", "Superman", Race.ALIEN, powerStats1, true);
        Hero hero2 = new Hero( "2", "Batman", Race.HUMAN, powerStats2, true);
        return new ArrayList<>(Arrays.asList(hero1, hero2));
    }

    private Hero createHero() {
        PowerStats powerStats = new PowerStats(100, 100, 50, 50);
        Hero hero = new Hero("1", "Superman", Race.ALIEN, powerStats, true);
        return hero;
    }

    private HeroDtoResponse createHeroDtoResponse() {
        PowerStats powerStats = new PowerStats(100, 100, 50, 50);

        return new HeroDtoResponse("1", "Superman", Race.ALIEN, powerStats);
    }



}




