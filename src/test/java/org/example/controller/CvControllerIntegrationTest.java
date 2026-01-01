package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Cv;
import org.example.entity.Kullanici;
import org.example.entity.Ogrenci;
import org.example.entity.Rol;
import org.example.repository.CvRepository;
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
public class CvControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CvRepository cvRepository;
    @Autowired
    private OgrenciRepository ogrenciRepository;
    @Autowired
    private KullaniciRepository kullaniciRepository;

    private Ogrenci ogrenci;

    @BeforeEach
    void setUp() {
        cvRepository.deleteAll();
        ogrenciRepository.deleteAll();
        kullaniciRepository.deleteAll();

        Kullanici kullanici = new Kullanici();
        kullanici.setEmail("cv@test.com");
        kullanici.setSifre("123");
        kullanici.setRol(Rol.OGRENCI);
        kullanici = kullaniciRepository.save(kullanici);

        ogrenci = new Ogrenci();
        ogrenci.setAd("Ali");
        ogrenci.setKullanici(kullanici);
        ogrenci = ogrenciRepository.save(ogrenci);
    }

    @Test
    void cvEkle_Basarili() throws Exception {
        Cv cv = new Cv();
        cv.setDosyaUrl("http://test.com/cv.pdf");
        cv.setOgrenci(ogrenci);

        mockMvc.perform(post("/api/cvler")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cv)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dosyaUrl").value("http://test.com/cv.pdf"));
    }

    @Test
    void tumCvleriGetir_Basarili() throws Exception {
        Cv cv = new Cv();
        cv.setDosyaUrl("http://test.com/cv2.pdf");
        cv.setOgrenci(ogrenci);
        cvRepository.save(cv);

        mockMvc.perform(get("/api/cvler"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dosyaUrl").value("http://test.com/cv2.pdf"));
    }
}
