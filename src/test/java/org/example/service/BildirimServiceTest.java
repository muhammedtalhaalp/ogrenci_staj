package org.example.service;

import org.example.entity.Bildirim;
import org.example.entity.Kullanici;
import org.example.repository.BildirimRepository;
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
class BildirimServiceTest {

    @Mock
    private BildirimRepository bildirimRepository;

    @InjectMocks
    private BildirimService bildirimService;

    private Bildirim bildirim;

    @BeforeEach
    void setUp() {
        bildirim = new Bildirim();
        bildirim.setId(1L);
        bildirim.setMesaj("Yeni başvuru var");
        bildirim.setKullanici(new Kullanici());
    }

    @Test
    void tumBildirimleriGetir_Basarili() {
        when(bildirimRepository.findAll()).thenReturn(Arrays.asList(bildirim));
        List<Bildirim> sonuc = bildirimService.tumBildirimleriGetir();
        assertEquals(1, sonuc.size());
    }

    @Test
    void idIleBildirimGetir_Basarili() {
        when(bildirimRepository.findById(1L)).thenReturn(Optional.of(bildirim));
        Optional<Bildirim> sonuc = bildirimService.idIleBildirimGetir(1L);
        assertTrue(sonuc.isPresent());
    }

    @Test
    void bildirimKaydet_Basarili() {
        when(bildirimRepository.save(any(Bildirim.class))).thenReturn(bildirim);
        Bildirim kaydedilen = bildirimService.bildirimKaydet(bildirim);
        assertEquals("Yeni başvuru var", kaydedilen.getMesaj());
    }
}
