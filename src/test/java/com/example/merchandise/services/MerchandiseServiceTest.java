package com.example.merchandise.services;

import com.example.merchandise.DataMerchandise;
import com.example.merchandise.database.entities.Merchandise;
import com.example.merchandise.database.repositories.MerchandiseRepository;
import com.example.merchandise.models.MerchandisePageableDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MerchandiseServiceTest {
    @Mock
    MerchandiseRepository repository;
    @Mock
    ModelMapper mapper;
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
        assertAll(()->{
            assertNotNull(all);
            assertEquals(5, all.size());
            assertEquals(1, all.get(0).getId());
        });
    }
}