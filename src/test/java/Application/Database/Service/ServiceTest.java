package Application.Database.Service;

import Application.Database.Enity.BaseEntity;
import Application.Database.Enity.Club;
import Application.Exceptions.InstanceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

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
        BDDMockito.when(repository.save(any())).thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return invocation.getArguments()[0];
            }
        });
        try {
            Assertions.assertEquals(dto, service.create(cdto));
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            Assertions.fail("Instance not found");
        }
        Mockito.verify(repository, Mockito.atLeastOnce()).save(any());
    }

    public <E, CDTO, DTO> void updateTest(E entity, DTO dto, CDTO cdto, JpaRepository<E, Integer> repository,
                                          ServiceInterface<E, CDTO, DTO, Integer> service){
        if(entity instanceof BaseEntity) {
            BDDMockito.given(repository.findById(((BaseEntity) entity).getId())).willReturn(Optional.of(entity));
            try {
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
}
