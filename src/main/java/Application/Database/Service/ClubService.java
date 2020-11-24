package Application.Database.Service;

import Application.Database.DTO.ClubCreateDTO;
import Application.Database.DTO.ClubDTO;
import Application.Database.Enity.Club;
import Application.Exceptions.InstanceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClubService extends BaseService implements ClubServiceInterface {

    @Override
    public Optional<Club> findById(Integer id){
        return  clubRepository.findById(id);
    }

    @Override
    public Optional<ClubDTO> findByIdAsDTO(Integer id){
        return toDTO(findById(id));
    }

    @Override
    public Optional<Club> findByName(String name)
    {
        return clubRepository.findByName(name);
    }

    @Override
    public Page<ClubDTO> findAll(Pageable pageable){
        return new PageImpl<>(clubRepository.findAll(pageable).stream()
                .map(this::toDTO).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public ClubDTO create(ClubCreateDTO clubSrc){
        Club club = new Club();
        club.setName(clubSrc.getName());
        return toDTO(clubRepository.save(club));
    }

    @Override
    @Transactional
    public ClubDTO update(Integer id, ClubCreateDTO clubSrc) throws InstanceNotFoundException {
        Club club = getIfExists(id, clubRepository);
        club.setName(clubSrc.getName());
        return toDTO(club);
    }

    private ClubDTO toDTO(Club club){
        return new ClubDTO(club.getId(), club.getName());
    }

    private Optional<ClubDTO> toDTO(Optional<Club> club){
        if(club.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(club.get()));
    }
}
