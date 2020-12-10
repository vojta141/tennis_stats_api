package Application.Database.Controller;

import Application.Database.DTO.ClubCreateDTO;
import Application.Database.DTO.ClubDTO;
import Application.Database.Enity.Club;
import Application.Database.Service.ServiceInterface;
import Application.Exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public class BaseController<E,CDTO, DTO extends RepresentationModel<? extends DTO>>{

    private ServiceInterface<E,CDTO,Integer> service;
    protected RepresentationModelAssembler<E, DTO> assembler;

    public BaseController(ServiceInterface<E, CDTO, Integer> service, RepresentationModelAssembler<E, DTO> assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/{id}")
    DTO findById(@PathVariable int id){
        Optional<E> club = service.findById(id);
        if(club.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return assembler.toModel(club.get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    DTO save(@RequestBody CDTO cdto){
        try{
            return assembler.toModel(service.create(cdto));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    DTO update(@RequestBody CDTO club, @PathVariable int id){
        try{
            return assembler.toModel(service.update(id, club));
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "" ,e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    void remove(@PathVariable int id){
        try{
            service.remove(id);
        } catch (InstanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "", e);
        }
    }
}
