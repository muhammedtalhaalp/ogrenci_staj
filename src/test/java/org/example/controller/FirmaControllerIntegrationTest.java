package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Firma;
import org.example.repository.FirmaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class FirmaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FirmaRepository firmaRepository;

    @BeforeEach
    void setUp() {
        firmaRepository.deleteAll();
    }

    @Test
    void firmaEkle_Basarili() throws Exception {
        Firma firma = new Firma();
        firma.setAd("Yeni Firma");
        firma.setSektor("Teknoloji");

        mockMvc.perform(post("/api/firmalar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firma)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ad").value("Yeni Firma"));
    }

    @Test
    void tumFirmalariGetir_Basarili() throws Exception {
        Firma firma = new Firma();
        firma.setAd("Getir Firma");
        firmaRepository.save(firma);

        mockMvc.perform(get("/api/firmalar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ad").value("Getir Firma"));
    }

    @Test
    void idIleFirmaGetir_Basarili() throws Exception {
        Firma firma = new Firma();
        firma.setAd("Tekil Firma");
        firma = firmaRepository.save(firma);

        mockMvc.perform(get("/api/firmalar/" + firma.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ad").value("Tekil Firma"));
    }

    @Test
    void firmaSil_Basarili() throws Exception {
        Firma firma = new Firma();
        firma.setAd("Silinecek Firma");
        firma = firmaRepository.save(firma);

        mockMvc.perform(delete("/api/firmalar/" + firma.getId()))
                .andExpect(status().isNoContent());
    }
}
