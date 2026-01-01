package org.example.service;

import org.example.entity.Isveren;
import org.example.entity.Ogrenci;
import org.example.entity.Sohbet;
import org.example.repository.SohbetRepository;
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
class SohbetServiceTest {

    @Mock
    private SohbetRepository sohbetRepository;

    @InjectMocks
    private SohbetService sohbetService;

    private Sohbet sohbet;

    @BeforeEach
    void setUp() {
        sohbet = new Sohbet();
        sohbet.setId(1L);
        sohbet.setOgrenci(new Ogrenci());
        sohbet.setIsveren(new Isveren());
    }

    @Test
    void tumSohbetleriGetir_Basarili() {
        when(sohbetRepository.findAll()).thenReturn(Arrays.asList(sohbet));
        List<Sohbet> sonuc = sohbetService.tumSohbetleriGetir();
        assertEquals(1, sonuc.size());
    }

    @Test
    void idIleSohbetGetir_Basarili() {
        when(sohbetRepository.findById(1L)).thenReturn(Optional.of(sohbet));
        Optional<Sohbet> sonuc = sohbetService.idIleSohbetGetir(1L);
        assertTrue(sonuc.isPresent());
    }

    @Test
    void sohbetKaydet_Basarili() {
        when(sohbetRepository.save(any(Sohbet.class))).thenReturn(sohbet);
        Sohbet kaydedilen = sohbetService.sohbetKaydet(sohbet);
        assertNotNull(kaydedilen);
    }
}
