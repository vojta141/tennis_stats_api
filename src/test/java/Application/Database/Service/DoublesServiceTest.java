package Application.Database.Service;

import Application.Database.DTO.DoublesCreateDTO;
import Application.Database.DTO.DoublesDTO;
import Application.Database.Enity.BaseEntity;
import Application.Database.Enity.Doubles;
import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;
import Application.Database.Repository.DoublesRepository;
import Application.Database.Repository.PlayerRepository;
import Application.Database.Repository.TournamentRepository;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.hibernate.metamodel.model.domain.BasicDomainType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DoublesServiceTest extends ServiceTest{

    @Autowired
    private DoublesService doublesService;

    @MockBean
    private DoublesRepository doublesRepository;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private TournamentRepository tournamentRepository;

    private Doubles doubles;
    private DoublesDTO doublesDTO;
    private DoublesCreateDTO doublesCreateDTO;

    @BeforeEach
    public void init(){
        List<Player> players = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            Player tmp = Mockito.mock(Player.class);
            Mockito.when(tmp.getId()).thenReturn(i);
            players.add(tmp);
        }
        Tournament tournamentMock = Mockito.mock(Tournament.class);
        Mockito.when(tournamentMock.getId()).thenReturn(1);
        doubles = new Doubles("6:0, 6:0", players.get(0), players.get(1), players.get(2),
                players.get(3), tournamentMock);
        doublesDTO = new DoublesDTO(doubles.getId(), doubles.getScore(), doubles.getWinner1().getId(),
                doubles.getWinner2().getId(), doubles.getLoser1().getId(), doubles.getLoser2().getId(),
                doubles.getTournament().getId());
        doublesCreateDTO = new DoublesCreateDTO(doubles.getScore(), doubles.getWinner1().getId(),
                doubles.getWinner2().getId(), doubles.getLoser1().getId(), doubles.getLoser2().getId(),
                doubles.getTournament().getId());
    }
    @Test
    void findById(){
        findByIdTest(doubles, doublesRepository, doublesService);
    }

    @Test
    void findAll(){
        List<Doubles> doublesList = new ArrayList<>();
        List<DoublesDTO> doublesDTOs = new ArrayList<>();
        doublesList.add(doubles);
        doublesDTOs.add(doublesDTO);
        findAllTest(doublesList, doublesDTOs, doublesRepository, doublesService);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create(){
        BDDMockito.given(playerRepository.findById(doubles.getWinner1().getId())).
                willReturn(Optional.of(doubles.getWinner1()));
        BDDMockito.given(playerRepository.findById(doubles.getWinner2().getId())).
                willReturn(Optional.of(doubles.getWinner2()));
        BDDMockito.given(playerRepository.findById(doubles.getLoser1().getId())).
                willReturn(Optional.of(doubles.getLoser1()));
        BDDMockito.given(playerRepository.findById(doubles.getLoser2().getId())).
                willReturn(Optional.of(doubles.getLoser2()));
        BDDMockito.given(tournamentRepository.findById(doubles.getTournament().getId())).
                willReturn(Optional.of(doubles.getTournament()));
        createTest(doubles, doublesDTO, doublesCreateDTO, doublesRepository, doublesService);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update(){
        BDDMockito.given(playerRepository.findById(doubles.getWinner1().getId())).
                willReturn(Optional.of(doubles.getWinner1()));
        BDDMockito.given(playerRepository.findById(doubles.getWinner2().getId())).
                willReturn(Optional.of(doubles.getWinner2()));
        BDDMockito.given(playerRepository.findById(doubles.getLoser1().getId())).
                willReturn(Optional.of(doubles.getLoser1()));
        BDDMockito.given(playerRepository.findById(doubles.getLoser2().getId())).
                willReturn(Optional.of(doubles.getLoser2()));
        BDDMockito.given(tournamentRepository.findById(doubles.getTournament().getId())).
                willReturn(Optional.of(doubles.getTournament()));
        updateTest(doubles, doublesDTO, doublesCreateDTO, doublesRepository, doublesService);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateFail(){
        //MISSING PLAYER ID
        BDDMockito.given(playerRepository.findById(doubles.getLoser1().getId())).
                willReturn(Optional.of(doubles.getLoser1()));
        BDDMockito.given(playerRepository.findById(doubles.getLoser2().getId())).
                willReturn(Optional.of(doubles.getLoser2()));
        BDDMockito.given(tournamentRepository.findById(doubles.getTournament().getId())).
                willReturn(Optional.of(doubles.getTournament()));
        updateTestFail(doubles, doublesDTO, doublesCreateDTO, doublesRepository, doublesService);
        BDDMockito.given(playerRepository.findById(doubles.getWinner1().getId())).
                willReturn(Optional.of(doubles.getWinner1()));
        updateTestFail(doubles, doublesDTO, doublesCreateDTO, doublesRepository, doublesService);
        //DUPLICIT VALUES
        Mockito.when(doubles.getWinner1().getId()).thenReturn(2);
        updateTestFail(doubles, doublesDTO, doublesCreateDTO, doublesRepository, doublesService);
    }

    @Test
    void findByWinnerId() {
        List<Doubles> doublesList = Collections.singletonList(doubles);
        BDDMockito.given(doublesRepository.findByWinner(doubles.getWinner1().getId())).willReturn(Arrays.asList(doubles));
        Assertions.assertEquals(doublesList, doublesService.findByWinnerId(doubles.getWinner1().getId()));
        Mockito.verify(doublesRepository, Mockito.atLeastOnce()).findByWinner(doubles.getWinner1().getId());
    }


    @Test
    void findByLoserId() {
        List<Doubles> doublesList = Collections.singletonList(doubles);
        BDDMockito.given(doublesRepository.findByLoser(doubles.getLoser1().getId())).willReturn(Arrays.asList(doubles));
        Assertions.assertEquals(doublesList, doublesService.findByLoserId(doubles.getLoser1().getId()));
        Mockito.verify(doublesRepository, Mockito.atLeastOnce()).findByLoser(doubles.getLoser1().getId());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void remove(){
        removeTest(doubles, doublesRepository, doublesService);
    }

}