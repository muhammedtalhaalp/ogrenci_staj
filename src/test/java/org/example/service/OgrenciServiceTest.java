package org.example.service;

import org.example.entity.Kullanici;
import org.example.entity.Ogrenci;
import org.example.repository.OgrenciRepository;
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
class OgrenciServiceTest {

    @Mock
    private OgrenciRepository ogrenciRepository;

    @InjectMocks
    private OgrenciService ogrenciService;

    private Ogrenci ogrenci;

    @BeforeEach
    void setUp() {
        ogrenci = new Ogrenci();
        ogrenci.setId(1L);
        ogrenci.setAd("Ali");
        ogrenci.setSoyad("Veli");
        ogrenci.setKullanici(new Kullanici());
    }

    @Test
    void tumOgrencileriGetir_Basarili() {
        when(ogrenciRepository.findAll()).thenReturn(Arrays.asList(ogrenci));
        List<Ogrenci> sonuc = ogrenciService.tumOgrencileriGetir();
        assertEquals(1, sonuc.size());
    }

    @Test
    void idIleOgrenciGetir_Basarili() {
        when(ogrenciRepository.findById(1L)).thenReturn(Optional.of(ogrenci));
        Optional<Ogrenci> sonuc = ogrenciService.idIleOgrenciGetir(1L);
        assertTrue(sonuc.isPresent());
    }

    @Test
    void ogrenciKaydet_Basarili() {
        when(ogrenciRepository.save(any(Ogrenci.class))).thenReturn(ogrenci);
        Ogrenci kaydedilen = ogrenciService.ogrenciKaydet(ogrenci);
        assertEquals("Ali", kaydedilen.getAd());
    }
}
