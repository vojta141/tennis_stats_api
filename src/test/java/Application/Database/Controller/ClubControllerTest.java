package Application.Database.Controller;

import Application.Database.Enity.Club;
import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;
import Application.Database.Service.ClubService;
import Application.Database.Service.PlayerService;
import Application.Database.Service.TournamentService;
import Application.Exceptions.InstanceNotFoundException;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
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

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
class ClubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClubController clubController;

    @MockBean
    private ClubService clubService;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private TournamentService tournamentService;


    Club club;

    @BeforeEach
    void init(){
        club = new Club("Tenis club");
    }

    @Test
    void all() {
        int page = 0;
        int size = 2;
        int total = 1;
        Pageable pageable = PageRequest.of(page, size);
        Page<Club> pageExpected = new PageImpl<>(List.of(club), pageable, total);
        BDDMockito.given(clubService.findAll(pageable)).willReturn(pageExpected);
        try {
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/club/all?page={page}&size={size}", page, size)
                            .accept("application/json")
                            .contentType("application/json")
                ).andExpect(MockMvcResultMatchers.jsonPath("$._embedded.clubDTOList[0].name", CoreMatchers.is(club.getName())))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void clubPlayers() {
        Player player = new Player("Paul", new Date(), 15, club, null);
        BDDMockito.given(playerService.findAllByClubId(club.getId())).willReturn(List.of(player));
        try {
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/club/{id}/players", club.getId())
                            .accept("application/json")
                            .contentType("application/json")
            ).andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is(player.getName())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(player.getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].bigPoints", CoreMatchers.is(player.getBigPoints())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].clubId", CoreMatchers.is(player.getClub().getId())))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
        BDDMockito.verify(playerService, Mockito.atLeastOnce()).findAllByClubId(club.getId());
    }

    @Test
    void clubTournaments(){
        Tournament tournament = new Tournament(new Date(), "Test tournament", "C",
                club);
        try {
            BDDMockito.given(tournamentService.findTournamentsByClubId(tournament.getClub().getId()))
                    .willReturn(Collections.singletonList(tournament));
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/club/{id}/tournaments", tournament.getClub().getId())
                            .accept("application/json")
                            .contentType("application/json")
            ).andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(tournament.getId())))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is(tournament.getName())))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].category", CoreMatchers.is(tournament.getCategory())))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].clubId", CoreMatchers.is(tournament.getClub().getId())))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            BDDMockito.verify(tournamentService, Mockito.atLeastOnce()).findTournamentsByClubId(tournament.getClub().getId());
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void clubTournamentsFail(){
        try {
            BDDMockito.given(tournamentService.findTournamentsByClubId(club.getId()))
                    .willThrow(new InstanceNotFoundException());
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/club/{id}/tournaments", club.getId())
                            .accept("application/json")
                            .contentType("application/json")
            ).andExpect(MockMvcResultMatchers.status().isNotFound());
            BDDMockito.verify(tournamentService, Mockito.atLeastOnce()).findTournamentsByClubId(club.getId());
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}