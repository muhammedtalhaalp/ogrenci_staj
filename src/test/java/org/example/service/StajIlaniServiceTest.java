package org.example.service;

import org.example.entity.Firma;
import org.example.entity.Isveren;
import org.example.entity.Kategori;
import org.example.entity.StajIlani;
import org.example.repository.StajIlaniRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StajIlaniServiceTest {

    @Mock
    private StajIlaniRepository stajIlaniRepository;

    @InjectMocks
    private StajIlaniService stajIlaniService;

    private StajIlani stajIlani;

    @BeforeEach
    void setUp() {
        stajIlani = new StajIlani();
        stajIlani.setId(1L);
        stajIlani.setBaslik("Java Developer Stajyer");
        stajIlani.setAciklama("Spring Boot bilen stajyer aranıyor.");
        stajIlani.setSehir("İstanbul");
        stajIlani.setCalismaTuru("Hibrit");
        stajIlani.setIlanTarihi(LocalDate.now());
        stajIlani.setAktifMi(true);
        
        // İlişkili entity'leri mocklamaya gerek yok, null olabilir veya dummy nesne atanabilir
        stajIlani.setFirma(new Firma());
        stajIlani.setIsveren(new Isveren());
        stajIlani.setKategori(new Kategori());
    }

    @Test
    void tumStajIlanlariniGetir_Basarili() {
        when(stajIlaniRepository.findAll()).thenReturn(Arrays.asList(stajIlani));

        List<StajIlani> sonuc = stajIlaniService.tumStajIlanlariniGetir();

        assertNotNull(sonuc);
        assertEquals(1, sonuc.size());
        assertEquals("Java Developer Stajyer", sonuc.get(0).getBaslik());
        verify(stajIlaniRepository, times(1)).findAll();
    }

    @Test
    void idIleStajIlaniGetir_MevcutId_IlanDoner() {
        when(stajIlaniRepository.findById(1L)).thenReturn(Optional.of(stajIlani));

        Optional<StajIlani> sonuc = stajIlaniService.idIleStajIlaniGetir(1L);

        assertTrue(sonuc.isPresent());
        assertEquals(stajIlani.getId(), sonuc.get().getId());
    }

    @Test
    void stajIlaniKaydet_Basarili() {
        when(stajIlaniRepository.save(any(StajIlani.class))).thenReturn(stajIlani);

        StajIlani kaydedilen = stajIlaniService.stajIlaniKaydet(stajIlani);

        assertNotNull(kaydedilen);
        assertEquals("Java Developer Stajyer", kaydedilen.getBaslik());
        verify(stajIlaniRepository, times(1)).save(stajIlani);
    }

    @Test
    void stajIlaniSil_Basarili() {
        doNothing().when(stajIlaniRepository).deleteById(1L);

        stajIlaniService.stajIlaniSil(1L);

        verify(stajIlaniRepository, times(1)).deleteById(1L);
    }
}
