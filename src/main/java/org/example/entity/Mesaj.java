package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "mesaj")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mesaj {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sohbet_id", referencedColumnName = "id", nullable = false)
    private Sohbet sohbet;

    @ManyToOne
    @JoinColumn(name = "gonderen_id", referencedColumnName = "id", nullable = false)
    private Kullanici gonderen;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String icerik;

    @CreationTimestamp
    @Column(name = "gonderim_tarihi", updatable = false)
    private LocalDateTime gonderimTarihi;

    @Column(name = "okundu_mu")
    private Boolean okunduMu = false;
}
