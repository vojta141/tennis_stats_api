package Application.Database.Controller;

import Application.Database.Enity.Tournament;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RootController {
    @GetMapping
    public RepresentationModel rootWithLinks() {
        return new RepresentationModel().add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClubController.class).all(0, 10)).withRel("clubs"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DoublesController.class).all(0, 10)).withRel("doubles"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PlayerController.class).all(0, 10)).withRel("players"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SinglesController.class).all(0, 10)).withRel("singles"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TournamentController.class).all(0, 10)).withRel("tournaments"),
                WebMvcLinkBuilder.linkTo(this.getClass()).withSelfRel()
        );
    }
}
