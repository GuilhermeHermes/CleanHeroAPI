package br.com.gubee.interview.core.features.hero;


import br.com.gubee.interview.core.configuration.exception.ResourceExceptionHandler;
import br.com.gubee.interview.core.features.hero.utils.HeroServiceStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HeroControllerTest {

    private MockMvc mockMvc;
    private HeroController heroController;
    private HeroServiceStub stubHeroService;

    @BeforeEach
    public void setUp() {
        // Inicializa o StubHeroService
        stubHeroService = new HeroServiceStub();
        // Injeta o Stub no controller
        heroController = new HeroController(stubHeroService);
        // Configura o MockMvc para testar o controller
        mockMvc = MockMvcBuilders.standaloneSetup(heroController).build();
    }

    private static final String URI_REQUEST = "/api/v1/heroes";


    @Test
    public void testFindAll_Success() throws Exception {
        // Testa o comportamento do controller com o stub
        mockMvc.perform(get("/api/v1/heroes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Verifica se o status é 200 OK
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Hero One"))
                .andExpect(jsonPath("$[0].race").value("HUMAN"))
                .andExpect(jsonPath("$[0].powerStats.strength").value(50))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("Hero Two"))
                .andExpect(jsonPath("$[1].race").value("ALIEN"))
                .andExpect(jsonPath("$[1].powerStats.strength").value(55));
    }

    @Test
    public void testFindById_Success() throws Exception {
        mockMvc.perform(get("/api/v1/heroes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Verifica se o status é 200 OK
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Hero One"))
                .andExpect(jsonPath("$.race").value("HUMAN"))
                .andExpect(jsonPath("$.powerStats.strength").value(50))
                .andExpect(jsonPath("$.powerStats.agility").value(60))
                .andExpect(jsonPath("$.powerStats.dexterity").value(70))
                .andExpect(jsonPath("$.powerStats.intelligence").value(80));
    }

    @Test
    public void testFindByName_Success() throws Exception {
        mockMvc.perform(get("/api/v1/heroes/name/hero")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Verifica se o status é 200 OK
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Hero One"))
                .andExpect(jsonPath("$[0].race").value("HUMAN"))
                .andExpect(jsonPath("$[0].powerStats.strength").value(50))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("Hero Two"))
                .andExpect(jsonPath("$[1].race").value("ALIEN"))
                .andExpect(jsonPath("$[1].powerStats.strength").value(55));
    }

    @Test
    public void shouldReturnStatusOkWhenDontFindByName() throws Exception {
        mockMvc.perform(get("/api/v1/heroes/name/NotFound")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }


//    @Test
//    public void shouldReturnUpdatedHero() throws Exception {
//
//        String heroJson = """
//    {
//        "name": "Hero One",
//        "race": "HUMAN",
//        "powerStats": {
//            "strength": 50,
//            "agility": 99,
//            "dexterity": 90,
//            "intelligence": 80
//        },
//        "enabled": true
//    }
//    """;
//
//
//        mockMvc.perform(put("/api/v1/heroes/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(heroJson))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value("1"))
//                .andExpect(jsonPath("$.name").value("Hero One"))
//                .andExpect(jsonPath("$.race").value("HUMAN"))
//                .andExpect(jsonPath("$.powerStats.strength").value(50))
//                .andExpect(jsonPath("$.powerStats.agility").value(99))
//                .andExpect(jsonPath("$.powerStats.dexterity").value(90))
//                .andExpect(jsonPath("$.powerStats.intelligence").value(80));
//    }



    }






