package Application.Database.Controller;

import Application.Database.Enity.Club;
import Application.Database.Enity.Player;
import Application.Database.Enity.Singles;
import Application.Database.Enity.Tournament;
import Application.Database.Service.SinglesService;
import Application.Database.Service.TournamentService;
import Application.Exceptions.InstanceNotFoundException;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
class TournamentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TournamentController tournamentController;

    @MockBean
    private TournamentService tournamentService;

    private Tournament tournament;

    @BeforeEach
    void init() {
        Club club = new Club("Test club");
        tournament = new Tournament(new Date(), "Test tournament", "C", club, null, null, null);
    }

    @Test
    void all() {
        int page = 0;
        int size = 2;
        int total = 2;
        Pageable pageable = PageRequest.of(page, size);
        Page<Tournament> pageExpected = new PageImpl<>(List.of(tournament), pageable, total);
        BDDMockito.given(tournamentService.findAll(pageable)).willReturn(pageExpected);
        try {
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/tournament/all?page={page}&size={size}", page, size)
                            .accept("application/json")
                            .contentType("application/json")
            ).andExpect(MockMvcResultMatchers.jsonPath("$._embedded.tournamentDTOList[0].category", CoreMatchers.is(tournament.getCategory())))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.tournamentDTOList[0].id", CoreMatchers.is(tournament.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.tournamentDTOList[0].clubId", CoreMatchers.is(tournament.getClub().getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.tournamentDTOList[0].name", CoreMatchers.is(tournament.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.tournamentDTOList[0].doublesIDs", CoreMatchers.is(tournament.getDoubles())))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.tournamentDTOList[0].singlesIDs", CoreMatchers.is(tournament.getSingles())))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.tournamentDTOList[0].playerIDs", CoreMatchers.is(tournament.getPlayers())))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
        BDDMockito.verify(tournamentService, Mockito.atLeastOnce()).findAll(pageable);
    }

}