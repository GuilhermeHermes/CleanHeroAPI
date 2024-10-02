package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.hero.HeroRepository;
import br.com.gubee.interview.core.features.hero.utils.HeroFactory;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.dtos.HeroDtoRequest;
import br.com.gubee.interview.model.dtos.HeroDtoResponse;
import br.com.gubee.interview.model.enums.Race;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("it")
public class HeroControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HeroRepository heroRepository;
    @Autowired
    private HeroController heroController;

    @BeforeEach
    @AfterEach
    void cleanup() {
        heroRepository.deleteAll();
    }

    String URI_REQUEST = "/api/v1/heroes";
    
    @Test
    void testSaveHero() throws Exception {
        HeroDtoRequest request = HeroFactory.createDefaultHeroDtoRequest();
        String requestJson = objectMapper.writeValueAsString(request);
        heroController.insert(request);
        MvcResult result = mockMvc.perform(post(URI_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();


        HeroDtoResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), HeroDtoResponse.class);
        assertNotNull(response.getId());
        assertEquals("Default Hero", response.getName());
        assertEquals(Race.HUMAN, response.getRace());
    }
    @Test
    void testGetAllHeroes() throws Exception {
        // Create and save a hero
        HeroDtoRequest request = HeroFactory.createDefaultHeroDtoRequest();
        heroController.insert(request);

        MvcResult result = mockMvc.perform(get(URI_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<HeroDtoResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<HeroDtoResponse>>(){});
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        assertEquals("Default Hero", response.get(0).getName());
    }

    @Test
    void testGetHeroById() throws Exception {
        // Create and save a hero
        HeroDtoRequest request = HeroFactory.createDefaultHeroDtoRequest();
        HeroDtoResponse savedHero = heroController.insert(request).getBody();

        MvcResult result = mockMvc.perform(get(URI_REQUEST + "/" + savedHero.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        HeroDtoResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), HeroDtoResponse.class);
        assertEquals(savedHero.getId(), response.getId());
        assertEquals("Default Hero", response.getName());
    }

    @Test
    void testUpdateHero() throws Exception {
        // Create and save a hero
        HeroDtoRequest request = HeroFactory.createDefaultHeroDtoRequest();
        HeroDtoResponse savedHero = heroController.insert(request).getBody();

        // Update the hero
        HeroDtoRequest updateRequest = new HeroDtoRequest();
        updateRequest.setId(savedHero.getId());
        updateRequest.setName("Updated Hero");
        updateRequest.setRace(Race.ALIEN);
        updateRequest.setPowerStats(savedHero.getPowerStats());
        String updateRequestJson = objectMapper.writeValueAsString(updateRequest);

        MvcResult result = mockMvc.perform(put(URI_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequestJson))
                .andExpect(status().isOk())
                .andReturn();

        HeroDtoResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), HeroDtoResponse.class);
        assertEquals(savedHero.getId(), response.getId());
        assertEquals("Updated Hero", response.getName());
        assertEquals(Race.ALIEN, response.getRace());
    }

    @Test
    void testDeleteHero() throws Exception {
        // Create and save a hero
        HeroDtoRequest request = HeroFactory.createDefaultHeroDtoRequest();
        HeroDtoResponse savedHero = heroController.insert(request).getBody();

        mockMvc.perform(delete(URI_REQUEST + "/" + savedHero.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify the hero is deleted
        mockMvc.perform(get(URI_REQUEST + "/" + savedHero.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}