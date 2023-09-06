package com.example.merchandise.controllers;

import com.example.merchandise.DataMerchandise;
import com.example.merchandise.models.MerchandiseDto;
import com.example.merchandise.models.MerchandisePageableDto;
import com.example.merchandise.models.MerchandiseToSaveDto;
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

import java.util.Collections;
import java.util.List;

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
        //when
        when(service.getAll(anyInt(), anyInt())).thenReturn(merchandisePageableDtos);
        //then
        mockMvc.perform(get("/api/v1/merchandise?page=0&size=5").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Iphone X"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("Iphone 11"))
                .andExpect(jsonPath("$[2].id").value("3"))
                .andExpect(jsonPath("$[2].name").value("Iphone 12"));
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
}

