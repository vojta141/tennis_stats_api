package Application.Database.Controller;

import Application.Database.Enity.Club;
import Application.Database.Enity.Doubles;
import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;
import Application.Database.Service.ClubService;
import Application.Database.Service.DoublesService;
import Application.Database.Service.PlayerService;
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
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class DoublesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DoublesController doublesController;

    @MockBean
    private DoublesService doublesService;

    private Doubles doubles;

    @BeforeEach
    void init(){
        Club club = new Club("Test club");
        Tournament tournament = new Tournament(new Date(), "Test tournament", "C", club, null, null, null);
        List<Player> players = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            players.add(new Player("Paul", new Date(), 15, club, null));
        }
        doubles = new Doubles("6:0, 6:0", players.get(0), players.get(1), players.get(2), players.get(3), tournament);
    }


    @Test
    void getAllWhereWinner() {
        Player winner = doubles.getWinner1();
        List<Doubles> result = new ArrayList<>();
        result.add(doubles);
        BDDMockito.given(doublesService.findByWinnerId(winner.getId())).willReturn(result);
        try {
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/doubles/winner/{id}", winner.getId())
                            .accept("application/json")
                            .contentType("application/json")
            ).andExpect(MockMvcResultMatchers.jsonPath("$[0].winner1ID", CoreMatchers.is(winner.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].winner2ID", CoreMatchers.is(doubles.getWinner2().getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].loser1ID", CoreMatchers.is(doubles.getLoser1().getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].loser2ID", CoreMatchers.is(doubles.getLoser2().getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].score", CoreMatchers.is(doubles.getScore())))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
        BDDMockito.verify(doublesService, Mockito.atLeastOnce()).findByWinnerId(winner.getId());
    }

    @Test
    void getAllWhereLoser() {
        Player loser = doubles.getLoser1();
        List<Doubles> result = new ArrayList<>();
        result.add(doubles);
        BDDMockito.given(doublesService.findByLoserId(loser.getId())).willReturn(result);
        try {
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/doubles/loser/{id}", loser.getId())
                            .accept("application/json")
                            .contentType("application/json")
            ).andExpect(MockMvcResultMatchers.jsonPath("$[0].winner1ID", CoreMatchers.is(loser.getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].winner2ID", CoreMatchers.is(doubles.getWinner2().getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].loser1ID", CoreMatchers.is(doubles.getLoser1().getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].loser2ID", CoreMatchers.is(doubles.getLoser2().getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].score", CoreMatchers.is(doubles.getScore())))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
        BDDMockito.verify(doublesService, Mockito.atLeastOnce()).findByLoserId(loser.getId());
    }

    @Test
    void all() {

        int page = 0;
        int size = 2;
        int total = 1;
        Pageable pageable = PageRequest.of(page, size);
        Page<Doubles> pageExpected = new PageImpl<>(List.of(doubles), pageable, total);
        BDDMockito.given(doublesService.findAll(pageable)).willReturn(pageExpected);
        try {
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/doubles/all?page={page}&size={size}", page, size)
                            .accept("application/json")
                            .contentType("application/json")
            ).andExpect(MockMvcResultMatchers.jsonPath("$._embedded.doublesDTOList[0].score", CoreMatchers.is(doubles.getScore())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.doublesDTOList[0].winner1ID", CoreMatchers.is(doubles.getWinner1().getId())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.doublesDTOList[0].winner2ID", CoreMatchers.is(doubles.getWinner2().getId())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.doublesDTOList[0].loser1ID", CoreMatchers.is(doubles.getLoser1().getId())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.doublesDTOList[0].loser2ID", CoreMatchers.is(doubles.getLoser2().getId())))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
        BDDMockito.verify(doublesService, Mockito.atLeastOnce()).findAll(pageable);
    }
}