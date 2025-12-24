package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "bildirim")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bildirim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "kullanici_id", referencedColumnName = "id", nullable = false)
    private Kullanici kullanici;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mesaj;

    @Column(name = "okundu_mu")
    private Boolean okunduMu = false;

    @CreationTimestamp
    @Column(name = "olusturma_tarihi", updatable = false)
    private LocalDateTime olusturmaTarihi;
}
