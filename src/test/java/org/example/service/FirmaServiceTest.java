package org.example.service;

import org.example.entity.Firma;
import org.example.repository.FirmaRepository;
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
class FirmaServiceTest {

    @Mock
    private FirmaRepository firmaRepository;

    @InjectMocks
    private FirmaService firmaService;

    private Firma firma;

    @BeforeEach
    void setUp() {
        firma = new Firma();
        firma.setId(1L);
        firma.setAd("Tech Corp");
        firma.setSektor("Bilişim");
    }

    @Test
    void tumFirmalariGetir_Basarili() {
        when(firmaRepository.findAll()).thenReturn(Arrays.asList(firma));
        List<Firma> sonuc = firmaService.tumFirmalariGetir();
        assertEquals(1, sonuc.size());
    }

    @Test
    void idIleFirmaGetir_Basarili() {
        when(firmaRepository.findById(1L)).thenReturn(Optional.of(firma));
        Optional<Firma> sonuc = firmaService.idIleFirmaGetir(1L);
        assertTrue(sonuc.isPresent());
    }

    @Test
    void firmaKaydet_Basarili() {
        when(firmaRepository.save(any(Firma.class))).thenReturn(firma);
        Firma kaydedilen = firmaService.firmaKaydet(firma);
        assertEquals("Tech Corp", kaydedilen.getAd());
    }
}
