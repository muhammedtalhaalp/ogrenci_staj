package org.example.service;

import org.example.entity.Degerlendirme;
import org.example.entity.Firma;
import org.example.entity.Ogrenci;
import org.example.repository.DegerlendirmeRepository;
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
class DegerlendirmeServiceTest {

    @Mock
    private DegerlendirmeRepository degerlendirmeRepository;

    @InjectMocks
    private DegerlendirmeService degerlendirmeService;

    private Degerlendirme degerlendirme;

    @BeforeEach
    void setUp() {
        degerlendirme = new Degerlendirme();
        degerlendirme.setId(1L);
        degerlendirme.setPuan(5);
        degerlendirme.setYorum("Harika stajdı");
        degerlendirme.setOgrenci(new Ogrenci());
        degerlendirme.setFirma(new Firma());
    }

    @Test
    void tumDegerlendirmeleriGetir_Basarili() {
        when(degerlendirmeRepository.findAll()).thenReturn(Arrays.asList(degerlendirme));
        List<Degerlendirme> sonuc = degerlendirmeService.tumDegerlendirmeleriGetir();
        assertEquals(1, sonuc.size());
    }

    @Test
    void idIleDegerlendirmeGetir_Basarili() {
        when(degerlendirmeRepository.findById(1L)).thenReturn(Optional.of(degerlendirme));
        Optional<Degerlendirme> sonuc = degerlendirmeService.idIleDegerlendirmeGetir(1L);
        assertTrue(sonuc.isPresent());
    }

    @Test
    void degerlendirmeKaydet_Basarili() {
        when(degerlendirmeRepository.save(any(Degerlendirme.class))).thenReturn(degerlendirme);
        Degerlendirme kaydedilen = degerlendirmeService.degerlendirmeKaydet(degerlendirme);
        assertEquals(5, kaydedilen.getPuan());
    }
}
