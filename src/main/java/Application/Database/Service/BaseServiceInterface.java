package Application.Database.Service;

import Application.Database.Enity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseServiceInterface {
    public <E> E getIfExists(int id, JpaRepository<E,Integer> repository) throws Exception;
}
