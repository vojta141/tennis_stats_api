package Application.Database.Controller;

import Application.Database.DTO.DoublesDTO;
import Application.Database.DTO.PlayerCreateDTO;
import Application.Database.DTO.PlayerDTO;
import Application.Database.DTOAssemblers.PlayerDTOAssembler;
import Application.Database.Enity.Player;
import Application.Database.Service.PlayerService;
import Application.Database.Service.ServiceInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/player")
public class PlayerController extends BaseController<Player, PlayerCreateDTO, PlayerDTO> {

    private final PlayerService playerService;
    private final PlayerDTOAssembler playerDTOAssembler;
    private final PagedResourcesAssembler<Player> pagedResourcesAssembler;

    public PlayerController(ServiceInterface<Player, PlayerCreateDTO, Integer> service,
                            RepresentationModelAssembler<Player, PlayerDTO> assembler,
                            PlayerService playerService, PlayerDTOAssembler playerDTOAssembler,
                            PagedResourcesAssembler<Player> pagedResourcesAssembler) {
        super(service, assembler);
        this.playerService = playerService;
        this.playerDTOAssembler = playerDTOAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/all")
    public PagedModel<PlayerDTO> all(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<Player> players = playerService.findAll(PageRequest.of(page, size));
        return pagedResourcesAssembler.toModel(players, playerDTOAssembler);
    }

    /*@GetMapping("/{id}/doubles")
    public PagedModel<DoublesDTO> getPlayerDoubles(@PathVariable int id){

    }*/
}
