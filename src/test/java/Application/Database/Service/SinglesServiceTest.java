package Application.Database.Service;

import Application.Database.DTO.SinglesCreateDTO;
import Application.Database.DTO.SinglesDTO;
import Application.Database.Enity.*;
import Application.Database.Repository.ClubRepository;
import Application.Database.Repository.PlayerRepository;
import Application.Database.Repository.SinglesRepository;
import Application.Database.Repository.TournamentRepository;
import Application.Exceptions.InstanceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
public class SinglesServiceTest extends ServiceTest{
    @Autowired
    private SinglesService singlesService;

    @MockBean
    private SinglesRepository singlesRepository;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private TournamentRepository tournamentRepository;

    private Singles singles;
    private SinglesDTO singlesDTO;
    private SinglesCreateDTO singlesCreateDTO;

    @BeforeEach
    public void init(){
        Player winner = Mockito.mock(Player.class);
        Player loser = Mockito.mock(Player.class);
        Mockito.when(winner.getId()).thenReturn(1);
        Mockito.when(loser.getId()).thenReturn(2);
        Tournament tournament = Mockito.mock(Tournament.class);
        Mockito.when(tournament.getId()).thenReturn(1);
        Club clubMock = Mockito.mock(Club.class);
        Mockito.when(clubMock.getId()).thenReturn(1);
        singles = new Singles("6:0, 6:0", winner, loser, tournament);
        singlesDTO = new SinglesDTO(singles.getId(), singles.getScore(), singles.getWinner().getId(),
                singles.getLoser().getId(), singles.getTournament().getId());
        singlesCreateDTO = new SinglesCreateDTO(singles.getScore(), singles.getWinner().getId(),
                singles.getLoser().getId(), singles.getTournament().getId());
    }
    @Test
    void findById(){
        findByIdTest(singles, singlesRepository, singlesService);
    }

    @Test
    void findByIdAsDTO(){
        findByIdAsDTOTest(singles, singlesDTO, singlesRepository, singlesService);
    }

    @Test
    void failFindByIdAsDTO(){
        failFindByIdAsDTOTest(singlesService);
    }

    @Test
    void findAll(){
        List<Singles> singlesList = new ArrayList<>();
        List<SinglesDTO> singlesDTOs = new ArrayList<>();
        singlesList.add(singles);
        singlesDTOs.add(singlesDTO);
        findAllTest(singlesList, singlesDTOs, singlesRepository, singlesService);
    }

    @Test
    void create(){

        BDDMockito.given(tournamentRepository.findById(singles.getTournament().getId()))
                .willReturn(Optional.of(singles.getTournament()));
        BDDMockito.given(playerRepository.findById(singles.getWinner().getId()))
                .willReturn(Optional.of(singles.getWinner()));
        BDDMockito.given(playerRepository.findById(singles.getLoser().getId()))
                .willReturn(Optional.of(singles.getLoser()));
        createTest(singles, singlesDTO, singlesCreateDTO, singlesRepository, singlesService);
    }

    @Test
    void createFail(){
        SinglesCreateDTO cdto = new SinglesCreateDTO(singles.getScore(), singles.getLoser().getId(),
                singles.getLoser().getId(), singles.getTournament().getId());
        try {
            singlesService.create(cdto);
        } catch (InstanceNotFoundException e) {
            return;
        }
        Assertions.fail();
    }

    @Test
    void update(){
        BDDMockito.given(tournamentRepository.findById(singles.getTournament().getId()))
                .willReturn(Optional.of(singles.getTournament()));
        BDDMockito.given(playerRepository.findById(singles.getWinner().getId()))
                .willReturn(Optional.of(singles.getWinner()));
        BDDMockito.given(playerRepository.findById(singles.getLoser().getId()))
                .willReturn(Optional.of(singles.getLoser()));
        updateTest(singles, singlesDTO, singlesCreateDTO, singlesRepository, singlesService);
    }

    @Test
    void updateFail(){
        //WINNER AND LOSER IDENTICAL
        BDDMockito.given(tournamentRepository.findById(singles.getTournament().getId()))
                .willReturn(Optional.of(singles.getTournament()));
        singles.setWinner(singles.getLoser());
        singlesCreateDTO.setWinner(singlesCreateDTO.getLoser());
        BDDMockito.given(playerRepository.findById(singles.getWinner().getId()))
                .willReturn(Optional.of(singles.getWinner()));
        BDDMockito.given(playerRepository.findById(singles.getLoser().getId()))
                .willReturn(Optional.of(singles.getLoser()));
        updateTestFail(singles, singlesDTO, singlesCreateDTO, singlesRepository, singlesService);
    }

    @Test
    void remove(){
        removeTest(singles, singlesRepository, singlesService);
    }
}
