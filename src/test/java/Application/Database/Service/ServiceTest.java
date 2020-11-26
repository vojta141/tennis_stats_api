package Application.Database.Service;

import Application.Database.Enity.BaseEntity;
import Application.Database.Enity.Club;
import Application.Exceptions.InstanceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

public class ServiceTest {

    public <E , CDTO, DTO> void findByIdTest(E entity, JpaRepository<E, Integer> repository,
                                             ServiceInterface<E, CDTO, DTO, Integer> service){
        if(entity instanceof BaseEntity) {
            BDDMockito.given(repository.findById(((BaseEntity) entity).getId())).willReturn(Optional.of(entity));
            Assertions.assertEquals(Optional.of(entity), service.findById(((BaseEntity) entity).getId()));
            Mockito.verify(repository, Mockito.atLeastOnce()).findById(((BaseEntity) entity).getId());
        }
        else{
            Assertions.fail("Incorrect entity passed, all entities have to be derived from BaseEntity");
        }
    }

    public <E , CDTO, DTO> void findByIdAsDTOTest(E entity, DTO DTO1, JpaRepository<E, Integer> repository,
                                                  ServiceInterface<E, CDTO, DTO, Integer> service){
        if(entity instanceof BaseEntity) {
            BDDMockito.given(repository.findById(((BaseEntity) entity).getId())).willReturn(Optional.of(entity));
            Optional<DTO> DTO2 = service.findByIdAsDTO(((BaseEntity) entity).getId());
            if (DTO2.isPresent())
                Assertions.assertEquals(DTO1, DTO2.get());
            else
                Assertions.fail("findByIdAsDTO returned empty object");
            Mockito.verify(repository, Mockito.atLeastOnce()).findById(((BaseEntity) entity).getId());
        }
        else{
            Assertions.fail("Incorrect entity passed, all entities have to be derived from BaseEntity");
        }
    }

    public <E, CDTO, DTO> void findAllTest(List<E> entities, List<DTO> DTOs, JpaRepository<E, Integer> repository,
                                           ServiceInterface<E, CDTO, DTO, Integer> service){
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<E> page = new PageImpl<>(entities);
        BDDMockito.given(repository.findAll(pageRequest)).willReturn((page));
        Assertions.assertArrayEquals(DTOs.toArray(), service.findAll(pageRequest).stream().toArray());
        Mockito.verify(repository, Mockito.atLeastOnce()).findAll(pageRequest);
    }

    public <E, CDTO, DTO> void createTest(E entity, DTO dto, CDTO cdto, JpaRepository<E, Integer> repository,
                                          ServiceInterface<E, CDTO, DTO, Integer> service){
        if(service instanceof BaseService) {
            BDDMockito.when(repository.save(any())).thenAnswer(new Answer() {
                @Override
                public Object answer(InvocationOnMock invocation) throws Throwable {
                    return invocation.getArguments()[0];
                }
            });
            try {
                DTO tmp = service.create(cdto);
                Assertions.assertEquals(dto, service.create(cdto));
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
                                          ServiceInterface<E, CDTO, DTO, Integer> service){
        if(entity instanceof BaseEntity) {
            BDDMockito.given(repository.findById(((BaseEntity) entity).getId())).willReturn(Optional.of(entity));
            try {
                DTO tmp = service.update(((BaseEntity) entity).getId(), cdto);
                Assertions.assertEquals(dto, service.update(((BaseEntity) entity).getId(), cdto));
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
                                          ServiceInterface<E, CDTO, DTO, Integer> service){
        if(entity instanceof BaseEntity) {
            BDDMockito.given(repository.findById(((BaseEntity) entity).getId())).willReturn(Optional.of(entity));
            try {
                DTO tmp = service.update(((BaseEntity) entity).getId(), cdto);
            } catch (InstanceNotFoundException e) {
                return;
            }
            Assertions.fail();
        }
    }

    public <E, DTO, CDTO> void failFindByIdAsDTOTest(ServiceInterface<E, CDTO, DTO, Integer> service){
        Optional<DTO> dto = service.findByIdAsDTO(1);
        if(dto.isPresent())
            Assertions.fail();
    }

    public <E, CDTO, DTO> void removeTest(E entity, JpaRepository<E, Integer> repository,
                                          ServiceInterface<E, CDTO, DTO, Integer> service){
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
