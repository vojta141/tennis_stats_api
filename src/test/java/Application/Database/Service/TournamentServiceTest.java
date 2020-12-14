package Application.Database.Service;

import Application.Database.DTO.TournamentCreateDTO;
import Application.Database.DTO.TournamentDTO;
import Application.Database.Enity.*;
import Application.Database.Enity.Tournament;
import Application.Database.Repository.*;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
public class TournamentServiceTest extends ServiceTest{
    @Autowired
    private TournamentService tournamentService;

    @MockBean
    private TournamentRepository tournamentRepository;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private ClubRepository clubRepository;

    @MockBean
    private SinglesRepository singlesRepository;

    @MockBean
    private DoublesRepository doublesRepository;

    private Tournament tournament;
    private TournamentDTO tournamentDTO;
    private TournamentCreateDTO tournamentCreateDTO;

    @BeforeEach
    public void init(){
        Set<Player> playerList = new HashSet<>();
        for(int i = 0; i < 4; i++){
            Player tmp = Mockito.mock(Player.class);
            BDDMockito.when(tmp.getId()).thenReturn(i+1);
            playerList.add(tmp);
        }

        Set<Singles> singlesList = new HashSet<>();
        for(int i = 0; i < 4; i++){
            Singles tmp = Mockito.mock(Singles.class);
            BDDMockito.when(tmp.getId()).thenReturn(i+1);
            singlesList.add(tmp);
        }

        Set<Doubles> doublesList = new HashSet<>();
        for(int i = 0; i < 4; i++){
            Doubles tmp = Mockito.mock(Doubles.class);
            BDDMockito.when(tmp.getId()).thenReturn(i+1);
            doublesList.add(tmp);
        }
        Club clubMock = Mockito.mock(Club.class);
        Mockito.when(clubMock.getId()).thenReturn(1);
        tournament = new Tournament(new Date(), "Random tournament", "C", clubMock, singlesList,
                doublesList, playerList);
        tournamentDTO = new TournamentDTO(tournament.getId(), tournament.getDate(), tournament.getName(),
                tournament.getCategory(), tournament.getClub().getId(),
                tournament.getSingles().stream().map(BaseEntity::getId).collect(Collectors.toSet()),
                tournament.getDoubles().stream().map(BaseEntity::getId).collect(Collectors.toSet()),
                tournament.getPlayers().stream().map(BaseEntity::getId).collect(Collectors.toSet()));
        tournamentCreateDTO = new TournamentCreateDTO(tournamentDTO.getDate(), tournamentDTO.getName(),
                tournamentDTO.getCategory(), tournamentDTO.getClubId(), tournamentDTO.getSinglesIDs(),
                tournamentDTO.getDoublesIDs(), tournamentDTO.getPlayerIDs());
    }
    @Test
    void findById(){
        findByIdTest(tournament, tournamentRepository, tournamentService);
    }

    @Test
    void findAll(){
        List<Tournament> tournamentList = new ArrayList<>();
        List<TournamentDTO> tournamentDTOs = new ArrayList<>();
        tournamentList.add(tournament);
        tournamentDTOs.add(tournamentDTO);
        findAllTest(tournamentList, tournamentDTOs, tournamentRepository, tournamentService);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create(){
        for(Singles tmp : tournament.getSingles()){
            BDDMockito.given(singlesRepository.findById(tmp.getId())).willReturn(Optional.of(tmp));
        }
        for(Doubles tmp : tournament.getDoubles()){
            BDDMockito.given(doublesRepository.findById(tmp.getId())).willReturn(Optional.of(tmp));
        }
        for(Player tmp : tournament.getPlayers()){
            BDDMockito.given(playerRepository.findById(tmp.getId())).willReturn(Optional.of(tmp));
        }
        BDDMockito.given(clubRepository.findById(tournament.getClub().getId()))
                .willReturn(Optional.of(tournament.getClub()));
        createTest(tournament, tournamentDTO, tournamentCreateDTO, tournamentRepository, tournamentService);
        tournament.setDoubles(null);
        tournament.setSingles(null);
        tournament.setPlayers(null);
        TournamentDTO dto = new TournamentDTO(tournament.getId(), tournament.getDate(), tournament.getName(),
                tournament.getCategory(), tournament.getClub().getId(), null, null, null);
        TournamentCreateDTO cdto = new TournamentCreateDTO(tournament.getDate(), tournament.getName(),
                tournament.getCategory(), tournament.getClub().getId(), null, null, null);
        createTest(tournament, dto, cdto, tournamentRepository, tournamentService);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update(){
        for(Singles tmp : tournament.getSingles()){
            BDDMockito.given(singlesRepository.findById(tmp.getId())).willReturn(Optional.of(tmp));
        }
        for(Doubles tmp : tournament.getDoubles()){
            BDDMockito.given(doublesRepository.findById(tmp.getId())).willReturn(Optional.of(tmp));
        }
        for(Player tmp : tournament.getPlayers()){
            BDDMockito.given(playerRepository.findById(tmp.getId())).willReturn(Optional.of(tmp));
        }
        BDDMockito.given(clubRepository.findById(tournament.getClub().getId()))
                .willReturn(Optional.of(tournament.getClub()));
        updateTest(tournament, tournamentDTO, tournamentCreateDTO, tournamentRepository, tournamentService);
        tournament.setDoubles(null);
        tournament.setSingles(null);
        tournament.setPlayers(null);
        TournamentDTO dto = new TournamentDTO(tournament.getId(), tournament.getDate(), tournament.getName(),
                tournament.getCategory(), tournament.getClub().getId(), null, null, null);
        TournamentCreateDTO cdto = new TournamentCreateDTO(tournament.getDate(), tournament.getName(),
                tournament.getCategory(), tournament.getClub().getId(), null, null, null);
        updateTest(tournament, dto, cdto, tournamentRepository, tournamentService);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void remove(){
        removeTest(tournament, tournamentRepository, tournamentService);
    }

    @Test
    void getParticipants(){
        BDDMockito.given(tournamentRepository.findById(tournament.getId())).willReturn(Optional.of(tournament));
        try {
            Assertions.assertEquals(tournament.getPlayers(), tournamentService.getParticipants(tournament.getId()));
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            Assertions.fail();
        }
        BDDMockito.verify(tournamentRepository, Mockito.atLeastOnce()).findById(tournament.getId());
    }

    @Test
    void findTournamentsByClubId(){
        BDDMockito.given(tournamentRepository.findAllByClubId(tournament.getClub().getId()))
                .willReturn(Collections.singletonList(tournament));
        try {
            Assertions.assertEquals(tournament, tournamentService.
                    findTournamentsByClubId(tournament.getClub().getId()).get(0));
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        }
        BDDMockito.verify(tournamentRepository, Mockito.atLeastOnce()).findAllByClubId(tournament.getClub().getId());
    }

    @Test
    void getDoubles(){
        PageRequest pageRequest = PageRequest.of(0,1);
        List<Doubles> doubles = new ArrayList<>(tournament.getDoubles());
        PageImpl<Doubles> page = new PageImpl<>(doubles, pageRequest, doubles.size());
        BDDMockito.given(tournamentRepository.findById(tournament.getId())).willReturn(Optional.of(tournament));
        try {
            Assertions.assertEquals(page, tournamentService.getDoubles(tournament.getId(), pageRequest));
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        }
        BDDMockito.verify(tournamentRepository, Mockito.atLeastOnce()).findById(tournament.getId());
    }

    @Test
    void getSingles(){
        PageRequest pageRequest = PageRequest.of(0,1);
        List<Singles> singles = new ArrayList<>(tournament.getSingles());
        PageImpl<Singles> page = new PageImpl<>(singles, pageRequest, singles.size());
        BDDMockito.given(tournamentRepository.findById(tournament.getId())).willReturn(Optional.of(tournament));
        try {
            Assertions.assertEquals(page, tournamentService.getSingles(tournament.getId(), pageRequest));
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        }
        BDDMockito.verify(tournamentRepository, Mockito.atLeastOnce()).findById(tournament.getId());
    }

    @Test
    void getDoublesOfPlayer(){
        List<Player> playerList = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            Player tmp = Mockito.mock(Player.class);
            BDDMockito.when(tmp.getId()).thenReturn(i+1);
            playerList.add(tmp);
        }
        Doubles doublesMatch = new Doubles("6:0", playerList.get(0), playerList.get(1), playerList.get(2), playerList.get(3), tournament);
        tournament.setDoubles(new HashSet<>(Collections.singletonList(doublesMatch)));
        BDDMockito.given(tournamentRepository.findById(tournament.getId())).willReturn(Optional.ofNullable(tournament));
        try {
            Assertions.assertEquals(doublesMatch, tournamentService.getDoublesOfPlayer(tournament.getId(), 1).get(0));
            Assertions.assertEquals(1, tournamentService.getDoublesOfPlayer(tournament.getId(), 1).size());
            BDDMockito.verify(tournamentRepository, Mockito.atLeastOnce()).findById(tournament.getId());
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    void getSinglesOfPlayer(){
        List<Player> playerList = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            Player tmp = Mockito.mock(Player.class);
            BDDMockito.when(tmp.getId()).thenReturn(i+1);
            playerList.add(tmp);
        }
        Singles singlesMatch = new Singles("6:0", playerList.get(0), playerList.get(1), tournament);
        tournament.setSingles(new HashSet<>(Collections.singletonList(singlesMatch)));
        BDDMockito.given(tournamentRepository.findById(tournament.getId())).willReturn(Optional.ofNullable(tournament));
        try {
            Assertions.assertEquals(singlesMatch, tournamentService.getSinglesOfPlayer(tournament.getId(), 1).get(0));
            Assertions.assertEquals(1, tournamentService.getSinglesOfPlayer(tournament.getId(), 1).size());
            BDDMockito.verify(tournamentRepository, Mockito.atLeastOnce()).findById(tournament.getId());
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        }

    }
}
