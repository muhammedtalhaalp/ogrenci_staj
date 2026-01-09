package org.example.service;

import org.example.entity.Basvuru;
import org.example.entity.BasvuruDurum;
import org.example.entity.Cv;
import org.example.entity.Ogrenci;
import org.example.entity.StajIlani;
import org.example.repository.BasvuruRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasvuruServiceTest {

    @Mock
    private BasvuruRepository basvuruRepository;

    @InjectMocks
    private BasvuruService basvuruService;

    private Basvuru basvuru;

    @BeforeEach
    void setUp() {
        basvuru = new Basvuru();
        basvuru.setId(1L);
        basvuru.setOgrenci(new Ogrenci());
        basvuru.setStajIlani(new StajIlani());
        basvuru.setCv(new Cv());
        basvuru.setDurum(BasvuruDurum.PENDING);
    }

    @Test
    void tumBasvurulariGetir_Basarili() {
        when(basvuruRepository.findAll()).thenReturn(Arrays.asList(basvuru));
        List<Basvuru> sonuc = basvuruService.tumBasvurulariGetir();
        assertEquals(1, sonuc.size());
        assertEquals(BasvuruDurum.PENDING, sonuc.get(0).getDurum());
    }

    @Test
    void basvuruKaydet_Basarili() {
        when(basvuruRepository.save(any(Basvuru.class))).thenReturn(basvuru);
        Basvuru kaydedilen = basvuruService.basvuruKaydet(basvuru);
        assertNotNull(kaydedilen);
        assertEquals(BasvuruDurum.PENDING, kaydedilen.getDurum());
    }
}
