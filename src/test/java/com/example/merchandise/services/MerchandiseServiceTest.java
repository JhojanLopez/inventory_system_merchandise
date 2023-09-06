package com.example.merchandise.services;

import com.example.merchandise.DataMerchandise;
import com.example.merchandise.clients.UserClient;
import com.example.merchandise.database.entities.Merchandise;
import com.example.merchandise.database.repositories.MerchandiseRepository;
import com.example.merchandise.models.MerchandiseDto;
import com.example.merchandise.models.MerchandisePageableDto;
import com.example.merchandise.models.MerchandiseToSaveDto;
import com.example.merchandise.models.MerchandiseToUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MerchandiseServiceTest {
    @Mock
    MerchandiseRepository repository;
    @Mock
    ModelMapper mapper;
    @Mock
    UserClient userClient;
    @InjectMocks
    MerchandiseServiceImpl merchandiseService;
    DataMerchandise data;

    @BeforeEach
    void setUp() {
        data = new DataMerchandise();
    }

    @Test
    @DisplayName("test get all merchandises pageable")
    void getAll() {
        //given
        int page = 0;
        int size = 5;
        List<Merchandise> merchandises = data.getMerchandises();
        List<MerchandisePageableDto> merchandisePageableDtos = data.getMerchandisePageableDtos();
        Page<Merchandise> merchandisePage = new PageImpl<>(merchandises);

        //when
        when(repository.findAll(any(PageRequest.class))).thenReturn(merchandisePage);
        when(mapper.map(merchandises.get(0), MerchandisePageableDto.class)).thenReturn(merchandisePageableDtos.get(0));
        when(mapper.map(merchandises.get(1), MerchandisePageableDto.class)).thenReturn(merchandisePageableDtos.get(1));
        when(mapper.map(merchandises.get(2), MerchandisePageableDto.class)).thenReturn(merchandisePageableDtos.get(2));
        when(mapper.map(merchandises.get(3), MerchandisePageableDto.class)).thenReturn(merchandisePageableDtos.get(3));
        when(mapper.map(merchandises.get(4), MerchandisePageableDto.class)).thenReturn(merchandisePageableDtos.get(4));

        //then
        List<MerchandisePageableDto> all = merchandiseService.getAll(page, size);
        assertAll(() -> {
            assertNotNull(all);
            assertEquals(5, all.size());
            assertEquals(1, all.get(0).getId());
        });
    }

    @Test
    @DisplayName("test get by id merchandise")
    void getById() {
        //given
        long userId = 1;
        Merchandise merchandise = data.getMerchandise();
        MerchandiseDto merchandiseDto = data.getMerchandiseDto();
        //when
        when(repository.findById(userId)).thenReturn(Optional.of(merchandise));
        when(mapper.map(merchandise, MerchandiseDto.class)).thenReturn(merchandiseDto);
        //then
        MerchandiseDto byId = merchandiseService.getById(userId);
        assertAll(
                () -> {
                    assertNotNull(byId);
                    assertEquals(1, byId.getId());
                    assertEquals("Iphone X", byId.getName());
                    assertEquals(100, byId.getAmount());
                    assertEquals("Juan Garcia", byId.getRegisteredByName());
                }
        );
    }

    @Test
    @DisplayName("test save merchandise, error unique name")
    void saveErrorUniqueName() {
        //given
        MerchandiseToSaveDto merchandiseToSaveDto = data.getMerchandiseToSaveDto();
        //when
        when(repository.existsByName(merchandiseToSaveDto.getName())).thenReturn(true);
        //then
        assertThrows(DataIntegrityViolationException.class, () -> merchandiseService.save(merchandiseToSaveDto));
    }

    @Test
    @DisplayName("test save merchandise, error user no exist")
    void saveErrorUserNoExist() {
        //given
        MerchandiseToSaveDto merchandiseToSaveDto = data.getMerchandiseToSaveDto();
        //when
        when(repository.existsByName(merchandiseToSaveDto.getName())).thenReturn(false);
        when(userClient.existUserById(merchandiseToSaveDto.getRegisteredById())).thenReturn(false);
        //then
        assertThrows(DataIntegrityViolationException.class, () -> merchandiseService.save(merchandiseToSaveDto));
    }

    @Test
    @DisplayName("test save merchandise")
    void save() {
        //given
        MerchandiseToSaveDto merchandiseToSaveDto = data.getMerchandiseToSaveDto();
        Merchandise merchandiseToSave = data.getMerchandiseToSave();
        MerchandiseDto merchandiseDtoSaved = data.getMerchandiseDtoSaved();
        //when
        when(repository.existsByName(merchandiseToSaveDto.getName())).thenReturn(false);
        when(userClient.existUserById(merchandiseToSaveDto.getRegisteredById())).thenReturn(true);
        when(repository.save(any(Merchandise.class))).thenReturn(merchandiseToSave);
        when(mapper.map(merchandiseToSave, MerchandiseDto.class)).thenReturn(merchandiseDtoSaved);
        //then
        MerchandiseDto saved = merchandiseService.save(merchandiseToSaveDto);
        assertAll(()->{
            assertDoesNotThrow(()-> merchandiseService.save(merchandiseToSaveDto));
            assertNotNull(saved);
            assertEquals(6, saved.getId());
        });
    }

    @Test
    @DisplayName("test update merchandise, error unique name")
    void updateErrorUniqueName() {
        //given
        long merchandiseId = 1;
        MerchandiseToUpdateDto merchandiseToUpdateDto = data.getMerchandiseToUpdateDto();
        //when
        when(repository.existsByName(merchandiseToUpdateDto.getName())).thenReturn(true);
        //then
        assertThrows(DataIntegrityViolationException.class, () -> merchandiseService.update(merchandiseId,merchandiseToUpdateDto));
    }

    @Test
    @DisplayName("test update merchandise, error user no exist")
    void updateErrorUserNoExist() {
        //given
        long merchandiseId = 1;
        MerchandiseToUpdateDto merchandiseToUpdateDto = data.getMerchandiseToUpdateDto();
        //when
        when(repository.existsByName(merchandiseToUpdateDto.getName())).thenReturn(false);
        when(userClient.existUserById(merchandiseToUpdateDto.getUpdatedById())).thenReturn(false);
        //then
        assertThrows(DataIntegrityViolationException.class, () -> merchandiseService.update(merchandiseId,merchandiseToUpdateDto));
    }

    @Test
    @DisplayName("test update merchandise")
    void update() {
        //given
        long merchandiseId = 1;
        MerchandiseToUpdateDto merchandiseToUpdateDto = data.getMerchandiseToUpdateDto();
        Merchandise merchandiseToUpdate = data.getMerchandiseToUpdate();
        MerchandiseDto merchandiseUpdatedDto = data.getMerchandiseUpdatedDto();
        //when
        when(repository.existsByName(merchandiseToUpdateDto.getName())).thenReturn(false);
        when(userClient.existUserById(merchandiseToUpdateDto.getUpdatedById())).thenReturn(true);
        when(repository.findById(merchandiseId)).thenReturn(Optional.of(merchandiseToUpdate));
        when(repository.save(any(Merchandise.class))).thenReturn(merchandiseToUpdate);
        when(mapper.map(merchandiseToUpdate,MerchandiseDto.class)).thenReturn(merchandiseUpdatedDto);
        //then
        MerchandiseDto updated = merchandiseService.update(merchandiseId,merchandiseToUpdateDto);
        assertAll(()->{
            assertDoesNotThrow(()-> merchandiseService.update(merchandiseId,merchandiseToUpdateDto));
            assertNotNull(updated);
            assertEquals("Nokia 3000", updated.getName());
        });
    }

    @Test
    @DisplayName("test delete merchandise")
    void delete() {
        //given
        long merchandiseId = 1;
        Merchandise merchandise = data.getMerchandise();
        //when
        when(repository.findById(merchandiseId)).thenReturn(Optional.of(merchandise));

        //then
        assertDoesNotThrow(()->  merchandiseService.delete(merchandiseId));
        verify(repository).delete(merchandise);
    }
}