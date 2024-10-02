package br.com.gubee.interview.core.features.hero;


import br.com.gubee.interview.core.features.hero.utils.HeroFactory;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.dtos.HeroDtoRequest;
import br.com.gubee.interview.model.dtos.HeroDtoResponse;
import br.com.gubee.interview.model.enums.Race;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static br.com.gubee.interview.model.enums.Race.ALIEN;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class HeroE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private HeroRepository heroRepository;

    private static final String BASE_URL = "/api/v1/heroes";
    private static String savedHeroId;

    @BeforeAll
    void setup() {
        heroRepository.deleteAll();
    }

    @Test
    @Order(1)
    void testCreateHero() {
        HeroDtoRequest request = HeroFactory.createDefaultHeroDtoRequest();
        request.setName("Hero000");
        request.setRace(Race.ALIEN);


        ResponseEntity<HeroDtoResponse> response = restTemplate.postForEntity(BASE_URL, request, HeroDtoResponse.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Hero000", response.getBody().getName());
        assertEquals(Race.ALIEN, response.getBody().getRace());

        savedHeroId = response.getBody().getId();
    }

    @Test
    @Order(2)
    void testGetHero() {
        ResponseEntity<HeroDtoResponse> response = restTemplate.getForEntity(BASE_URL + "/" + savedHeroId, HeroDtoResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Hero000", response.getBody().getName());
    }

    @Test
    @Order(3)
    void testUpdateHero() {
        HeroDtoRequest request = new HeroDtoRequest();
        request.setName("Hero001");
        request.setRace(Race.ALIEN);
        request.setId(savedHeroId);
        request.setPowerStats(new PowerStats(5,5,5,5));
        request.setEnabled(true);
        restTemplate.put(BASE_URL, request);

        ResponseEntity<HeroDtoResponse> response = restTemplate.getForEntity(BASE_URL + "/" + savedHeroId, HeroDtoResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Hero001", response.getBody().getName());
    }

    @Test
    @Order(4)
    void testGetAllHeroes() {
        ResponseEntity<List<HeroDtoResponse>> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<HeroDtoResponse>>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @Order(5)
    void testDeleteHero() {
        restTemplate.delete(BASE_URL + "/" + savedHeroId);

        ResponseEntity<HeroDtoResponse> response = restTemplate.getForEntity(BASE_URL + "/" + savedHeroId, HeroDtoResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
