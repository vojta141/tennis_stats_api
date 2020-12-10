package Application.Database.Controller;

import Application.Database.Service.ServiceInterface;
import Application.Exceptions.InstanceNotFoundException;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public abstract class BaseController<E,CDTO, DTO extends RepresentationModel<? extends DTO>>{

    private ServiceInterface<E,CDTO,Integer> service;
    protected RepresentationModelAssembler<E, DTO> assembler;

    public BaseController(ServiceInterface<E, CDTO, Integer> service, RepresentationModelAssembler<E, DTO> assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/{id}")
    public DTO findById(@PathVariable int id){
        Optional<E> club = service.findById(id);
        if(club.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        DTO dto = assembler.toModel(club.get());
        dto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(getClass()).all(0,10)).
                withRel(IanaLinkRelations.COLLECTION));
        return dto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DTO save(@RequestBody CDTO cdto){
        try{
            return assembler.toModel(service.create(cdto));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DTO update(@RequestBody CDTO club, @PathVariable int id){
        try{
            return assembler.toModel(service.update(id, club));
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "" ,e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public void remove(@PathVariable int id){
        try{
            service.remove(id);
        } catch (InstanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "", e);
        }
    }

    public abstract PagedModel<DTO> all(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size);
}
