package Application.Database.Service;

import Application.Database.Enity.BaseEntity;
import Application.Exceptions.InstanceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.test.context.support.WithMockUser;


import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

public class ServiceTest {

    public <E , CDTO, DTO> void findByIdTest(E entity, JpaRepository<E, Integer> repository,
                                             ServiceInterface<E, CDTO, Integer> service){
        if(entity instanceof BaseEntity) {
            BDDMockito.given(repository.findById(((BaseEntity) entity).getId())).willReturn(Optional.of(entity));
            Assertions.assertEquals(Optional.of(entity), service.findById(((BaseEntity) entity).getId()));
            Mockito.verify(repository, Mockito.atLeastOnce()).findById(((BaseEntity) entity).getId());
        }
        else{
            Assertions.fail("Incorrect entity passed, all entities have to be derived from BaseEntity");
        }
    }

    public <E, CDTO, DTO> void findAllTest(List<E> entities, List<DTO> DTOs, JpaRepository<E, Integer> repository,
                                           ServiceInterface<E, CDTO, Integer> service){
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<E> page = new PageImpl<>(entities);
        BDDMockito.given(repository.findAll(pageRequest)).willReturn((page));
        Assertions.assertArrayEquals(entities.toArray(), service.findAll(pageRequest).stream().toArray());
        Mockito.verify(repository, Mockito.atLeastOnce()).findAll(pageRequest);
    }

    public <E, CDTO, DTO> void createTest(E entity, DTO dto, CDTO cdto, JpaRepository<E, Integer> repository,
                                          ServiceInterface<E, CDTO, Integer> service){
        if(service instanceof BaseService) {
            BDDMockito.given(repository.save(any())).willReturn(entity);
            try {
                E tmp = service.create(cdto);
                Assertions.assertEquals(entity, service.create(cdto));
            } catch (InstanceNotFoundException e) {
                e.printStackTrace();
                Assertions.fail("Instance not found");
            }
            Mockito.verify(repository, Mockito.atLeastOnce()).save(any());
        }
        else{
            Assertions.fail("Incorrect entity passed, all entities have to be derived from BaseEntity");
        }
    }

    public <E, CDTO, DTO> void updateTest(E entity, DTO dto, CDTO cdto, JpaRepository<E, Integer> repository,
                                          ServiceInterface<E, CDTO, Integer> service){
        if(entity instanceof BaseEntity) {
            BDDMockito.given(repository.findById(((BaseEntity) entity).getId())).willReturn(Optional.of(entity));
            try {
                E tmp = service.update(((BaseEntity) entity).getId(), cdto);
                Assertions.assertEquals(entity, service.update(((BaseEntity) entity).getId(), cdto));
            } catch (InstanceNotFoundException e) {
                e.printStackTrace();
                Assertions.fail();
            }
            Mockito.verify(repository, Mockito.atLeastOnce()).findById(((BaseEntity) entity).getId());
        }
        else{
            Assertions.fail("Incorrect entity passed, all entities have to be derived from BaseEntity");
        }
    }

    public <E, CDTO, DTO> void updateTestFail(E entity, DTO dto, CDTO cdto, JpaRepository<E, Integer> repository,
                                          ServiceInterface<E, CDTO, Integer> service){
        if(entity instanceof BaseEntity) {
            BDDMockito.given(repository.findById(((BaseEntity) entity).getId())).willReturn(Optional.of(entity));
            try {
                E tmp = service.update(((BaseEntity) entity).getId(), cdto);
            } catch (InstanceNotFoundException e) {
                return;
            }
            Assertions.fail();
        }
    }

    public <E, CDTO, DTO> void removeTest(E entity, JpaRepository<E, Integer> repository,
                                          ServiceInterface<E, CDTO, Integer> service){
        if(entity instanceof BaseEntity) {
            BDDMockito.given(repository.findById(((BaseEntity) entity).getId())).willReturn(Optional.of(entity));
            try {
                service.remove(((BaseEntity) entity).getId());
            } catch (InstanceNotFoundException e) {
                e.printStackTrace();
                Assertions.fail("Entity with given ID not found");
            }
            BDDMockito.verify(repository, Mockito.atLeastOnce()).findById(((BaseEntity) entity).getId());
            BDDMockito.verify(repository, Mockito.atLeastOnce()).delete(entity);
        }
        else {
            Assertions.fail("Incorrect entity passed, all entities have to be derived from BaseEntity");
        }
    }


}
