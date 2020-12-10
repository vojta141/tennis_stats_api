package Application.Database.Controller;

import Application.Database.DTO.SinglesCreateDTO;
import Application.Database.DTO.SinglesDTO;
import Application.Database.DTO.TournamentDTO;
import Application.Database.DTOAssemblers.PlayerDTOAssembler;
import Application.Database.DTOAssemblers.SinglesDTOAssembler;
import Application.Database.Enity.Club;
import Application.Database.Enity.Singles;
import Application.Database.Enity.Tournament;
import Application.Database.Service.PlayerService;
import Application.Database.Service.ServiceInterface;
import Application.Database.Service.SinglesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/singles")
public class SinglesController extends BaseController<Singles, SinglesCreateDTO, SinglesDTO> {

    private final SinglesService singlesService;
    private final SinglesDTOAssembler singlesDTOAssembler;
    private final PagedResourcesAssembler<Singles> pagedResourcesAssembler;

    public SinglesController(ServiceInterface<Singles, SinglesCreateDTO,
            Integer> service, RepresentationModelAssembler<Singles, SinglesDTO> assembler,
                             SinglesService singlesService, SinglesDTOAssembler singlesDTOAssembler,
                             PagedResourcesAssembler<Singles> pagedResourcesAssembler) {
        super(service, assembler);
        this.singlesService = singlesService;
        this.singlesDTOAssembler = singlesDTOAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/all")
    PagedModel<SinglesDTO> all(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<Singles> singles = singlesService.findAll(PageRequest.of(page, size));
        return pagedResourcesAssembler.toModel(singles, singlesDTOAssembler);
    }
}
