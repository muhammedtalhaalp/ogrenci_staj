package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Firma;
import org.example.entity.Isveren;
import org.example.entity.Kullanici;
import org.example.entity.Rol;
import org.example.repository.FirmaRepository;
import org.example.repository.IsverenRepository;
import org.example.repository.KullaniciRepository;
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
public class IsverenControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IsverenRepository isverenRepository;
    @Autowired
    private KullaniciRepository kullaniciRepository;
    @Autowired
    private FirmaRepository firmaRepository;

    private Kullanici kullanici;
    private Firma firma;

    @BeforeEach
    void setUp() {
        isverenRepository.deleteAll();
        kullaniciRepository.deleteAll();
        firmaRepository.deleteAll();

        kullanici = new Kullanici();
        kullanici.setEmail("isveren@test.com");
        kullanici.setSifre("123");
        kullanici.setRol(Rol.ISVEREN);
        kullanici = kullaniciRepository.save(kullanici);

        firma = new Firma();
        firma.setAd("Test Firma");
        firma = firmaRepository.save(firma);
    }

    @Test
    void isverenEkle_Basarili() throws Exception {
        Isveren isveren = new Isveren();
        isveren.setAd("Mehmet");
        isveren.setSoyad("Yılmaz");
        isveren.setKullanici(kullanici);
        isveren.setFirma(firma);

        mockMvc.perform(post("/api/isverenler")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(isveren)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ad").value("Mehmet"));
    }

    @Test
    void tumIsverenleriGetir_Basarili() throws Exception {
        Isveren isveren = new Isveren();
        isveren.setAd("Ahmet");
        isveren.setKullanici(kullanici);
        isveren.setFirma(firma);
        isverenRepository.save(isveren);

        mockMvc.perform(get("/api/isverenler"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ad").value("Ahmet"));
    }
}
