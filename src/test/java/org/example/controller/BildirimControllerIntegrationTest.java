package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Bildirim;
import org.example.entity.Kullanici;
import org.example.entity.Rol;
import org.example.repository.BildirimRepository;
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
public class BildirimControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BildirimRepository bildirimRepository;
    @Autowired
    private KullaniciRepository kullaniciRepository;

    private Kullanici kullanici;

    @BeforeEach
    void setUp() {
        bildirimRepository.deleteAll();
        kullaniciRepository.deleteAll();

        kullanici = new Kullanici();
        kullanici.setEmail("bildirim@test.com");
        kullanici.setSifre("123");
        kullanici.setRol(Rol.OGRENCI);
        kullanici = kullaniciRepository.save(kullanici);
    }

    @Test
    void bildirimEkle_Basarili() throws Exception {
        Bildirim bildirim = new Bildirim();
        bildirim.setKullanici(kullanici);
        bildirim.setMesaj("Test Bildirim");

        mockMvc.perform(post("/api/bildirimler")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bildirim)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mesaj").value("Test Bildirim"));
    }

    @Test
    void tumBildirimleriGetir_Basarili() throws Exception {
        Bildirim bildirim = new Bildirim();
        bildirim.setKullanici(kullanici);
        bildirim.setMesaj("Getir Bildirim");
        bildirimRepository.save(bildirim);

        mockMvc.perform(get("/api/bildirimler"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mesaj").value("Getir Bildirim"));
    }
}
