package Application.Database.Controller;

import Application.Database.Enity.*;
import Application.Database.Service.PlayerService;
import Application.Database.Service.SinglesService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class SinglesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SinglesController playerController;

    @MockBean
    private SinglesService singlesService;

    private Singles singles;

    @BeforeEach
    void init(){
        Club club = new Club("Test club");
        Tournament tournament = new Tournament(new Date(), "Test tournament", "C", club, null, null, null);
        List<Player> players = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            players.add(new Player("Paul", new Date(), 15, club, null));
        }
        singles = new Singles("6:0, 6:0", players.get(0), players.get(1), tournament);
    }

    @Test
    void all() {
        int page = 0;
        int size = 2;
        int total = 1;
        Pageable pageable = PageRequest.of(page, size);
        Page<Singles> pageExpected = new PageImpl<>(List.of(singles), pageable, total);
        BDDMockito.given(singlesService.findAll(pageable)).willReturn(pageExpected);
        try {
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/singles/all?page={page}&size={size}", page, size)
                            .accept("application/json")
                            .contentType("application/json")
            ).andExpect(MockMvcResultMatchers.jsonPath("$._embedded.singlesDTOList[0].score", CoreMatchers.is(singles.getScore())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.singlesDTOList[0].winnerId", CoreMatchers.is(singles.getWinner().getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.singlesDTOList[0].loserId", CoreMatchers.is(singles.getLoser().getId())))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
        BDDMockito.verify(singlesService, Mockito.atLeastOnce()).findAll(pageable);
    }

}