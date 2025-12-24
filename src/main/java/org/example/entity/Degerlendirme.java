package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "degerlendirme")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Degerlendirme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ogrenci_id", referencedColumnName = "id")
    private Ogrenci ogrenci;

    @ManyToOne
    @JoinColumn(name = "firma_id", referencedColumnName = "id")
    private Firma firma;

    private Integer puan;

    @Column(columnDefinition = "TEXT")
    private String yorum;
}
