package Application.Database.Service;

import Application.Database.DTO.DoublesDTO;
import Application.Exceptions.InstanceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ServiceInterface<E, CDTO, DTO, KEY> {

    Optional<E> findById(KEY id);

    Optional<DTO> findByIdAsDTO(KEY id);

    Page<DTO> findAll(Pageable pageable);

    DTO create(CDTO c) throws InstanceNotFoundException;

    DTO update(KEY id, CDTO c) throws InstanceNotFoundException;

}
