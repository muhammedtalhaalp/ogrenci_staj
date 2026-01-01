package org.example.service;

import org.example.entity.Cv;
import org.example.entity.Ogrenci;
import org.example.repository.CvRepository;
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
class CvServiceTest {

    @Mock
    private CvRepository cvRepository;

    @InjectMocks
    private CvService cvService;

    private Cv cv;

    @BeforeEach
    void setUp() {
        cv = new Cv();
        cv.setId(1L);
        cv.setDosyaUrl("http://cv.com/ali.pdf");
        cv.setOgrenci(new Ogrenci());
    }

    @Test
    void tumCvleriGetir_Basarili() {
        when(cvRepository.findAll()).thenReturn(Arrays.asList(cv));
        List<Cv> sonuc = cvService.tumCvleriGetir();
        assertEquals(1, sonuc.size());
    }

    @Test
    void idIleCvGetir_Basarili() {
        when(cvRepository.findById(1L)).thenReturn(Optional.of(cv));
        Optional<Cv> sonuc = cvService.idIleCvGetir(1L);
        assertTrue(sonuc.isPresent());
    }

    @Test
    void cvKaydet_Basarili() {
        when(cvRepository.save(any(Cv.class))).thenReturn(cv);
        Cv kaydedilen = cvService.cvKaydet(cv);
        assertEquals("http://cv.com/ali.pdf", kaydedilen.getDosyaUrl());
    }
}
