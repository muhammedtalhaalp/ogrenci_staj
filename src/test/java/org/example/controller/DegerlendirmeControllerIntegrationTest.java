package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.*;
import org.example.repository.*;
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
public class DegerlendirmeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired private DegerlendirmeRepository degerlendirmeRepository;
    @Autowired private OgrenciRepository ogrenciRepository;
    @Autowired private FirmaRepository firmaRepository;
    @Autowired private KullaniciRepository kullaniciRepository;

    private Ogrenci ogrenci;
    private Firma firma;

    @BeforeEach
    void setUp() {
        degerlendirmeRepository.deleteAll();
        ogrenciRepository.deleteAll();
        firmaRepository.deleteAll();
        kullaniciRepository.deleteAll();

        Kullanici k1 = kullaniciRepository.save(new Kullanici(null, "ogr@deger.com", "123", Rol.OGRENCI, true, null));
        ogrenci = ogrenciRepository.save(new Ogrenci(null, k1, "Ali", "Veli", "Uni", "CS", 4));

        firma = firmaRepository.save(new Firma(null, "Firma Deger", "IT", "Adres", 10));
    }

    @Test
    void degerlendirmeEkle_Basarili() throws Exception {
        Degerlendirme degerlendirme = new Degerlendirme();
        degerlendirme.setOgrenci(ogrenci);
        degerlendirme.setFirma(firma);
        degerlendirme.setPuan(5);
        degerlendirme.setYorum("Harika");

        mockMvc.perform(post("/api/degerlendirmeler")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(degerlendirme)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.puan").value(5));
    }

    @Test
    void tumDegerlendirmeleriGetir_Basarili() throws Exception {
        Degerlendirme degerlendirme = new Degerlendirme();
        degerlendirme.setOgrenci(ogrenci);
        degerlendirme.setFirma(firma);
        degerlendirme.setPuan(4);
        degerlendirmeRepository.save(degerlendirme);

        mockMvc.perform(get("/api/degerlendirmeler"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].puan").value(4));
    }
}
