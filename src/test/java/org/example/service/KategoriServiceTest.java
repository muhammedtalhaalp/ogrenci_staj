package org.example.service;

import org.example.entity.Kategori;
import org.example.repository.KategoriRepository;
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
class KategoriServiceTest {

    @Mock
    private KategoriRepository kategoriRepository;

    @InjectMocks
    private KategoriService kategoriService;

    private Kategori kategori;

    @BeforeEach
    void setUp() {
        kategori = new Kategori();
        kategori.setId(1L);
        kategori.setAd("Yazılım");
    }

    @Test
    void tumKategorileriGetir_Basarili() {
        when(kategoriRepository.findAll()).thenReturn(Arrays.asList(kategori));
        List<Kategori> sonuc = kategoriService.tumKategorileriGetir();
        assertEquals(1, sonuc.size());
    }

    @Test
    void idIleKategoriGetir_Basarili() {
        when(kategoriRepository.findById(1L)).thenReturn(Optional.of(kategori));
        Optional<Kategori> sonuc = kategoriService.idIleKategoriGetir(1L);
        assertTrue(sonuc.isPresent());
    }

    @Test
    void kategoriKaydet_Basarili() {
        when(kategoriRepository.save(any(Kategori.class))).thenReturn(kategori);
        Kategori kaydedilen = kategoriService.kategoriKaydet(kategori);
        assertEquals("Yazılım", kaydedilen.getAd());
    }
}
