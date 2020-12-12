package Application.Database.Controller;

import Application.Database.DTO.PlayerDTO;
import Application.Database.Enity.*;
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

import java.util.*;

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

    @Test
    void getTournamentDoubles(){
        int page = 0;
        int size = 2;
        int total = 2;
        Pageable pageable = PageRequest.of(page, size);
        List<Player> players = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            players.add(new Player("Paul" + i, new Date(), 1 + i, tournament.getClub(), null));
        }
        Doubles doubles = new Doubles("6:0, 6:0", players.get(0), players.get(1), players.get(2), players.get(3), tournament);
        Page<Doubles> pageExpected = new PageImpl<>(List.of(doubles), pageable, 1);
        tournament.setDoubles(new HashSet<>(Collections.singletonList(doubles)));
        try {
            BDDMockito.given(tournamentService.getDoubles(tournament.getId(), pageable)).willReturn(pageExpected);
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/tournament/{id}/doubles?page={page}&size={size}", tournament.getId(), page, size)
                            .accept("application/json")
                            .contentType("application/json")
            ).andExpect(MockMvcResultMatchers.jsonPath("$._embedded.doublesDTOList[0].score", CoreMatchers.is(doubles.getScore())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.doublesDTOList[0].loser1Id", CoreMatchers.is(doubles.getLoser1().getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.doublesDTOList[0].loser2Id", CoreMatchers.is(doubles.getLoser2().getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.doublesDTOList[0].winner1Id", CoreMatchers.is(doubles.getWinner1().getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.doublesDTOList[0].winner2Id", CoreMatchers.is(doubles.getWinner2().getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.doublesDTOList[0].tournamentId", CoreMatchers.is(doubles.getTournament().getId())))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            BDDMockito.verify(tournamentService, Mockito.atLeastOnce()).getDoubles(tournament.getId(), pageable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getTournamentSingles(){
        int page = 0;
        int size = 2;
        int total = 2;
        Pageable pageable = PageRequest.of(page, size);
        List<Player> players = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            players.add(new Player("Paul" + i, new Date(), 1 + i, tournament.getClub(), null));
        }
        Singles singles = new Singles("6:0, 6:0", players.get(0), players.get(1), tournament);
        Page<Singles> pageExpected = new PageImpl<>(List.of(singles), pageable, 1);
        tournament.setSingles(new HashSet<>(Collections.singletonList(singles)));
        try {
            BDDMockito.given(tournamentService.getSingles(tournament.getId(), pageable)).willReturn(pageExpected);
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/tournament/{id}/singles?page={page}&size={size}", tournament.getId(), page, size)
                            .accept("application/json")
                            .contentType("application/json")
            ).andExpect(MockMvcResultMatchers.jsonPath("$._embedded.singlesDTOList[0].score", CoreMatchers.is(singles.getScore())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.singlesDTOList[0].loserId", CoreMatchers.is(singles.getLoser().getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.singlesDTOList[0].winnerId", CoreMatchers.is(singles.getWinner().getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.singlesDTOList[0].tournamentId", CoreMatchers.is(singles.getTournament().getId())))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            BDDMockito.verify(tournamentService, Mockito.atLeastOnce()).getSingles(tournament.getId(), pageable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}