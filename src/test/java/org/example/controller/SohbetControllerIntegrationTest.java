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
public class SohbetControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired private SohbetRepository sohbetRepository;
    @Autowired private OgrenciRepository ogrenciRepository;
    @Autowired private IsverenRepository isverenRepository;
    @Autowired private KullaniciRepository kullaniciRepository;
    @Autowired private FirmaRepository firmaRepository;

    private Ogrenci ogrenci;
    private Isveren isveren;

    @BeforeEach
    void setUp() {
        sohbetRepository.deleteAll();
        ogrenciRepository.deleteAll();
        isverenRepository.deleteAll();
        kullaniciRepository.deleteAll();
        firmaRepository.deleteAll();

        Kullanici k1 = kullaniciRepository.save(new Kullanici(null, "ogr@sohbet.com", "123", Rol.OGRENCI, true, null));
        ogrenci = ogrenciRepository.save(new Ogrenci(null, k1, "Ali", "Veli", "Uni", "CS", 4));

        Firma f = firmaRepository.save(new Firma(null, "Firma Sohbet", "IT", "Adres", 10));
        Kullanici k2 = kullaniciRepository.save(new Kullanici(null, "isv@sohbet.com", "123", Rol.ISVEREN, true, null));
        isveren = isverenRepository.save(new Isveren(null, k2, f, "Patron", "Bey"));
    }

    @Test
    void sohbetEkle_Basarili() throws Exception {
        Sohbet sohbet = new Sohbet();
        sohbet.setOgrenci(ogrenci);
        sohbet.setIsveren(isveren);

        mockMvc.perform(post("/api/sohbetler")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sohbet)))
                .andExpect(status().isCreated());
    }

    @Test
    void tumSohbetleriGetir_Basarili() throws Exception {
        Sohbet sohbet = new Sohbet();
        sohbet.setOgrenci(ogrenci);
        sohbet.setIsveren(isveren);
        sohbetRepository.save(sohbet);

        mockMvc.perform(get("/api/sohbetler"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }
}
