package Application.Database.Service;

import Application.Database.DTO.PlayerCreateDTO;
import Application.Database.DTO.PlayerDTO;
import Application.Database.Enity.*;
import Application.Database.Enity.Player;
import Application.Database.Repository.ClubRepository;
import Application.Database.Repository.PlayerRepository;
import Application.Database.Repository.PlayerRepository;
import Application.Database.Repository.TournamentRepository;
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
public class PlayerServiceTest extends ServiceTest{
    @Autowired
    private PlayerService playerService;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private ClubRepository clubRepository;

    @MockBean
    private TournamentRepository tournamentRepository;

    private Player player;
    private PlayerDTO playerDTO;
    private PlayerCreateDTO playerCreateDTO;

    @BeforeEach
    public void init(){
        Set<Tournament> tournamentList = new HashSet<>();
        for(int i = 0; i < 3; i++) {
            Tournament tmp = Mockito.mock(Tournament.class);
            Mockito.when(tmp.getId()).thenReturn(i+1);
            tournamentList.add(tmp);
        }
        Club clubMock = Mockito.mock(Club.class);
        Mockito.when(clubMock.getId()).thenReturn(1);
        player = new Player("Player Test", new Date(), 15, clubMock, tournamentList);
        playerDTO = new PlayerDTO(player.getId(), player.getName(), player.getBirthDate(),
                player.getBigPoints(), player.getClub().getId(), player.getTournaments().stream().
                map(BaseEntity::getId).collect(Collectors.toSet()));
        playerCreateDTO = new PlayerCreateDTO(player.getName(), player.getBirthDate(),
                player.getBigPoints(), player.getClub().getId(), player.getTournaments().stream().
                map(BaseEntity::getId).collect(Collectors.toSet()));
    }
    @Test
    void findById(){
        findByIdTest(player, playerRepository, playerService);
    }


    @Test
    void findAll(){
        List<Player> playerList = new ArrayList<>();
        List<PlayerDTO> playerDTOs = new ArrayList<>();
        playerList.add(player);
        playerDTOs.add(playerDTO);
        findAllTest(playerList, playerDTOs, playerRepository, playerService);
    }

    @Test
    void create(){
        for(Tournament tournament : player.getTournaments()) {
            BDDMockito.given(tournamentRepository.findById(tournament.getId()))
                    .willReturn(Optional.of(tournament));
        }
        BDDMockito.given(clubRepository.findById(player.getClub().getId()))
                .willReturn(Optional.of(player.getClub()));
        createTest(player, playerDTO, playerCreateDTO, playerRepository, playerService);
    }

    @Test
    void update(){

        for(Tournament tournament : player.getTournaments()) {
            BDDMockito.given(tournamentRepository.findById(tournament.getId()))
                    .willReturn(Optional.of(tournament));
        }
        BDDMockito.given(clubRepository.findById(player.getClub().getId()))
                .willReturn(Optional.of(player.getClub()));
        updateTest(player, playerDTO, playerCreateDTO, playerRepository, playerService);
    }

    @Test
    void updateFail(){
        //DUPLICIT TOURNAMENT ID
        Set<Tournament> tournaments = player.getTournaments();
        Mockito.when(tournaments.iterator().next().getId()).thenReturn(2);
        for(Tournament tournament : player.getTournaments()) {
            BDDMockito.given(tournamentRepository.findById(tournament.getId()))
                    .willReturn(Optional.of(tournament));
        }
        updateTestFail(player, playerDTO, playerCreateDTO, playerRepository, playerService);
    }

    @Test
    void findAllByClubId(){
        int clubId = 1;
        BDDMockito.when(player.getClub().getId()).thenReturn(clubId);
        BDDMockito.given(playerRepository.findAllByClubId(clubId)).willReturn(Collections.singletonList(player));
        Assertions.assertEquals(Collections.singletonList(player), playerService.findAllByClubId(clubId));
        Mockito.verify(playerRepository, Mockito.atLeastOnce()).findAllByClubId(clubId);
    }

    @Test
    void remove(){
        removeTest(player, playerRepository, playerService);
    }
}
