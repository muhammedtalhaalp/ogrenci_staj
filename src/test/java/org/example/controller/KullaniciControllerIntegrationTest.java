package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Kullanici;
import org.example.entity.Rol;
import org.example.repository.KullaniciRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class KullaniciControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private KullaniciRepository kullaniciRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        kullaniciRepository.deleteAll();
    }

    @Test
    void kullaniciEkle_Basarili() throws Exception {
        Kullanici kullanici = new Kullanici();
        kullanici.setEmail("entegrasyon@test.com");
        kullanici.setSifre("123456");
        kullanici.setRol(Rol.OGRENCI);

        mockMvc.perform(post("/api/kullanicilar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(kullanici)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("entegrasyon@test.com"));
    }

    @Test
    void tumKullanicilariGetir_Basarili() throws Exception {
        Kullanici kullanici = new Kullanici();
        kullanici.setEmail("getir@test.com");
        kullanici.setSifre("123456");
        kullanici.setRol(Rol.ISVEREN);
        kullaniciRepository.save(kullanici);

        mockMvc.perform(get("/api/kullanicilar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("getir@test.com"));
    }
}
