package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "firma")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Firma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ad;

    private String sektor;

    @Column(columnDefinition = "TEXT")
    private String adres;

    @Column(name = "calisan_sayisi")
    private Integer calisanSayisi;
}
