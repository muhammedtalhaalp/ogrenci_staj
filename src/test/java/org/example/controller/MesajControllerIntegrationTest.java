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
public class MesajControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired private MesajRepository mesajRepository;
    @Autowired private SohbetRepository sohbetRepository;
    @Autowired private OgrenciRepository ogrenciRepository;
    @Autowired private IsverenRepository isverenRepository;
    @Autowired private KullaniciRepository kullaniciRepository;
    @Autowired private FirmaRepository firmaRepository;

    private Sohbet sohbet;
    private Kullanici gonderen;

    @BeforeEach
    void setUp() {
        mesajRepository.deleteAll();
        sohbetRepository.deleteAll();
        ogrenciRepository.deleteAll();
        isverenRepository.deleteAll();
        kullaniciRepository.deleteAll();
        firmaRepository.deleteAll();

        gonderen = kullaniciRepository.save(new Kullanici(null, "gonderen@mesaj.com", "123", Rol.OGRENCI, true, null));
        Ogrenci ogrenci = ogrenciRepository.save(new Ogrenci(null, gonderen, "Ali", "Veli", "Uni", "CS", 4));

        Firma f = firmaRepository.save(new Firma(null, "Firma Mesaj", "IT", "Adres", 10));
        Kullanici k2 = kullaniciRepository.save(new Kullanici(null, "alici@mesaj.com", "123", Rol.ISVEREN, true, null));
        Isveren isveren = isverenRepository.save(new Isveren(null, k2, f, "Patron", "Bey"));

        sohbet = sohbetRepository.save(new Sohbet(null, ogrenci, isveren, null));
    }

    @Test
    void mesajEkle_Basarili() throws Exception {
        Mesaj mesaj = new Mesaj();
        mesaj.setSohbet(sohbet);
        mesaj.setGonderen(gonderen);
        mesaj.setIcerik("Merhaba");

        mockMvc.perform(post("/api/mesajlar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mesaj)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.icerik").value("Merhaba"));
    }

    @Test
    void tumMesajlariGetir_Basarili() throws Exception {
        Mesaj mesaj = new Mesaj(null, sohbet, gonderen, "Selam", null, false);
        mesajRepository.save(mesaj);

        mockMvc.perform(get("/api/mesajlar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].icerik").value("Selam"));
    }
}
