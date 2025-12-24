package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "cv")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ogrenci_id", referencedColumnName = "id", nullable = false)
    private Ogrenci ogrenci;

    @Column(name = "dosya_url", nullable = false, columnDefinition = "TEXT")
    private String dosyaUrl;

    @Column(columnDefinition = "TEXT")
    private String aciklama;

    @CreationTimestamp
    @Column(name = "olusturma_tarihi", updatable = false)
    private LocalDateTime olusturmaTarihi;
}
