package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "staj_ilani")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StajIlani {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String baslik;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String aciklama;

    @ManyToOne
    @JoinColumn(name = "firma_id", referencedColumnName = "id", nullable = false)
    private Firma firma;

    @ManyToOne
    @JoinColumn(name = "isveren_id", referencedColumnName = "id", nullable = false)
    private Isveren isveren;

    @ManyToOne
    @JoinColumn(name = "kategori_id", referencedColumnName = "id", nullable = false)
    private Kategori kategori;

    private String sehir;

    @Column(name = "calisma_turu")
    private String calismaTuru;

    @CreationTimestamp
    @Column(name = "ilan_tarihi", updatable = false)
    private LocalDate ilanTarihi;

    @Column(name = "son_basvuru_tarihi")
    private LocalDate sonBasvuruTarihi;

    @Column(name = "aktif_mi")
    private Boolean aktifMi = true;
}
