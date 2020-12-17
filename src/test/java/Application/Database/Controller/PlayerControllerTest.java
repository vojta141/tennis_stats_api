package Application.Database.Controller;

import Application.Database.Enity.*;
import Application.Database.Service.DoublesService;
import Application.Database.Service.PlayerService;
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
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlayerController playerController;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private TournamentService tournamentService;

    private List<Player> players;

    @BeforeEach
    void init(){
        Club club = new Club("Test club");
        players = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            players.add(new Player("Paul" + i, new Date(), 15 + i, club, null));
        }
    }

    @Test
    void all() {
        int page = 0;
        int size = 4;
        int total = 4;
        Pageable pageable = PageRequest.of(page, size);
        Page<Player> pageExpected = new PageImpl<>(players, pageable, total);
        BDDMockito.given(playerService.findAll(pageable)).willReturn(pageExpected);
        try {
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/player/all?page={page}&size={size}", page, size)
                            .accept("application/json")
                            .contentType("application/json")
            ).andExpect(MockMvcResultMatchers.jsonPath("$._embedded.playerDTOList[0].name", CoreMatchers.is(players.get(0).getName())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.playerDTOList[1].name", CoreMatchers.is(players.get(1).getName())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.playerDTOList[2].name", CoreMatchers.is(players.get(2).getName())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.playerDTOList[3].name", CoreMatchers.is(players.get(3).getName())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.playerDTOList[0].bigPoints", CoreMatchers.is(players.get(0).getBigPoints())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.playerDTOList[1].bigPoints", CoreMatchers.is(players.get(1).getBigPoints())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.playerDTOList[2].bigPoints", CoreMatchers.is(players.get(2).getBigPoints())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.playerDTOList[3].bigPoints", CoreMatchers.is(players.get(3).getBigPoints())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.playerDTOList[0].id", CoreMatchers.is(players.get(0).getId())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.playerDTOList[1].id", CoreMatchers.is(players.get(1).getId())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.playerDTOList[2].id", CoreMatchers.is(players.get(2).getId())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.playerDTOList[3].id", CoreMatchers.is(players.get(3).getId())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.playerDTOList[0].clubID", CoreMatchers.is(players.get(0).getClub().getId())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.playerDTOList[1].clubID", CoreMatchers.is(players.get(1).getClub().getId())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.playerDTOList[2].clubID", CoreMatchers.is(players.get(2).getClub().getId())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.playerDTOList[3].clubID", CoreMatchers.is(players.get(3).getClub().getId())))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
        BDDMockito.verify(playerService, Mockito.atLeastOnce()).findAll(pageable);
    }

    @Test
    void getPlayerTournaments(){
        int page = 0;
        int size = 10;
        Tournament tournament = new Tournament(new Date(), "Test tournament", "C", players.get(0).getClub());
        List<Tournament> tournaments = new ArrayList<>();
        tournaments.add(tournament);
        Pageable pageable = PageRequest.of(page, size);
        Page<Tournament> expected = new PageImpl<>(tournaments, pageable, tournaments.size());
        try {
            BDDMockito.given(playerService.getTournaments(players.get(0).getId(), pageable)).willReturn(expected);
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/player/{id}/tournaments", players.get(0).getId(), page, size)
                            .accept("application/json")
                            .contentType("application/json")
            ).andExpect(MockMvcResultMatchers.jsonPath("$._embedded.tournamentDTOList[0].name", CoreMatchers.is(tournament.getName())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.tournamentDTOList[0].category", CoreMatchers.is(tournament.getCategory())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.tournamentDTOList[0].clubID", CoreMatchers.is(players.get(0).getClub().getId())))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getPlayerTournamentsFail(){
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        try {
            BDDMockito.given(playerService.getTournaments(players.get(0).getId(), pageable)).willThrow(new InstanceNotFoundException());
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/player/{id}/tournaments", players.get(0).getId(), page, size)
                            .accept("application/json")
                            .contentType("application/json")
            ).andExpect(MockMvcResultMatchers.status().isNotFound());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getDoublesAtTournament(){
        Tournament tournament = new Tournament(new Date(), "Test tournament", "C", players.get(0).getClub());
        List<Doubles> doubles = new ArrayList<>();
        doubles.add(new Doubles("6:0", players.get(0), players.get(1), players.get(2), players.get(3), tournament));
        doubles.add(new Doubles("6:1", players.get(0), players.get(1), players.get(2), players.get(3), tournament));
        try {
            BDDMockito.given(tournamentService.getDoublesOfPlayer(tournament.getId(), players.get(0).getId())).willReturn(doubles);
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/player/{playerID}/tournaments/{tournamentID}/doubles", players.get(0).getId(), tournament.getId())
                            .accept("application/json")
                            .contentType("application/json")
            ).andExpect(MockMvcResultMatchers.jsonPath("$[0].score", CoreMatchers.is(doubles.get(0).getScore())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].winner1ID", CoreMatchers.is(doubles.get(0).getWinner1().getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].winner2ID", CoreMatchers.is(doubles.get(0).getWinner2().getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].loser1ID", CoreMatchers.is(doubles.get(0).getLoser1().getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].loser2ID", CoreMatchers.is(doubles.get(0).getLoser2().getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].score", CoreMatchers.is(doubles.get(1).getScore())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].winner1ID", CoreMatchers.is(doubles.get(1).getWinner1().getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].winner2ID", CoreMatchers.is(doubles.get(1).getWinner2().getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].loser1ID", CoreMatchers.is(doubles.get(1).getLoser1().getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].loser2ID", CoreMatchers.is(doubles.get(1).getLoser2().getId())))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getDoublesAtTournamentFail(){
        Tournament tournament = new Tournament(new Date(), "Test tournament", "C", players.get(0).getClub());
        try {
            BDDMockito.given(tournamentService.getDoublesOfPlayer(tournament.getId(), players.get(0).getId())).willThrow(new InstanceNotFoundException());
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/player/{playerID}/tournaments/{tournamentID}/doubles", players.get(0).getId(), tournament.getId())
                            .accept("application/json")
                            .contentType("application/json")
            ).andExpect(MockMvcResultMatchers.status().isNotFound());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getSinglesAtTournament(){
        Tournament tournament = new Tournament(new Date(), "Test tournament", "C", players.get(0).getClub());
        List<Singles> singles = new ArrayList<>();
        singles.add(new Singles("6:0", players.get(0), players.get(1), tournament));
        singles.add(new Singles("6:1", players.get(0), players.get(1), tournament));
        try {
            BDDMockito.given(tournamentService.getSinglesOfPlayer(tournament.getId(), players.get(0).getId())).willReturn(singles);
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/player/{playerID}/tournaments/{tournamentID}/singles", players.get(0).getId(), tournament.getId())
                            .accept("application/json")
                            .contentType("application/json")
            ).andExpect(MockMvcResultMatchers.jsonPath("$[0].score", CoreMatchers.is(singles.get(0).getScore())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].winnerID", CoreMatchers.is(singles.get(0).getWinner().getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].loserID", CoreMatchers.is(singles.get(0).getLoser().getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].score", CoreMatchers.is(singles.get(1).getScore())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].winnerID", CoreMatchers.is(singles.get(1).getWinner().getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].loserID", CoreMatchers.is(singles.get(1).getLoser().getId())))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getSinglesAtTournamentFail(){
        Tournament tournament = new Tournament(new Date(), "Test tournament", "C", players.get(0).getClub());
        try {
            BDDMockito.given(tournamentService.getSinglesOfPlayer(tournament.getId(), players.get(0).getId())).willThrow(new InstanceNotFoundException());
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/player/{playerID}/tournaments/{tournamentID}/singles", players.get(0).getId(), tournament.getId())
                            .accept("application/json")
                            .contentType("application/json")
            ).andExpect(MockMvcResultMatchers.status().isNotFound());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}