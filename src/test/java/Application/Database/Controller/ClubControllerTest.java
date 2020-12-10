package Application.Database.Controller;

import Application.Database.Enity.Club;
import Application.Database.Enity.Player;
import Application.Database.Service.ClubService;
import Application.Database.Service.PlayerService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
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
    }
}