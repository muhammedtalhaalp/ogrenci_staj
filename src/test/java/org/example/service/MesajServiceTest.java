package org.example.service;

import org.example.entity.Kullanici;
import org.example.entity.Mesaj;
import org.example.entity.Sohbet;
import org.example.repository.MesajRepository;
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
class MesajServiceTest {

    @Mock
    private MesajRepository mesajRepository;

    @InjectMocks
    private MesajService mesajService;

    private Mesaj mesaj;

    @BeforeEach
    void setUp() {
        mesaj = new Mesaj();
        mesaj.setId(1L);
        mesaj.setIcerik("Merhaba");
        mesaj.setSohbet(new Sohbet());
        mesaj.setGonderen(new Kullanici());
    }

    @Test
    void tumMesajlariGetir_Basarili() {
        when(mesajRepository.findAll()).thenReturn(Arrays.asList(mesaj));
        List<Mesaj> sonuc = mesajService.tumMesajlariGetir();
        assertEquals(1, sonuc.size());
    }

    @Test
    void idIleMesajGetir_Basarili() {
        when(mesajRepository.findById(1L)).thenReturn(Optional.of(mesaj));
        Optional<Mesaj> sonuc = mesajService.idIleMesajGetir(1L);
        assertTrue(sonuc.isPresent());
    }

    @Test
    void mesajKaydet_Basarili() {
        when(mesajRepository.save(any(Mesaj.class))).thenReturn(mesaj);
        Mesaj kaydedilen = mesajService.mesajKaydet(mesaj);
        assertEquals("Merhaba", kaydedilen.getIcerik());
    }
}
