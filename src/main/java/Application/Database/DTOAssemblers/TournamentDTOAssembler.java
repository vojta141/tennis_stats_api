package Application.Database.DTOAssemblers;

import Application.Database.Controller.ClubController;
import Application.Database.Controller.TournamentController;
import Application.Database.DTO.TournamentDTO;
import Application.Database.Enity.BaseEntity;
import Application.Database.Enity.Tournament;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TournamentDTOAssembler extends RepresentationModelAssemblerSupport<Tournament, TournamentDTO> {

    TournamentDTOAssembler(){
        super(TournamentController.class, TournamentDTO.class);
    }

    @Override
    public TournamentDTO toModel(Tournament tournament) {
        Set<Integer> singles = null;
        if(tournament.getSingles() != null)
            singles = tournament.getSingles().stream().map(BaseEntity::getId).collect(Collectors.toSet());
        Set<Integer> doubles = null;
        if(tournament.getDoubles() != null)
            doubles = tournament.getDoubles().stream().map(BaseEntity::getId).collect(Collectors.toSet());
        Set<Integer> players = null;
        if(tournament.getPlayers() != null)
            players = tournament.getPlayers().stream().map(BaseEntity::getId).collect(Collectors.toSet());
        TournamentDTO tournamentDTO = new TournamentDTO(tournament.getId(), tournament.getDate(), tournament.getName(),
                tournament.getCategory(), tournament.getClub().getId(), singles, doubles, players);
        tournamentDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TournamentController.class).findById(tournament.getId())).withSelfRel());
        tournamentDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClubController.class).findById(tournament.getClub().getId())).withRel("club"));
        tournamentDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TournamentController.class).getParticipants(tournament.getId())).withRel("participants"));
        tournamentDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TournamentController.class).getTournamentDoubles(tournament.getId(), 0, 10)).withRel("participants"));
        tournamentDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TournamentController.class).getTournamentSingles(tournament.getId(), 0, 10)).withRel("participants"));
        return tournamentDTO;
    }
}
