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
        kullanici.setSifre("password");
        kullanici.setRol(Rol.OGRENCI);
    }

    @Test
    void tumKullanicilariGetir_Basarili() {
        when(kullaniciRepository.findAll()).thenReturn(Arrays.asList(kullanici));

        List<Kullanici> sonuc = kullaniciService.tumKullanicilariGetir();

        assertNotNull(sonuc);
        assertEquals(1, sonuc.size());
        assertEquals(kullanici.getEmail(), sonuc.get(0).getEmail());
        verify(kullaniciRepository, times(1)).findAll();
    }

    @Test
    void idIleKullaniciGetir_MevcutId_KullaniciDoner() {
        when(kullaniciRepository.findById(1L)).thenReturn(Optional.of(kullanici));

        Optional<Kullanici> sonuc = kullaniciService.idIleKullaniciGetir(1L);

        assertTrue(sonuc.isPresent());
        assertEquals(kullanici.getId(), sonuc.get().getId());
    }

    @Test
    void kullaniciKaydet_Basarili() {
        when(kullaniciRepository.save(any(Kullanici.class))).thenReturn(kullanici);

        Kullanici kaydedilen = kullaniciService.kullaniciKaydet(kullanici);

        assertNotNull(kaydedilen);
        assertEquals("test@example.com", kaydedilen.getEmail());
        verify(kullaniciRepository, times(1)).save(kullanici);
    }
}
