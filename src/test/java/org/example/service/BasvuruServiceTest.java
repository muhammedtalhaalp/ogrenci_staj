package org.example.service;

import org.example.entity.*;
import org.example.repository.BasvuruRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        basvuru.setId(100L);
        basvuru.setDurum(BasvuruDurum.PENDING);
        
        // Dummy nesneler
        basvuru.setOgrenci(new Ogrenci());
        basvuru.setStajIlani(new StajIlani());
        basvuru.setCv(new Cv());
    }

    @Test
    void tumBasvurulariGetir_Basarili() {
        when(basvuruRepository.findAll()).thenReturn(Arrays.asList(basvuru));

        List<Basvuru> sonuc = basvuruService.tumBasvurulariGetir();

        assertNotNull(sonuc);
        assertFalse(sonuc.isEmpty());
        assertEquals(BasvuruDurum.PENDING, sonuc.get(0).getDurum());
    }

    @Test
    void basvuruKaydet_Basarili() {
        when(basvuruRepository.save(any(Basvuru.class))).thenReturn(basvuru);

        Basvuru kaydedilen = basvuruService.basvuruKaydet(basvuru);

        assertEquals(100L, kaydedilen.getId());
        verify(basvuruRepository).save(basvuru);
    }
}
