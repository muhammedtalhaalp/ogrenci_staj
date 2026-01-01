package org.example.service;

import org.example.entity.Firma;
import org.example.entity.Isveren;
import org.example.entity.Kullanici;
import org.example.repository.IsverenRepository;
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
class IsverenServiceTest {

    @Mock
    private IsverenRepository isverenRepository;

    @InjectMocks
    private IsverenService isverenService;

    private Isveren isveren;

    @BeforeEach
    void setUp() {
        isveren = new Isveren();
        isveren.setId(1L);
        isveren.setAd("Mehmet");
        isveren.setFirma(new Firma());
        isveren.setKullanici(new Kullanici());
    }

    @Test
    void tumIsverenleriGetir_Basarili() {
        when(isverenRepository.findAll()).thenReturn(Arrays.asList(isveren));
        List<Isveren> sonuc = isverenService.tumIsverenleriGetir();
        assertEquals(1, sonuc.size());
    }

    @Test
    void idIleIsverenGetir_Basarili() {
        when(isverenRepository.findById(1L)).thenReturn(Optional.of(isveren));
        Optional<Isveren> sonuc = isverenService.idIleIsverenGetir(1L);
        assertTrue(sonuc.isPresent());
    }

    @Test
    void isverenKaydet_Basarili() {
        when(isverenRepository.save(any(Isveren.class))).thenReturn(isveren);
        Isveren kaydedilen = isverenService.isverenKaydet(isveren);
        assertEquals("Mehmet", kaydedilen.getAd());
    }
}
