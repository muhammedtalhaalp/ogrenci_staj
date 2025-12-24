package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "basvuru", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ogrenci_id", "staj_ilani_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Basvuru {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ogrenci_id", referencedColumnName = "id", nullable = false)
    private Ogrenci ogrenci;

    @ManyToOne
    @JoinColumn(name = "staj_ilani_id", referencedColumnName = "id", nullable = false)
    private StajIlani stajIlani;

    @ManyToOne
    @JoinColumn(name = "cv_id", referencedColumnName = "id", nullable = false)
    private Cv cv;

    @CreationTimestamp
    @Column(name = "basvuru_tarihi", updatable = false)
    private LocalDateTime basvuruTarihi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BasvuruDurum durum = BasvuruDurum.PENDING;
}
