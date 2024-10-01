package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.hero.utils.HeroFactory;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.dtos.HeroDtoRequest;
import br.com.gubee.interview.model.dtos.HeroDtoResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class HeroMapperTest {

    private HeroMapper heroMapper = new HeroMapper();

    @Nested
    class MapToDtoResponse {


            @Test
            void shouldReturnHeroDtoResponseWhenPassHero() {
                //arrange
                Hero hero = HeroFactory.createDefaultHero();
                //act
                HeroDtoResponse response = heroMapper.mapToDtoResponse(hero);
                //assert
                assertNotNull(response);
                assertEquals(hero.getId(), response.getId());
                assertEquals(hero.getName(), response.getName());
                assertEquals(hero.getRace(), response.getRace());
                assertEquals(hero.getPowerStats(), response.getPowerStats());
                assertEquals(HeroDtoResponse.class, response.getClass());
            }

            @Test
            void shouldReturnHeroDtoResponseWhenPassHeroDtoRequest() {
                //arrange
                Hero hero = HeroFactory.createDefaultHero();
                //act
                HeroDtoResponse response = heroMapper.mapToDtoResponse(hero);
                //assert
                assertNotNull(response);
                assertEquals(hero.getId(), response.getId());
                assertEquals(hero.getName(), response.getName());
                assertEquals(hero.getRace(), response.getRace());
                assertEquals(hero.getPowerStats(), response.getPowerStats());
                assertEquals(HeroDtoResponse.class, response.getClass());
            }
    }

    @Nested
    class MapToDtoRequest {

        @Test
        void shouldReturnHeroDtoRequestWhenPassHero() {
            //arrange
            Hero hero = HeroFactory.createDefaultHero();
            //act
            var response = heroMapper.mapToDtoRequest(hero);
            //assert
            assertNotNull(response);
            assertEquals(hero.getId(), response.getId());
            assertEquals(hero.getName(), response.getName());
            assertEquals(hero.getRace(), response.getRace());
            assertEquals(hero.getPowerStats(), response.getPowerStats());
            assertEquals(HeroDtoRequest.class, response.getClass());
        }

        @Test
        void shouldReturnHeroDtoRequestWhenPassHeroDtoResponse() {
            //arrange
            Hero hero = HeroFactory.createDefaultHero();
            //act
            var response = heroMapper.mapToDtoRequest(hero);
            //assert
            assertNotNull(response);
            assertEquals(hero.getId(), response.getId());
            assertEquals(hero.getName(), response.getName());
            assertEquals(hero.getRace(), response.getRace());
            assertEquals(hero.getPowerStats(), response.getPowerStats());
            assertEquals(HeroDtoRequest.class, response.getClass());
        }
    }

    @Nested
    class MapToHero {

        @Test
        void shouldReturnHeroWhenPassHeroDtoRequest() {
            //arrange
            HeroDtoRequest hero = HeroFactory.createDefaultHeroDtoRequest();
            //act
            var response = heroMapper.mapToHero(hero);
            //assert
            assertNotNull(response);
            assertEquals(hero.getId(), response.getId());
            assertEquals(hero.getName(), response.getName());
            assertEquals(hero.getRace(), response.getRace());
            assertEquals(hero.getPowerStats(), response.getPowerStats());
            assertEquals(Hero.class, response.getClass());
        }

        @Test
        void shouldReturnHeroWhenPassHeroDtoResponse() {
            //arrange
            HeroDtoResponse hero = HeroFactory.createDefaultHeroDtoResponse();
            //act
            var response = heroMapper.mapToHero(hero);
            //assert
            assertNotNull(response);
            assertEquals(hero.getId(), response.getId());
            assertEquals(hero.getName(), response.getName());
            assertEquals(hero.getRace(), response.getRace());
            assertEquals(hero.getPowerStats(), response.getPowerStats());
            assertEquals(Hero.class, response.getClass());
        }
    }

    @Nested
    class UpdateHero {

        @Test
        void shouldReturnHeroWhenPassHeroAndHeroDtoRequest() {
            //arrange
            Hero hero = HeroFactory.createDefaultHero();
            HeroDtoRequest request = HeroFactory.createDefaultHeroDtoRequest();
            //act
            var response = heroMapper.updateHero(hero, request);
            //assert
            assertNotNull(response);
            assertEquals(request.getName(), response.getName());
            assertEquals(request.getRace(), response.getRace());
            assertEquals(request.getPowerStats(), response.getPowerStats());
            assertEquals(request.isEnabled(), response.isEnabled());
        }
    }

    @Nested
    class MapToDtoResponseList {

        @Test
        void shouldReturnListHeroDtoResponseWhenPassListHero() {
            //arrange
            var heroes = HeroFactory.createListHeroes();
            //act
            var response = heroMapper.mapToDtoResponseList(heroes);
            //assert
            assertNotNull(response);
            assertEquals(heroes.size(), response.size());
            assertEquals(HeroDtoResponse.class, response.get(0).getClass());
        }



    }

    @Nested
    class MapToHeroList {

        @Test
        void shouldReturnListHeroWhenPassListHeroDtoRequest() {
            //arrange
            var heroes = HeroFactory.createListHeroesDtoRequest();
            //act
            var response = heroMapper.mapToHeroList(heroes);
            //assert
            assertNotNull(response);
            assertEquals(heroes.size(), response.size());
            assertEquals(Hero.class, response.get(0).getClass());
        }
    }

    @Nested
    class mapToDtoResponseList {

        @Test
        void shouldReturnListHeroDtoResponseWhenPassListHero() {
            //arrange
            var heroes = HeroFactory.createListHeroes();
            //act
            var response = heroMapper.mapToDtoResponseList(heroes);
            //assert
            assertNotNull(response);
            assertEquals(heroes.size(), response.size());
            assertEquals(HeroDtoResponse.class, response.get(0).getClass());
        }
    }
}