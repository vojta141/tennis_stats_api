package Application.Database.Controller;

import Application.Database.DTO.ClubCreateDTO;
import Application.Database.DTO.ClubDTO;
import Application.Database.DTO.PlayerDTO;
import Application.Database.DTOAssemblers.ClubDTOAssembler;
import Application.Database.DTOAssemblers.PlayerDTOAssembler;
import Application.Database.Enity.Club;
import Application.Database.Service.ClubService;
import Application.Database.Service.PlayerService;
import Application.Database.Service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/club")
public class ClubController extends BaseController<Club, ClubCreateDTO, ClubDTO>{

    private final ClubService clubService;
    private final PlayerService playerService;
    private final ClubDTOAssembler clubDTOAssembler;
    private final PlayerDTOAssembler playerDTOAssembler;
    private final PagedResourcesAssembler<Club> pagedResourcesAssembler;


    @Autowired
    public ClubController(ServiceInterface<Club, ClubCreateDTO, Integer> service,
                          RepresentationModelAssembler<Club, ClubDTO> assembler,
                          ClubService clubService, PlayerService playerService,
                          ClubDTOAssembler clubDTOAssembler,
                          PlayerDTOAssembler playerDTOAssembler,
                          PagedResourcesAssembler<Club> pagedResourcesAssembler) {
        super(service, assembler);
        this.clubService = clubService;
        this.playerService = playerService;
        this.clubDTOAssembler = clubDTOAssembler;
        this.playerDTOAssembler = playerDTOAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/all")
    public PagedModel<ClubDTO> all(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<Club> clubs = clubService.findAll(PageRequest.of(page, size));
        System.out.println(clubs.getTotalElements());
        return pagedResourcesAssembler.toModel(clubs, clubDTOAssembler);
    }

    @GetMapping("/{clubId}/players")
    public List<PlayerDTO> clubPlayers(@PathVariable int clubId){
        return playerService.findAllByClubId(clubId).stream().map(playerDTOAssembler::toModel).collect(Collectors.toList());
    }
}
