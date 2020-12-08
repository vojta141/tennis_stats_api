package Application.Database.Service;

import Application.Database.DTO.ClubCreateDTO;
import Application.Database.DTO.ClubDTO;
import Application.Database.Enity.Club;
import Application.Database.Enity.Player;
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
    public Optional<Club> findByName(String name)
    {
        return clubRepository.findByName(name);
    }

    @Override
    public Page<Club> findAll(Pageable pageable){
        return clubRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Club create(ClubCreateDTO clubSrc){
        Club club = new Club();
        club.setName(clubSrc.getName());
        return clubRepository.save(club);
    }

    @Override
    @Transactional
    public Club update(Integer id, ClubCreateDTO clubSrc) throws InstanceNotFoundException {
        Club club = getIfExists(id, clubRepository);
        club.setName(clubSrc.getName());
        return club;
    }

    @Override
    public void remove(Integer id) throws InstanceNotFoundException {
        Club club = getIfExists(id, clubRepository);
        clubRepository.delete(club);
    }
}
