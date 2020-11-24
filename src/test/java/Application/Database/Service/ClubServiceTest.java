package Application.Database.Service;

import Application.Database.DTO.ClubCreateDTO;
import Application.Database.DTO.ClubDTO;
import Application.Database.Enity.Club;
import Application.Database.Repository.ClubRepository;
import Application.Exceptions.InstanceNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.internal.hamcrest.HamcrestArgumentMatcher;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class ClubServiceTest {

    @Autowired
    private ClubService clubService;

    @MockBean
    private ClubRepository clubRepository;

    @Test
    void findById() {
        Club club = new Club("Tenis club");
        BDDMockito.given(clubRepository.findById(club.getId())).willReturn(Optional.of(club));
        Assertions.assertEquals(Optional.of(club), clubService.findById(club.getId()));
        Mockito.verify(clubRepository, Mockito.atLeastOnce()).findById(club.getId());
    }

    @Test
    void findByIdAsDTO() {
        Club club = new Club("Tennis");
        ClubDTO clubDTO = new ClubDTO(club.getId(), club.getName());
        BDDMockito.given(clubRepository.findById(club.getId())).willReturn(Optional.of(club));
        //Club clubDTO2 = clubService.findByIdAsDTO(club.getId());
        Optional<ClubDTO> clubDTO2 = clubService.findByIdAsDTO(club.getId());
        if(clubDTO2.isPresent())
            Assertions.assertEquals(clubDTO, clubDTO2.get());
        Mockito.verify(clubRepository, Mockito.atLeastOnce()).findById(club.getId());


    }

    @Test
    void findByName() {
        Club club = new Club("Tenis club");
        BDDMockito.given(clubRepository.findByName(club.getName())).willReturn(Optional.of(club));
        Assertions.assertEquals(Optional.of(club), clubService.findByName(club.getName()));
        Mockito.verify(clubRepository, Mockito.atLeastOnce()).findByName(club.getName());
    }

    @Test
    void findAll() {
        List<Club> clubs = new ArrayList<>();
        List<ClubDTO> clubDTOs = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Club tmp = new Club("Club no." + i);
            clubs.add(tmp);
            clubDTOs.add(new ClubDTO(tmp.getId(), tmp.getName()));
        }
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<Club> page = new PageImpl<>(clubs);
        BDDMockito.given(clubRepository.findAll(pageRequest)).willReturn(page);
        Assertions.assertArrayEquals(clubDTOs.toArray(), clubService.findAll(pageRequest).stream().toArray());
        Mockito.verify(clubRepository, Mockito.atLeastOnce()).findAll(pageRequest);
    }

    @Test
    void create() {
        Club club = new Club("Tenis");
        ClubDTO clubDTO = new ClubDTO(club.getId(), club.getName());
        ClubCreateDTO clubSrc = new ClubCreateDTO(club.getName());
        BDDMockito.when(clubRepository.save(any(Club.class))).thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return invocation.getArguments()[0];
            }
        });
        Assertions.assertEquals(clubDTO, clubService.create(clubSrc));
        Mockito.verify(clubRepository, Mockito.atLeastOnce()).save(any(Club.class));
    }

    @Test
    void update() {
        Club club = new Club("Tenis");
        ClubDTO clubDTO = new ClubDTO(club.getId(), club.getName());
        ClubCreateDTO clubSrc = new ClubCreateDTO(club.getName());
        BDDMockito.given(clubRepository.findById(club.getId())).willReturn(Optional.of(club));
        try {
            Assertions.assertEquals(clubDTO, clubService.update(club.getId(), clubSrc));
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            Assertions.fail();
        }
        Mockito.verify(clubRepository, Mockito.atLeastOnce()).findById(club.getId());
    }
}