package com.example.merchandise.controllers;

import com.example.merchandise.DataMerchandise;
import com.example.merchandise.database.entities.Merchandise;
import com.example.merchandise.models.MerchandiseDto;
import com.example.merchandise.models.MerchandisePageableDto;
import com.example.merchandise.models.MerchandiseToSaveDto;
import com.example.merchandise.models.MerchandiseToUpdateDto;
import com.example.merchandise.services.MerchandiseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MerchandiseController.class)
class MerchandiseControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    MerchandiseService service;
    DataMerchandise data;

    @BeforeEach
    void setUp() {
        data = new DataMerchandise();
    }

    @Test
    @DisplayName("get All pageable OK")
    void getAll() throws Exception {
        //given
        List<MerchandisePageableDto> merchandisePageableDtos = data.getMerchandisePageableDtos();
        Map<String, Object> response = new HashMap<>();
        response.put("content", merchandisePageableDtos);
        response.put("totalElements", 10);
        //when
        when(service.getAll(anyInt(), anyInt())).thenReturn(response);
        //then
        mockMvc.perform(get("/api/v1/merchandise?page=0&size=5").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.size()").value(5))
                .andExpect(jsonPath("$.totalElements").value(10));
    }

    @Test
    @DisplayName("get All by name")
    void getAllByName() throws Exception {
        //given
        List<Merchandise> filter = data.getMerchandises().stream().filter(m -> m.getName().contains("X")).toList();
        MerchandisePageableDto merchandisePageableDto = MerchandisePageableDto.builder()
                .id(filter.get(0).getId())
                .name(filter.get(0).getName())
                .amount(filter.get(0).getAmount())
                .dateEntry(filter.get(0).getDateEntry())
                .build();
        //when
        when(service.getByNameContainingIgnoreCase("X")).thenReturn(List.of(merchandisePageableDto));
        //then
        mockMvc.perform(get("/api/v1/merchandise/name/X").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$.[0].name").value("Iphone X"));
    }


    @Test
    @DisplayName("get by id  OK")
    void getByIdOk() throws Exception {
        //given
        long userId = 1;
        MerchandiseDto merchandiseDto = data.getMerchandiseDto();
        //when
        when(service.getById(userId)).thenReturn(merchandiseDto);
        //then
        mockMvc.perform(get("/api/v1/merchandise/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Iphone X"))
                .andExpect(jsonPath("$.registeredByName").value("Juan Garcia"));
    }

    @Test
    @DisplayName("get by id No found")
    void getByIdNoFound() throws Exception {
        //given
        long userId = 100;
        //when
        when(service.getById(userId)).thenReturn(null);
        //then
        mockMvc.perform(get("/api/v1/merchandise/100").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("test save invalid merchandise")
    public void testSaveInvalidMerchandise() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        MerchandiseToSaveDto merchandiseToSaveDto = data.getMerchandiseToSaveDto();
        merchandiseToSaveDto.setAmount(-1);//invalid amount

        //then
        mockMvc.perform(post("/api/v1/merchandise").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(merchandiseToSaveDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("test save merchandise")
    public void testSave() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        MerchandiseToSaveDto merchandiseToSaveDto = data.getMerchandiseToSaveDto();
        MerchandiseDto merchandiseDtoSaved = data.getMerchandiseDtoSaved();
        //when
        when(service.save(merchandiseToSaveDto)).thenReturn(merchandiseDtoSaved);
        //then
        mockMvc.perform(post("/api/v1/merchandise").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(merchandiseToSaveDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("test update invalid merchandise id")
    public void testUpdateInvalidMerchandiseId() throws Exception {
        //given
        long merchandiseId=100;
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        MerchandiseToUpdateDto merchandiseToUpdateDto = data.getMerchandiseToUpdateDto();
        //when
        when(service.getById(merchandiseId)).thenReturn(null);
        //then
        mockMvc.perform(put("/api/v1/merchandise/100").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(merchandiseToUpdateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("test update invalid merchandise")
    public void testUpdateInvalidMerchandise() throws Exception {
        //given
        long merchandiseId=1;
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        MerchandiseToUpdateDto merchandiseToUpdateDto = data.getMerchandiseToUpdateDto();

        merchandiseToUpdateDto.setAmount(-1L);
        merchandiseToUpdateDto.setDateEntry(LocalDate.now().plusMonths(5));

        //when
        when(service.getById(merchandiseId)).thenReturn(data.getMerchandiseDto());
        //then
        mockMvc.perform(put("/api/v1/merchandise/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(merchandiseToUpdateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("test update merchandise")
    public void testUpdate() throws Exception {
        //given
        long merchandiseId=1;
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        MerchandiseToUpdateDto merchandiseToUpdateDto = data.getMerchandiseToUpdateDto();
        MerchandiseDto merchandiseUpdatedDto = data.getMerchandiseUpdatedDto();
        //when
        when(service.getById(merchandiseId)).thenReturn(data.getMerchandiseDto());
        when(service.update(merchandiseId, merchandiseToUpdateDto)).thenReturn(merchandiseUpdatedDto);
        //then
        mockMvc.perform(put("/api/v1/merchandise/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(merchandiseToUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Nokia 3000"));
    }

    @Test
    @DisplayName("test delete, invalid merchandise id")
    public void testDeleteInvalid() throws Exception {
        //given
        long merchandiseId=1;
        //when
        when(service.getById(merchandiseId)).thenReturn(null);
        //then
        mockMvc.perform(delete("/api/v1/merchandise/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("test delete, invalid merchandise id")
    public void testDelete() throws Exception {
        //given
        long merchandiseId=1;
        MerchandiseDto merchandiseDto = data.getMerchandiseDto();
        //when
        when(service.getById(merchandiseId)).thenReturn(merchandiseDto);
        //then
        mockMvc.perform(delete("/api/v1/merchandise/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(service).delete(merchandiseId);
    }
}

