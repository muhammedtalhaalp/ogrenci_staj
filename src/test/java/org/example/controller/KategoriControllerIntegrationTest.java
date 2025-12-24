package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Kategori;
import org.example.repository.KategoriRepository;
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
public class KategoriControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KategoriRepository kategoriRepository;

    @BeforeEach
    void setUp() {
        kategoriRepository.deleteAll();
    }

    @Test
    void kategoriEkle_Basarili() throws Exception {
        Kategori kategori = new Kategori();
        kategori.setAd("Yeni Kategori");

        mockMvc.perform(post("/api/kategoriler")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(kategori)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ad").value("Yeni Kategori"));
    }

    @Test
    void tumKategorileriGetir_Basarili() throws Exception {
        Kategori kategori = new Kategori();
        kategori.setAd("Getir Kategori");
        kategoriRepository.save(kategori);

        mockMvc.perform(get("/api/kategoriler"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ad").value("Getir Kategori"));
    }

    @Test
    void idIleKategoriGetir_Basarili() throws Exception {
        Kategori kategori = new Kategori();
        kategori.setAd("Tekil Kategori");
        kategori = kategoriRepository.save(kategori);

        mockMvc.perform(get("/api/kategoriler/" + kategori.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ad").value("Tekil Kategori"));
    }

    @Test
    void kategoriSil_Basarili() throws Exception {
        Kategori kategori = new Kategori();
        kategori.setAd("Silinecek Kategori");
        kategori = kategoriRepository.save(kategori);

        mockMvc.perform(delete("/api/kategoriler/" + kategori.getId()))
                .andExpect(status().isNoContent());
    }
}
