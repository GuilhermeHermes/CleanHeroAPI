package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.hero.utils.HeroFactory;
import br.com.gubee.interview.model.Hero;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HeroControllerTest {

    @Mock
    private HeroService heroService;

    @InjectMocks
    private HeroController heroController;

    @Captor
    ArgumentCaptor<String> heroIdCaptor;

    @Nested
    class ListHeroes{

        @Test
        void shouldReturnHttpOk() {
            //arrange
        doReturn(HeroFactory.createListHeroes()).when(heroService).findAll();
            //act
        var response = heroController.listHeroes();
            //assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        void shouldReturnResponseBodyCorrectly() {
            //arrange
        var heroes = HeroFactory.createListHeroes();
            doReturn(heroes).when(heroService).findAll();
            //act
        var response = heroController.listHeroes();
            //assert
        assertEquals(heroes, response.getBody());
        }
    }

    @Nested
    class FindById{

        @Test
        void shouldReturnHttpOk() {
        //arrange
        var hero = HeroFactory.createDefaultHeroDtoResponse();
        doReturn(hero).when(heroService).findById(heroIdCaptor.capture());
        //act
        var response = heroController.findById("1");
        //assert
        assertEquals(heroIdCaptor.getValue(), "1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        void shouldReturnResponseBodyCorrectly() {
            //arrange
            var hero = HeroFactory.createDefaultHeroDtoResponse();
            doReturn(hero).when(heroService).findById(anyString());
            //act
            var response = heroController.findById("1");
            //assert
        assertEquals(hero, response.getBody());
        }
    }

    @Nested
    class compare{

        @Test
        void shouldPassCorrectParamsToService() {
            //arrange
            var hero = HeroFactory.createDefaultHeroDtoResponse();
            doReturn(hero).when(heroService).compare(heroIdCaptor.capture(), heroIdCaptor.capture());
            //act
            var response = heroController.compare("1", "2");
            //assert
            assertEquals(heroIdCaptor.getAllValues().get(0), "1");
            assertEquals(heroIdCaptor.getAllValues().get(1), "2");
        }

        @Test
        void shouldReturnHttpOk() {
            List<Hero> heroes = HeroFactory.createListHeroes();
            Map<String, Object> compareResponse = new HashMap<>();
            compareResponse.put("hero1", heroes.get(0).getId());
            compareResponse.put("hero2", heroes.get(1).getId());
            Integer strengthDiff = heroes.get(0).getPowerStats().getStrength() - heroes.get(1).getPowerStats().getStrength();
            Integer agilityDiff = heroes.get(0).getPowerStats().getAgility() - heroes.get(1).getPowerStats().getAgility();
            Integer dexterityDiff = heroes.get(0).getPowerStats().getDexterity() - heroes.get(1).getPowerStats().getDexterity();
            Integer intelligenceDiff = heroes.get(0).getPowerStats().getIntelligence() - heroes.get(1).getPowerStats().getIntelligence();
            compareResponse.put("strengthDiff", strengthDiff);
            compareResponse.put("agilityDiff", agilityDiff);
            compareResponse.put("dexterityDiff", dexterityDiff);
            compareResponse.put("intelligenceDiff", intelligenceDiff);

            doReturn(compareResponse).when(heroService).compare(heroIdCaptor.capture(), heroIdCaptor.capture());
            //act
            var response = heroController.compare("1", "2");
            //assert
            assertEquals(heroIdCaptor.getAllValues().get(0), "1");
            assertEquals(heroIdCaptor.getAllValues().get(1), "2");
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        void shouldReturnResponseBodyCorrectly() {
            //arrange
            List<Hero> heroes = HeroFactory.createListHeroes();
            Map<String, Object> compareResponse = new HashMap<>();
            compareResponse.put("hero1", heroes.get(0).getId());
            compareResponse.put("hero2", heroes.get(1).getId());
            Integer strengthDiff = heroes.get(0).getPowerStats().getStrength() - heroes.get(1).getPowerStats().getStrength();
            Integer agilityDiff = heroes.get(0).getPowerStats().getAgility() - heroes.get(1).getPowerStats().getAgility();
            Integer dexterityDiff = heroes.get(0).getPowerStats().getDexterity() - heroes.get(1).getPowerStats().getDexterity();
            Integer intelligenceDiff = heroes.get(0).getPowerStats().getIntelligence() - heroes.get(1).getPowerStats().getIntelligence();
            compareResponse.put("strengthDiff", strengthDiff);
            compareResponse.put("agilityDiff", agilityDiff);
            compareResponse.put("dexterityDiff", dexterityDiff);
            compareResponse.put("intelligenceDiff", intelligenceDiff);

            doReturn(compareResponse).when(heroService).compare(heroIdCaptor.capture(), heroIdCaptor.capture());
            //act
            var response = heroController.compare("1", "2");
            //assert

            assertNotNull(response.getBody().get("hero1"));
            assertNotNull(response.getBody().get("hero2"));
            assertNotNull(response.getBody().get("strengthDiff"));
            assertNotNull(response.getBody().get("agilityDiff"));
            assertNotNull(response.getBody().get("dexterityDiff"));
            assertNotNull(response.getBody().get("intelligenceDiff"));


            assertEquals(compareResponse, response.getBody());
        }

    }



}