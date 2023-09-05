package com.example.merchandise.controllers;

import com.example.merchandise.DataMerchandise;
import com.example.merchandise.models.MerchandisePageableDto;
import com.example.merchandise.services.MerchandiseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
        when(service.getAll(anyInt(),anyInt())).thenReturn(merchandisePageableDtos);
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
}