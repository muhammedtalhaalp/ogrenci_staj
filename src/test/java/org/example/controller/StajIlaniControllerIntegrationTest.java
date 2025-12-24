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
@Transactional // Test sonrası verileri geri alır (Rollback)
public class StajIlaniControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StajIlaniRepository stajIlaniRepository;
    @Autowired
    private FirmaRepository firmaRepository;
    @Autowired
    private IsverenRepository isverenRepository;
    @Autowired
    private KategoriRepository kategoriRepository;
    @Autowired
    private KullaniciRepository kullaniciRepository;

    private Firma firma;
    private Isveren isveren;
    private Kategori kategori;

    @BeforeEach
    void setUp() {
        // İlişkili verileri oluştur
        firma = new Firma();
        firma.setAd("Test Firma");
        firma = firmaRepository.save(firma);

        Kullanici kullanici = new Kullanici();
        kullanici.setEmail("isveren@test.com");
        kullanici.setSifre("pass");
        kullanici.setRol(Rol.ISVEREN);
        kullanici = kullaniciRepository.save(kullanici);

        isveren = new Isveren();
        isveren.setAd("Ahmet");
        isveren.setSoyad("Yılmaz");
        isveren.setFirma(firma);
        isveren.setKullanici(kullanici);
        isveren = isverenRepository.save(isveren);

        kategori = new Kategori();
        kategori.setAd("Yazılım");
        kategori = kategoriRepository.save(kategori);
    }

    @Test
    void stajIlaniEkle_Basarili() throws Exception {
        StajIlani stajIlani = new StajIlani();
        stajIlani.setBaslik("Entegrasyon Test İlanı");
        stajIlani.setAciklama("Detaylı açıklama");
        stajIlani.setFirma(firma);
        stajIlani.setIsveren(isveren);
        stajIlani.setKategori(kategori);
        stajIlani.setSehir("Ankara");

        mockMvc.perform(post("/api/staj-ilanlari")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stajIlani)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.baslik").value("Entegrasyon Test İlanı"))
                .andExpect(jsonPath("$.sehir").value("Ankara"));
    }

    @Test
    void tumStajIlanlariniGetir_Basarili() throws Exception {
        StajIlani stajIlani = new StajIlani();
        stajIlani.setBaslik("Getir Test İlanı");
        stajIlani.setAciklama("Açıklama");
        stajIlani.setFirma(firma);
        stajIlani.setIsveren(isveren);
        stajIlani.setKategori(kategori);
        stajIlaniRepository.save(stajIlani);

        mockMvc.perform(get("/api/staj-ilanlari"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].baslik").value("Getir Test İlanı"));
    }
}
