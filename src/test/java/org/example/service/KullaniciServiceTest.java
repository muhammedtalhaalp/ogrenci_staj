package org.example.service;

import org.example.entity.Kullanici;
import org.example.entity.Rol;
import org.example.repository.KullaniciRepository;
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
class KullaniciServiceTest {

    @Mock
    private KullaniciRepository kullaniciRepository;

    @InjectMocks
    private KullaniciService kullaniciService;

    private Kullanici kullanici;

    @BeforeEach
    void setUp() {
        kullanici = new Kullanici();
        kullanici.setId(1L);
        kullanici.setEmail("test@example.com");
        kullanici.setSifre("123456");
        kullanici.setRol(Rol.OGRENCI);
        kullanici.setAktifMi(true);
    }

    @Test
    void tumKullanicilariGetir_Basarili() {
        when(kullaniciRepository.findAll()).thenReturn(Arrays.asList(kullanici));
        List<Kullanici> sonuc = kullaniciService.tumKullanicilariGetir();
        assertEquals(1, sonuc.size());
        assertEquals("test@example.com", sonuc.get(0).getEmail());
    }

    @Test
    void idIleKullaniciGetir_Basarili() {
        when(kullaniciRepository.findById(1L)).thenReturn(Optional.of(kullanici));
        Optional<Kullanici> sonuc = kullaniciService.idIleKullaniciGetir(1L);
        assertTrue(sonuc.isPresent());
        assertEquals("test@example.com", sonuc.get().getEmail());
    }

    @Test
    void kullaniciKaydet_Basarili() {
        when(kullaniciRepository.save(any(Kullanici.class))).thenReturn(kullanici);
        Kullanici kaydedilen = kullaniciService.kullaniciKaydet(kullanici);
        assertNotNull(kaydedilen);
        assertEquals("test@example.com", kaydedilen.getEmail());
    }
}
