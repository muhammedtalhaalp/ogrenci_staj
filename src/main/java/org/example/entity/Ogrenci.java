package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ogrenci")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ogrenci {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "kullanici_id", referencedColumnName = "id", nullable = false, unique = true)
    private Kullanici kullanici;

    private String ad;

    private String soyad;

    private String universite;

    private String bolum;

    private Integer sinif;
}
