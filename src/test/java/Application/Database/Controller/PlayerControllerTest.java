package Application.Database.Controller;

import Application.Database.Enity.Club;
import Application.Database.Enity.Doubles;
import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;
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
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.playerDTOList[0].clubId", CoreMatchers.is(players.get(0).getClub().getId())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.playerDTOList[1].clubId", CoreMatchers.is(players.get(1).getClub().getId())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.playerDTOList[2].clubId", CoreMatchers.is(players.get(2).getClub().getId())))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.playerDTOList[3].clubId", CoreMatchers.is(players.get(3).getClub().getId())))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
        BDDMockito.verify(playerService, Mockito.atLeastOnce()).findAll(pageable);
    }
}