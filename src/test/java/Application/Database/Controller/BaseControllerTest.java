package Application.Database.Controller;

import Application.Database.DTO.ClubCreateDTO;
import Application.Database.Enity.Club;
import Application.Database.Service.ClubService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;


@SpringBootTest
@AutoConfigureMockMvc
class BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ClubService serviceMock;

    private Club club;

    private ClubCreateDTO clubCreateDTO;


    @BeforeEach
    private void init(){
        club = new Club("Tenis Club");
        clubCreateDTO = new ClubCreateDTO(club.getName());
    }

    @Test
    void findById() {
        BDDMockito.given(serviceMock.findById(club.getId()))
                .willReturn(Optional.of(club));
        try {
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/club/{id}", club.getId())
                            .accept("application/json")
                            .contentType("application/json")
            ).andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(club.getName())))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void findByIdFail() {
        BDDMockito.given(serviceMock.findById(club.getId())).willReturn(Optional.empty());
        try {
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/club/{id}", club.getId())
                            .accept("application/json")
                            .contentType("application/json")
            ).andExpect(MockMvcResultMatchers.status().isNotFound());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void save() {

        try {
            BDDMockito.given(serviceMock.create(BDDMockito.any()))
                    .willReturn(club);
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .post("/club")
                            .accept("application/json")
                            .contentType("application/json")
                            .content("{\"name\":\"Tenis Club\"}")
            ).andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(club.getName())))
                    .andExpect(MockMvcResultMatchers.status().isCreated());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void saveFail() {

        try {
            BDDMockito.given(serviceMock.create(BDDMockito.any()))
                    .willThrow(new InstanceNotFoundException());
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .post("/club")
                            .accept("application/json")
                            .contentType("application/json")
                            .content("{\"name\":\"Tenis Club\"}")
            ).andExpect(MockMvcResultMatchers.status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    void update() {
        try {
            clubCreateDTO.setName("New name");
            club.setName("New name");
            BDDMockito.given(serviceMock.update(BDDMockito.eq(club.getId()), BDDMockito.any()))
                .willReturn(club);
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .post("/club/" + club.getId())
                            .accept("application/json")
                            .contentType("application/json")
                            .content("{\"name\":\"New name\"}")
            ).andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(clubCreateDTO.getName())))
                    .andExpect(MockMvcResultMatchers.status().isAccepted());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateFail() {
        try {
            clubCreateDTO.setName("New name");
            BDDMockito.given(serviceMock.update(BDDMockito.eq(club.getId()), BDDMockito.any()))
                    .willThrow(new InstanceNotFoundException());
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .post("/club/" + club.getId())
                            .accept("application/json")
                            .contentType("application/json")
                            .content("{\"name\":\"New name\"}")
            ).andExpect(MockMvcResultMatchers.status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void remove() {
        try {
            BDDMockito.doNothing().when(serviceMock).remove(club.getId());
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .delete("/club/" + club.getId())
                            .accept("application/json")
                            .contentType("application/json")
            ).andExpect(MockMvcResultMatchers.status().isFound());
            BDDMockito.verify(serviceMock, Mockito.atLeastOnce()).remove(club.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void removeFail() {
        try {
            BDDMockito.doThrow(new InstanceNotFoundException()).when(serviceMock).remove(club.getId());
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .delete("/club/" + club.getId())
                            .accept("application/json")
                            .contentType("application/json")
            ).andExpect(MockMvcResultMatchers.status().isNotFound());
            BDDMockito.verify(serviceMock, Mockito.atLeastOnce()).remove(club.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}