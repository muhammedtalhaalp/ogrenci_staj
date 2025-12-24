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
public class BasvuruControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired private BasvuruRepository basvuruRepository;
    @Autowired private OgrenciRepository ogrenciRepository;
    @Autowired private StajIlaniRepository stajIlaniRepository;
    @Autowired private CvRepository cvRepository;
    @Autowired private KullaniciRepository kullaniciRepository;
    @Autowired private FirmaRepository firmaRepository;
    @Autowired private IsverenRepository isverenRepository;
    @Autowired private KategoriRepository kategoriRepository;

    private Ogrenci ogrenci;
    private StajIlani stajIlani;
    private Cv cv;

    @BeforeEach
    void setUp() {
        // Gerekli tüm bağımlılıkları oluştur
        Kullanici k1 = kullaniciRepository.save(new Kullanici(null, "ogr@test.com", "123", Rol.OGRENCI, true, null));
        ogrenci = ogrenciRepository.save(new Ogrenci(null, k1, "Ali", "Veli", "Uni", "CS", 4));

        Firma f = firmaRepository.save(new Firma(null, "Firma A", "IT", "Adres", 10));
        Kullanici k2 = kullaniciRepository.save(new Kullanici(null, "isv@test.com", "123", Rol.ISVEREN, true, null));
        Isveren isveren = isverenRepository.save(new Isveren(null, k2, f, "Patron", "Bey"));
        Kategori kat = kategoriRepository.save(new Kategori(null, "Kat", "Desc"));
        
        stajIlani = stajIlaniRepository.save(new StajIlani(null, "Java Staj", "Desc", f, isveren, kat, "Ist", "Remote", null, null, true));
        cv = cvRepository.save(new Cv(null, ogrenci, "url", "desc", null));
    }

    @Test
    void basvuruEkle_Basarili() throws Exception {
        Basvuru basvuru = new Basvuru();
        basvuru.setOgrenci(ogrenci);
        basvuru.setStajIlani(stajIlani);
        basvuru.setCv(cv);
        basvuru.setDurum(BasvuruDurum.PENDING);

        mockMvc.perform(post("/api/basvurular")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(basvuru)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.durum").value("PENDING"));
    }

    @Test
    void tumBasvurulariGetir_Basarili() throws Exception {
        Basvuru basvuru = new Basvuru(null, ogrenci, stajIlani, cv, null, BasvuruDurum.ACCEPTED);
        basvuruRepository.save(basvuru);

        mockMvc.perform(get("/api/basvurular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].durum").value("ACCEPTED"));
    }

    @Test
    void idIleBasvuruGetir_Basarili() throws Exception {
        Basvuru basvuru = basvuruRepository.save(new Basvuru(null, ogrenci, stajIlani, cv, null, BasvuruDurum.REJECTED));

        mockMvc.perform(get("/api/basvurular/" + basvuru.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.durum").value("REJECTED"));
    }

    @Test
    void basvuruSil_Basarili() throws Exception {
        Basvuru basvuru = basvuruRepository.save(new Basvuru(null, ogrenci, stajIlani, cv, null, BasvuruDurum.PENDING));

        mockMvc.perform(delete("/api/basvurular/" + basvuru.getId()))
                .andExpect(status().isNoContent());
    }
}
