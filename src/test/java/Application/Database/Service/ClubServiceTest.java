package Application.Database.Service;

import Application.Database.DTO.ClubCreateDTO;
import Application.Database.DTO.ClubDTO;
import Application.Database.Enity.Club;
import Application.Database.Repository.ClubRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@SpringBootTest
class ClubServiceTest extends ServiceTest{

    @Autowired
    private ClubService clubService;

    @MockBean
    private ClubRepository clubRepository;

    @Test
    void findById(){
        Club club = new Club("Tenis club");
        findByIdTest(club, clubRepository, clubService);
    }

    @Test
    void findByNameTest() {
        Club club = new Club("Tenis club");
        BDDMockito.given(clubRepository.findByName(club.getName())).willReturn(Optional.of(club));
        Assertions.assertEquals(Optional.of(club), clubService.findByName(club.getName()));
        Mockito.verify(clubRepository, Mockito.atLeastOnce()).findByName(club.getName());
    }

    @Test
    void findAll(){
        List<Club> clubs = new ArrayList<>();
        List<ClubDTO> clubDTOs = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Club tmp = new Club("Club no." + i);
            clubs.add(tmp);
            clubDTOs.add(new ClubDTO(tmp.getId(), tmp.getName()));
        }
        findAllTest(clubs, clubDTOs, clubRepository, clubService);
    }

    @Test
    void create(){
        Club club = new Club("Tenis");
        ClubDTO clubDTO = new ClubDTO(club.getId(), club.getName());
        ClubCreateDTO clubSrc = new ClubCreateDTO(club.getName());
        createTest(club, clubDTO, clubSrc, clubRepository, clubService);
    }

     @Test
    void update(){
         Club club = new Club("Tenis");
         ClubDTO clubDTO = new ClubDTO(club.getId(), club.getName());
         ClubCreateDTO clubSrc = new ClubCreateDTO(club.getName());
         updateTest(club, clubDTO, clubSrc, clubRepository, clubService);
     }

    @Test
    void remove(){
        Club club = new Club("Tenis");
        removeTest(club, clubRepository, clubService);
    }
}