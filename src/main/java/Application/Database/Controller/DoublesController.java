package Application.Database.Controller;

import Application.Database.DTO.DoublesCreateDTO;
import Application.Database.DTO.DoublesDTO;
import Application.Database.DTO.PlayerDTO;
import Application.Database.DTOAssemblers.DoublesDTOAssembler;
import Application.Database.Enity.Club;
import Application.Database.Enity.Doubles;
import Application.Database.Enity.Player;
import Application.Database.Service.DoublesService;
import Application.Database.Service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/doubles")
public class DoublesController extends BaseController<Doubles, DoublesCreateDTO, DoublesDTO>{
    private final DoublesService doublesService;
    private final DoublesDTOAssembler doublesDTOAssembler;
    private final PagedResourcesAssembler<Doubles> pagedResourcesAssembler;

    @Autowired
    public DoublesController(ServiceInterface<Doubles, DoublesCreateDTO, Integer> service,
                             RepresentationModelAssembler<Doubles, DoublesDTO> assembler,
                             DoublesService doublesService, DoublesDTOAssembler doublesDTOAssembler,
                             PagedResourcesAssembler<Doubles> pagedResourcesAssembler) {
        super(service, assembler);
        this.doublesService = doublesService;
        this.doublesDTOAssembler = doublesDTOAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/winner/{id}")
    public List<DoublesDTO> getAllWhereWinner(@PathVariable int id){
           return doublesService.findByWinnerId(id).stream().map(doublesDTOAssembler::toModel).collect(Collectors.toList());
    }

    @GetMapping("/all")
    public PagedModel<DoublesDTO> all(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<Doubles> doubles = doublesService.findAll(PageRequest.of(page, size));
        return pagedResourcesAssembler.toModel(doubles, doublesDTOAssembler);
    }
}
