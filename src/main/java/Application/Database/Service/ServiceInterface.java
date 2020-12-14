package Application.Database.Service;

import Application.Database.DTO.DoublesDTO;
import Application.Exceptions.InstanceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import java.util.Optional;

public interface ServiceInterface<E, CDTO, KEY> {

    Optional<E> findById(KEY id);

    Page<E> findAll(Pageable pageable);

    E create(CDTO c) throws InstanceNotFoundException;

    E update(KEY id, CDTO c) throws InstanceNotFoundException;

    void remove(KEY id) throws InstanceNotFoundException;

}
