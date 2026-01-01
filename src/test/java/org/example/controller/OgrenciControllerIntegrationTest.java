package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Kullanici;
import org.example.entity.Ogrenci;
import org.example.entity.Rol;
import org.example.repository.KullaniciRepository;
import org.example.repository.OgrenciRepository;
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
public class OgrenciControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OgrenciRepository ogrenciRepository;

    @Autowired
    private KullaniciRepository kullaniciRepository;

    private Kullanici kullanici;

    @BeforeEach
    void setUp() {
        ogrenciRepository.deleteAll();
        kullaniciRepository.deleteAll();

        kullanici = new Kullanici();
        kullanici.setEmail("ogrenci@test.com");
        kullanici.setSifre("123");
        kullanici.setRol(Rol.OGRENCI);
        kullanici = kullaniciRepository.save(kullanici);
    }

    @Test
    void ogrenciEkle_Basarili() throws Exception {
        Ogrenci ogrenci = new Ogrenci();
        ogrenci.setAd("Ali");
        ogrenci.setSoyad("Veli");
        ogrenci.setKullanici(kullanici);

        mockMvc.perform(post("/api/ogrenciler")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ogrenci)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ad").value("Ali"));
    }

    @Test
    void tumOgrencileriGetir_Basarili() throws Exception {
        Ogrenci ogrenci = new Ogrenci();
        ogrenci.setAd("Ayşe");
        ogrenci.setKullanici(kullanici);
        ogrenciRepository.save(ogrenci);

        mockMvc.perform(get("/api/ogrenciler"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ad").value("Ayşe"));
    }
}
